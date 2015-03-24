package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aneesh on 2/5/15.
 */
public class Location {

    @SerializedName("id")
    String mId;

    @SerializedName("latitude")
    float mLatitude;

    @SerializedName("longitude")
    float mLongitude;

    @SerializedName("name")
    String name;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
    public float getLatitude() {
        return mLatitude;
    }

    public void setLatitude(float latitude) {
        mLatitude = latitude;
    }

    public float getLongitude() {
        return mLongitude;
    }

    public void setLongitude(float longitude) {
        mLongitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "mId=" + mId +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", name='" + name + '\'' +
                '}';
    }
}
