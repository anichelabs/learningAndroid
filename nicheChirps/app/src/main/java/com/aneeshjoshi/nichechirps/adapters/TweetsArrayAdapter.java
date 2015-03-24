package com.aneeshjoshi.nichechirps.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aneeshjoshi.nichechirps.R;
import com.aneeshjoshi.nichechirps.helpers.TimeUtils;
import com.aneeshjoshi.nichechirps.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{



    public class ViewHolder{
        ImageView ivProfile;
        TextView tvUsername;
        TextView tvTweet;
        TextView tvTimestamp;
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.item_tweet, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.ivProfilePic);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvTweet = (TextView) convertView.findViewById(R.id.tvTweet);
            viewHolder.tvTimestamp = (TextView) convertView.findViewById(R.id.tvTimestamp);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tweet tweet = getItem(position);
        viewHolder.ivProfile.setImageResource(android.R.color.transparent);
        Picasso.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .fit().centerInside().into(viewHolder.ivProfile);
        viewHolder.tvUsername.setText("@" + tweet.getUser().getScreenName());
        viewHolder.tvTweet.setText(tweet.getBody());
        String createdAt = tweet.getCreatedAt();
        String relativeLongString = TimeUtils.getRelativeTimeAgo(createdAt);
        viewHolder.tvTimestamp.setText(TimeUtils.getElapsedString(relativeLongString));

        return convertView;
    }




}
