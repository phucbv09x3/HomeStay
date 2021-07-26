package com.kujira.homestay.ui.weather

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.kujira.homestay.data.api.ApiCoroutines
import com.kujira.homestay.data.api.api_key
import com.kujira.homestay.data.model.response.Root
import com.kujira.homestay.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : BaseViewModel() {
    val textSearchWeather = ObservableField<String>()
    var rootResponseWeather = MutableLiveData<Root>()


    private val baseUrlWeather = "https://api.openweathermap.org/data/2.5/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrlWeather)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiCoroutines::class.java)

    fun getResponseWeather(city_name: String) {
        val call = retrofit.getResponseWeather(city_name, api_key)
        call.enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                if (response.isSuccessful) {
                    rootResponseWeather.value = response.body()
                }

            }

            override fun onFailure(call: Call<Root>, t: Throwable) {

            }
        }
        )
    }

    fun showWeather() {
        val city = textSearchWeather.get().toString()
        if (city.isEmpty()) {
        } else {
            getResponseWeather(city)
        }
    }
}