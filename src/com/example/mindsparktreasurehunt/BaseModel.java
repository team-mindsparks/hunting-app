package com.example.mindsparktreasurehunt;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;

public class BaseModel {
	
	public BaseModel() {
	}
	
	public static ArrayList<BaseModel> deserializeArray(JSONArray jsonArray, Class<?> cls) {
		ArrayList<BaseModel> models = new ArrayList<BaseModel>();
	
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				models.add(deserializeObject(jsonArray.getJSONObject(i), cls));
			} catch (JSONException e) {}
		}
		
		return models;
	}
	
	public static ArrayList<BaseModel> deserializeArray(String jsonString, Class<?> cls) {
		try {
			JSONArray jsonArray = new JSONArray(jsonString);
			return deserializeArray(jsonArray, cls);
		} catch (JSONException e) {}			
		return null;
	}
	
	public static BaseModel deserializeObject(String json, Class<?> cls) {
		try {
			return deserializeObject(new JSONObject(json), cls);
		} catch (JSONException e) {
			Log.e("BaseModel", "deserializeObject exception", e);
			return null;
		}
	}
	
	public static BaseModel deserializeObject(JSONObject object, Class<?> cls) {		
		BaseModel model;
		try {
			model = (BaseModel) cls.getConstructors()[0].newInstance();
			model.assignAttributes(object);
			return model;
		} catch (Exception e) {
			Log.e("BaseModel", "deserializeObject exception", e);
		}
		return null;
	}
	
	public void assignAttributes(JSONObject o) {
		try {
			Iterator<?> keys = o.keys();
	        while (keys.hasNext()) {
	            String key = (String) keys.next();
	            Object value = o.get(key);
	            
	            if (value instanceof JSONArray) {
	            	JSONArray jArray = (JSONArray) value;
	            	Class<?> cls = classForName(StringConverters.singularize(key));
	            	ArrayList<BaseModel> children = deserializeArray(jArray, cls);
	            	assignAttribute(key, children);
	            	
	            } else if (value instanceof JSONObject) {
	            	JSONObject jObject = (JSONObject) value;
	            	Class<?> cls = classForName(key);
					BaseModel child = deserializeObject(jObject, cls);
					assignAttribute(key, child);
					
	            } else if (value instanceof String) {
	            	assignAttribute(key, value);
				}
	        }
		} catch (Exception e) {
			Log.e("Mindspark", "assignAttributes", e);
		}
	}
	
	public static Class<?> classForName(String name) {
		try {
			return Class.forName("com.example.mindsparktreasurehunt." + StringConverters.camelCase(name));
		} catch (ClassNotFoundException e) {
			Log.e("Mindspark", "ClassNotFoundException", e);
		}
		return null;
	}
	
	public void assignAttribute(String name, Object value) {
		try {
			Field field = getClass().getDeclaredField(name); 
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			Log.e("Mindspark", "assignAttribute for " + getClass().getSimpleName(), e);
		}
	}
}
