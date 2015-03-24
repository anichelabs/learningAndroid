package ca.aniche.instagramclient.models.instagram;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstagramItem {

    @SerializedName("id")
    String mId;

    @SerializedName("caption")
    Comment mCaption;

    @SerializedName("comments")
    CommentWrapper mCommentWrapper;

    @SerializedName("created_time")
    long mCreatedTime;

    @SerializedName("images")
    Images mImages;

    @SerializedName("filter")
    String mFilter;

    @SerializedName("likes")
    Likes mLikes;

    @SerializedName("link")
    String mLink;

    @SerializedName("tags")
    List<String> mTags;

    @SerializedName("location")
    Location mLocation;

    @SerializedName("type")
    Type mType;

    @SerializedName("user")
    User mUser;

    @SerializedName("users_in_photo")
    List<User> mUsersInPhoto;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Comment getCaption() {
        return mCaption;
    }

    public void setCaption(Comment caption) {
        mCaption = caption;
    }

    public CommentWrapper getCommentWrapper() {
        return mCommentWrapper;
    }

    public void setCommentWrapper(CommentWrapper commentWrapper) {
        mCommentWrapper = commentWrapper;
    }

    public long getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(long createdTime) {
        mCreatedTime = createdTime;
    }

    public Images getImages() {
        return mImages;
    }

    public void setImages(Images images) {
        mImages = images;
    }

    public String getFilter() {
        return mFilter;
    }

    public void setFilter(String filter) {
        mFilter = filter;
    }

    public Likes getLikes() {
        return mLikes;
    }

    public void setLikes(Likes likes) {
        mLikes = likes;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        mTags = tags;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public List<User> getUsersInPhoto() {
        return mUsersInPhoto;
    }

    public void setUsersInPhoto(List<User> usersInPhoto) {
        this.mUsersInPhoto = usersInPhoto;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
