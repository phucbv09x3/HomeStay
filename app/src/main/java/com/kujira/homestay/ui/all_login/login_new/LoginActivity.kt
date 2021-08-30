package com.kujira.homestay.ui.all_login.login_new

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import com.kujira.homestay.R
import com.kujira.homestay.databinding.ActivityLoginBinding
import com.kujira.homestay.ui.all_login.register_new.RegisterActivity
import com.kujira.homestay.ui.base.BaseActivity
import com.kujira.homestay.ui.base.BaseFragment

class LoginActivity : BaseActivity<LoginAccViewModel, ActivityLoginBinding>() {
    override fun createViewModel(): Class<LoginAccViewModel> {
        return LoginAccViewModel::class.java
    }

    override fun getContentView(): Int = R.layout.activity_login

    override fun initAction() {

    }

    override fun initData() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_login_new)
        listener()
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                //  you will get result here in result.data
                Log.d("result.data", "${result.data}")
            }

        }

    private fun listener() {
        mViewModel.listenerClick.observe(this, {
            when (it) {
                LoginAccViewModel.LOGIN -> {

                }
                LoginAccViewModel.REGISTER_ACC -> {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startForResult.launch(intent)
                }
                LoginAccViewModel.FORGOT_PASSWORD -> {

                }
            }
        })
    }

    override fun onFragmentResumed(fragment: BaseFragment<*, *>) {

    }

    override fun navigate(fragmentId: Int, bundle: Bundle?, addToBackStack: Boolean) {

    }

    override fun navigateWithSharedElement(
        fragmentId: Int,
        bundle: Bundle?,
        sharedElements: FragmentNavigator.Extras?,
        addToBackStack: Boolean
    ) {

    }

    override fun navigateUp() {

    }

    override fun present(fragmentId: Int, bundle: Bundle?) {

    }
}