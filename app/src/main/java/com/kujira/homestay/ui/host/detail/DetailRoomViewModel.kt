package com.kujira.homestay.ui.host.detail

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.ui.client.comment.CommentModel
import com.kujira.homestay.utils.Constants

class DetailRoomViewModel : BaseViewModel() {
    private val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
    val listCommentLiveData = MutableLiveData<MutableList<CommentModel>>()
    private var listComment = mutableListOf<CommentModel>()

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