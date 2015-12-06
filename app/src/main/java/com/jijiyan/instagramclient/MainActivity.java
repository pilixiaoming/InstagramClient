package com.jijiyan.instagramclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.jijiyan.instagramclient.Adapter.InstagramPhotosAdapter;
import com.jijiyan.instagramclient.model.InstagramPhoto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {
    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private List<InstagramPhoto> photos = new ArrayList<InstagramPhoto>();
    private InstagramPhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photosAdapter = new InstagramPhotosAdapter(this,photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photosAdapter);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());

                try {
                    JSONArray photoList = response.getJSONArray("data");
                    for (int i = 0; i < photoList.length(); i++) {
                        JSONObject photoData = photoList.getJSONObject(i);
                        InstagramPhoto instagramPhoto = new InstagramPhoto();
                        if (photoData.getJSONObject("user") == null
                                || photoData.getJSONObject("caption") == null
                                || photoData.getJSONObject("images") == null
                                || photoData.getJSONObject("likes") == null) {
                            continue;
                        }
                        instagramPhoto.setUsername(photoData.getJSONObject("user").getString("username"));
                        instagramPhoto.setCaption(photoData.getJSONObject("caption").getString("text"));
                        instagramPhoto.setImageUrl(photoData.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        instagramPhoto.setImageHeight(photoData.getJSONObject("images").getJSONObject("standard_resolution").getString("height"));
                        instagramPhoto.setLikesCount(photoData.getJSONObject("likes").getInt("count"));
                        photos.add(instagramPhoto);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                photosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
