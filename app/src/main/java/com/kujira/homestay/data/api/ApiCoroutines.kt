package com.kujira.homestay.data.api

import com.kujira.homestay.data.model.response.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by OpenYourEyes on 4/4/21
 */
interface ApiCoroutines {
//    @GET("search/repositories")
//    suspend fun getRepoGit(
//        @Query("q") q: String = "trending",
//        @Query("sort") stars: String = "stars"
//    ): GitResponse
//
//    @POST("api/login")
//    fun login(): BaseResponse<LoginViewModel?>

    @GET("https://api.openweathermap.org/data/2.5/weather?q={city_name}&appid=fa0dfcf4442ccae2ca433e25ea283f19")
    fun getResponseWeather(@Path("city_name") city_name: String): Call<Root>
}