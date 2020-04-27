package com.mao.library.abs

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mao.library.interfaces.AdapterInterface


import java.util.ArrayList

abstract class AbsRecyclerAdapter<T, V : ViewDataBinding> :
    RecyclerView.Adapter<DataBoundViewHolder<V>>, AdapterInterface<T> {

    var onItemClickListener:OnItemClickListener? =null

    private var mList: ArrayList<T>? = null

    constructor(list: ArrayList<T>) {
        this.mList = list
    }

    constructor() {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {

        bind(holder.binding, position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, position: Int)

    override fun getItemCount(): Int {
        return getSize()
    }

    fun getItem(position: Int): T? {
       mList?.let {
           if(position>=0&&position<it.size){
               return it[position]
           }
       }
        return null
    }

    override fun setList(list: ArrayList<T>) {
        this.mList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun addAll(list: List<T>) {
        list?.let {
            if(list.isNotEmpty()){
                if(mList==null){
                    mList= ArrayList()
                }
                var positionStart = mList?.size?:0
                mList?.addAll(it)
                notifyItemRangeInserted(positionStart, it.size)
            }
        }
    }

    override fun getList(): MutableList<T>? {
        return mList
    }

    override fun add(position: Int, item: T) {
        if (mList == null) {
            mList = ArrayList()
            (mList as ArrayList<T>).add(item)
            notifyDataSetChanged()
        } else {
            mList?.add(position, item)
            notifyItemInserted(position)
            notifyItemRangeChanged(position, getSize() - position)
        }
    }

    override fun remove(position: Int) {
        mList?.let {
            if(position<it.size){
                it.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, getSize() - position)
            }
        }
    }

    override fun getSize(): Int {
        return mList?.size?:0
    }

    override fun clear() {
        mList?.run {
            clear()
            notifyDataSetChanged()
        }
    }

    fun updateItem(position: Int, item: T) {
        mList?.let {
            if(position<it.size&&position!=-1){
                it[position]=item
                notifyItemChanged(position)
            }
        }
    }


    //    protected final void loadImage(ImageView imageView, String url, int defaultImage) {
    //        this.loadImage(imageView, url, defaultImage,null);
    //    }
    //
    //
    //    protected final void loadImage(ImageView imageView, String url, int defaultImage, AsyncImageListener listener) {
    //        if(imageView!=null){
    //            AsyncImageManager.downloadAsync(imageView,url,defaultImage,listener);
    //        }
    //    }

    override fun onViewRecycled(holder: DataBoundViewHolder<V>) {
        super.onViewRecycled(holder)
        if (holder.itemView.scaleX != 1.0f) {
            holder.itemView.scaleX = 1.0f
            holder.itemView.scaleY = 1.0f
        }
    }

    interface OnItemClickListener {
        fun onItemClick(dataBinding: ViewDataBinding, position: Int)
    }


    override fun unLockLoadingImage(context: Context) {
        //        if(context!=null&&context instanceof Activity&&!((Activity) context).isFinishing()){
        //            AsyncImageManager.unlock(context);
        //        }
    }


    override fun lockLoadingImageWhenScrolling(context: Context) {
        //        if(context!=null&&context instanceof Activity&&!((Activity) context).isFinishing()){
        //            AsyncImageManager.lock(context);
        //        }
    }
}
