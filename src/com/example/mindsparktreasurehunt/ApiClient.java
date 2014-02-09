package com.example.mindsparktreasurehunt;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiClient {
	public static String BASE_URL_STRING = "http://188.226.156.181:80/";


	private static AsyncHttpClient client() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Content-Type", "application/json");
		client.addHeader("Accept", "application/json");
		return client;
	}

	public static void get(String url, RequestParams params,
			final ApiClientResponseHandler responseHandler) {
		client().get(getAbsoluteUrl(url), params,
				jsonHttpResponseHandler(responseHandler));
	}

	// TODO: instead of body, accept hashmap
	public static void post(String url, String body,
			ApiClientResponseHandler responseHandler) {
		try {
			client().post(null, getAbsoluteUrl(url), new StringEntity(body),
					"application/json",
					jsonHttpResponseHandler(responseHandler));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static JsonHttpResponseHandler jsonHttpResponseHandler(
			final ApiClientResponseHandler responseHandler) {
		return new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject object) {
				ApiClientResponse response = new ApiClientResponse(
						headers, statusCode);
				responseHandler.onSuccess(response, object);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONArray object) {
				ApiClientResponse response = new ApiClientResponse(
						headers, statusCode);
				responseHandler.onSuccess(response, object);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject object) {
				ApiClientResponse response = new ApiClientResponse(
						headers, statusCode);
				responseHandler.onFailure(response, throwable, object);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONArray object) {
				ApiClientResponse response = new ApiClientResponse(
						headers, statusCode);
				responseHandler.onSuccess(response, object);
			}
		};
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL_STRING + relativeUrl;
	}

	public interface ApiClientResponseHandler {
		public void onSuccess(ApiClientResponse response, Object object);

		public void onFailure(ApiClientResponse response,
				Throwable error, Object object);
	}

	public static class ApiClientResponse {
		private Header[] headers;
		private int statusCode;

		public ApiClientResponse(Header[] headers, int statusCode) {
			this.headers = headers;
			this.statusCode = statusCode;
		}

		public Header[] getHeaders() {
			return headers;
		}

		public int getStatusCode() {
			return statusCode;
		}
	}
}
