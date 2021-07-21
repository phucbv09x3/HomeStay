package com.kujira.homestay.ui.listRoom

import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentListRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.travel.TravelAdapter
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
        search()
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
    private fun search() {
        dataBinding.svNameProvinceListRoom.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!TextUtils.isEmpty(query?.trim())) {
                    viewModel.searchHomeStay(query)
                    viewModel.listRoomLiveData.observe(this@ListRoomFragment, {
                        Log.d("list", "$it")
                        (dataBinding.rcyListRoom.adapter as ListRoomAdapter).setList(it)
                    })


                } else {
                    viewModel.getListRoom()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!TextUtils.isEmpty(newText?.trim())) {
                    viewModel.searchHomeStay(newText)
                    viewModel.listRoomLiveData.observe(this@ListRoomFragment, {
                        Log.d("list", "$it")
                        (dataBinding.rcyListRoom.adapter as ListRoomAdapter).setList(it)
                    })
                } else {
                    viewModel.getListRoom()
                }
                return false
            }

        })
    }

}