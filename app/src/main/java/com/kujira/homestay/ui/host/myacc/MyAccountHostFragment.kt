package com.kujira.homestay.ui.host.myacc

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentAccountHostBinding
import com.kujira.homestay.ui.all_login.login_new.LoginActivity
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_host_main.*

class MyAccountHostFragment : BaseFragment<MyAccountHostViewModel, FragmentAccountHostBinding>() {
    override fun createViewModel(): Class<MyAccountHostViewModel> {
        return MyAccountHostViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_account_host

    override fun initView() {
        activity.btn_managerRoom_on_main.setTextColor(context.getColor(R.color.black))
        activity.btn_add_room_on_main.setTextColor(context.getColor(R.color.black))
        activity.btn_my_account_on_main.setTextColor(context.getColor(R.color.rdc))
        viewModel.updateUI()
    }

    override fun bindViewModel() {
        dataBinding.tvEdit.setOnClickListener {
            showDialogEdit()
        }

        dataBinding.tvLogout.setOnClickListener {
            showDialogLogout()
        }

        dataBinding.rule.setOnClickListener {
            showDialogRule()
        }

    }

    private fun showDialogEdit() {
        val alertDialog = AlertDialog.Builder(context).create()
        val dialogView = layoutInflater.inflate(R.layout.custom_change_acc, null)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setView(dialogView)

        val nameNew = dialogView.findViewById<EditText>(R.id.edt_new_name).text
        val phoneNew = dialogView.findViewById<EditText>(R.id.edt_new_phone).text
        dialogView.findViewById<Button>(R.id.btn_change_acc).setOnClickListener {
            if (nameNew.isNotEmpty() && phoneNew.isNotEmpty()){
                viewModel.changeAcc(nameNew.toString(), phoneNew.toString())
                viewModel.listener.observe(this, {
                    if (it == MyAccountHostViewModel.SUCCESS) {
                        alertDialog.dismiss()
                        Toast.makeText(context, getString(R.string.success), Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
                    }
                })
            }else{
                Toast.makeText(context,"Vui lòng nhập đầy đủ thông tin !",Toast.LENGTH_LONG).show()
            }

        }
        alertDialog.show()
    }

    private fun showDialogLogout() {
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setTitle("Đăng xuất")
        alertDialog.setMessage("Bạn vẫn muốn đăng xuất!")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { _, _ ->
            viewModel.logOut()
            context.startActivity(Intent(context,LoginActivity::class.java))
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showDialogRule() {
        val alertDialog = AlertDialog.Builder(context).create()
        val dialogView = layoutInflater.inflate(R.layout.custom_rule, null)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setView(dialogView)
        dialogView.findViewById<Button>(R.id.btn_showed).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}