package com.example.mindsparktreasurehunt;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
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
		setContentView(R.layout.activity_main);
		prepareViews();
	}
	
	private void prepareViews() {
		listView = (ListView) findViewById(R.id.treasureHuntList);
		listView.setOnItemClickListener(listViewItemClickListener);
		populateTreasureHunts();
	}
	
	AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          final Hunt item = (Hunt) parent.getItemAtPosition(position);
          Log.e("APP", "" + item.toString());
        }
	};

	private void populateTreasureHunts() {
		ArrayList<BaseModel> hunts = BaseModel.deserializeArray(treasureHuntJson(), Hunt.class);
		
		ListView listView = (ListView) findViewById(R.id.treasureHuntList);
		ArrayAdapter<BaseModel> adapter = new ArrayAdapter<BaseModel>(this, android.R.layout.simple_list_item_1, hunts);
		listView.setAdapter(adapter);
	}
	
	private String treasureHuntJson() {
		return "[{\"name\" : \"A Museum\", \"clues\" : [{ \"name\" : \"Test Clue\" }]},{\"name\" : \"Playground\" },{\"name\" : \"Forest Trail\" },{\"name\" : \"Playcenter\" }]";
	}

}
