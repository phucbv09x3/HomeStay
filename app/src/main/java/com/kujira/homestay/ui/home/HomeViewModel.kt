package com.kujira.homestay.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.data.model.ProvinceModel
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {
    var listProvince = MutableLiveData<MutableList<Provinces>>()
    private var lisProvincesVMD = mutableListOf<Provinces>()
    fun getListProvince() {
        lisProvincesVMD.clear()
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Client").child("Province")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (pos in snapshot.children) {
                    var provinceModel: ProvinceModel? = pos.getValue(ProvinceModel::class.java)
                    var objectsPr = Provinces(
                        id = pos.child("id").value.toString(),
                        imageUrl = pos.child("imageUrl").value.toString(),
                        name = pos.child("name").value.toString()
                    )
                    lisProvincesVMD.add(objectsPr)
                }
                listProvince.value = lisProvincesVMD
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    var listener = MutableLiveData<Int>()
    fun onClick(view: View) {
        when (view.id) {
            R.id.linear_homestay -> {
                navigation.navigate(R.id.listRoom)
            }
            R.id.linear_dulich -> {
                listener.value = 1
            }
            R.id.tv_search_map -> {
                listener.value =2
            }
        }
    }
}