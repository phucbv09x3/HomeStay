package com.kujira.homestay.ui.splash

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants
import java.util.logging.Handler

class SplashViewModel : BaseViewModel() {
    val auth = FirebaseAuth.getInstance()
    val autoLogin = MutableLiveData<Int>()

    fun checkCurrentUser() {
        showLoading.onNext(true)
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null) {
            showLoading.onNext(false)
            autoLogin.value = 0
        } else {
            val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(
                Constants.ACCOUNT)
                .child(auth.currentUser!!.uid)
            dataRef.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val permission = snapshot.child(Constants.PERMISSION).value.toString()
                    if(permission == "1"){
                        showLoading.onNext(false)
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            //Do something after 100ms
                            autoLogin.value = 1
                        }, 300)

                    }else if (permission == "2"){
                        showLoading.onNext(false)
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            //Do something after 100ms
                            autoLogin.value = 2
                        }, 300)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showLoading.onNext(false)
                }
            })
        }
    }
}