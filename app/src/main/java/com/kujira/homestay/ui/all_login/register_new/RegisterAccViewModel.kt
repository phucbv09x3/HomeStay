package com.kujira.homestay.ui.all_login.register_new

import android.os.Bundle
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

class RegisterAccViewModel : BaseViewModel() {
    val emailRegister = ObservableField<String>()
    val passwordRegister = ObservableField<String>()
    val userNameRegister = ObservableField<String>()
    val numberPhoneRegister = ObservableField<String>()
    val listenerShowToast = MutableLiveData<Int>()
    fun click(view: View) {
        when (view.id) {
            R.id.btn_register_host_new -> {
                registerAcc(1)
            }
            R.id.btn_register_client_new -> {

                registerAcc(2)
            }

        }
    }

    private var fireBaseAuth = FirebaseAuth.getInstance()
    private var dataReference =
        FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
    private var listEmail = mutableListOf<String>()

    private fun registerAcc(permission: Int) {
        val userName = userNameRegister.get()
        val mail = emailRegister.get()
        val passWord = passwordRegister.get()
        val phone = numberPhoneRegister.get()
        if (userName?.isEmpty() == false && mail?.isEmpty() == false
            && passWord?.isEmpty() == false && phone?.isEmpty() == false
        ) {
            val isCheckMail = listEmail.contains(mail)
            if (isCheckMail) {
                listenerShowToast.value = R.string.email_exist
            } else {

                fireBaseAuth.createUserWithEmailAndPassword(mail, passWord)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = fireBaseAuth.currentUser
                            val mail = user?.email
                            val userUID = user?.uid
                            val hashMap = HashMap<String, String>()
                            hashMap[Constants.USER_NAME] = userName
                            hashMap[Constants.MAIL] = mail!!
                            hashMap[Constants.UID] = userUID!!
                            hashMap[Constants.PASSWORD] = passWord
                            hashMap[Constants.PHONE] = phone
                            hashMap[Constants.PERMISSION] = permission.toString()
                            dataReference.child(userUID).setValue(hashMap)

                            user.sendEmailVerification()
                                .addOnCompleteListener {
                                    listenerShowToast.value = R.string.email_verify
                                }
                        } else {
                            listenerShowToast.value = R.string.register_fail
                        }
                    }
            }
        } else {
            listenerShowToast.value = R.string.error_isEmpty
        }
    }


    fun getListAcc(): MutableList<String> {

        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listEmail.clear()
                for (snap in snapshot.children) {

                    listEmail.add(snap.child(Constants.MAIL).value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listEmail

    }
}