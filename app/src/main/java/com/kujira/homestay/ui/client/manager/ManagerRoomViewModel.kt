package com.kujira.homestay.ui.client.manager

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants

class ManagerRoomViewModel : BaseViewModel() {
    private var auth = FirebaseAuth.getInstance()
    private var dataReferences = FirebaseDatabase.getInstance().getReference(Constants.HOST)

    private var listRoom = mutableListOf<AddRoomModel>()
    var listRoomLiveData = MutableLiveData<MutableList<AddRoomModel>>()
    fun managerMyRoom() {
        val query =
            dataReferences.child(Constants.LIST_ROOM).orderByChild(Constants.ID_CLIENT).equalTo(auth.currentUser?.uid)
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

    fun cancelRoom(id: String) {
        dataReferences.child(Constants.LIST_ROOM).child(id).child(Constants.ID_CLIENT).removeValue()
       val query= dataReferences.child(Constants.LIST_ROOM)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hash = HashMap<String, Any>()
                    hash[Constants.STATUS] = Constants.EMPTY_ROOM
                    query.child(id).updateChildren(hash)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    var modelShowHost = MutableLiveData<DetailHost>()
    fun getDetail(addRoomModel: AddRoomModel) {

        val firebase = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
        firebase.child(Constants.ACCOUNT).orderByChild(Constants.UID).equalTo(addRoomModel.uid)
            .limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
                        val model = DetailHost(
                            snap.child(Constants.USER_NAME).value.toString(),
                            snap.child(Constants.PHONE).value.toString()
                        )
                        modelShowHost.value = model
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}

data class DetailHost(
    val userName: String,
    val phone: String
)