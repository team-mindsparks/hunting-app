package com.example.mindsparktreasurehunt;

import java.util.ArrayList;

import org.json.JSONArray;

import com.example.mindsparktreasurehunt.ApiClient.ApiClientResponse;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AbstractListViewActivity {

	ListView listView;
	ArrayList<Hunt> hunts; 
	ImageDownload imageDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hunts = new ArrayList<Hunt>();
		setContentView(R.layout.activity_main);
		setTitle("Treasure Hunts");
		fetchHunts();
	}
	
	private void fetchHunts() {	
		//start spinner here
		ApiClient.get("/hunts", null, new ApiClient.ApiClientResponseHandler() {
			@Override
			public void onSuccess(ApiClientResponse response, Object object) {
				ArrayList<BaseModel> hunts = BaseModel.deserializeArray((JSONArray) object, Hunt.class);
				MainActivity.this.hunts = Hunt.withoutClues(hunts);
				populate();
				
				final String cache = ((JSONArray) object).toString();
				
				imageDownload = new ImageDownload(MainActivity.this, hunts, new ImageDownload.ImageDownloadCompleteListener() {
					@Override
					public void downloadComplete() {
						Log.w("!!!!", "FUCKING DONE");
						FileSave.writeStringAsFile(MainActivity.this, cache, "response.json");
					}
				});
				
				imageDownload.start();
			}

			@Override
			public void onFailure(ApiClientResponse response, Throwable error, Object object) {
				Log.e("Mindsparks", "/hunts", error);
				FileSave.readFileAsString(MainActivity.this, "response.json");
				//TODO: if no cached request say "sorry, we need internets mate"
			}
		});
	}

	@Override
	protected int rowCount() {
		return hunts.size();
	}
	
	@Override
	protected int listView() {
		return R.id.listView;
	}

	@Override
	protected int cellLayoutView() {
		return R.layout.listview_cell;
	}

	@Override
	protected void populateRowAtPosition(int position, View row) {
		Hunt hunt = hunts.get(position);
		setWidgetText(row, R.id.textViewName, hunt.getTitle());
		setWidgetText(row, R.id.textViewDescription, hunt.getDescription());
		rotateViewRandomly(row);
	}

	@Override
	protected void onItemClicked(int row) {
		Hunt hunt = hunts.get(row);
        Persistence.sharedInstance.setSelectedHunt(hunt);
        startActivity(new Intent(MainActivity.this, HuntActivity.class));
	}

}
