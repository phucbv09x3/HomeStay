package com.kujira.homestay.ui.login

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.kujira.homestay.R
import com.kujira.homestay.data.api.ApiCoroutines
import com.kujira.homestay.ui.base.BaseViewModel

/**
 * Created by OpenYourEyes on 10/22/2020
 */
class LoginViewModel : BaseViewModel() {
    private val apiWithoutAuthenticator: ApiCoroutines by lazy {
        apiService.apiWithoutAuthenticator()
    }


    suspend fun saveUserName(name: String) {
        dataManager.saveUserName(name)
    }

    private suspend fun getUserName(): String {
        return dataManager.getUserName()
    }


    fun saveUser(view: View) {
        viewModelScope.launch {
            val name = getUserName()
            saveUserName("Hello Chao cac ban !")
            val bundle = bundleOf("DKS" to "OpenYourEyes")
            navigation.navigate(R.id.listFragment, bundle = bundle, addToBackStack = false)
        }
        login()
    }

    fun login() {
        apiService.createApiCoroutines("Hello")
    }
}