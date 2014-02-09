package com.example.mindsparktreasurehunt;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
		rotateViewRandomly(row);
		
		
		if (clue.isComplete(HuntActivity.this)) {
			ImageView polaroidImageView = (ImageView) row.findViewById(R.id.polaroidImage);
			Bitmap bitmap = clue.getBitmap();
			polaroidImageView.setImageBitmap(bitmap);
		}
	}

	@Override
	protected void onItemClicked(int row) {
        final Clue clue = hunt.getClues().get(row);
        
        if (clue.isComplete(HuntActivity.this)) {
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(clue.getFact())
    			   .setTitle("Fact for " + clue.getName())
    		       .setCancelable(false)
    		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		    	   public void onClick(DialogInterface dialog, int which) {}
    				});
    		AlertDialog alert = builder.create();
    		alert.show();
        } else {
        	Persistence.sharedInstance.setSelectedClue(clue);
            Intent intent = new Intent(HuntActivity.this, ClueFindingActivity.class);
            startActivityForResult(intent, STATUS_CODE_CLUE_FOUND);
        }
        
	}
	
}
