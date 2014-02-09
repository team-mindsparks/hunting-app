package com.example.mindsparktreasurehunt;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

public class ImageDownload {
	
	Context context;
	ArrayList<Photo> photos;
	private ProgressDialog progressDialog;
	private ImageDownloadCompleteListener listener;
	
	public ImageDownload(Context c, ArrayList<BaseModel> hunts, ImageDownloadCompleteListener listener) {
		context = c;
		photos = new ArrayList<Photo>();
		this.listener = listener;
		
		for (BaseModel hunt : hunts) {
			for (Clue clue : ((Hunt) hunt).getClues()) {
				photos.add(clue.getPhoto());
			}
		}
	}
	
	public void start() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Finding treasure hunts near you...");
		progressDialog.setMax(photos.size());
		progressDialog.setIndeterminate(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.show();
		downloadImage(0);
	}
	
	private void downloadImage(final int index) {
		Log.e("Mindspark", "downloadImage " + index);
		progressDialog.setProgress(index);
		
		//update progress bar
		if (index > photos.size() - 1) {
			finishedDownloadingImages();
			return;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };
		final Photo photo = photos.get(index);
		
		File f = new File(Environment.getExternalStorageDirectory(),"mindsparks/pictures/" + photo.getUuid() + ".jpg");
		if(!f.exists()) { 
			client.get(photo.getUrl(), new BinaryHttpResponseHandler(allowedContentTypes) {
			    @Override
			    public void onSuccess(byte[] fileData) {
			    	new SavePhotoTask(photo.getUuid()).execute(fileData);
			        downloadImage(index + 1);
			    }
			});
			return;
		}
		downloadImage(index + 1);
	}
	
	class SavePhotoTask extends AsyncTask<byte[], String, String> {
		private String name;
		
		public SavePhotoTask(String name) {
			this.name = name;
		}
		
	    @Override
	    protected String doInBackground(byte[]... jpeg) {          
	      File photo = new File(Environment.getExternalStorageDirectory(), "mindsparks/pictures/" + name + ".jpg");
	      photo.mkdirs();
	      if (photo.exists()) {
	            photo.delete();
	      }

	      try {
	        FileOutputStream fos = new FileOutputStream(photo.getPath());

	        fos.write(jpeg[0]);
	        fos.close();
	      }
	      catch (java.io.IOException e) {
	        Log.e("PictureDemo", "Exception in photoCallback", e);
	      }

	      return(null);
	    }
	}
	
	private void finishedDownloadingImages() {
		progressDialog.dismiss();
		progressDialog = null;
		listener.downloadComplete();
	}
	
	public interface ImageDownloadCompleteListener {
		public void downloadComplete();
	}
	
}
