package com.kujira.homestay.ui.travelAll

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.data.model.TravelModel
import com.kujira.homestay.ui.base.BaseViewModel

class TravelAllViewModel : BaseViewModel() {
    var listTravels = MutableLiveData<MutableList<TravelModel>>()
    private var listTravel = mutableListOf<TravelModel>()
    var listenerBack = MutableLiveData<Int>()
    fun getListTravelAll() {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Client")
            .child("TravelList")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listTravel.clear()
                for (pos in snapshot.children) {
                    val objectsTravel = TravelModel(
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

    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_previus_travel_all -> {
                listenerBack.value = 1
            }
        }
    }
}