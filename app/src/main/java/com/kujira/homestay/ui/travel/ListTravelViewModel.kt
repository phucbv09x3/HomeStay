package com.kujira.homestay.ui.travel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.data.model.ProvinceModel
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.data.model.TravelModel
import com.kujira.homestay.ui.base.BaseViewModel

class ListTravelViewModel : BaseViewModel() {

    var listTravels = MutableLiveData<MutableList<TravelModel>>()
    private var listTravel = mutableListOf<TravelModel>()
    fun getListTravelId(id: String) {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Client")
            .child("TravelList")
            .orderByChild("id")
            .equalTo(id)
        firebaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listTravel.clear()
                for (pos in snapshot.children) {
                    //var provinceModel: ProvinceModel? = pos.getValue(ProvinceModel::class.java)
                    var objectsTravel = TravelModel(
                        id = pos.child("id").value.toString(),
                        address = pos.child("address").value.toString(),
                        detail = pos.child("detail").value.toString(),
                        img = pos.child("img").value.toString()
                    )
                    listTravel.add(objectsTravel)

                }
                Log.d("list1", "$listTravel")
                listTravels.value = listTravel
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}