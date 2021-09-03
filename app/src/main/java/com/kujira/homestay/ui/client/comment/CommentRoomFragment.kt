package com.kujira.homestay.ui.client.comment

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.databinding.FragmentCommentRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.utils.widget.view.setImageViewResource
import kotlinx.android.synthetic.main.fragment_comment_room.*

class CommentRoomFragment : BaseFragment<CommentViewModel, FragmentCommentRoomBinding>() {
    override fun createViewModel(): Class<CommentViewModel> {
        return CommentViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_comment_room
    }

    override fun initView() {
        dataBinding.recyclerViewComment.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CommentAdapter(mutableListOf())
        }
        val bundle = arguments
        val data = bundle?.getParcelable<AddRoomModel>("bundle")
        data?.let {
            viewModel.idRoom = data.id
            setImageViewResource(this.img_1_detail, data.imageRoom1)
            setImageViewResource(this.img_2_detail, data.imageRoom2)
            viewModel.getComment(it)
        }

    }

    override fun bindViewModel() {
        viewModel.listCommentLiveData.observe(this, {
            (dataBinding.recyclerViewComment.adapter as CommentAdapter).setList(it)
        })

        viewModel.listener.observe(this, {
            if (it == 1) {
                Toast.makeText(context, getString(R.string.error_isEmpty), Toast.LENGTH_LONG).show()
            }
        })
    }
}