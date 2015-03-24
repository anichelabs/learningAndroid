package ca.aniche.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.aniche.instagramclient.models.instagram.Comment;
import ca.aniche.instagramclient.models.instagram.InstagramItem;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramItem> implements StickyListHeadersAdapter {

    public static final String TAG = "InstagramPhotosAdapter";
    public static final String COLOR_INSTA_HEART_BLUE = "#71BDEE";

    Pattern MATCH_HASHTAGS = Pattern.compile("#(\\w+|\\W+)");
    Pattern MATCH_ELAPSED_TIME = Pattern.compile("\\s(minutes\\sago|minute\\sago|" +
            "hours\\sago|hour\\sago|" +
            "day\\sago|days\\sago|" +
            "week\\sago|weeks\\sago|" +
            "month\\sago|months\\sago|" +
            "year\\sago|years\\sago)");
    private LayoutInflater inflater;

    private class ViewHolder{
        ImageView ivPhoto;
        TextView tvCaption;
        TextView tvNumLikes;
        TextView tvViewAllComments;
        TextView tvComment1;
        TextView tvComment2;
    }

    private class HeaderViewHolder {
        TextView tvTimeSincePost;
        TextView tvUsername;
        ImageView ivProfilePic;

    }

    public InstagramPhotosAdapter(Context context, List<InstagramItem> objects) {
        super(context, R.layout.instagram_item, objects);
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramItem item = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.instagram_item, parent, false);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tv_caption);
            viewHolder.tvNumLikes = (TextView) convertView.findViewById(R.id.tv_num_likes);
            viewHolder.tvComment1 = (TextView) convertView.findViewById(R.id.tv_comment1);
            viewHolder.tvComment2 = (TextView) convertView.findViewById(R.id.tv_comment2);
            viewHolder.tvViewAllComments = (TextView) convertView.findViewById(R.id.tv_view_all_comments);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivPhoto.setImageResource(0);
        //Set the picture and the caption.
        Picasso.with(getContext())
                .load(item.getImages()
                .getStandardRes()
                .getUrl())
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.ivPhoto);
        String username = item.getUser().getUsername();
        String captionText = item.getCaption() != null ? item.getCaption().getText() : "";
        captionText = styleHashTags(captionText);
        String caption = styleUserName(username)+ " " + captionText;
        long numLikes = item.getLikes().getCount();
        viewHolder.tvNumLikes.setText(numLikes + " likes");
        viewHolder.tvCaption.setText(Html.fromHtml(caption));
        setComments(item, viewHolder);

        return convertView;
    }

    private void setComments(InstagramItem item, ViewHolder viewHolder) {
        if(item.getCommentWrapper() != null && item.getCommentWrapper().getCount() > 0){
            long count = item.getCommentWrapper().getCount();
            viewHolder.tvViewAllComments.setText("View " + count + " comments");
            List<Comment> comments = item.getCommentWrapper().getComments();

            if(count > 1){
                Comment comment = comments.get(comments.size() - 2);
                String unstyledCommentText = comment.getText();
                String commentUser = comment.getFrom().getUsername();
                String elapsedString = getElapsedString(DateUtils.getRelativeTimeSpanString(comment.getCreatedTime() * 1000));
                String comment2 = styleUserName(commentUser) + " " + elapsedString + ": "
                        + styleHashTags(unstyledCommentText);
                viewHolder.tvComment2.setText(Html.fromHtml(comment2));
            } else {
                viewHolder.tvComment2.setVisibility(View.GONE);
            }
            Comment comment = comments.get(comments.size() - 1);
            String unstyledCommentText = comment.getText();
            String commentUser = comment.getFrom().getUsername();
            String elapsedString = getElapsedString(DateUtils.getRelativeTimeSpanString(comment.getCreatedTime() * 1000));
            String comment1 = styleUserName(commentUser) + " " + elapsedString + ": "
                    + styleHashTags(unstyledCommentText);

            viewHolder.tvComment1.setText(Html.fromHtml(comment1));

        } else {
            viewHolder.tvViewAllComments.setVisibility(View.GONE);
            viewHolder.tvComment1.setVisibility(View.GONE);
            viewHolder.tvComment2.setVisibility(View.GONE);
        }
    }

    private String styleUserName(String username) {
        return "<b><font color='" + COLOR_INSTA_HEART_BLUE + "'>" +username+"</font></b> ";
    }


    private String styleHashTags(String captionText) {
        Matcher mat = MATCH_HASHTAGS.matcher(captionText);
        StringBuffer sb = new StringBuffer();

        while(mat.find()){
            String text = mat.group(1);
            mat.appendReplacement(sb, "<font color='" + COLOR_INSTA_HEART_BLUE + "'>#" + text + "</font>");
        }
        mat.appendTail(sb);
        return sb.toString();
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder headerViewHolder;
        if(convertView == null){
            headerViewHolder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            headerViewHolder.tvTimeSincePost = (TextView) convertView.findViewById(R.id.tv_time_since_post);
            headerViewHolder.ivProfilePic = (ImageView) convertView.findViewById(R.id.iv_profile);
            headerViewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tv_username);
            convertView.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }

        InstagramItem item = getItem(position);
        item.getCreatedTime();
        CharSequence elapsedTime = DateUtils.getRelativeTimeSpanString(item.getCreatedTime() * 1000);
        String elapsedTimeString = getElapsedString(elapsedTime);
        headerViewHolder.tvTimeSincePost.setText(elapsedTimeString);
        headerViewHolder.tvUsername.setText(item.getUser().getUsername());
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.with(getContext())
                .load(item.getUser().getProfilePicUrl())
                .fit()
                .transform(transformation)
                .into(headerViewHolder.ivProfilePic);

        return convertView;
    }

    private String getElapsedString(CharSequence elapsedTime) {
        Matcher m = MATCH_ELAPSED_TIME.matcher(elapsedTime);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            String text = m.group(1);
            if(text.contains("minute")){
                m.appendReplacement(sb, "min");
            } else if(text.contains("hour")){
                m.appendReplacement(sb, "h");
            } else if(text.contains("day")){
                m.appendReplacement(sb, "d");
            } else if(text.contains("week")){
                m.appendReplacement(sb, "w");
            } else if(text.contains("month")){
                m.appendReplacement(sb, "mon");
            } else if(text.contains("year")){
                m.appendReplacement(sb, "y");
            }
            m.appendTail(sb);
        }
        return sb.toString();
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }


}
