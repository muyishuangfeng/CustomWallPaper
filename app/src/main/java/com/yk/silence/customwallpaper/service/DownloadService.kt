package com.yk.silence.customwallpaper.service

import android.app.IntentService
import android.content.Intent
import android.text.TextUtils
import com.yk.silence.customwallpaper.net.download.DownloadUtil
import android.widget.Toast
import com.yk.silence.customwallpaper.constance.AppConfig
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.util.FileUtil


class DownloadService : IntentService("DownloadService") {

    private var mAction: String? = null

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            if (!TextUtils.isEmpty(intent.getStringExtra("params"))) {
                mAction = intent.getStringExtra("params");
            }
            when (mAction) {
                "download" -> {//下载文件
                    doDownload(AppConfig.DOWNLOAD_URL)
                }

            }

        }
    }

    /**
     * Download
     */
    private fun doDownload(url: String) {
        if (!FileUtil.isFileExists(Constants.DOWNLOAD_PATH)) {
            FileUtil.createFile(Constants.DOWNLOAD_PATH)
        }
        DownloadUtil.get().download(url, Constants.DOWNLOAD_PATH,
                object : DownloadUtil.OnDownloadListener {
                    override fun onDownloadSuccess() {
                        Toast.makeText(this@DownloadService, "下载成功了", Toast.LENGTH_LONG).show()
                    }

                    override fun onDownloading(progress: Int) {

                    }

                    override fun onDownloadFailed() {

                    }
                })

    }
}
