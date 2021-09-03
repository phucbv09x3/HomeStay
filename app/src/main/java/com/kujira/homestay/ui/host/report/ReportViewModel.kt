package com.kujira.homestay.ui.host.report

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
import com.kujira.homestay.utils.printLog

class ReportViewModel : BaseViewModel() {
    var userNameReport = ObservableField("")
    var edtReport = ObservableField("")
    private val dataRef =
        FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
    private val auth = FirebaseAuth.getInstance()
    private var idClient = ""
    val listener = MutableLiveData(0)

    fun click(view: View) {
        when (view.id) {
            R.id.access_report -> {
                if (edtReport.get().toString().isEmpty()) {
                    listener.value = 1000
                } else {
                    showLoading.onNext(true)
                    reportAcc()
                }
            }
        }
    }

    fun getClient(idClient: String) {
        this.idClient = idClient

        dataRef.child(idClient)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("userName").value.toString()
                    printLog("snapshot : $name")
                    userNameReport.set("Khách hàng : $name")
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    private fun reportAcc() {
        val timeCurrent = System.currentTimeMillis()
        val dataRefer =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
        val hashMap = HashMap<String, String>()
        hashMap["idHost"] = auth.currentUser?.uid.toString()
        hashMap["idClient"] = idClient
        hashMap["contentReport"] = edtReport.get().toString()
        dataRefer.child("Report").child(timeCurrent.toString()).setValue(hashMap)
            .addOnSuccessListener {
                edtReport.set("")
                listener.value = 1
                navigation.navigateUp()
                showLoading.onNext(false)
            }
            .addOnFailureListener {
                listener.value = 2
            }
    }
}

