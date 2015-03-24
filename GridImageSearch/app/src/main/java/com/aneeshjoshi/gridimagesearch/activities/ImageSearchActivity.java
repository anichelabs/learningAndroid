package com.aneeshjoshi.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.aneeshjoshi.gridimagesearch.R;
import com.aneeshjoshi.gridimagesearch.adapters.ImageResultsAdapter;
import com.aneeshjoshi.gridimagesearch.helpers.NetworkChecker;
import com.aneeshjoshi.gridimagesearch.listeners.EndlessScrollListener;
import com.aneeshjoshi.gridimagesearch.models.ImageResult;
import com.aneeshjoshi.gridimagesearch.models.Setting;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ImageSearchActivity extends ActionBarActivity {

    private static final String TAG = "ImageSearchActivity";
    private static final int SETTINGS_REQUEST = 1;
    public static final String BASE_SEARCH_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    public static final String AMPERSAND = "&";
    GridView gvSearchResults;


    private Setting currentSetting;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private String currentQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_search);
        showActivityLogo();
        setupViews();
        imageResults = Lists.newArrayList();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvSearchResults.setAdapter(aImageResults);
        currentSetting = new Setting();

        gvSearchResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(page < 8)
                    executeSearch(page);
            }
        });
    }

    private void showActivityLogo() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void setupViews() {
        gvSearchResults = (GridView) findViewById(R.id.gv_search_results);
        gvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ImageSearchActivity.this, ImageDisplayActivity.class);
                ImageResult img = aImageResults.getItem(position);
                i.putExtra("result", img);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentQuery = query;
                aImageResults.clear();
                executeSearch(0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra(SettingsActivity.INTENT_PARAM_SETTING, currentSetting);
            startActivityForResult(i, SETTINGS_REQUEST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SETTINGS_REQUEST){
            if(resultCode == RESULT_OK) {
                currentSetting = (Setting) data.getSerializableExtra(SettingsActivity.INTENT_PARAM_SETTING);
            } else {
                Toast.makeText(this, "Could not get updated settings", Toast.LENGTH_SHORT).show();;
            }
        }
    }

    private void executeSearch(int offsetPage) {
        //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = buildSearchUrl(currentQuery, offsetPage);
        if(!NetworkChecker.isNetworkAvailable(this)){
            Toast.makeText(this, "Unable to connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }
        client.get(searchUrl,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d(TAG, response.toString());
                JSONArray imageResultsJson;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));

                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing json", e);
                }
            }
        });
    }

    private String buildSearchUrl(String query, int offsetPage) {
        StringBuilder builder = new StringBuilder(BASE_SEARCH_URL);
        builder.append(query)
               .append(AMPERSAND)
               .append("rsz=8")
               .append(AMPERSAND)
               .append("start=")
               .append(offsetPage);

        if(!Strings.isNullOrEmpty(currentSetting.getSize())){
            builder.append(AMPERSAND).append("imgsz=").append(currentSetting.getSize());
        }

        if(!Strings.isNullOrEmpty(currentSetting.getColor())){
            builder.append(AMPERSAND).append("imgcolor=").append(currentSetting.getColor());
        }

        if(!Strings.isNullOrEmpty(currentSetting.getType())){
            builder.append(AMPERSAND).append("imgtype=").append(currentSetting.getType());
        }

        if(!Strings.isNullOrEmpty(currentSetting.getSite())){
            builder.append(AMPERSAND).append("as_sitesearch=").append(currentSetting.getSite());
        }
        return builder.toString();
    }
}
