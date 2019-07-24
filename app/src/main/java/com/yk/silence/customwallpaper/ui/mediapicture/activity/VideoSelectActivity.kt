package com.yk.silence.customwallpaper.ui.mediapicture.activity

import android.Manifest
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.commont.Constants
import com.yk.silence.customwallpaper.ui.mediapicture.decoration.MediaItemDecoration
import com.yk.silence.customwallpaper.ui.mediapicture.adapter.LocalVideoAdapter
import com.yk.silence.customwallpaper.ui.mediapicture.impl.OnLocalVideoSelectListener
import com.yk.silence.customwallpaper.ui.mediapicture.model.VideoMediaEntity
import com.yk.silence.customwallpaper.util.FileUtil
import com.yk.silence.customwallpaper.util.MediaUtils
import com.yk.silence.toolbar.CustomTitleBar
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem
import kotlinx.android.synthetic.main.activity_video_select.*
import java.lang.ref.WeakReference

class VideoSelectActivity : AppCompatActivity(), OnLocalVideoSelectListener, CustomTitleBar.TitleClickListener {


    companion object {
        const val GET_LOCAL_VIDEOS: Int = 100
        const val CALL_FINISH: Int = 101

        /**
         * by moosphon on 2018/09/16
         * desc: 解决handler内存泄漏的问题，消息的处理需要放在内部类的{@link #Handler.handleMessage}
         */
        private class WithoutLeakHandler(mActivity: VideoSelectActivity) : Handler() {
            private var weakReference: WeakReference<VideoSelectActivity> = WeakReference(mActivity)

            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    GET_LOCAL_VIDEOS -> {
                        val activity = weakReference.get()

                        if (activity != null) {
                            activity.adapter.setData(activity.videoData!!)
                            activity.rlv_video_select.adapter = activity.adapter

                        }

                    }
                    CALL_FINISH -> {
                        val activity = weakReference.get()
                        activity?.onBackPressed()

                    }
                }
            }
        }
    }

    private var videoData: List<VideoMediaEntity>? = ArrayList()
    private var handler: Handler = WithoutLeakHandler(this)
    private val adapter = LocalVideoAdapter()
    private var currentVideo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_video_select)
        initView()
    }

    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
        copy()
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        toolBar_video.setTitleClickListener(this)
        getPermission()
        rlv_video_select.layoutManager = GridLayoutManager(this, 3)
        rlv_video_select.addItemDecoration(MediaItemDecoration(8, 3))
        adapter.mListener = this

    }

    /** 获取存储权限 */
    private fun getPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            val permissionItems = java.util.ArrayList<PermissionItem>()
            permissionItems.add(PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE,
                    resources.getString(R.string.text_read), R.drawable.permission_ic_storage))
            permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    resources.getString(R.string.text_read), R.drawable.permission_ic_storage))
            HiPermission.create(this)
                    .title(resources.getString(R.string.permission_get))
                    .msg(resources.getString(R.string.permission_desc))
                    .permissions(permissionItems)
                    .checkMutiPermission(object : PermissionCallback {
                        override fun onClose() {
                        }

                        override fun onDeny(permission: String?, position: Int) {
                        }

                        override fun onFinish() {
                            searchForLocalVideos()
                        }

                        override fun onGuarantee(permission: String?, position: Int) {

                        }
                    })
        } else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
            searchForLocalVideos()
        }

    }

    /**
     * desc: 搜索系统本地所有视频
     */
    private fun searchForLocalVideos() {
        Thread(Runnable {
            videoData = MediaUtils.getLocalVideos(this)
            Log.e("VideoSelectActivity", "扫描本地视频的数量为->" + videoData?.size)
            if (videoData != null) {
                val message = Message()
                message.what = GET_LOCAL_VIDEOS
                handler.sendMessage(message)
                img_empty.visibility = View.GONE
            } else {
                img_empty.visibility = View.VISIBLE
            }
        }).start()
    }


    override fun onDestroy() {
        super.onDestroy()
        /** 消除内存泄漏隐患 */
        handler.removeCallbacksAndMessages(null)
    }

    override fun onVideoSelect(view: View, position: Int) {
        currentVideo = videoData!![position].path

    }


    private fun copy() {
        if (currentVideo != null) {
            Thread(Runnable {
                if (currentVideo!!.isNotEmpty()) {
                    if (FileUtil.isFileExists(Constants.VIDEO_PATH)) {
                        FileUtil.copyFile(currentVideo!!, Constants.VIDEO_PATH
                                + System.currentTimeMillis() + ".mp4")
                    }
                    handler.sendEmptyMessage(ImageSelectActivity.CALL_FINISH)
                }
            }).start()
        }

    }
}
