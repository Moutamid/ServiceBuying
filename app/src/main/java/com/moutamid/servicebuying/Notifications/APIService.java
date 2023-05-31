package com.moutamid.servicebuying.Notifications;


import com.fxn.stash.Stash;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    String id = Stash.getString("serverId");
    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key="
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}