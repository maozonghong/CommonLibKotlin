package com.mao.library.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.text.TextUtils
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.mao.library.R
import com.mao.library.abs.GlideApp
import com.mao.library.interfaces.AsyncImageListener

import com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION

/**
 * Created by maozonghong
 * on 2019/11/21
 */
object AsyncImageManager {

    @JvmStatic
    fun loadVideoScreenshot(imageView: ImageView?, url: String) {
        if (imageView != null) {
            val requestOptions = RequestOptions.frameOf(1000000)
            requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST)
            GlideApp.with(imageView).asBitmap().load(url).apply(requestOptions).dontAnimate()
                .into(object : MySimpleTarget(imageView) {

                    override fun onLoadFailed(errorDrawable: Drawable?) {

                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        if (view != null && resource != null) {
                            view.setImageBitmap(resource)
                        }
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        view?.setImageBitmap(null)
                    }
                })
        }
    }

    @JvmOverloads
    @JvmStatic
    fun downloadAsync(imageView: ImageView?, url: String, defaultImage: Int = 0, listener: AsyncImageListener? = null) {
        if (imageView != null) {
            if (TextUtils.isEmpty(url)) {
                imageView.setImageResource(defaultImage)
                return
            }
            listener?.onLoadStart(url)

            GlideApp.with(imageView).asBitmap().placeholder(defaultImage).error(defaultImage)
                .load(url).diskCacheStrategy(DiskCacheStrategy.RESOURCE).dontAnimate()
                .into(object : MySimpleTarget(imageView, url, listener) {
                    override fun onResourceCleared(placeholder: Drawable?) {
                        if (view != null && placeholder != null) {
                            view.setImageDrawable(placeholder)
                        }
                    }


                    override fun onResourceLoading(placeholder: Drawable?) {

                    }

                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        if (view != null && resource != null) {
                            view.setImageBitmap(resource)
                        }
                        listener?.onLoadFinish(view, resource)

                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        listener?.onLoadFailed(imageUrl)
                    }
                }
                )

        }
    }


    @JvmStatic
    fun lock(context: Context?) {
        if (context != null) {
            Glide.with(context).pauseRequests()
        }
    }

    @JvmStatic
    fun unlock(context: Context?) {
        if (context != null) {
            Glide.with(context).resumeRequests()
        }
    }

    abstract class MySimpleTarget : CustomViewTarget<ImageView, Bitmap> {
        private var listener: AsyncImageListener? = null
        var imageUrl: String?=null

        constructor(imageView: ImageView, imageUrl: String, listener: AsyncImageListener?) : this(imageView) {
            this.imageUrl = imageUrl
            this.listener = listener
        }

        /**
         * Constructor that defaults `waitForLayout` to `false`.
         *
         * @param view
         */
        constructor(view: ImageView) : super(view)
    }


    private fun doFinishLoad(imageView: ImageView, url: String, bitmap: Bitmap, defaultImage: Int,
                             listener: AsyncImageListener?) {
        val tag = imageView.getTag(R.id.image_download_tag_id)
        if (tag != null && tag == url) {
            imageView.setImageBitmap(bitmap)
            listener?.onLoadFinish(imageView, bitmap)
        } else {
            try {
                GlideApp.with(imageView).clear(imageView)
                imageView.setImageResource(defaultImage)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }
}
