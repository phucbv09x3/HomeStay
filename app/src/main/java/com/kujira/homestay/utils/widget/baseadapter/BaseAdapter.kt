package com.kujira.homestay.utils.widget.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kujira.homestay.BR

/**
 * Created by Created by Phucbv on 5/2021
 */


class BaseAdapter<M>(
    context: Context,
    @LayoutRes
    private val layoutRes: Int,
    private val onItemClick: ((M) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mList = mutableListOf<M>()
    private lateinit var recyclerView: RecyclerView


    var onItemClickWithSharedElement: ((model: M, rootView: View) -> Unit)? =
        null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun updateList(list: MutableList<M>) {
        (recyclerView as? LoadMoreRecyclerView)?.setLoaded()
        val size = mList.size
        mList = list
        notifyItemInserted(size)
    }

    private fun getItem(position: Int): M {
        return mList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: ViewDataBinding =
            DataBindingUtil.inflate(layoutInflater, layoutRes, parent, false)

        val localItemClick: (M, View) -> Unit = { model, rootView ->
            onItemClick?.invoke(model) ?: kotlin.run {
                onItemClickWithSharedElement?.invoke(model, rootView)
            }
        }
        return BaseViewHolder(view, localItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder<M>)?.bindData(getItem(position))
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}


open class BaseViewHolder<M>(
    private val rootView: ViewDataBinding,
    private val onItemClick: ((M, View) -> Unit)?
) :
    RecyclerView.ViewHolder(rootView.root) {

    fun bindData(model: M) {
        rootView.root.setOnClickListener {
            onItemClick?.invoke(model, rootView.root)
        }
        rootView.setVariable(BR.model, model)
        rootView.executePendingBindings()
    }
}
