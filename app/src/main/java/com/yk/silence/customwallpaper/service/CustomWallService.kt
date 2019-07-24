package com.yk.silence.customwallpaper.service

import android.content.Intent
import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import java.io.IOException

class CustomWallService : WallpaperService() {

    var path: String? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("CustomService",path);
        if (intent != null) {
            if (intent.getStringExtra("path").isNotEmpty()) {
                path = intent.getStringExtra("path")
                Log.e("CustomService",path);
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreateEngine(): WallpaperService.Engine {

        return CustomWallEngine()

    }

    /**
     * 壁纸引擎
     */
    internal inner class CustomWallEngine : WallpaperService.Engine() {

        private var mPlayer: MediaPlayer? = null



        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            mPlayer = MediaPlayer()
            mPlayer!!.setSurface(holder.surface)
            try {
                if (mPlayer != null && mPlayer!!.isPlaying) {
                    return
                }
                mPlayer!!.setDataSource(path)
                mPlayer!!.isLooping = true
                mPlayer!!.setVolume(0f, 0f)
                mPlayer!!.prepare()
                mPlayer!!.setOnPreparedListener { mPlayer!!.start() }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            mPlayer!!.release()
            mPlayer = null
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                mPlayer!!.start()
            } else {
                mPlayer!!.pause()
            }
        }
    }
}
