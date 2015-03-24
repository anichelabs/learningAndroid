package com.aneeshjoshi.gridimagesearch.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageResult implements Serializable {

    public static final String TAG = "ImageResult";
    public String fullUrl;
    public String thumbUrl;
    public String title;
    public int width;
    public int height;


    public ImageResult(JSONObject json){
        try {
            fullUrl = json.getString("url");
            thumbUrl = json.getString("tbUrl");
            title = json.getString("title");
            width = json.getInt("width");
            height = json.getInt("height");
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json object", e);
        }


    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray array){
        ArrayList<ImageResult> results = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            try{
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                Log.e(TAG, "Error occurred while retrieving index " + i + " from JsonArray", e);
            }

        }
        return results;
    }

    @Override
    public String toString() {
        return "ImageResult{" +
                ", title='" + title + '\'' +
                '}';
    }
}
