package ca.aniche.instagramclient.managers;

import ca.aniche.instagramclient.models.instagram.PopularItemResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface InstagramService {

    @GET("/v1/media/popular")
    void getPopularItemsAsync(@Query("client_id") String clientId, Callback<PopularItemResponse> callback);


}
