package com.yk.silence.customwallpaper.mvp.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.mvp.model.bean.AppBean
import rx.Observable
import java.text.DecimalFormat
import java.util.ArrayList

class AppModel {

    fun loadData(context: Context) {
        Observable.create<List<AppBean>> { subscriber ->
            val list = ArrayList<AppBean>()
            val dir = Constants.DIR
            if (dir.exists() && dir.isDirectory) {
                val fileNames = dir.listFiles()
                if (fileNames != null) {
                    for (fileName in fileNames) {
                        handleApk(context, fileName.absolutePath, fileName.length(), list)
                    }
                }
            }
            subscriber.onNext(list)
            subscriber.onCompleted()
        }

    }


    private fun handleApk(context: Context, path: String, fileSize: Long, list: MutableList<AppBean>){
        val mApps: MutableList<AppBean> = ArrayList()
        val infoModel: AppBean? = AppBean()
        var archiveFilePath = ""
        archiveFilePath = path
        val pm = context.packageManager
        val info = pm.getPackageArchiveInfo(archiveFilePath, 0)

        if (info != null) {
            val appInfo = info.applicationInfo
            appInfo.sourceDir = archiveFilePath
            appInfo.publicSourceDir = archiveFilePath
            val packageName = appInfo.packageName  //得到安装包名称
            val version = info.versionName       //得到版本信息
            var icon: Drawable? = pm.getApplicationIcon(appInfo)
            var appName = pm.getApplicationLabel(appInfo).toString()
            if (TextUtils.isEmpty(appName)) {
                appName = getApplicationName(context, packageName)
            }
            if (icon == null) {
                icon = getIconFromPackageName(packageName, context) // 获得应用程序图标
            }
            infoModel?.name = appName
            infoModel?.packageName = packageName
            infoModel?.path = path
            infoModel?.size = getFileSize(fileSize)
            infoModel?.version = version
            infoModel?.icon = icon
            infoModel?.installed = isAvilible(context, packageName)
            infoModel?.let { list.add(it) } ?: infoModel?.let { mApps.add(it) }
        }
    }

    /**
     * 获取文件大小
     */
    private fun getFileSize(length: Long): String {
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
    private fun getApplicationName(context: Context, packageName: String): String {
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
    private fun getIconFromPackageName(packageName: String, context: Context): Drawable? {
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
    private fun isAvilible(context: Context, packageName: String): Boolean {
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

}
