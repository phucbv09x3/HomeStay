package com.kujira.homestay.ui.client.listRoom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.data.model.response.NotificationData
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.ui.client.service.PushNotification
import com.kujira.homestay.ui.client.service.RetrofitInstance
import com.kujira.homestay.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListRoomViewModel : BaseViewModel() {
    private var dataReferences = FirebaseDatabase.getInstance().getReference(Constants.HOST).child(Constants.LIST_ROOM)
    private var listRoom = mutableListOf<AddRoomModel>()
    var listRoomLiveData = MutableLiveData<MutableList<AddRoomModel>>()
    val listenerScc = MutableLiveData<Int>(0)
    val listenerToken = MutableLiveData<TokenModel>()
    fun getListRoom() {
        dataReferences.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listRoom.clear()
                for (snap in snapshot.children) {
                    val addModel = AddRoomModel(
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
                    listRoom.add(addModel)
                }
                listRoomLiveData.value = listRoom

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun chooseRoom(id:String){
       dataReferences.orderByChild("id").equalTo(id).limitToFirst(1)
           .addListenerForSingleValueEvent(object : ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   val hash = HashMap<String,Any>()
                   hash["status"] = "Đã Đặt"
                   hash["idClient"]= FirebaseAuth.getInstance().currentUser!!.uid
                   dataReferences.child(id).updateChildren(hash).addOnSuccessListener {
                       listenerScc.postValue(1)
                   }
               }

               override fun onCancelled(error: DatabaseError) {
               }
           })
    }
    fun getTokenFromId(id : String){
        var getIdHost = ""
        var token = ""
        var nameRoom = ""
        dataReferences.child(id).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                getIdHost = snapshot.child("uid").value.toString()
                nameRoom = snapshot.child("nameRoom").value.toString()
                if (getIdHost.isNotEmpty()){
                    val dbR = FirebaseDatabase.getInstance().getReference("Client").child("Account")
                    dbR.child(getIdHost).addListenerForSingleValueEvent(object  : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            token = snapshot.child("token").value.toString()
                            listenerToken.value = TokenModel(token,nameRoom)
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun click(){
        navigation.navigateUp()
    }

    fun searchHomeStay(newText: String?) {
        val firebaseRef = FirebaseDatabase.getInstance().getReference(Constants.HOST)
            .child(Constants.LIST_ROOM)
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listRoom.clear()
                for (pos in snapshot.children) {

                    val id = pos.child("id").value.toString()
                    val address = pos.child("address").value.toString()
                    val typeRoom = pos.child("typeRoom").value.toString()
                    val nameRoom = pos.child("nameRoom").value.toString()
                    val s_room = pos.child("s_room").value.toString()
                    val numberSleepRoom = pos.child("numberSleepRoom").value.toString()
                    val convenient = pos.child("convenient").value.toString()
                    val introduce = pos.child("introduce").value.toString()
                    val imageRoom1 = pos.child("imageRoom1").value.toString()
                    val imageRoom2 = pos.child("imageRoom2").value.toString()
                    val status = pos.child("status").value.toString()
                    val price = pos.child("price").value.toString()
                    val uid = pos.child("uid").value.toString()
                    val model = AddRoomModel(id,address,typeRoom,nameRoom,s_room,numberSleepRoom,convenient,introduce,imageRoom1,imageRoom2,status,price,uid)
                    //val model = pos.getValue(AddRoomModel::class.java)
                    if (model.address.toLowerCase().contains(newText!!.toLowerCase())) {
                        listRoom.add(model)
                        listRoomLiveData.value =listRoom

                    }

                }
            }

        })
    }
}

data class TokenModel(
    val token : String,
    val nameRoom:String,
)