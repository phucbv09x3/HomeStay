package com.kujira.homestay.ui.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants

class HomeViewModel : BaseViewModel() {

    var listProvince = MutableLiveData<MutableList<Provinces>>()
    private var lisProvincesVMD = mutableListOf<Provinces>()
    companion object{
        const val SEARCH_MAP = 2
    }
    fun getListProvince() {
        lisProvincesVMD.clear()
        val firebaseRef =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.PROVINCE)
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (pos in snapshot.children) {
                    val objectsPr = Provinces(
                        id = pos.child(Constants.ID).value.toString(),
                        imageUrl = pos.child(Constants.IMG_URL).value.toString(),
                        name = pos.child(Constants.NAME).value.toString()
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
                navigation.navigate(R.id.travelAll_fragment)
            }
            R.id.tv_search_map -> {
                listener.value = SEARCH_MAP
            }
            R.id.tv_next_weather -> {
                navigation.navigate(R.id.weather_fragment)
            }
        }
    }
}