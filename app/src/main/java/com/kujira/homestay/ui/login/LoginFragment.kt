package com.kujira.homestay.ui.login

import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
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

        dataBinding.tvForgotPassword.setOnClickListener {
            forgotPassWord()
        }
    }

    private fun forgotPassWord() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Đặt lại mật khẩu")
        val linearLayout = LinearLayout(context)

        val edtMail = EditText(context)
        linearLayout.addView(edtMail)
        builder.setView(linearLayout)
        edtMail.hint = "Nhập email bạn cần khôi phục mật khẩu"
        builder.setPositiveButton("Gửi") { _: DialogInterface?, _: Int ->
            val mAuth =
                FirebaseAuth.getInstance().sendPasswordResetEmail(edtMail.text.toString().trim())
                    .addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Thành công ! Vui lòng kiểm tra mail và đặt lại mật khẩu",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Send failed....",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
        builder.setNegativeButton("Không") { dialog: DialogInterface?, _: Int ->
            dialog!!.dismiss()
        }
        builder.show()

    }

    override fun bindViewModel() {
        viewModel.listener.observe(this, {
            when (it) {
                LoginViewModel.EmailVerified -> {
                    replaceFragment(R.id.home_fragment)
                }
                R.string.error_auth -> {
                    Toast.makeText(context, getString(R.string.error_auth), Toast.LENGTH_LONG)
                        .show()
                }
                R.string.error_isEmpty -> {
                    Toast.makeText(context, getString(R.string.error_isEmpty), Toast.LENGTH_LONG)
                        .show()
                }
                R.string.not_exit_account -> {
                    Toast.makeText(context, getString(R.string.not_exit_account), Toast.LENGTH_LONG)
                        .show()
                }
            }
        })

    }
}