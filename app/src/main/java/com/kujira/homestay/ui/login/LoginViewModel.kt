package com.kujira.homestay.ui.login

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants

/**
 * Created by OpenYourEyes on 10/22/2020
 */
class LoginViewModel : BaseViewModel() {

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    val listener = MutableLiveData<Int>()

    companion object {
        const val EmailVerified = 10
    }

    fun login(view: View) {
        when (view.id) {
            R.id.btn_login -> {
                checkSignIn()
            }
            R.id.tv_register -> {
                navigation.navigate(R.id.registerFragment)
            }
            R.id.tv_forgot_password -> {

            }
        }
    }

    private var listEmail = mutableListOf<String>()
     fun getListAcc(): MutableList<String> {
        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listEmail.clear()
                for (snap in snapshot.children) {
                    val objectMail = ObjectMail(
                        snap.child(Constants.MAIL).toString(),
                        snap.child(Constants.PHONE).toString(),
                        snap.child(Constants.PASSWORD).toString(),
                        snap.child(Constants.UID).toString(),
                        snap.child(Constants.USER_NAME).toString()
                    )
                    listEmail.add(objectMail.mail)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listEmail
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
                            listener.value = EmailVerified
                        } else {
                            listener.value = R.string.error_auth
                        }
                    }
                }
        } else {
            listener.value = R.string.error_isEmpty
        }
    }

    fun checkCurrentUser() {
        if (auth.currentUser?.isEmailVerified == false || auth.currentUser == null) {

        } else {

            navigation.navigate(R.id.home_fragment)
        }
    }
}

data class ObjectMail(
    val mail: String,
    val phone: String,
    val password: String,
    val uid: String,
    val userName: String,
)