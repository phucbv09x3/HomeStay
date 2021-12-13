package com.kujira.homestay.ui.client.service

import com.kujira.homestay.data.model.response.NotificationData
import com.kujira.homestay.utils.Constants
import com.kujira.homestay.utils.Constants.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationAPI {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:${Constants.CONTENT_TYPE}")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}
data class PushNotification(
    val data: NotificationData,
    val to: String
)