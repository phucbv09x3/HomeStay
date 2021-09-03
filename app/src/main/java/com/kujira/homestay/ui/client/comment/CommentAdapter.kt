package com.kujira.homestay.ui.client.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kujira.homestay.databinding.ItemCommentRoomBinding
import kotlinx.android.synthetic.main.item_comment_room.view.*

class CommentAdapter(var listComment: MutableList<CommentModel>) :
    RecyclerView.Adapter<CommentAdapter.CommentHolder>() {


    fun setList(mutableList: MutableList<CommentModel>) {
        listComment = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentAdapter.CommentHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemCommentRoomBinding.inflate(inflate, parent, false)
        return CommentHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentHolder, position: Int) {
        holder.setUp(listComment[position])
    }

    override fun getItemCount(): Int = listComment.size

    inner class CommentHolder(
        private var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setUp(itemData: CommentModel) {
            itemView.tv_name_client_comment.text = itemData.nameClient
            itemView.detail_comment.text = itemData.comment
        }
    }
}

data class CommentModel(
    val idRoom: String,
    val nameClient: String,
    val comment: String
)