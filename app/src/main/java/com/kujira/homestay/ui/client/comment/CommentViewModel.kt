package com.kujira.homestay.ui.client.comment

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants

class CommentViewModel : BaseViewModel() {
    val edtComment = ObservableField("")
    private val auth = FirebaseAuth.getInstance()
    private val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
    val listCommentLiveData = MutableLiveData<MutableList<CommentModel>>()
    private var listComment = mutableListOf<CommentModel>()
    var idRoom = ""
    val listener = MutableLiveData(0)
    fun send(view: View) {
        when (view.id) {
            R.id.btn_comment -> {
                if (edtComment.get().toString().isEmpty()) {
                    listener.value = 1
                } else {
                    listener.value = 2
                    showLoading.onNext(true)
                    commentRoom()
                }
            }
        }
    }

    fun delete() {
        dataRef.child("Comment").removeValue()
    }

    private fun commentRoom() {
        val hashMap = HashMap<String, String>()
        hashMap["idRoom"] = idRoom
        hashMap["nameClient"] = idRoom
        hashMap["comment"] = edtComment.get().toString()
        dataRef.child("Comment").child(System.currentTimeMillis().toString()).setValue(hashMap)
            .addOnSuccessListener {
                edtComment.set("")
                showLoading.onNext(false)
            }
    }


    fun getComment(addRoomModel: AddRoomModel) {
        val query = dataRef.child("Comment").orderByChild("idRoom").equalTo(addRoomModel.id)
            .limitToFirst(50)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listComment.clear()
                for (snap in snapshot.children) {
                    val idRoom = snap.child("idRoom").value.toString()
                    val nameClient = snap.child("nameClient").value.toString()
                    val comment = snap.child("comment").value.toString()
                    listComment.add(CommentModel(idRoom, nameClient, comment))
                }
                listCommentLiveData.value = listComment
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}