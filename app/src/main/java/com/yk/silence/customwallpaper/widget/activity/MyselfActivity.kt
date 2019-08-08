package com.yk.silence.customwallpaper.widget.activity

import android.Manifest
import android.content.Intent
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.impl.OnPictureSelectListener
import com.yk.silence.customwallpaper.ui.dialog.PictureSelectDialog
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.customwallpaper.widget.adapter.FragmentMyselfAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_myself.*
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem
import java.io.File
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import com.yk.silence.customwallpaper.util.TabUtil
import com.yk.silence.customwallpaper.util.camera.TakePhotoUtils
import com.yk.silence.customwallpaper.util.camera.FileProviderUtils


class MyselfActivity : BaseActivity(), CustomTitleBar.TitleClickListener,
        OnPictureSelectListener, View.OnClickListener {


    private var mAdapter: FragmentMyselfAdapter? = null
    private var mDialog: PictureSelectDialog? = null
    private var mFileUri: Uri? = null;
    private var outputFile: File? = null;
    private var mChooseAvatar: Boolean? = false
    private var mChooseBg: Boolean? = false


    override fun getLayoutID(): Int {
        return R.layout.activity_myself
    }

    override fun initData() {
        mAdapter = FragmentMyselfAdapter(supportFragmentManager, this)
        vp_myself.adapter = mAdapter
        tab_myself.setupWithViewPager(vp_myself)
        title_myself.setTitleClickListener(this)
        initUserData()

    }

    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
        mChooseBg = true
        initPermission()
    }

    override fun onPictureSelect() {
        TakePhotoUtils.choosePhoto(this)
    }

    override fun onCameraSelect() {
        val file = File(Constants.AVATAR_PATH)
        if (!file.exists()) {
            file.createNewFile()
        }
        TakePhotoUtils.takePhoto(this, File(Constants.USER_AVATAR))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_myself_avatar -> {
                mChooseAvatar = true
                initPermission()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TakePhotoUtils.REQUEST_TAKE_PHOTO -> {
                //拍照完成，进行图片裁切
                if (mChooseAvatar == true) {
                    outputFile = File(Constants.USER_AVATAR)//裁切后输出的图片
                } else if (mChooseBg == true) {
                    outputFile = File(Constants.USER_BG)//裁切后输出的图片
                }
                mFileUri = FileProviderUtils.uriFromFile(this, outputFile!!)
                TakePhotoUtils.cropPhoto(this, mFileUri!!, outputFile!!)
            }
            TakePhotoUtils.REQUEST_CHOOSE_PHOTO -> {
                //相册选择图片完毕，进行图片裁切
                if (data == null || data.data == null) {
                    return
                }
                mFileUri = data.data
                if (mChooseAvatar == true) {
                    outputFile = File(Constants.USER_AVATAR)//裁切后输出的图片
                } else if (mChooseBg == true) {
                    outputFile = File(Constants.USER_BG)//裁切后输出的图片
                }
                TakePhotoUtils.cropPhoto(this, mFileUri!!, outputFile!!)
            }
            TakePhotoUtils.REQUEST_CROP_PHOTO ->
                //图片裁切完成，显示裁切后的图片
                try {
                    if (mChooseAvatar == true) {
                        outputFile = File(Constants.USER_AVATAR)//裁切后输出的图片
                    } else if (mChooseBg == true) {
                        outputFile = File(Constants.USER_BG)//裁切后输出的图片
                    }
                    val uri = Uri.fromFile(outputFile)
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                    if (mChooseAvatar == true) {
                        img_myself_avatar.setImageBitmap(bitmap)//裁切后输出的图片
                        mChooseAvatar = false
                    } else if (mChooseBg == true) {
                        img_myself_photo.setImageBitmap(bitmap)//裁切后输出的图片
                        mChooseBg = false
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }


        }

    }


    /**
     * 设置用户数据
     */
    private fun initUserData() {
        img_myself_avatar.setOnClickListener(this)
        if (Constants.USER_AVATAR.isNotEmpty()) {
            GlideUtil.loadAvatar(this,
                    Constants.USER_AVATAR,
                    R.drawable.ic_no,
                    DiskCacheStrategy.ALL,
                    img_myself_avatar)
        }
        if (Constants.USER_BG.isNotEmpty()) {
            GlideUtil.loadAvatar(this,
                    Constants.USER_BG,
                    R.drawable.ic_no,
                    DiskCacheStrategy.ALL,
                    img_myself_photo)
        }
    }


    /**
     * 获取权限
     */
    private fun initPermission() {
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
                        chooseImage()
                    }

                    override fun onGuarantee(permission: String?, position: Int) {

                    }
                })
    }

    /**
     * 选择图片
     */
    private fun chooseImage() {
        mDialog = PictureSelectDialog(this)
        mDialog!!.setPictureSelectListener(this)
        mDialog!!.show()
    }

}
