package com.kujira.homestay.utils.widget.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kujira.homestay.R
import com.kujira.homestay.utils.printLog

/**
 * Created by OpenYourEyes on 11/19/20
 */


class LoadMoreAdapter<M>(
    context: Context,
    @LayoutRes
    private val layoutRes: Int,
    private val onItemClick: ((M) -> Unit)? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), TriggerLoadMore {


    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var mList = mutableListOf<M>()
    private var stateLoadMore: Boolean = false
    private lateinit var recyclerView: RecyclerView


    var onItemClickWithSharedElement: ((model: M, rootView: View) -> Unit)? =
        null

    override fun showItemLoadMore() {
        printLog("showLoadMore")
        stateLoadMore = true
        recyclerView.post {
            notifyItemInserted(itemCount)
            recyclerView.smoothScrollToPosition(itemCount)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun updateList(list: MutableList<M>) {
        (recyclerView as? LoadMoreRecyclerView)?.setLoaded()
        if (stateLoadMore) {
            stateLoadMore = false
            notifyItemRemoved(itemCount)
        }
        if (list.size == 0) return
        val size = mList.size
        mList = list
        val sizeAfter = itemCount + 1
        notifyItemRangeInserted(size, sizeAfter)
    }

    private fun getItem(position: Int): M {
        return mList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == R.layout.item_load_more) {
            val loadMoreView = layoutInflater.inflate(R.layout.item_load_more, parent, false)
            return LoadMoreVH(loadMoreView)
        }
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

    override fun getItemViewType(position: Int): Int {
        return if (stateLoadMore && position == itemCount - 1) {
            R.layout.item_load_more
        } else {
            layoutRes
        }
    }

    override fun getItemCount(): Int {
        return mList.size + if (stateLoadMore) 1 else 0
    }

}


class LoadMoreVH(itemView: View) : RecyclerView.ViewHolder(itemView) {}

interface TriggerLoadMore {
    fun showItemLoadMore()
}
