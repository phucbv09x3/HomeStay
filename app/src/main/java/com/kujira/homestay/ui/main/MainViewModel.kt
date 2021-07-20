package com.kujira.homestay.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.kujira.homestay.R
import com.kujira.homestay.ui.base.BaseViewModel

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class MainViewModel : BaseViewModel() {

    companion object{
        const val BTN_HOME = 1
        const val BTN_HEART = 2
        const val BTN_DATCHO = 3
        const val BTN_MESSAGE = 4
        const val BTN_ACCOUNT = 5

    }
    var btnClick = MutableLiveData<Int>()
    fun click(view: View) {
        when (view.id) {
            R.id.btn_home -> {
              btnClick.value = BTN_HOME
            }

            R.id.btn_manager -> {
                btnClick.value = BTN_DATCHO
            }

            R.id.btn_account -> {
                btnClick.value = BTN_ACCOUNT
            }
        }
    }
}