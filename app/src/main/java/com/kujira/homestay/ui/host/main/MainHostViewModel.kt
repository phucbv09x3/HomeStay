package com.kujira.homestay.ui.host.main

import android.view.View
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
 * Created by OpenYourEyes on 10/24/2020
 */
class MainHostViewModel : BaseViewModel() {
    companion object {
        const val BTN_MANAGER_ROOM = 1
        const val BTN_ADD_ROOM = 2
        const val BTN_MY_ACC = 3
    }

    private val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
    private val auth = FirebaseAuth.getInstance()
    var onClickMain = MutableLiveData<Int>()
    val dataReport = MutableLiveData<String>()
    fun click(view: View) {
        when (view.id) {
            R.id.btn_managerRoom_on_main -> {
                onClickMain.value = BTN_MANAGER_ROOM
            }
            R.id.btn_add_room_on_main -> {
                onClickMain.value = BTN_ADD_ROOM
            }
            R.id.btn_my_account_on_main -> {
                onClickMain.value = BTN_MY_ACC
            }
        }
    }

    fun checkReport() {
        dataRef.child("Report").child("ReportHost").child(auth.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataReport.value = snapshot.child("contentReport").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun logOut() {
        auth.signOut()
    }
}