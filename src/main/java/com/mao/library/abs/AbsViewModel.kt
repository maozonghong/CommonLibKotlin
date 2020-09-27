package com.mao.library.abs

import androidx.lifecycle.*
import com.mao.library.http.AbsTask
import com.mao.library.interfaces.OnTaskCompleteListener
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * Created by maozonghong
 * on 2020/8/6
 */
class AbsViewModel:ViewModel() {

    var isShowLoading = MutableLiveData<Boolean>()//是否显示loading

    private fun showLoading() {
        isShowLoading.value = true
    }

    private fun dismissLoading() {
        isShowLoading.value = false
    }

    fun <T : Serializable?> launcher(task: AbsTask<T>, isShowLoading: Boolean = false){
        viewModelScope.launch {
            task?.let {
                if(isShowLoading) showLoading()
                task.addListenerWithOutPost(object:OnTaskCompleteListener<T>{
                    override fun onTaskComplete(result: T?) {
                        dismissLoading()
                    }

                    override fun onTaskFailed(error: String?, code: Int) {
                        dismissLoading()
                    }

                    override fun onTaskCancel() {
                        dismissLoading()
                    }

                    override fun onTaskLoadMoreComplete(result: T?) {
                    }

                })
                task.start()
            }
        }
    }
}