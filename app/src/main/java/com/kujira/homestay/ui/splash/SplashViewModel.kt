package com.kujira.homestay.ui.splash

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants

class SplashViewModel : BaseViewModel() {
    val auth = FirebaseAuth.getInstance()
    val autoLogin = MutableLiveData<Int>()

    fun checkCurrentUser() {
        showLoading.onNext(true)
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null) {
            autoLogin.value = 0
            showLoading.onNext(false)
        } else {
            val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(
                Constants.ACCOUNT)
                .child(auth.currentUser!!.uid)
            dataRef.addValueEventListener(object  : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val permission = snapshot.child(Constants.PERMISSION).value.toString()
                    if(permission == "1"){
                        autoLogin.value = 1
                        showLoading.onNext(false)
                    }else if (permission == "2"){
                        autoLogin.value = 2
                        showLoading.onNext(false)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}