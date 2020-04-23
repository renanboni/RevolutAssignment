package com.example.revolutassingment.domain

import io.reactivex.Scheduler

interface SchedulerProvider {
    val mainThread: Scheduler
    val io: Scheduler
    val newThread: Scheduler
}