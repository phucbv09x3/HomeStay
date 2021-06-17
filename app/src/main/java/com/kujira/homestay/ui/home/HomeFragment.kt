package com.kujira.homestay.ui.home

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Pro
import com.kujira.homestay.data.model.ProvinceModel
import com.kujira.homestay.databinding.FragmentHomeBinding
import com.kujira.homestay.ui.base.BaseFragment


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override fun createViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

      //  var listProvinceModel = mutableListOf()
        val firebaseRef = FirebaseDatabase.getInstance().getReference("Province")

        /// val databaseRef : DatabaseReference =firebaseRef.getReference("Province")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (pos in snapshot.children) {
                    var provinceModel : ProvinceModel?= pos.getValue(ProvinceModel::class.java)
                    //var pr = Pro(pos.child("imageUrl").value)
                    var objectsPr = Pro(
                        imageUrl = pos.child("imageUrl").value.toString(),
                        name = pos.child("name").value.toString()
                    )
                   // listProvinceModel.add(objectsPr)
                    Log.d("listProvinceModel", "${pos.child("imageUrl").value} - $objectsPr")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun bindViewModel() {

    }
}