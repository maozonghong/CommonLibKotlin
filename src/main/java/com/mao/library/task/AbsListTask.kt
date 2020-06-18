package com.mao.library.task

import android.content.Context
import com.mao.library.abs.AbsModel
import com.mao.library.http.AbsRequest
import com.mao.library.interfaces.AdapterInterface
import com.mao.library.interfaces.AdapterViewInterface
import com.mao.library.interfaces.OnTaskCompleteListener
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Constructor
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*


/**
 * Created by maozonghong
 * on 2020/6/16
 */
abstract class AbsListTask<T:AbsModel> @JvmOverloads
constructor(context: Context, request: AbsRequest?, onTaskCompleteListener:
OnTaskCompleteListener<ArrayList<T>>?) :AbsBaseTask<ArrayList<T>>(context,request,onTaskCompleteListener),
    OnTaskCompleteListener<ArrayList<T>?> {

    private var listView: AdapterViewInterface? = null

    private var clz: Class<out AbsModel?>? = null

    constructor(listView: AdapterViewInterface,request: AbsRequest?,
                onTaskCompleteListener:OnTaskCompleteListener<ArrayList<T>>?):
            this(listView.getContext(),request,onTaskCompleteListener){
            this.listView=listView
        addListener(this)
        initClass()
        initListViewListeners(listView)
    }

    constructor(listView: AdapterViewInterface,request: AbsRequest?):
            this(listView,request,null)


    private fun initClass() {
        var genType: Type? = javaClass.genericSuperclass
        while (genType is Class<*>) {
            genType = (genType as Class<*>?)?.genericSuperclass
        }
        var params: Array<Type> = (genType as ParameterizedType)?.actualTypeArguments
        clz = params[0] as Class<out AbsModel>
    }


    protected open fun initListViewListeners(listView: AdapterViewInterface){

    }



    override fun onTaskComplete(result: ArrayList<T>?) {
        val adapter = listView?.getAbsAdapter() as? AdapterInterface<T>
        adapter?.setList(result)
        onTaskFinish()
    }

    protected open fun onTaskFinish() {
        listView?.hideProgress()
    }

    override fun onTaskCancel() {
        onTaskFinish()
    }

    override fun onTaskFailed(error: String?, code: Int) {
        onTaskFinish()
    }

    override fun onTaskLoadMoreComplete(result: ArrayList<T>?) {
    }

    override fun start(isLoadMore: Boolean, isRestart: Boolean) {
        super.start(isLoadMore, isRestart)
        if (listView != null && !isLoadMore) {
            listView?.showProgress()
        }
    }

    override fun parseJson(json: JSONObject?): ArrayList<T>? {
        if (clz == null) return null
        var list: ArrayList<T> = ArrayList()
        try {
            var constructor =
                clz?.getConstructor(JSONObject::class.java) as? Constructor<T>
            constructor?.let {
                val array: JSONArray? = getJsonArray(json)
                if (array != null && array.length() > 0) {
                    for (i in 0 until array.length()) {
                        val jsonObject = array.optJSONObject(i)
                        if (jsonObject != null) {
                            list.add(constructor.newInstance(jsonObject))
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return list
    }


    protected open fun getJsonArray(json: JSONObject?): JSONArray? {
        return json?.optJSONArray("items")
    }
}