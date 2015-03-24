package ca.aniche.instagramclient.models.instagram;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aneesh on 2/5/15.
 */
public class Likes {
    @SerializedName("count")
    long mCount;

    @SerializedName("data")
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getCount() {
        return mCount;
    }

    public void setCount(long count) {
        mCount = count;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "mCount=" + mCount +
                ", users=" + users +
                '}';
    }
}
