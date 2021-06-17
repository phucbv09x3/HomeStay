package com.kujira.homestay.utils.widget.baseadapter

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by OpenYourEyes on 12/29/20
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
