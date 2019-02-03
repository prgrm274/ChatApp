package org.chat.notifications;

import org.chat.notifications.models.NotifMyResponse;
import org.chat.notifications.models.NotifSender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA1BhREn4:APA91bGeYAxQ1WFnEq00qTWuOR7YGq_dtSlCoC3GADyWe4Uqvlg9e1SiXJNfPxO2s7A6S3nCn6qKn1DGVx-M6a4_jQGyHks4VRV8e4VkX1TpRrAG5g-3Y8mH9xahaoWXc6Co6rvd7rZm"
    })

    @POST("fcm/send")
    Call<NotifMyResponse> sendNotif(@Body NotifSender body);
}
