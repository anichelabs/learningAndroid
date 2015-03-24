package ca.aniche.instagramclient.managers;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

public class InstagramServiceManager {

    public static final String SERVER_URL = "https://api.instagram.com";
    public static final String TAG = "InstagramServiceManager";
    public static final String CLIENT_ID = "94fb0859f8964ecd990892168a733d60";
    private InstagramService mInstagramService;


    public InstagramService getServiceInterface(){
        return getRestAdapter().create(InstagramService.class);
    }

    public RestAdapter getRestAdapter(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(TAG))
                .build();

        return restAdapter;
    }
}
