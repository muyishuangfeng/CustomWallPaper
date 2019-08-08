package com.yk.silence.customwallpaper.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import androidx.core.content.FileProvider
import com.yk.silence.customwallpaper.constance.Constants
import java.io.File
import java.text.DecimalFormat

object AppUtils {



    /**
     * 获取文件大小
     */
     fun getFileSize(length: Long): String {


        val df = DecimalFormat("######0.00")
        val d1 = 3.23456
        val d2 = 0.0
        val d3 = 2.0
        df.format(d1)
        df.format(d2)
        df.format(d3)
        val l = length / 1000//KB
        if (l < 1024) {
            return df.format(l) + "KB"
        } else if (l < 1024 * 1024f) {
            return df.format((l / 1024f).toDouble()) + "MB"
        }
        return df.format((l.toFloat() / 1024f / 1024f).toDouble()) + "GB"
    }

    /**
     * 获取应用名称
     */
     fun getApplicationName(context: Context, packageName: String): String {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo? = null
        try {
            packageManager = context.applicationContext.packageManager
            applicationInfo = packageManager!!.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            applicationInfo = null
        }
        return if (packageManager != null && applicationInfo != null) {
            packageManager.getApplicationLabel(applicationInfo) as String
        } else packageName
    }

    /**
     * 获取应用图标
     */
    @SuppressLint("ObsoleteSdkInt")
    @Synchronized
     fun getIconFromPackageName(packageName: String, context: Context): Drawable? {
        val pm = context.packageManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                val pi = pm.getPackageInfo(packageName, 0)
                val otherAppCtx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
                val displayMetrics = intArrayOf(DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV)
                for (displayMetric in displayMetrics) {
                    try {
                        val d = otherAppCtx.resources.getDrawableForDensity(pi.applicationInfo.icon, displayMetric)
                        if (d != null) {
                            return d
                        }
                    } catch (e: Resources.NotFoundException) {
                        continue
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        var appInfo: ApplicationInfo? = null
        try {
            appInfo = pm.getApplicationInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            return null
        }

        return appInfo!!.loadIcon(pm)
    }


    /**
     * 判断相对应的APP是否存在
     *
     * @param context
     * @param packageName(包名)(若想判断QQ，则改为com.tencent.mobileqq，若想判断微信，则改为com.tencent.mm)
     * @return
     */
     fun isAvilible(context: Context, packageName: String): Boolean {
        val packageManager = context.packageManager

        //获取手机系统的所有APP包名，然后进行一一比较
        val pinfo = packageManager.getInstalledPackages(0)
        for (i in pinfo.indices) {
            if ((pinfo[i] as PackageInfo).packageName
                            .equals(packageName, ignoreCase = true))
                return true
        }
        return false
    }

    /**
     * 安装
     */
    fun installApkFile(context: Context, file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        //兼容7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            val contentUri = FileProvider.getUriForFile(context, context.packageName + ".FileProvider", file)
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (context.packageManager.queryIntentActivities(intent, 0).size > 0) {
            context.startActivity(intent)
        }
    }

    /**
     * 卸载
     */
    fun unInstall(context: Context, packageName: String) {
        val uri = Uri.fromParts("package", packageName, null)
        val intent = Intent(Intent.ACTION_DELETE, uri)
        context.startActivity(intent)
    }

    /**
     * 删除所有文件
     */
     fun deleteAll() {
        val dir = Constants.DIR
        if (dir.exists() && dir.isDirectory()) {
            val fileNames = dir.listFiles()
            if (fileNames != null) {
                for (fileName in fileNames) {
                    fileName.delete()
                }
            }
        }
    }
}