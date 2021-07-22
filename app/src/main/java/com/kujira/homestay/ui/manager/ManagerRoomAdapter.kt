package com.kujira.homestay.ui.manager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.kujira.homestay.data.model.response.AddRoomModel
import com.kujira.homestay.databinding.ItemManagerBinding
import kotlinx.android.synthetic.main.item_manager.view.*

class ManagerRoomAdapter(var listRoom: MutableList<AddRoomModel>,var click : IClick) :
    RecyclerView.Adapter<ManagerRoomAdapter.ManagerHolder>() {
    fun setList(mutableList: MutableList<AddRoomModel>) {
        listRoom = mutableList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManagerRoomAdapter.ManagerHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemManagerBinding.inflate(inflate, parent, false)
        return ManagerHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ManagerRoomAdapter.ManagerHolder, position: Int) {
        holder.setUp(listRoom[position])
        holder.click(listRoom[position].id)
        holder.longClick(listRoom[position])
    }

    override fun getItemCount(): Int = listRoom.size
    inner class ManagerHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setUp(itemData: AddRoomModel) {
            binding.executePendingBindings()
            binding.setVariable(BR.model, itemData)
            itemView.name_room.text = itemData.nameRoom
            itemView.status.text = itemData.status
            itemView.address_room.text = itemData.address
        }

        fun click(id:String){
            itemView.btn_cancel_room.setOnClickListener {
                click.clickCancel(id)
            }
        }
        fun longClick(addRoomModel: AddRoomModel){
            itemView.setOnLongClickListener {
                click.longClick(addRoomModel)
                return@setOnLongClickListener true
            }
        }
    }

}

interface IClick{
    fun clickCancel(id:String)
    fun longClick(addRoomModel: AddRoomModel)
}