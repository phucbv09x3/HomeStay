package com.kujira.homestay.data

import com.kujira.homestay.data.local.DataStoreHelper
import io.reactivex.subjects.PublishSubject

/**
 * Created by Phucbv on 5/2021
 */
interface DataManager : DataStoreHelper {
    val listenerHomeClientCallBack : PublishSubject<Int>
}