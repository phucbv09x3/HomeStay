package com.kujira.homestay.ui.manager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentManagerRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class ManagerRoomFragment : BaseFragment<ManagerRoomViewModel,FragmentManagerRoomBinding>() {
    override fun createViewModel(): Class<ManagerRoomViewModel> {
        return ManagerRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_manager_room

    override fun initView() {
        activity.btn_home.setTextColor(context.getColor(R.color.black))
        activity.btn_manager.setTextColor(context.getColor(R.color.rdc))
        activity.btn_account.setTextColor(context.getColor(R.color.black))
    }

    override fun bindViewModel() {

    }
}