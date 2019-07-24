package com.yk.silence.customwallpaper.util

import android.os.Environment
import android.text.TextUtils
import com.yk.silence.customwallpaper.commont.Constants
import com.yk.silence.customwallpaper.model.HomeModel
import java.io.*
import org.json.JSONObject
import org.json.JSONArray


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

    /**
     * 文件夹路径
     */
    fun getVideoDir(): File {
        val dir = File(Environment
                .getExternalStorageDirectory(), Constants.WALL_PATH);
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    /**
     * move file
     *
     * @param sourceFilePath 资源路径
     * @param destFilePath   目标的路径
     */
    fun moveFile(sourceFilePath: String, destFilePath: String) {
        if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
            throw RuntimeException(
                    "Both sourceFilePath and destFilePath cannot be null.")
        }
        moveFile(File(sourceFilePath), File(destFilePath))
    }

    /**
     * move file
     *
     * @param srcFile  文件对象
     * @param destFile 目标文件
     */
    private fun moveFile(srcFile: File, destFile: File) {

        val rename = srcFile.renameTo(destFile)
        if (!rename) {
            copyFile(srcFile.absolutePath, destFile.absolutePath)
            deleteFile(srcFile.absolutePath)
        }
    }


    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath 路径
     * @param stream   输入流
     * @return 返回是否写入成功
     */
    private fun writeFile(filePath: String, stream: InputStream): Boolean {
        return writeFile(filePath, stream, false)
    }

    /**
     * write file
     *
     * @param filePath 路径
     * @param stream   the input stream
     * @param append   if `true`, then bytes will be written to the end of
     */
    private fun writeFile(filePath: String?, stream: InputStream,
                          append: Boolean): Boolean {
        if (filePath != null)
            return FileUtil.writeFile(File(filePath), stream, append)
        else
            return false
    }


    /**
     *
     * 写入文件
     * write file
     */
    private fun writeFile(file: File, stream: InputStream,
                          append: Boolean): Boolean {
        var o: OutputStream? = null
        try {
            makeDirs(file.absolutePath)
            o = FileOutputStream(file, append)
            val data = ByteArray(1024)
            var length: Int
            length = stream.read(data)
            while (length != -1) {
                o.write(data, 0, length)
            }
            o.flush()
            return true
        } catch (e: FileNotFoundException) {
            throw RuntimeException("FileNotFoundException occurred. ", e)
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            IOUtils.close(o)
            IOUtils.close(stream)
        }
    }

    /**
     * 创建文件夹
     * @param filePath 路径
     * @return 是否创建成功
     */
    fun makeDirs(filePath: String): Boolean {

        val folderName = getFolderName(filePath)
        if (StringUtil.isEmpty(folderName)) {
            return false
        }

        val folder = File(folderName)
        return if (folder.exists() && folder.isDirectory())
            true
        else
            folder.mkdirs()
    }

    /**
     * 获取文件夹名称
     */
    fun getFolderName(filePath: String): String {
        if (StringUtil.isEmpty(filePath)) {
            return filePath
        }
        val filePosi = filePath.lastIndexOf(File.separator)
        return if (filePosi == -1) "" else filePath.substring(0, filePosi)
    }

    /**
     * @param path 路径
     * @return 是否删除成功
     */
    fun deleteFile(path: String): Boolean {

        if (StringUtil.isBlank(path)) {
            return true
        }

        val file = File(path)
        if (!file.exists()) {
            return true
        }
        if (file.isFile) {
            return file.delete()
        }
        if (!file.isDirectory) {
            return false
        }
        for (f in file.listFiles()) {
            if (f.isFile) {
                f.delete()
            } else if (f.isDirectory) {
                deleteFile(f.absolutePath)
            }
        }
        return file.delete()
    }


    /**
     * 获取指定目录内所有文件路径
     * @param dirPath 需要查询的文件目录
     * @param _type 查询类型，比如mp3什么的
     */
    fun searchAllFile(mPath: String): List<HomeModel>?{
        var fileList: MutableList<HomeModel> = mutableListOf()
        //在该目录下走一圈，得到文件目录树结构
        val fileTree: FileTreeWalk = File(mPath).walk()
        fileTree.maxDepth(1) //需遍历的目录层级为1，即无需检查子目录
                .filter { it.isFile } //只挑选文件，不处理文件夹
                .forEach { fileList.add(HomeModel(it.absolutePath, it.name)) } //循环处理符合条件的文件
        return fileList
    }


    /**
     * 复制文件
     * @param oldPath 源文件路径
     * @param newPath 目标文件路径
     */
    fun copyFile(oldPath: String, newPath: String): Boolean {
        val oldFile = File(oldPath)
        val newFile = File(newPath)
        newFile.deleteOnExit()
        newFile.createNewFile()
        var c = -1
        val buffer = ByteArray(1024 * 1000)
        val inputStream = oldFile.inputStream()
        val now = System.currentTimeMillis()
        while ({ c = inputStream.read(buffer);c }() > 0) {
            newFile.appendBytes(buffer.copyOfRange(0, c))
        }
        println("复制完毕，耗时${(System.currentTimeMillis() - now) / 1000}秒")
        return true
    }

}
