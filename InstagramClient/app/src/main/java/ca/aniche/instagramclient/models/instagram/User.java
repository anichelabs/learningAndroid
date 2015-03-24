package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aneesh on 2/5/15.
 */
public class User {

    @SerializedName("id")
    String id;

    @SerializedName("full_name")
    String mFullName;

    @SerializedName("profile_picture")
    String mProfilePicUrl;

    @SerializedName("username")
    String mUsername;

    @SerializedName("bio")
    String mBio;

    @SerializedName("website")
    String mWebsite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public String getProfilePicUrl() {
        return mProfilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        mProfilePicUrl = profilePicUrl;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mFullName='" + mFullName + '\'' +
                ", mProfilePicUrl='" + mProfilePicUrl + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mBio='" + mBio + '\'' +
                ", mWebsite='" + mWebsite + '\'' +
                '}';
    }
}
