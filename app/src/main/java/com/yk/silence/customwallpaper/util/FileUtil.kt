package com.yk.silence.customwallpaper.util

import java.io.File

object FileUtil {

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    fun isFileExists(filePath: String): Boolean {
        return isFileExists(getFileByPath(filePath))
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    fun getFileByPath(filePath: String): File? {
        return if (StringUtil.isSpace(filePath)) null else File(filePath)
    }

    /**
     * 创建文件夹
     */
    fun createFile(path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
    }
}
