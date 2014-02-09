package com.example.mindsparktreasurehunt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class FileSave {
	
	private static final String TAG = "MINDSPARK";
	
	public static void writeStringAsFile(Context context, final String fileContents, String fileName) {
        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    public static String readFileAsString(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
        	Log.e(TAG, "", e);
        } catch (IOException e) {
        	Log.e(TAG, "", e);
        } finally {
        	try { in.close(); } catch (IOException e) {}
        }

        return stringBuilder.toString();
    }
}
