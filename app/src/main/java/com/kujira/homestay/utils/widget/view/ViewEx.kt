package com.kujira.homestay.utils.widget.view

import android.view.View
import io.reactivex.Observable

/**
 * Created by Created by Phucbv on 5/2021
 */
fun View.click(): Observable<Unit> {
    return Observable.create {
        it.onNext(Unit)
    }
}