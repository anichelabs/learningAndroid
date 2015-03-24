package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    String mId;

    @SerializedName("text")
    String mText;

    @SerializedName("created_time")
    long mCreatedTime;

    @SerializedName("from")
    User mFrom;

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        mCreatedTime = createdTime;
    }

    public User getFrom() {
        return mFrom;
    }

    public void setFrom(User from) {
        mFrom = from;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "mId=" + mId +
                ", mText='" + mText + '\'' +
                ", mCreatedTime=" + mCreatedTime +
                ", mFrom=" + mFrom +
                '}';
    }


}
