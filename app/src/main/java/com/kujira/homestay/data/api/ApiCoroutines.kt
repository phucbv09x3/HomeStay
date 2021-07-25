package com.kujira.homestay.data.api

import com.kujira.homestay.data.model.response.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

/**
 * Created by OpenYourEyes on 4/4/21
 */
const val api_key="fa0dfcf4442ccae2ca433e25ea283f19"
interface ApiCoroutines {
//    @GET("search/repositories")
//    suspend fun getRepoGit(
//        @Query("q") q: String = "trending",
//        @Query("sort") stars: String = "stars"
//    ): GitResponse
//
//    @POST("api/login")
//    fun login(): BaseResponse<LoginViewModel?>

    @GET("weather")
    fun getResponseWeather(@Query("q") city: String,
    @Query("appid" )appid : String): Call<Root>
}