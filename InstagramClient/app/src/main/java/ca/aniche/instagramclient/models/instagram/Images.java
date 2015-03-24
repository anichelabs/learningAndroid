package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aneesh on 2/5/15.
 */
public class Images {
    @SerializedName("low_resolution")
    Media mLowRes;

    @SerializedName("standard_resolution")
    Media mStandardRes;

    @SerializedName("thumbnail")
    Media mThumbnail;


    public Media getLowRes() {
        return mLowRes;
    }

    public void setLowRes(Media lowRes) {
        mLowRes = lowRes;
    }

    public Media getStandardRes() {
        return mStandardRes;
    }

    public void setStandardRes(Media standardRes) {
        mStandardRes = standardRes;
    }

    public Media getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Media thumbnail) {
        mThumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Images{" +
                "mLowRes=" + mLowRes +
                ", mStandardRes=" + mStandardRes +
                ", mThumbnail=" + mThumbnail +
                '}';
    }
}
