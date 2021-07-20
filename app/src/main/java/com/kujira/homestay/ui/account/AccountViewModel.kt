package com.kujira.homestay.ui.account

import androidx.databinding.ObservableField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.ui.base.BaseViewModel

class AccountViewModel : BaseViewModel() {
    val myName = ObservableField<String>()
    val myMail = ObservableField<String>()
    val myPhone = ObservableField<String>()
    private var auth = FirebaseAuth.getInstance()
    private var dataReference = FirebaseDatabase.getInstance().getReference("Client")
    fun updateUI(){

        dataReference.child("Account").child(auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("userName").value.toString()
                    val email = snapshot.child("mail").value.toString()
                    val phone = snapshot.child("phone").value.toString()
                    myName.set(name)
                    myMail.set(email)
                    myPhone.set(phone)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}