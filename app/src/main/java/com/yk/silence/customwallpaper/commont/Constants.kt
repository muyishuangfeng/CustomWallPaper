package com.yk.silence.customwallpaper.commont

import android.os.Environment

import java.io.File

object Constants {

    /**
     * 路径
     */
   private val SDCARD_PATH = Environment.getExternalStorageDirectory().path

    /**
     * log路径
     */
    val VIDEO_PATH = (SDCARD_PATH + File.separator + "CustomWallPaper" + File.separator
            + "video" + File.separator)

}
