package com.yk.silence.customwallpaper.widget.activity

import android.Manifest
import android.animation.Animator
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.util.FileUtil
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.ArrayList

class SplashActivity : BaseActivity() {


    override fun getLayoutID(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        initPermission()
    }


    /**
     * 获取权限
     */
    private fun initPermission() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE,
                resources.getString(R.string.text_read), R.drawable.permission_ic_storage))
        permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                resources.getString(R.string.text_read), R.drawable.permission_ic_storage))
        permissionItems.add(PermissionItem(Manifest.permission.READ_PHONE_STATE,
                resources.getString(R.string.text_read_phone_state), R.drawable.permission_ic_phone))
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
                        if (!FileUtil.isFileExists(Constants.VIDEO_PATH)) {
                            FileUtil.createFile(Constants.VIDEO_PATH)
                        }
                        if (!FileUtil.isFileExists(Constants.IMAGE_PATH)) {
                            FileUtil.createFile(Constants.IMAGE_PATH)
                        }
                        if (!FileUtil.isFileExists(Constants.DOWNLOAD_PATH)) {
                            FileUtil.createFile(Constants.DOWNLOAD_PATH)
                        }
                        initView()
                    }

                    override fun onGuarantee(permission: String?, position: Int) {

                    }
                })
    }

    /**
     * 初始化控件
     */
    private fun initView() {
        lav_splash.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                lav_splash.cancelAnimation()
                startActivity(MainActivity::class.java)
                finish()
            }

            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        lav_splash.playAnimation()

    }

    override fun onDestroy() {
        super.onDestroy()
        lav_splash.cancelAnimation()
    }
}
