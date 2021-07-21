package com.kujira.homestay.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentAccountBinding
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class AccountFragment : BaseFragment<AccountViewModel,FragmentAccountBinding>() {

    override fun createViewModel(): Class<AccountViewModel> {
        return AccountViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_account

    override fun initView() {
        activity.btn_home.setTextColor(context.getColor(R.color.black))
        activity.btn_manager.setTextColor(context.getColor(R.color.black))
        activity. btn_account.setTextColor(context.getColor(R.color.rdc))
        viewModel.updateUI()
    }

    override fun bindViewModel() {

        viewModel.listener.observe(this,{

        })

    }
}