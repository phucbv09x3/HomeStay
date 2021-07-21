package com.kujira.homestay.ui.manager

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentManagerRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class ManagerRoomFragment : BaseFragment<ManagerRoomViewModel, FragmentManagerRoomBinding>(),IClick {
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
            adapter = ManagerRoomAdapter(mutableListOf(),this@ManagerRoomFragment)
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
        alertDialog.setTitle("Hủy Phòng")
        alertDialog.setMessage("Bạn xác nhận hủy đặt phòng này!")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ ->
            viewModel.cancelRoom(id)
            dialog.dismiss()
        }
        alertDialog.show()
    }
}