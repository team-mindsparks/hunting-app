package com.example.mindsparktreasurehunt;

import java.util.ArrayList;

import org.json.JSONArray;

import com.example.mindsparktreasurehunt.ApiClient.ApiClientResponse;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prepareViews();
		setTitle("Treasure Hunts");
		fetchHunts();
	}
	
	private void fetchHunts() {	
		ApiClient.get("/hunts", null, new ApiClient.ApiClientResponseHandler() {
			
			@Override
			public void onSuccess(ApiClientResponse response, Object object) {
				ArrayList<BaseModel> hunts = BaseModel.deserializeArray((JSONArray) object, Hunt.class);
				
				populateTreasureHunts(hunts);
			}
			
			@Override
			public void onFailure(ApiClientResponse response, Throwable error, Object object) {
				Log.e("Mindsparks", "/hunts", error);
			}
		});
	}

	private void prepareViews() {
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(listViewItemClickListener);
	}
	
	AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          final Hunt item = (Hunt) parent.getItemAtPosition(position);
          Persistence.sharedInstance.setSelectedHunt(item);
          startActivity(new Intent(MainActivity.this, HuntActivity.class));
        }
	};

	private void populateTreasureHunts(ArrayList<BaseModel> hunts) {
		ArrayAdapter<BaseModel> adapter = new ArrayAdapter<BaseModel>(this, android.R.layout.simple_list_item_1, filterHuntsWithoutClues(hunts));
		listView.setAdapter(adapter);
	}
	
	private ArrayList<BaseModel> filterHuntsWithoutClues(ArrayList<BaseModel> hunts) {
		ArrayList<BaseModel> filtered = new ArrayList<BaseModel>();
		for (BaseModel model : hunts) {
			Hunt hunt = (Hunt) model;
			if (hunt.getClues().size() > 0) {
				filtered.add(hunt);
			}
		}
		return filtered;
	}

}
