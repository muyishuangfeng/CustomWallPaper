package com.yk.silence.customwallpaper.widget.fragment

import android.content.Intent
import android.view.View
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.flyco.tablayout.listener.OnTabSelectListener
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.impl.OnItemClickListener
import com.yk.silence.customwallpaper.ui.mediapicture.activity.ImageSelectActivity
import com.yk.silence.customwallpaper.util.FileUtil
import com.yk.silence.customwallpaper.widget.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import android.app.WallpaperManager
import android.graphics.BitmapFactory
import com.yk.silence.customwallpaper.mvp.model.bean.HomeModel
import java.io.IOException
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.yk.silence.customwallpaper.ui.mediapicture.activity.VideoSelectActivity


class HomeFragment : BaseFragment(), OnTabSelectListener, OnItemClickListener, View.OnClickListener {


    private val mTitles = arrayOf("图片", "视频")
    private var mAdapter: HomeAdapter? = null
    private var mList: List<HomeModel>? = ArrayList()
    private var mModel: HomeModel? = null
    private var mIndex: Int? = 0

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        fab.setOnClickListener(this)
        slide_layout.setTabData(mTitles)
        slide_layout.setOnTabSelectListener(this)
    }

    override fun onFragmentVisibleChange(b: Boolean) {
        if (b) {
            searchFile(Constants.IMAGE_PATH)
        }
    }


    override fun onTabSelect(position: Int) {
        if (position == 0) {
            mIndex = 0
            searchFile(Constants.IMAGE_PATH)
        } else {
            mIndex = 1
            searchFile(Constants.VIDEO_PATH)
        }
    }

    override fun onTabReselect(position: Int) {
    }

    override fun itemClick(position: Int, itemView: View) {
        mModel = mList!![position]
        if (mModel != null)
            setToWallPaper(mModel!!.path)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.fab -> {
                if (mIndex == 0) {
                    startActivity(Intent(context, ImageSelectActivity::class.java))
                } else {
                    startActivity(Intent(context, VideoSelectActivity::class.java))
                }
            }
        }
    }

    /**
     * 查询图片
     */
    private fun searchFile(path: String) {
        if (FileUtil.isFileExists(path)) {
            mList = FileUtil.searchAllFile(path)
            if (mList!!.isNotEmpty()) {
                rlv_home.visibility = View.VISIBLE
                lyt_empty.visibility = View.GONE
                mAdapter = HomeAdapter(context!!, mList!!)
                rlv_home.layoutManager = GridLayoutManager(context, 2)
                rlv_home.adapter = mAdapter
                mAdapter!!.mListener = this
            } else {
                rlv_home.visibility = View.GONE
                lyt_empty.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 设置壁纸
     */
    private fun setToWallPaper(path: String) {
        val wallpaperManager = WallpaperManager.getInstance(context);
        try {
            wallpaperManager.clear();
            val bitmap = BitmapFactory.decodeFile(path)
            wallpaperManager.setBitmap(bitmap);
        } catch (e: IOException) {
            e.printStackTrace();
        }
        Snackbar.make(fab, resources.getString(R.string.text_set_wallpaper_success), Snackbar.LENGTH_LONG)
                .setAction(resources.getString(R.string.text_wallpaper)) {
                    Toast.makeText(context, resources.getString(R.string.text_set_wallpaper_success),
                            Toast.LENGTH_LONG).show()
                }.show()
//        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
//        intent.putExtra("path", path)
//        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
//                ComponentName(context!!, CustomWallService::class.java))
//        startActivity(intent)
    }
}


