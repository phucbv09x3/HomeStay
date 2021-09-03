package com.kujira.homestay.ui.host.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.databinding.FragmentDetailBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.client.comment.CommentAdapter

class DetailFragment : BaseFragment<DetailRoomViewModel , FragmentDetailBinding>() {
    override fun createViewModel(): Class<DetailRoomViewModel> {
        return DetailRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int {
       return  R.layout.fragment_detail
    }

    override fun initView() {
        dataBinding.rcyDetailHost.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CommentAdapter(mutableListOf())
        }
        val bundle = arguments
        val data = bundle?.getParcelable<AddRoomModel>("bundle")
        data?.let {
            dataBinding.nameRoomDetail.text = it.nameRoom
            viewModel.getComment(it)
        }
        dataBinding.tvBackFromDetail.setOnClickListener {
            navigators.navigateUp()
        }
    }

    override fun bindViewModel() {
        viewModel.listCommentLiveData.observe(this, {
            (dataBinding.rcyDetailHost.adapter as CommentAdapter).setList(it)
        })
    }
}