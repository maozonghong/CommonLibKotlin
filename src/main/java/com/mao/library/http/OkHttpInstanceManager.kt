package com.mao.library.http

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import java.net.Proxy

/**
 * Created by maozonghong
 * on 2019/11/21
 */
object OkHttpInstanceManager {

     var httpClient: OkHttpClient = OkHttpClient.Builder().readTimeout(30000, TimeUnit.MILLISECONDS)
       .connectTimeout(40000, TimeUnit.MILLISECONDS).writeTimeout(30, TimeUnit.SECONDS)
       .retryOnConnectionFailure(true).proxy(Proxy.NO_PROXY).build()
}
