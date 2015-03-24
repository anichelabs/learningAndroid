package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularItemResponse {

    @SerializedName("data")
    List<InstagramItem> mItems;

    public List<InstagramItem> getItems() {
        return mItems;
    }

    public void setItems(List<InstagramItem> items) {
        mItems = items;
    }
}
