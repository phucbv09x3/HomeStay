package com.kujira.homestay.ui.all_login.login_new

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.kujira.homestay.R
import com.kujira.homestay.ui.base.BaseViewModel

class LoginAccViewModel : BaseViewModel() {
    val emailLogin = ObservableField<String>()
    val passwordLogin = ObservableField<String>()
    val listenerClick = MutableLiveData<Int>()
    companion object{
        const val LOGIN = 1
        const val FORGOT_PASSWORD = 2
        const val REGISTER_ACC = 3
    }
    fun click(view: View) {
        when (view.id) {
            R.id.btn_login_new -> {
                listenerClick.value = LOGIN
            }
            R.id.tv_forgot_password_new -> {
                listenerClick.value = FORGOT_PASSWORD
            }
            R.id.tv_register_new -> {
                listenerClick.value = REGISTER_ACC
            }
        }
    }
}