package com.kujira.homestay.ui.login

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import com.kujira.homestay.R
import com.kujira.homestay.data.api.ApiCoroutines
import com.kujira.homestay.ui.base.BaseViewModel
import java.util.*

/**
 * Created by OpenYourEyes on 10/22/2020
 */
class LoginViewModel : BaseViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    val authFail = MutableLiveData<Int>()

    fun login(view: View){
        when(view.id){
            R.id.btn_login -> {
                checkSignIn()
            }
            R.id.tv_register ->{
                navigation.navigate(R.id.registerFragment)
            }
        }
    }
    private fun checkSignIn() {
        val email = email.get() ?: ""
        val password = password.get() ?: ""
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = auth.currentUser
                        if (user!!.isEmailVerified) {
                            navigation.navigate(R.id.home_fragment)
                        } else {
                            authFail.value = R.string.error_auth
                        }
                    }
                }
        } else {
            authFail.value = 1
        }
    }

    fun checkCurrentUser() {
        Log.d("currentUSer", "${auth.currentUser?.isEmailVerified}")
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null) {

        } else {
            navigation.navigate(R.id.home_fragment)
        }
    }
}