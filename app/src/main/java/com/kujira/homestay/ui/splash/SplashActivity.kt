package com.kujira.homestay.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigator
import com.kujira.homestay.R
import com.kujira.homestay.databinding.ActivitySplashBinding
import com.kujira.homestay.ui.all_login.login_new.LoginActivity
import com.kujira.homestay.ui.base.BaseActivity
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.client.main.MainActivity
import com.kujira.homestay.ui.host.main.MainHostActivity

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {
    override fun createViewModel(): Class<SplashViewModel> {
        return SplashViewModel::class.java
    }

    override fun getContentView(): Int {
        return R.layout.activity_splash
    }

    override fun onStart() {
        super.onStart()
        mViewModel.checkCurrentUser()
        mViewModel.autoLogin.observe(this, {
            when (it) {
                1 -> {
                    startActivity(Intent(this, MainHostActivity::class.java))
                    finish()
                }
                2 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                0 -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        })
    }

    override fun initAction() {

    }

    override fun initData() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
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