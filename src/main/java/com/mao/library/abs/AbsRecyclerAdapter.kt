package com.mao.library.abs

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.mao.library.interfaces.AdapterInterface


import java.util.ArrayList

abstract class AbsRecyclerAdapter<T, V : ViewDataBinding> :
    RecyclerView.Adapter<DataBoundViewHolder<V>>, AdapterInterface<T> {

    private var onItemClickListener: OnItemClickListener?=null
    private var list: ArrayList<T>? = null

    constructor(list: ArrayList<T>) {
        this.list = list
    }

    constructor() {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {

        bind(holder.binding, list!![position])
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)

    override fun getItemCount(): Int {
        return getSize()
    }

    fun getItem(position: Int): T? {
        return if (position >= 0 && this.list != null && this.list!!.size > position) this.list!![position] else null
    }

    override fun setList(list: ArrayList<T>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun addAll(list: List<T>) {
        if (list != null && !list.isEmpty()) {
            if (this.list == null) {
                this.list = ArrayList()
                this.list!!.addAll(list)
                notifyDataSetChanged()
            } else {
                val positionStart = this.list!!.size
                this.list!!.addAll(list)
                notifyItemRangeInserted(positionStart, list.size)
            }
        }
    }

    override fun getList(): MutableList<T> {
        return list!!
    }

    override fun add(position: Int, item: T) {
        if (list == null) {
            list = ArrayList()
            (list as ArrayList<T>).add(item)
            notifyDataSetChanged()
        } else {
            list!!.add(position, item)
            notifyItemInserted(position)
            notifyItemRangeChanged(position, getSize() - position)
        }
    }

    override fun remove(position: Int) {
        if (list != null && position < list!!.size) {
            list!!.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, getSize() - position)
        }
    }

    override fun getSize(): Int {
        return if (list != null) list!!.size else 0
    }

    override fun clear() {
        if (list != null && !list!!.isEmpty()) {
            list!!.clear()
            notifyDataSetChanged()
        }
    }

    fun updateItem(position: Int, item: T) {
        if (list != null && position < list!!.size && position != -1) {
            list!![position] = item
            notifyItemChanged(position)
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
        fun onItemClick(holder: RecyclerView.ViewHolder, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
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
