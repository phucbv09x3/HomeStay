package com.kujira.homestay.ui.weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentWheatherBinding
import com.kujira.homestay.ui.base.BaseFragment


class WeatherFragment : BaseFragment<WeatherViewModel,FragmentWheatherBinding>() {

    override fun createViewModel(): Class<WeatherViewModel> {
        return WeatherViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_wheather

    override fun initView() {

    }

    override fun bindViewModel() {
        viewModel.getResponseWeather("Ha Noi")
        viewModel.rootResponseWeather.observe(this,{
            Log.d("rootResponseWeather : ","${it}")

        })
    }
}