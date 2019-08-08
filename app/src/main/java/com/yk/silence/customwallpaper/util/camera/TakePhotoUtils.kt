package com.yk.silence.customwallpaper.util.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

import java.io.File

object TakePhotoUtils {
    /***拍照 */
    const val REQUEST_TAKE_PHOTO = 1
    /***从相册选择照片 */
    const val REQUEST_CHOOSE_PHOTO = 2
    /***裁剪 */
    const val REQUEST_CROP_PHOTO = 3
    const val REQUEST_CHOOSE_CAMERA = 4

    /**
     * 拍照
     */
    fun takePhoto(activity: Activity, outputFile: File) {
        val intent = Intent()
        intent.action = "android.media.action.IMAGE_CAPTURE"
        intent.addCategory("android.intent.category.DEFAULT")
        val uri = FileProviderUtils.uriFromFile(activity, outputFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO)
    }

    /**
     * 拍照
     */
    fun chooseCamera(activity: Activity, outputFile: File) {
        val intent = Intent()
        intent.action = "android.media.action.IMAGE_CAPTURE"
        intent.addCategory("android.intent.category.DEFAULT")
        val uri = FileProviderUtils.uriFromFile(activity, outputFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        activity.startActivityForResult(intent, REQUEST_CHOOSE_CAMERA)
    }

    /**
     * 从相册选择
     */
    fun choosePhoto(activity: Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = "android.intent.action.PICK"
        intent.addCategory("android.intent.category.DEFAULT")
        activity.startActivityForResult(intent, REQUEST_CHOOSE_PHOTO)
    }

    /**
     * 裁剪照片
     */
    fun cropPhoto(activity: Activity, uri: Uri, outputFile: File) {
        val intent = Intent("com.android.camera.action.CROP")
        FileProviderUtils.setIntentDataAndType(activity, intent, "image/*", uri, true)
        intent.putExtra("crop", "true")
        if (Build.MANUFACTURER.contains("HUAWEI")) {
            intent.putExtra("aspectX", 9998)
            intent.putExtra("aspectY", 9999)
        } else {
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
        }
        intent.putExtra("outputX", 300)
        intent.putExtra("outputY", 300)
        //return-data为true时，直接返回bitmap，可能会很占内存，不建议，小米等个别机型会出异常！！！
        //所以适配小米等个别机型，裁切后的图片，不能直接使用data返回，应使用uri指向
        //裁切后保存的URI，不属于我们向外共享的，所以可以使用fill://类型的URI
        val outputUri = Uri.fromFile(outputFile)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        intent.putExtra("return-data", false)

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        activity.startActivityForResult(intent, REQUEST_CROP_PHOTO)
    }

}
