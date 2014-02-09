package com.example.mindsparktreasurehunt;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Clue extends BaseModel {

	private String name;
	private String description;
	private Photo photo;
	private String uuid;
	private String fact;

	private static final String PREFS_FILE = "MindsparksClues";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return name;
	}

	public Photo getPhoto() {
		return photo;
	}

    public String imagePath() {
    	return "/storage/sdcard0/mindsparks/pictures/" + getPhoto().getUuid() + ".jpg";
    }
	
    public Bitmap getBitmap() {
    	Bitmap bitmap = null;
    	File imgFile = new File(imagePath());
    	if(imgFile.exists()){
		    bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		}
    	return bitmap;
    }
    
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFact() {
		return fact;
	}

	public void setFact(String fact) {
		this.fact = fact;
	}
	
	public boolean isComplete(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
		return prefs.getBoolean(name + uuid + "complete", false);
	}
	
	public void setComplete(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(name + uuid + "complete", true);
		editor.commit();
	}
	
}
