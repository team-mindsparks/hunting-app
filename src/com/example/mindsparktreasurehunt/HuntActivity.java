package com.example.mindsparktreasurehunt;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HuntActivity extends Activity {
	Hunt hunt;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hunt = Persistence.sharedInstance.getSelectedHunt();
		setTitle(hunt.getName());
		prepareViews();
	}
	
	private void prepareViews() {
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(listViewItemClickListener);
		populateClues();
	}
	
	AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          final Clue item = (Clue) parent.getItemAtPosition(position);
          Log.e("APP", "" + item.toString());
        }
	};

	private void populateClues() {
		ArrayAdapter<Clue> adapter = new ArrayAdapter<Clue>(this, android.R.layout.simple_list_item_1, hunt.getClues());
		listView.setAdapter(adapter);
	}

	
}
