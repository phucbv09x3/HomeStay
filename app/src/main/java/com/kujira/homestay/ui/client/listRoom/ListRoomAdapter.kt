package com.kujira.homestay.ui.client.listRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kujira.homestay.R
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.databinding.ItemListRoomBinding
import com.kujira.homestay.utils.Constants
import kotlinx.android.synthetic.main.item_list_room.view.*

class ListRoomAdapter(var listRoom: MutableList<AddRoomModel>, var onChoose: IChooseRoom) :
    RecyclerView.Adapter<ListRoomAdapter.ManagerHolder>() {
    fun setList(mutableList: MutableList<AddRoomModel>) {
        listRoom = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListRoomAdapter.ManagerHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemListRoomBinding.inflate(inflate, parent, false)
        return ManagerHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ListRoomAdapter.ManagerHolder, position: Int) {
        holder.setUp(listRoom[position])
        holder.chooseRoom(listRoom[position].id)
    }

    override fun getItemCount(): Int = listRoom.size
    inner class ManagerHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setUp(itemData: AddRoomModel) {
            binding.executePendingBindings()
            binding.setVariable(BR.model, itemData)
            Glide.with(itemView.context).load(itemData.imageRoom1)
                .into(itemView.img_room)
            itemView.title_room.text = itemData.typeRoom
            itemView.address_room.text = itemData.address
            itemView.price_room.text = "Giá : " + itemData.price + "(VND)"
            itemView.detail_host_room.text = "Phòng :" + itemData.nameRoom
            itemView.detail_room.text =
                itemData.s_room + " (m2) - " + itemData.numberSleepRoom + "Phòng ngủ"
            itemView.tv_status.text = itemData.status
            if (itemData.status== Constants.BOOKED_ROOM){
                itemView.tv_status.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.red
                    )
                )
                itemView.choose_room.visibility = View.GONE
            }else{
                itemView.tv_status.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
                itemView.choose_room.visibility = View.VISIBLE
            }
        }

        fun chooseRoom(id: String) {
            itemView.choose_room.setOnClickListener {
                onChoose.onChoose(id)
            }
        }
    }
}

interface IChooseRoom {
    fun onChoose(id: String)
}