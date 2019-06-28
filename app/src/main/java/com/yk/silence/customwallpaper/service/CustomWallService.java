package com.yk.silence.customwallpaper.service;

import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.yk.silence.customwallpaper.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class CustomWallService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
        return new CustomWallEngine();
    }

    /**
     * 壁纸引擎
     */
    class CustomWallEngine extends Engine {

        MediaPlayer mPlayer;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mPlayer = new MediaPlayer();
            mPlayer.setSurface(holder.getSurface());
            try {
                if (mPlayer != null && mPlayer.isPlaying()) {
                    return;
                }
                mPlayer.setDataSource(new File("", "hlj_wallpaper").getAbsolutePath());
                mPlayer.setLooping(true);
                mPlayer.setVolume(0, 0);
                mPlayer.prepare();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mPlayer.start();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mPlayer.release();
            mPlayer = null;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                mPlayer.start();
            } else {
                mPlayer.pause();
            }
        }
    }
}
