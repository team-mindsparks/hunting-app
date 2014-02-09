package com.example.mindsparktreasurehunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HuntActivity extends AbstractListViewActivity {
	Hunt hunt;
	ListView listView;
	
	public static final int STATUS_CODE_CLUE_FOUND = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		hunt = Persistence.sharedInstance.getSelectedHunt();
		setContentView(R.layout.activity_main);
		setTitle(hunt.getTitle());
	}
	
	AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
	};

	@Override
	protected int rowCount() {
		return hunt.getClues().size();
	}

	@Override
	protected int listView() {
		return R.id.listView;
	}

	@Override
	protected int cellLayoutView() {
		return R.layout.listview_cell_polaroid;
	}

	@Override
	protected void populateRowAtPosition(int position, View row) {
		Clue clue = hunt.getClues().get(position);
		setWidgetText(row, R.id.textViewName, clue.getName());
		setWidgetText(row, R.id.textViewDescription, clue.getDescription());
		rotateViewRandomly(row);
	}

	@Override
	protected void onItemClicked(int row) {
        final Clue clue = hunt.getClues().get(row);
        Persistence.sharedInstance.setSelectedClue(clue);
       
        Intent intent = new Intent(HuntActivity.this, ClueFindingActivity.class);
        startActivityForResult(intent, STATUS_CODE_CLUE_FOUND);
	}

	
}
