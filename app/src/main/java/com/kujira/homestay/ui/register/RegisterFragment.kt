package com.kujira.homestay.ui.register

import kotlinx.android.synthetic.main.register_fragment.*
import com.kujira.homestay.R
import com.kujira.homestay.databinding.RegisterFragmentBinding
import com.kujira.homestay.ui.base.BaseFragment

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class RegisterFragment : BaseFragment<RegisterViewModel, RegisterFragmentBinding>() {
    override fun createViewModel(): Class<RegisterViewModel> {
        return RegisterViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.register_fragment
    }

    override fun bindViewModel() {
    }

    override fun initView() {
        buttonRegister.setOnClickListener {
            navigators.navigate(R.id.aboutFragment)
        }
    }

}