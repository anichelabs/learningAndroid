package com.aneeshjoshi.gridimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.aneeshjoshi.gridimagesearch.R;
import com.aneeshjoshi.gridimagesearch.helpers.NetworkChecker;
import com.aneeshjoshi.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    private static class ViewHolder {
        ImageView ivImage;
    }

    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult result = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result,
                    parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(!NetworkChecker.isNetworkAvailable(getContext())){
            Toast.makeText(getContext(), "Unable to connect to the internet", Toast.LENGTH_SHORT).show();
            return convertView;
        }
        //Clear out last image
        viewHolder.ivImage.setImageResource(0);


        Picasso.with(getContext()).load(result.thumbUrl)
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.ivImage);

        return convertView;
    }
}
