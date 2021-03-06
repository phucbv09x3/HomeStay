package com.kujira.homestay.ui.host.myacc

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.ui.base.BaseViewModel

class MyAccountHostViewModel : BaseViewModel() {
    val myName = ObservableField<String>()
    val myMail = ObservableField<String>()
    val myPhone = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    private var dataReference = FirebaseDatabase.getInstance().getReference("Client")
    var listener = MutableLiveData<Int>()

    companion object {
        const val SUCCESS = 1
        const val ERROR = 2
    }

    fun updateUI() {

        dataReference.child("Account").child(auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("userName").value.toString()
                    val email = snapshot.child("mail").value.toString()
                    val phone = snapshot.child("phone").value.toString()
                    myName.set(name)
                    myMail.set(email)
                    myPhone.set("SĐT : $phone")
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun changeAcc(name: String, phone: String) {
        val dataReference = FirebaseDatabase.getInstance().getReference("Client").child("Account")
        dataReference.orderByChild("uid")
            .equalTo(auth.currentUser!!.uid).limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hash = HashMap<String, Any>()
                    hash["userName"] = name
                    hash["phone"] = phone
                    dataReference.child(auth.currentUser!!.uid).updateChildren(hash)
                        .addOnSuccessListener {
                            listener.value = SUCCESS
                        }
                        .addOnFailureListener {
                            listener.value = ERROR
                        }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun logOut(){
        auth.signOut()
    }
}