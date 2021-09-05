package com.kujira.homestay.ui.client.main

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
 * Created by Phucbv on 5/2021
 */
class MainViewModel : BaseViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
        .child("Report")
    companion object{
        const val BTN_HOME = 1
        const val BTN_MANAGER = 3
        const val BTN_ACCOUNT = 5

    }
    var btnClick = MutableLiveData<Int>()
    fun click(view: View) {
        when (view.id) {
            R.id.btn_home -> {
              btnClick.value = BTN_HOME
            }

            R.id.btn_manager -> {
                btnClick.value = BTN_MANAGER
            }

            R.id.btn_account -> {
                btnClick.value = BTN_ACCOUNT
            }
        }
    }

    val listReport = mutableListOf<String>()
    val listReportLiveData = MutableLiveData<MutableList<String>>()
    fun checkReport(){
        dataRef.child("ReportClient")
            .child(auth.currentUser?.uid.toString())
            .orderByChild("idClient").equalTo(auth.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    listReport.clear()
                    for (snap in snapshot.children){
                        listReport.add(snap.child("contentReport").value.toString())
                    }
                    listReportLiveData.value = listReport
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    fun logOut(){
        auth.signOut()
    }
}