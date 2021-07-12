package com.kujira.homestay.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentListRoomBinding
import com.kujira.homestay.ui.base.BaseFragment


class ListRoomFragment : BaseFragment<ListRoomViewModel,FragmentListRoomBinding>() {
    override fun createViewModel(): Class<ListRoomViewModel> {
        return ListRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_list_room
    }

    override fun initView() {

    }

    override fun bindViewModel() {
        
    }
}