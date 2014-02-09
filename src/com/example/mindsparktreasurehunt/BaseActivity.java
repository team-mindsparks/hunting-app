package com.example.mindsparktreasurehunt;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
	
	Random random;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		random = new Random(); 
	}
	
	protected void rotateViewRandomly(View view) {
		float rotation = (random.nextInt(200) - 100) / 100.0f;
	    Animation an = new RotateAnimation(0.0f, rotation, 0, 0);
	    an.setDuration(300);                 // duration in ms
	    an.setRepeatCount(0);                // -1 = infinite repeated
	    an.setRepeatMode(Animation.REVERSE); // reverses each repeat
	    an.setFillAfter(true);               // keep rotation after animation

	    // Aply animation to image view
	    view.setAnimation(an);
	}
	
	protected void setWidgetText(View widget, String text) {
		if (widget instanceof TextView) {
			TextView tv = (TextView) widget;
			tv.setText(text);
		}
	}
	
	protected void setWidgetText(View parent, int id, String text) {
		View widget = parent.findViewById(id);
		setWidgetText(widget, text);
	}
	
	protected void setWidgetText(int id, String text) {
		View widget = findViewById(id);
		setWidgetText(widget, text);
	}
	
	protected String getWidgetText(View widget) {
		if (widget instanceof TextView) {
			TextView tv = (TextView) widget;
			return tv.getText().toString();
		}
		
		if (widget instanceof Spinner) {
			Spinner s = (Spinner) widget;
			return s.getSelectedItem().toString();
		}
		return "";
	}
	
	protected String getWidgetText(int id) {
		View widget = findViewById(id);
		return getWidgetText(widget);
	}
	
	protected String getWidgetText(View parent, int id) {
		View widget = parent.findViewById(id);
		return getWidgetText(widget);
	}
	
	protected void yesNoAlert(String message, DialogInterface.OnClickListener confirmAction) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton("Yes", confirmAction)
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
}
