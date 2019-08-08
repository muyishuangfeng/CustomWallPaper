package com.yk.silence.customwallpaper.ui.glide

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File

object GlideUtil {

    /**
     * 加载网络图片
     *
     *
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     *
     * @param context           上下文
     * @param url               url
     * @param resID             占位图
     * @param errorID           错误图片
     * @param diskCacheStrategy 缓存
     * @param imageView         图片
     */
    fun loadUrlToImage(context: Context, url: String, errorID: Int,
                       diskCacheStrategy: DiskCacheStrategy, imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(imageView.drawable)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    fun loadAvatar(context: Context, url: String, errorID: Int,
                   diskCacheStrategy: DiskCacheStrategy, imageView: ImageView) {
        val options = RequestOptions()
                .placeholder(imageView.drawable)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载网络图片
     *
     *
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     * @param context           上下文
     * @param url               url
     * @param resID             占位图
     * @param errorID           错误图片
     * @param diskCacheStrategy 缓存
     * @param height
     * @param width
     * @param imageView         图片
     */
    fun loadUrlToWithHeight(context: Context, url: String, resID: Int, errorID: Int,
                            diskCacheStrategy: DiskCacheStrategy, height: Double, width: Double,
                            imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .override(width.toInt(), height.toInt())
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载原始尺寸的网络图片
     *
     * @param context           上下文
     * @param url               url
     * @param resID             占位图
     * @param errorID           错误图片
     * @param diskCacheStrategy 缓存
     * @param AppCompatImageView         图片
     */
    fun loadOriginalToImage(context: Context, url: String, resID: Int, errorID: Int,
                            diskCacheStrategy: DiskCacheStrategy, imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .override(Target.SIZE_ORIGINAL)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(MyGlideUrl(url))
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载网络图片
     *
     * @param context   上下文
     * @param url       网络图片地址
     * @param AppCompatImageView AppCompatImageView
     * @param options   加载参数设置
     */
    fun loadImage(context: Context, url: String, imageView: AppCompatImageView,
                  options: RequestOptions) {
        Glide.with(context)
                .load(MyGlideUrl(url))
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载Gif图片
     *
     * @param context
     * @param url
     * @param resID
     * @param errorID
     * @param diskCacheStrategy
     * @param imageView
     */
    fun loadGifFromUrl(context: Context, url: String, resID: Int, errorID: Int,
                       diskCacheStrategy: DiskCacheStrategy, imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .asGif()
                .load(MyGlideUrl(url))
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载静态图片
     *
     * @param context
     * @param url
     * @param resID
     * @param errorID
     * @param diskCacheStrategy
     * @param imageView
     */
    fun loadBitmapFromUrl(context: Context, url: String, resID: Int, errorID: Int,
                          diskCacheStrategy: DiskCacheStrategy, imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .asBitmap()
                .load(MyGlideUrl(url))
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载缩略图
     *
     * @param context           上下文
     * @param url               地址
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param thumbnailSize     缩略图大小
     * @param imageView         资源文件
     */
    fun loadThumbnails(context: Context, url: String, resID: Int, errorID: Int,
                       diskCacheStrategy: DiskCacheStrategy, thumbnailSize: Float,
                       imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(MyGlideUrl(url))
                .thumbnail(thumbnailSize)
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载本地图片
     *
     * @param context           上下文
     * @param file              文件
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param imageView         资源文件
     */
    fun loadLocalFile(context: Context, file: File, resID: Int, errorID: Int,
                      diskCacheStrategy: DiskCacheStrategy,
                      imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(file)
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载应用资源
     *
     * @param context           上下文
     * @param resource          资源文件图片
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param imageView         资源文件
     */
    fun loadResource(context: Context, resource: Int, resID: Int, errorID: Int,
                     diskCacheStrategy: DiskCacheStrategy, imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(resource)
                .apply(options)
                .into(imageView)


    }

    /**
     * 加载流资源
     *
     * @param context           上下文
     * @param image             二进制流
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param thumbnailSize     缩略图大小
     * @param imageView         资源文件
     */
    fun loadStream(context: Context, image: ByteArray, resID: Int, errorID: Int,
                   diskCacheStrategy: DiskCacheStrategy, thumbnailSize: Float,
                   imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(image)
                .thumbnail(thumbnailSize)
                .apply(options)
                .into(imageView)
    }

    /**
     * 加载Uri对象
     *
     * @param context           上下文
     * @param imageUri          uri
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param thumbnailSize     缩略图大小
     * @param imageView         资源文件
     */
    fun loadUri(context: Context, imageUri: Uri, resID: Int, errorID: Int,
                diskCacheStrategy: DiskCacheStrategy, thumbnailSize: Float,
                imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(imageUri)
                .thumbnail(thumbnailSize)
                .apply(options)
                .into(imageView)
    }

    /**
     * 图片转换
     *
     * @param context           上下文
     * @param url               地址
     * @param resID             占位图
     * @param errorID           错误图
     * @param diskCacheStrategy 缓存
     * @param transformation    转换
     * @param imageView         资源ID
     */
    fun loadUrlWithTranslate(context: Context, url: String, resID: Int, errorID: Int,
                             diskCacheStrategy: DiskCacheStrategy,
                             transformation: BitmapTransformation, imageView: AppCompatImageView) {
        val options = RequestOptions()
                .placeholder(resID)
                .error(errorID)
                .transform(transformation)
                .diskCacheStrategy(diskCacheStrategy)
        Glide.with(context)
                .load(MyGlideUrl(url))
                .apply(options)
                .into(imageView)
    }

    /**
     * 下载图片并显示
     *
     * @param url
     * @param resID
     * @param errorID
     * @param context
     * @param view
     */
    fun downloadImage(url: String, resID: Int, errorID: Int,
                      context: Activity, view: AppCompatImageView) {
        Thread(Runnable {
            try {
                val target = Glide.with(context)
                        .asFile()
                        .load(url)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                val imageFile = target.get()
                val options = RequestOptions()
                        .placeholder(resID)
                        .error(errorID)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(context)
                        .load(imageFile)
                        .apply(options)
                        .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }
}