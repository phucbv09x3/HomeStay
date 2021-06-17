package com.kujira.homestay.data.scheduler

import io.reactivex.Scheduler

interface ISchedulerProvider {
    val computation: Scheduler
    val io: Scheduler
    val ui: Scheduler
}