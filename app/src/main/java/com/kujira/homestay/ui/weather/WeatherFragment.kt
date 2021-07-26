package com.kujira.homestay.ui.weather

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentWheatherBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : BaseFragment<WeatherViewModel, FragmentWheatherBinding>() {

    companion object {
        const val urlWeatherIcon = "https://openweathermap.org/img/w/"
    }

    override fun createViewModel(): Class<WeatherViewModel> {
        return WeatherViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_wheather

    override fun initView() {
        viewModel.getResponseWeather("Hà Nội")
    }

    @SuppressLint("SetTextI18n")
    override fun bindViewModel() {

        viewModel.rootResponseWeather.observe(this, {
            it?.let {
                Toast.makeText(context, getString(R.string.success), Toast.LENGTH_LONG).show()
            }
            val listWeather = it.weather
            listWeather?.let { list ->
                for (i in list) {
                    val img = i.icon
                    Picasso.get()
                        .load("$urlWeatherIcon$img.png")
                        .into(dataBinding.imgWeather)
                    dataBinding.tvCloud.text = i.main
                }
            }
            dataBinding.apply {
                tvC.text =
                    (it.main?.temp?.minus(273.15))?.toInt().toString() + getString(R.string.doC)
                tvShowHumidity.text = it.main?.humidity.toString() + " (%)"
                tvShowWind.text = it.wind?.speed.toString() + " (km/h)"
                val longTime = Date(it.dt.toLong() * 1000)
                val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm")
                val day = sdf.format(longTime)
                tvDay.text = day
                tvAddress.text = it.name
            }

        })
    }
}