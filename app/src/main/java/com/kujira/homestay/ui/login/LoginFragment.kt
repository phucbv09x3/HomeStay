package com.kujira.homestay.ui.login

import com.kujira.homestay.R
import com.kujira.homestay.databinding.LoginFragmentBinding
import com.kujira.homestay.ui.base.BaseFragment

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class LoginFragment : BaseFragment<LoginViewModel, LoginFragmentBinding>() {
    override fun createViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.login_fragment
    }


    override fun initView() {
    }

    override fun bindViewModel() {

    }
}