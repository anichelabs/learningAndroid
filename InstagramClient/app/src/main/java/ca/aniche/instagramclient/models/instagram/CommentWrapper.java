package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aneesh on 2/5/15.
 */
public class CommentWrapper {

    @SerializedName("count")
    long mCount;

    @SerializedName("data")
    List<Comment> mComments;

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public long getCount() {

        return mCount;
    }

    public void setCount(long count) {
        mCount = count;
    }

    @Override
    public String toString() {
        return "CommentWrapper{" +
                "mCount=" + mCount +
                ", mComments=" + mComments +
                '}';
    }

}
