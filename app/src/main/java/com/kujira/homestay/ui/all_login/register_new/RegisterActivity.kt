package com.kujira.homestay.ui.all_login.register_new

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import com.kujira.homestay.R
import com.kujira.homestay.databinding.ActivityRegisterBinding
import com.kujira.homestay.ui.base.BaseActivity
import com.kujira.homestay.ui.base.BaseFragment

class RegisterActivity : BaseActivity<RegisterAccViewModel, ActivityRegisterBinding>() {
    override fun createViewModel(): Class<RegisterAccViewModel> {
        return RegisterAccViewModel::class.java
    }

    override fun getContentView(): Int = R.layout.activity_register

    override fun initAction() {

    }

    override fun initData() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_login_new)
        mViewModel.getListAcc()
        mViewModel.listenerShowToast.observe(this,{
            Toast.makeText(this,getString(it),Toast.LENGTH_LONG).show()
            if (it == R.string.email_verify){
                finish()
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