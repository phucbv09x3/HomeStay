package com.kujira.homestay.ui.account

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentAccountBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>() {

    override fun createViewModel(): Class<AccountViewModel> {
        return AccountViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_account

    override fun initView() {
        activity.btn_home.setTextColor(context.getColor(R.color.black))
        activity.btn_manager.setTextColor(context.getColor(R.color.black))
        activity.btn_account.setTextColor(context.getColor(R.color.rdc))
        viewModel.updateUI()
    }

    override fun bindViewModel() {

        viewModel.listener.observe(this, {
            when (it) {
                AccountViewModel.CHANGE_ACC -> {
                    val alertDialog = AlertDialog.Builder(context).create()
                    val dialogView = layoutInflater.inflate(R.layout.custom_change_acc, null)
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    alertDialog.setView(dialogView)

                    val nameNew = dialogView.findViewById<EditText>(R.id.edt_new_name).text
                    val phoneNew = dialogView.findViewById<EditText>(R.id.edt_new_phone).text
                    dialogView.findViewById<Button>(R.id.btn_change_acc).setOnClickListener {
                        viewModel.changeAcc(nameNew.toString(), phoneNew.toString())
                        viewModel.listener.observe(this, { listener ->
                            if (listener == AccountViewModel.SUCCESS_CHANGE) {
                                alertDialog.dismiss()
                                Toast.makeText(
                                    context,
                                    getString(R.string.success),
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    getString(R.string.error),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                    alertDialog.show()
                }
                AccountViewModel.LOG_OUT -> {
                    val alertDialog = android.app.AlertDialog.Builder(context).create()
                    alertDialog.setTitle(Constants.LOG_OUT)
                    alertDialog.setMessage(Constants.ACCESS_LOGOUT)
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL, Constants.OK_DIALOG
                    ) { dialog, _ ->
                        viewModel.logOut()
                        replaceFragment(R.id.loginFragment)
                        alertDialog.dismiss()
                    }
                    alertDialog.show()

                }
            }

        })

    }
}