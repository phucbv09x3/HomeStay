package com.kujira.homestay.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Created by Created by Phucbv on 5/2021
 */
class ListLiveData<T> : MutableLiveData<MutableList<T>>()

fun <T> LiveData<T>.hasValue(): Boolean {
    return value?.let {
        true
    } ?: kotlin.run {
        false
    }
}


fun <T> Flow<T>.trackingProgress(progressBar: PublishSubject<Boolean>): Flow<T> {
    return this.onStart {
        printLog("onStart")
        progressBar.onNext(true)
    }.onCompletion {
        printLog("onCompletion")
        progressBar.onNext(false)
    }
}