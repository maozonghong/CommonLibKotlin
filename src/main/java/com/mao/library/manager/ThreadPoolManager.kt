package com.mao.library.manager

import java.util.concurrent.*

/**
 * Created by maozonghong
 * on 2019/11/21
 */
class ThreadPoolManager {
    //伴生对象
    companion object{
        private val imageExecutorService: ThreadPoolExecutor
        private val downloadExecutorService: ThreadPoolExecutor
        private val httpExecutorService: ThreadPoolExecutor
        private val scheduledExecutorService: ScheduledExecutorService
        private val cacheExecutorService: ThreadPoolExecutor

        init {
            var core = Runtime.getRuntime().availableProcessors()
            if (core < 2) {
                core = 2
            }
            if (core > 8) {
                core = 8
            }

            imageExecutorService = Executors.newFixedThreadPool(core shl 1) as ThreadPoolExecutor
            downloadExecutorService = Executors.newFixedThreadPool(3) as ThreadPoolExecutor
            httpExecutorService = Executors.newFixedThreadPool(core shl 1) as ThreadPoolExecutor
            scheduledExecutorService=Executors.newScheduledThreadPool(core)
            cacheExecutorService = Executors.newCachedThreadPool() as ThreadPoolExecutor
        }

        fun imageExecute(runnable: Runnable) {
            imageExecutorService.execute {
                try {
                    cacheExecutorService.submit(runnable).get(10, TimeUnit.SECONDS)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun <T> imageSubmit(callable: Callable<T>): Future<T> {
            return imageExecutorService.submit(callable)
        }

        @JvmStatic
        fun downloadExecute(runnable: Runnable) {
            downloadExecutorService.execute(runnable)
        }

        fun <T> downloadSubmit(callable: Callable<T>): Future<T> {
            return downloadExecutorService.submit(callable)
        }

        fun httpExecute(runnable: Runnable) {
            httpExecutorService.execute(runnable)
        }

        fun <T> httpSubmit(callable: Callable<T>): Future<T> {
            return httpExecutorService.submit(callable)
        }

        @JvmStatic
        fun cacheExecute(runnable: Runnable) {
            cacheExecutorService.execute(runnable)
        }

        fun <T> cacheExecute(callable: Callable<T>): Future<T> {
            return cacheExecutorService.submit(callable)
        }
    }





}
