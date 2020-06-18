package com.mao.library.task

import android.content.Context
import com.mao.library.http.AbsRequest
import com.mao.library.http.AbsTask
import com.mao.library.interfaces.OnTaskCompleteListener
import java.io.Serializable


/**
 * Created by maozonghong
 * on 2020/6/16
 */
abstract class AbsBaseTask<T:Serializable> @JvmOverloads constructor(context: Context, request: AbsRequest?,
                                                                     completeListener: OnTaskCompleteListener<T>?
) : AbsTask<T>(context, request,completeListener){

     override  fun getRequestUrl(): String? {
        return null
    }
}