package com.kujira.homestay.ui.login

import android.view.View
import android.widget.Toast
import com.kujira.homestay.R
import com.kujira.homestay.databinding.LoginFragmentBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Phucbv on 5/2021
 */
class LoginFragment : BaseFragment<LoginViewModel, LoginFragmentBinding>() {

    override fun createViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.login_fragment
    }


    override fun initView() {
        activity.linear_on_main.visibility = View.GONE
        viewModel.checkCurrentUser()
        val arg = arguments
        val email = arg?.getString(Constants.EMAIL) ?: ""
        val passWord = arg?.getString(Constants.PASS) ?: ""
        viewModel.email.set(email)
        viewModel.password.set(passWord)
        viewModel.login(requireView())
        viewModel.getListAcc()

    }

    override fun bindViewModel() {
        viewModel.listener.observe(this,{
            when(it){
                LoginViewModel.EmailVerified->{
                    replaceFragment(R.id.home_fragment)
                }
                R.string.error_auth -> {
                    Toast.makeText(context,getString(R.string.error_auth), Toast.LENGTH_LONG).show()
                }
                R.string.error_isEmpty -> {
                    Toast.makeText(context,getString(R.string.error_isEmpty), Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}