package com.example.revolutassingment.domain

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultSchedulerProvider @Inject constructor() : SchedulerProvider {
    override val mainThread: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val io: Scheduler
        get() = Schedulers.io()
    override val newThread: Scheduler
        get() = Schedulers.newThread()
}