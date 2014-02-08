package com.example.mindsparktreasurehunt;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;
	ArrayList<BaseModel> hunts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prepareViews();
		setTitle("Treasure Hunts");
	}
	
	private void fetchHunts() {
		hunts = BaseModel.deserializeArray(treasureHuntJson(), Hunt.class);
	}

	private void prepareViews() {
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(listViewItemClickListener);
		populateTreasureHunts();
	}
	
	AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          final Hunt item = (Hunt) parent.getItemAtPosition(position);
          Persistence.sharedInstance.setSelectedHunt(item);
          startActivity(new Intent(MainActivity.this, HuntActivity.class));
        }
	};

	private void populateTreasureHunts() {
		fetchHunts();
		ArrayAdapter<BaseModel> adapter = new ArrayAdapter<BaseModel>(this, android.R.layout.simple_list_item_1, huntsWithClues());
		listView.setAdapter(adapter);
	}
	
	private ArrayList<BaseModel> huntsWithClues() {
		ArrayList<BaseModel> hunts = new ArrayList<BaseModel>();
		for (BaseModel model : this.hunts) {
			Hunt hunt = (Hunt) model;
			if (hunt.getClues().size() > 0) {
				hunts.add(hunt);
			}
		}
		return hunts;
	}
	
	private String treasureHuntJson() {
		return "[{\"name\" : \"A Museum\", \"clues\" : [{ \"name\" : \"Test Clue\" }]},{\"name\" : \"Playground\" },{\"name\" : \"Forest Trail\" },{\"name\" : \"Playcenter\" }]";
	}

}
