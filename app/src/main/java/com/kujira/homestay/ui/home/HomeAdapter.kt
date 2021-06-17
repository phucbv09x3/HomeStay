package com.kujira.homestay.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.databinding.ItemProvinceBinding
import kotlinx.android.synthetic.main.item_province.view.*

class HomeAdapter(var listProvince : MutableList<Provinces>) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeHolder {
        val inflate = LayoutInflater.from(parent.context)
        val dataBinding = ItemProvinceBinding.inflate(inflate, parent, false)
        return HomeHolder(dataBinding)
    }

    fun setList(mutableList: MutableList<Provinces>){
        this.listProvince = mutableList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: HomeAdapter.HomeHolder, position: Int) {
       holder.setUp(listProvince[position])
    }

    override fun getItemCount(): Int = listProvince.size

    inner class HomeHolder(
        private var dataBiding: ViewDataBinding
    ) : RecyclerView.ViewHolder(dataBiding.root) {
        fun setUp(itemData: Provinces) {
            dataBiding.executePendingBindings()
            dataBiding.setVariable(BR.model, itemData)
            Glide.with(itemView.context).load(itemData.imageUrl).error(R.drawable.homestay)
                .into(itemView.circle_image_province)
            itemView.tv_name_province.text = itemData.name
        }
    }
}