package com.kujira.homestay.utils.widget.baseadapter

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Created by Phucbv on 5/2021
 */
interface ITriggerPaging {
    val paging: BehaviorSubject<PagingType>
    var isEndOf: Boolean
    var limit: Int
    var page: Int
}

enum class PagingType {
    Refresh, LoadMore, None
}
