package com.kujira.homestay.ui.client.account

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
import com.kujira.homestay.utils.Constants

class AccountViewModel : BaseViewModel() {
    val myName = ObservableField<String>()
    val myMail = ObservableField<String>()
    val myPhone = ObservableField<String>()
    val listener = MutableLiveData<Int>()
    private var auth = FirebaseAuth.getInstance()
    private var dataReference = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)

    companion object {
        const val CHANGE_ACC = 1
        const val LOG_OUT = 2
        const val SUCCESS_CHANGE = 3
        const val ERROR_CHANGE = 4
        const val RULE = 5
    }

    fun updateUI() {

        dataReference.child(Constants.ACCOUNT).child(auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child(Constants.USER_NAME).value.toString()
                    val email = snapshot.child(Constants.MAIL).value.toString()
                    val phone = snapshot.child(Constants.PHONE).value.toString()
                    myName.set(name)
                    myMail.set(email)
                    myPhone.set(phone)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun click(view: View) {
        when (view.id) {
            R.id.tv_edit -> {
                listener.value = CHANGE_ACC
            }
            R.id.tv_logout -> {
                listener.value = LOG_OUT
            }
            R.id.rule -> {
                listener.value = RULE
            }
        }
    }

    fun changeAcc(name: String, phone: String) {
        val dataReference =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
        dataReference.orderByChild(Constants.UID)
            .equalTo(auth.currentUser!!.uid).limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hash = HashMap<String, Any>()
                    hash[Constants.USER_NAME] = name
                    hash[Constants.PHONE] = phone
                    dataReference.child(auth.currentUser!!.uid).updateChildren(hash)
                        .addOnSuccessListener {
                            listener.value = SUCCESS_CHANGE
                        }
                        .addOnFailureListener {
                            listener.value = ERROR_CHANGE
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