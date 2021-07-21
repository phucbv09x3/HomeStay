package com.kujira.homestay.ui.account

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.ui.base.BaseViewModel

class AccountViewModel : BaseViewModel() {
    val myName = ObservableField<String>()
    val myMail = ObservableField<String>()
    val myPhone = ObservableField<String>()
    val listener = MutableLiveData<Int>()
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

    fun click(view : View){
        when(view.id){
            R.id.tv_edit -> {
                listener.value=1
            }
            R.id.tv_logout -> {

            }
        }
    }
}