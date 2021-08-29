package com.kujira.homestay.ui.register

import android.annotation.SuppressLint
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.kujira.homestay.R
import com.kujira.homestay.databinding.RegisterFragmentBinding
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Phucbv on 5/2021
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

    @SuppressLint("HardwareIds")
    override fun initView() {
        viewModel.getListAcc()
        activity.linear_on_main.visibility = View.GONE
        viewModel.onClick.observe(this, {
            when (it) {
                1 -> {
                    val id =
                        Settings.Secure.getString(
                            activity.contentResolver,
                            Settings.Secure.ANDROID_ID
                        )
                    if (viewModel.checkDeviceId(id)) {
                        Toast.makeText(context, getString(R.string.register_out), Toast.LENGTH_LONG)
                            .show()
                    } else {
                        viewModel.registerAcc()
                        viewModel.notifyRegister.observe(this, { value ->
                            when (value) {
                                R.string.error_isEmpty -> {
                                    Toast.makeText(context, getString(value), Toast.LENGTH_LONG)
                                        .show()
                                }
                                RegisterViewModel.NOTIFY_AUTH_FAIL -> {

                                }
                            }

                        })
                    }

                }

            }
        })
    }

}