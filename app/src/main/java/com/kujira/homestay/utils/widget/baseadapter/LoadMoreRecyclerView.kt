package com.kujira.homestay.utils.widget.baseadapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kujira.homestay.utils.printLog


/**
 * Created by Created by Phucbv on 5/2021
 */
class LoadMoreRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : RecyclerView(context, attrs, def) {
    private val visibleThresholds = 5
    private var isLoading = false
    var pastVisibleItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    lateinit var triggerLoadMore: ITriggerPaging
    fun setLoaded() {
        isLoading = false
        triggerLoadMore.paging.onNext(PagingType.None)
    }

    fun addListenerLoadMore(triggerLoadMore: ITriggerPaging) {
        this.triggerLoadMore = triggerLoadMore
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val mLayoutManager = layoutManager as LinearLayoutManager
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                    val isBottom =
                        (visibleItemCount + pastVisibleItems + visibleThresholds) >= totalItemCount
                    val isNotLoadMore = triggerLoadMore.paging.value == PagingType.None
                    if (isBottom && isNotLoadMore && !triggerLoadMore.isEndOf) {
                        printLog("addOnScrollListener ${triggerLoadMore.paging.value} isEndOf ${triggerLoadMore.isEndOf}")
                        (adapter as? TriggerLoadMore)?.showItemLoadMore()
                        triggerLoadMore.paging.onNext(PagingType.LoadMore)
                    }
                }
            }
        })
    }

}

