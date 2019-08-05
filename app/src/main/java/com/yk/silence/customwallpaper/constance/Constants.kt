package com.yk.silence.customwallpaper.constance

import android.os.Environment

import java.io.File

object Constants {

    /**
     * 路径
     */
    private val SDCARD_PATH = Environment.getExternalStorageDirectory().path

    private const val DIR_IN_SDCARD = "CustomWallPaper"

    /**
     * 文件夹路径
     */
    val WALL_PATH = (SDCARD_PATH + File.separator + DIR_IN_SDCARD + File.separator)
    /**
     *视频文件路径
     */
    val VIDEO_PATH = (SDCARD_PATH + File.separator + DIR_IN_SDCARD + File.separator
            + "video" + File.separator)
    /**
     *图片文件路径
     */
    val IMAGE_PATH = (SDCARD_PATH + File.separator + DIR_IN_SDCARD + File.separator
            + "image" + File.separator)
    /**
     *下载的文件路径
     */
    val DOWNLOAD_PATH = (SDCARD_PATH + File.separator + DIR_IN_SDCARD + File.separator
            + "download" + File.separator)
    /**
     *安装的文件路径
     */
    val INSTALL_PATH = (SDCARD_PATH + File.separator + DIR_IN_SDCARD + File.separator
            + "install" + File.separator)
    /**
     *端口
     */
    const val HTTP_PORT = 12345
    /**
     * 对话框隐藏
     */
    const val MSG_DIALOG_DISMISS = 0
    /**
     * 文件安装路径
     */
    val DIR = File(SDCARD_PATH + File.separator + DIR_IN_SDCARD + File.separator
            + "install" + File.separator)

    /**
     * 类型
     */
    object RxBusEventType {
        const val POPUP_MENU_DIALOG_SHOW_DISMISS = "POPUP MENU DIALOG SHOW DISMISS"
        //wifi状态变化事件
        const val WIFI_CONNECT_CHANGE_EVENT = "WIFI CONNECT CHANGE EVENT"
        const val LOAD_BOOK_LIST = "LOAD BOOK LIST"
    }
}
