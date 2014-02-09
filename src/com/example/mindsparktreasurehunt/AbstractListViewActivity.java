package com.example.mindsparktreasurehunt;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public abstract class AbstractListViewActivity extends BaseActivity {

	abstract protected int rowCount();
	abstract protected int listView();
	abstract protected int cellLayoutView();
	abstract protected void populateRowAtPosition(int position, View row);
	abstract protected void onItemClicked(int row);
	
	@Override
	public void onResume() {
		super.onResume();
		populate();
	};
	
	protected void populate() {
		ArrayList<HashMap<String, Object>> emptyHashMaps = new ArrayList<HashMap<String,Object>>();
		ArrayList<String> emptyHeadings = new ArrayList<String>();
		int[] emptyIds = new int[rowCount()];
		String[] headings = new String[rowCount()];
		for (int i = 0; i < rowCount(); i++) {
			emptyHashMaps.add(new HashMap<String, Object>());
			emptyHeadings.add("" + i);
			emptyIds[i] = cellLayoutView();
			headings[i] = "" + i;
		}
		
        ListView list = (ListView) findViewById(listView());
		
        if (emptyHashMaps == null || emptyHashMaps.isEmpty()) {
			list.setAdapter(null);
			list.setOnItemClickListener(null);
			return;
		}
        
		SimpleAdapter adapterForList = new SimpleAdapter(this, emptyHashMaps, cellLayoutView(), headings, emptyIds) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row = super.getView(position, convertView, parent);
				populateRowAtPosition(position, row);
				return row;
			}
		};
		list.setAdapter(adapterForList);   
		list.setOnItemClickListener(listOnItemClickListener);
	}
	
	private OnItemClickListener listOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			onItemClicked(position);
		}
	};
	
}