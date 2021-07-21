package com.kujira.homestay.ui.manager

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.ui.base.BaseViewModel

class ManagerRoomViewModel : BaseViewModel() {
    private var auth = FirebaseAuth.getInstance()
    private var dataReferences = FirebaseDatabase.getInstance().getReference("Host")
        .child("ListRoom")
    private var listRoom = mutableListOf<AddRoomModel>()
    var listRoomLiveData = MutableLiveData<MutableList<AddRoomModel>>()
    fun managerMyRoom() {
        val query = dataReferences.orderByChild("idClient").equalTo(auth.currentUser?.uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listRoom.clear()
                for (snap in snapshot.children) {
                    val obStatus = AddRoomModel(
                        snap.child("id").value.toString(),
                        snap.child("address").value.toString(),
                        snap.child("typeRoom").value.toString(),
                        snap.child("nameRoom").value.toString(),
                        snap.child("s_room").value.toString(),
                        snap.child("numberSleepRoom").value.toString(),
                        snap.child("convenient").value.toString(),
                        snap.child("introduce").value.toString(),
                        snap.child("imageRoom1").value.toString(),
                        snap.child("imageRoom2").value.toString(),
                        snap.child("status").value.toString(),
                        snap.child("price").value.toString(),
                        snap.child("uid").value.toString(),
                    )
                    //  var po=pos.child("img").toString()
                    listRoom.add(obStatus)
                }
                listRoomLiveData.value = listRoom

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun cancelRoom(id:String){
       dataReferences.child(id).child("idClient").removeValue()
        dataReferences.child(id)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hash = HashMap<String,Any>()
                    hash["status"] = "Còn Trống"
                    dataReferences.child(id).updateChildren(hash)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}