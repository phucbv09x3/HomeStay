package com.kujira.homestay.data.api

import com.kujira.homestay.data.model.response.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryName

/**
 * Created by Phuc on /10/21
 */
//const val api_key="fa0dfcf4442ccae2ca433e25ea283f19"
const val api_key_encrypted="DL68r0e7K4jpm8ux+tJYBGIryqOiC65C+1Vg3h7oodB1v3C0jcW+xwC5qvK1Uk6w"
interface ApiCoroutines {


    @GET("weather")
    fun getResponseWeather(@Query("q") city: String,
    @Query("appid" )appid : String): Call<Root>
}