package com.kujira.homestay.ui.travel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.data.model.TravelModel
import com.kujira.homestay.databinding.FragmentListTravelBinding
import com.kujira.homestay.databinding.ItemProvinceBinding
import com.kujira.homestay.databinding.ItemTravelBinding
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_province.view.*
import kotlinx.android.synthetic.main.item_province.view.tv_name_province
import kotlinx.android.synthetic.main.item_travel.view.*

class TravelAdapter(var listTravel : MutableList<TravelModel>) : RecyclerView.Adapter<TravelAdapter.TravelHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelAdapter.TravelHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemTravelBinding.inflate(inflate, parent, false)
        return TravelHolder(dataBinding)
    }

    fun setList(mutableList: MutableList<TravelModel>) {
        this.listTravel = mutableList
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: TravelAdapter.TravelHolder, position: Int) {
        holder.setUp(listTravel[position])
    }

    override fun getItemCount(): Int =listTravel.size
    inner class TravelHolder(
        var binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun setUp(itemData : TravelModel){
            binding.executePendingBindings()
            binding.setVariable(BR.model, itemData)
            val pathOf = itemView.findViewById<ImageView>(R.id.img_travel)
            Glide.with(pathOf).load(itemData.img).error(R.drawable.dulich).into(pathOf)
//            Glide.with(itemView.context).load(itemData.img).error(R.drawable.homestay)
//                .into(itemView.img_travel)
            itemView.tv_name_province_travel.text = itemData.address
            itemView.tv_detail_travel.text = itemData.detail
        }
    }
}