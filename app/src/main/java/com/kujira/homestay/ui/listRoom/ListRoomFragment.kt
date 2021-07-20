package com.kujira.homestay.ui.listRoom

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentListRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list_room.*


class ListRoomFragment : BaseFragment<ListRoomViewModel, FragmentListRoomBinding>(), IChooseRoom {
    override fun createViewModel(): Class<ListRoomViewModel> {
        return ListRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_list_room
    }

    override fun initView() {
        dataBinding.rcyListRoom.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListRoomAdapter(mutableListOf(), this@ListRoomFragment)
        }
    }

    override fun bindViewModel() {
        viewModel.getListRoom()
        viewModel.listRoomLiveData.observe(this, {
            (dataBinding.rcyListRoom.adapter as ListRoomAdapter).setList(it)
        })
    }

    override fun onChoose(id: String) {
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setTitle("Chọn Phòng")
        alertDialog.setMessage("Bạn xác nhận đặt phòng này")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ ->
            viewModel.chooseRoom(id)
            dialog.dismiss()
        }
        alertDialog.show()

    }
}