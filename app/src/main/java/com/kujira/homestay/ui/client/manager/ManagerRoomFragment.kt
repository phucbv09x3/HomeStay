package com.kujira.homestay.ui.client.manager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.databinding.FragmentManagerRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class ManagerRoomFragment : BaseFragment<ManagerRoomViewModel, FragmentManagerRoomBinding>(),
    IClick {
    override fun createViewModel(): Class<ManagerRoomViewModel> {
        return ManagerRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_manager_room

    override fun initView() {
        activity.btn_home.setTextColor(context.getColor(R.color.black))
        activity.btn_manager.setTextColor(context.getColor(R.color.rdc))
        activity.btn_account.setTextColor(context.getColor(R.color.black))
        dataBinding.rcyMyRoom.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ManagerRoomAdapter(mutableListOf(), this@ManagerRoomFragment)
        }
    }


    override fun bindViewModel() {
        viewModel.managerMyRoom()
        viewModel.listRoomLiveData.observe(this, {
            (dataBinding.rcyMyRoom.adapter as ManagerRoomAdapter).setList(it)
        })
    }

    override fun clickCancel(id: String) {
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setTitle(Constants.TITLE_CANCEL_ROOM)
        alertDialog.setMessage(Constants.ACCESS_CANCEL_ROOM)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, Constants.OK_DIALOG
        ) { dialog, _ ->
            viewModel.cancelRoom(id)
            dialog.dismiss()
        }
        alertDialog.show()
    }

    override fun longClick(addRoomModel: AddRoomModel) {
        viewModel.getDetail(addRoomModel)
        viewModel.modelShowHost.observe(this, {
            it?.let {
                val alertDialog = AlertDialog.Builder(context).create()
                val dialogView = layoutInflater.inflate(R.layout.custom_dialog_show, null)
                alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                alertDialog.setView(dialogView)
                dialogView.findViewById<TextView>(R.id.name_show).text = it.userName
                dialogView.findViewById<TextView>(R.id.phone_show).text = it.phone
                alertDialog.show()
            }
        })

    }
}