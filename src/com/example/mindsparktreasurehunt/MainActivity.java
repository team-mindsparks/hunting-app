package com.example.mindsparktreasurehunt;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		populateTreasureHunts();

		final ListView lv = (ListView) findViewById(R.id.treasureHuntList);

	    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	          final Hunt item = (Hunt) parent.getItemAtPosition(position);
	          Log.e("APP", "" + item.toString());
	        }
	      });

	}
	
	
	private void populateTreasureHunts() {
		ListView lv = (ListView) findViewById(R.id.treasureHuntList);
		Type listType = new TypeToken<List<Hunt>>() {}.getType();
		List<Hunt> yourList = new Gson().fromJson(treasureHuntJson(), listType);
		
		ArrayAdapter<Hunt> adapter = new ArrayAdapter<Hunt>(this,
		        android.R.layout.simple_list_item_1, yourList);
		 lv.setAdapter(adapter);
	}
	
	private String treasureHuntJson() {
		return "[{\"name\" : \"A Museum\" },{\"name\" : \"Playground\" },{\"name\" : \"Forest Trail\" },{\"name\" : \"Playcenter\" }]";
	}

}
