package com.e.cleanarchitecture.data

import android.os.Handler
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

abstract class Repository<T> {

    private val handler = Handler()

    private var threadPoolExecutor = ThreadPoolExecutor(
        POOL_SIZE,
        MAX_POOL_SIZE,
        TIMEOUT.toLong(),
        TimeUnit.SECONDS,
        ArrayBlockingQueue(POOL_SIZE)
    )

    fun execute(value: T, callback: Callback<T>) {
        threadPoolExecutor.execute {
            Thread.sleep(3000L)
            handler.post { callback.success(value) }
        }
    }

    interface Callback<T> {
        fun success(value: T)
        fun error(value: T, throwable: Throwable)
    }

    companion object {

        private val POOL_SIZE = 2

        private val MAX_POOL_SIZE = 4

        private val TIMEOUT = 15
    }
}