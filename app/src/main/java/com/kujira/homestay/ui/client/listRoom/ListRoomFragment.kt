package com.kujira.homestay.ui.client.listRoom

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.data.model.response.NotificationData
import com.kujira.homestay.databinding.FragmentListRoomBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.client.service.FirebaseFCMService
import com.kujira.homestay.ui.client.service.PushNotification
import com.kujira.homestay.ui.client.service.RetrofitInstance
import com.kujira.homestay.utils.Constants
import kotlinx.android.synthetic.main.fragment_list_room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListRoomFragment : BaseFragment<ListRoomViewModel, FragmentListRoomBinding>(), IChooseRoom {
    private var token = "";
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
        viewModel.listenerScc.observe(this,{
            if(it == 1) {
                Toast.makeText(context,"Thành công",Toast.LENGTH_LONG).show()
            }
        })
        viewModel.listenerToken.observe(this,{
            if(it.isNotEmpty()) {
                PushNotification(
                    NotificationData("Thông báo book phòng", "Bạn có 1 phòng có khách book !"),
                    it )
                    .also { pushNoti->
                        sendNotification(pushNoti)
                    }
                return@observe
            }
        })

    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {

            } else {

            }
        } catch(e: Exception) {

        }
    }
    override fun onChoose(id: String) {
        viewModel.getTokenFromId(id)
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setTitle(Constants.CHOOSE_ROOM)
        alertDialog.setMessage(Constants.ACCESS_CHOOSE_ROOM)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, Constants.OK_DIALOG
        ) { dialog, _ ->
            viewModel.chooseRoom(id)
            dialog.dismiss()
        }
        alertDialog.show()

    }
//    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val response = RetrofitInstance.api.postNotification(notification)
//            if(response.isSuccessful) {
//
//            } else {
//
//            }
//        } catch(e: Exception) {
//
//        }
//    }
    private fun search() {
        dataBinding.svNameProvinceListRoom.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!TextUtils.isEmpty(query?.trim())) {
                    viewModel.searchHomeStay(query)
                    viewModel.listRoomLiveData.observe(this@ListRoomFragment, {
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
                        (dataBinding.rcyListRoom.adapter as ListRoomAdapter).setList(it)
                    })
                } else {
                    viewModel.getListRoom()
                }
                return false
            }

        })
    }

    override fun clickRoom(addRoomModel: AddRoomModel) {
        val bundle = Bundle()
        bundle.putParcelable("bundle",addRoomModel)
        navigators.navigate(R.id.comment_fragment,bundle)
    }
}