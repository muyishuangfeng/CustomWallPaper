package com.yk.silence.customwallpaper.ui.mediapicture.activity

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.commont.Constants
import com.yk.silence.customwallpaper.ui.mediapicture.decoration.MediaItemDecoration
import com.yk.silence.customwallpaper.ui.mediapicture.adapter.LocalImageAdapter
import com.yk.silence.customwallpaper.ui.mediapicture.impl.OnLocalImageSelectListener
import com.yk.silence.customwallpaper.ui.mediapicture.model.ImageMediaEntity
import com.yk.silence.customwallpaper.util.FileUtil
import com.yk.silence.customwallpaper.util.MediaUtils
import com.yk.silence.toolbar.CustomTitleBar
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem
import kotlinx.android.synthetic.main.activity_image_select.*
import java.lang.ref.WeakReference

class ImageSelectActivity : AppCompatActivity(), OnLocalImageSelectListener, CustomTitleBar.TitleClickListener {

    companion object {
        const val GET_LOCAL_IMAGES: Int = 100
        const val CALL_FINISH: Int = 101

        /**
         * Handler
         */
        private class WithoutLeakHandler(mActivity: ImageSelectActivity) : Handler() {
            private var weakReference: WeakReference<ImageSelectActivity>? = WeakReference(mActivity)


            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when (msg!!.what) {
                    GET_LOCAL_IMAGES -> {
                        val activity = weakReference?.get()
                        if (activity != null) {
                            activity.mAdapter.setData(activity.mList!!)
                            activity.rlv_image_select.adapter = activity.mAdapter
                        }
                    }
                    CALL_FINISH->{
                        val activity = weakReference?.get()
                        activity?.onBackPressed()
                    }
                }
            }
        }

    }

    //适配器
    private val mAdapter = LocalImageAdapter(this)
    //集合
    private var mList: List<ImageMediaEntity>? = ArrayList()
    private var mHandler: Handler = WithoutLeakHandler(this)
    private var mPathList: List<String>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_image_select)
        initView()
    }

    override fun onImageSelect(view: View, position: Int, images: List<String>) {
        mPathList = images
        Log.e("ImageSelectActivity", "当前选中的图片为->$images")
    }

    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
        Thread(Runnable {
            if (mPathList != null && mPathList!!.isNotEmpty()) {
                for (item in mPathList!!) {
                    if (FileUtil.isFileExists(Constants.IMAGE_PATH)) {
                        FileUtil.copyFile(item, Constants.IMAGE_PATH
                                + System.currentTimeMillis() + ".jpg")
                    }
                }
                mHandler.sendEmptyMessage(CALL_FINISH)
            }
        }).start()

    }


    /**
     * 初始化控件
     */
    private fun initView() {
        getPermission()
        toolBar.setTitleClickListener(this)
        rlv_image_select.layoutManager = GridLayoutManager(this, 3) as GridLayoutManager?
        rlv_image_select.addItemDecoration(MediaItemDecoration(8, 3))
        mAdapter.setOnItemSelectListener(this)
    }

    /**
     * 初始化权限
     */
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
                            searchForLocalImages()
                        }

                        override fun onGuarantee(permission: String?, position: Int) {

                        }
                    })
        } else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
            searchForLocalImages()
        }
    }

    /**
     * 扫描本地图片
     */
    private fun searchForLocalImages() {
        Thread(Runnable {
            mList = MediaUtils.getLocalPictures(this)
            if (mList != null) {
                val message = Message()
                message.what = GET_LOCAL_IMAGES
                mHandler.sendMessage(message)
                img_empty.visibility = View.GONE
            } else {
                img_empty.visibility = View.VISIBLE
            }
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
