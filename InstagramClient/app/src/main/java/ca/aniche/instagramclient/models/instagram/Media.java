package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aneesh on 2/5/15.
 */
public class Media {

    @SerializedName("height")
    int mHeight;

    @SerializedName("width")
    int mWidth;

    @SerializedName("url")
    String mUrl;

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mHeight=" + mHeight +
                ", mWidth=" + mWidth +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
