package com.aneeshjoshi.gridimagesearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aneeshjoshi.gridimagesearch.R;
import com.aneeshjoshi.gridimagesearch.helpers.NetworkChecker;
import com.aneeshjoshi.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDisplayActivity extends ActionBarActivity {


    private static final String TAG = "ImageDisplayActivity";
    private boolean isActionBarHidden = false;
    private ImageView ivImageFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        ivImageFull = (ImageView) findViewById(R.id.iv_image_full);
        ivImageFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActionBarHidden) {
                    isActionBarHidden = false;
                    getSupportActionBar().show();
                } else {
                    getSupportActionBar().hide();
                    isActionBarHidden = true;
                }
            }
        });


        Intent i = getIntent();
        ImageResult result = (ImageResult) i.getSerializableExtra("result");


        Log.d(TAG, "Data passed in was " + result.fullUrl);
        if(!NetworkChecker.isNetworkAvailable(this)){
            Toast.makeText(this, "Unable to connect to the internet", Toast.LENGTH_SHORT).show();
            return;
        }

        Picasso.with(this)
                .load(result.fullUrl)
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .into(ivImageFull);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            onShareItem();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onShareItem() {
        // Get access to bitmap image from view

        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImageFull);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Share Image");
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // ...sharing failed, handle error
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {

        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
