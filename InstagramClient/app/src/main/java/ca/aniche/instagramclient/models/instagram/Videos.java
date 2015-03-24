package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aneesh on 2/5/15.
 */
public class Videos {

    @SerializedName("low_bandwidth")
    Media mLowBandwidth;

    @SerializedName("low_resolution")
    Media mLowResolution;

    @SerializedName("standard_resolution")
    Media mStandardResolution;

    public Media getLowBandwidth() {
        return mLowBandwidth;
    }

    public void setLowBandwidth(Media lowBandwidth) {
        mLowBandwidth = lowBandwidth;
    }

    public Media getLowResolution() {
        return mLowResolution;
    }

    public void setLowResolution(Media lowResolution) {
        mLowResolution = lowResolution;
    }

    public Media getStandardResolution() {
        return mStandardResolution;
    }

    public void setStandardResolution(Media standardResolution) {
        mStandardResolution = standardResolution;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "mLowBandwidth=" + mLowBandwidth +
                ", mLowResolution=" + mLowResolution +
                ", mStandardResolution=" + mStandardResolution +
                '}';
    }
}
