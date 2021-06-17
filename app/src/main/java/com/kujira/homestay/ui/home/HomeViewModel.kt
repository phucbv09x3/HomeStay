package com.kujira.homestay.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.data.model.ProvinceModel
import com.kujira.homestay.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    var listProvince = MutableLiveData<MutableList<Provinces>>()
    private var lisProvincesVMD = mutableListOf<Provinces>()
    fun getListProvince(){
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Client").child("Province")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (pos in snapshot.children) {
                    var provinceModel : ProvinceModel?= pos.getValue(ProvinceModel::class.java)
                    var objectsPr = Provinces(
                        imageUrl = pos.child("imageUrl").value.toString(),
                        name = pos.child("name").value.toString()
                    )
                    lisProvincesVMD.add(objectsPr)

                    Log.d("lisProvincesVMD","$lisProvincesVMD")
                }
                listProvince.value = lisProvincesVMD
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}