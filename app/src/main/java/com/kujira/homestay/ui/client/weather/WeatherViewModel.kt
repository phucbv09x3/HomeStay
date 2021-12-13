package com.kujira.homestay.ui.client.weather

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.kujira.homestay.data.api.ApiCoroutines
import com.kujira.homestay.data.api.api_key_encrypted
import com.kujira.homestay.data.model.response.Root
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

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
        val call = retrofit.getResponseWeather(city_name, deCryptAESApiKey(api_key_encrypted, Constants.secretKey))
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

    private fun deCryptAESApiKey(textEncrypted: String, secret: String): String {
        val secretKeySpec = SecretKeySpec(secret.toByteArray().copyOf(16), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        return String(
            cipher.doFinal(
                Base64.getDecoder().decode(textEncrypted)
            )
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