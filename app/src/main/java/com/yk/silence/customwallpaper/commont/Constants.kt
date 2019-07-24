package com.yk.silence.customwallpaper.commont

import android.os.Environment

import java.io.File

object Constants {

    /**
     * 路径
     */
    private val SDCARD_PATH = Environment.getExternalStorageDirectory().path

    /**
     * 文件夹路径
     */
    val WALL_PATH = (SDCARD_PATH + File.separator + "CustomWallPaper" + File.separator)
    /**
     *视频文件名称
     */
    val VIDEO_PATH = (SDCARD_PATH + File.separator + "CustomWallPaper" + File.separator
            + "video" + File.separator)
    /**
     *图片文件名称
     */
    val IMAGE_PATH = (SDCARD_PATH + File.separator + "CustomWallPaper" + File.separator
            + "image" + File.separator)
    /**
     *文件名称
     */
    val VIDEO_NAME = "video"

}
