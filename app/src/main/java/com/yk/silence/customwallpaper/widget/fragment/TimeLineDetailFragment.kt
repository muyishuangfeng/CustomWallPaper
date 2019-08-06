package com.yk.silence.customwallpaper.widget.fragment

import android.content.Intent
import com.yk.silence.customwallpaper.R
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.base.BaseFragment
import com.yk.silence.customwallpaper.constance.AppConfig
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.service.DownloadService
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.fragment_timeline_detail.*


@Suppress("UNREACHABLE_CODE")
class TimeLineDetailFragment : BaseFragment(), CustomTitleBar.TitleClickListener, View.OnClickListener {

    private var mUrl: String? = null

    /**
     * 静态方法
     */
    companion object {
        fun newInstance(url: String): TimeLineDetailFragment {
            val args = Bundle()
            val fragment = TimeLineDetailFragment()
            args.putString(Constants.ARG_NUMBER, url);
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_timeline_detail
    }

    override fun initView() {
        txt_timeline_detail_download.setOnClickListener(this)
        title_timeline_detail.setTitleClickListener(this)
        val bundle = arguments
        if (bundle != null) {
            mUrl = bundle.getString(Constants.ARG_NUMBER)
            mUrl?.let {
                GlideUtil.loadUrlToImage(context!!,
                        it, R.drawable.ic_no,
                        DiskCacheStrategy.ALL,
                        img_timeline_detail_url)
            }
        }

    }


    override fun onLeftClick() {

    }

    override fun onRightClick() {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.txt_timeline_detail_download -> {
                if (!mUrl.isNullOrEmpty()) {
                    val mIntent = Intent(context, DownloadService::class.java)
                    mIntent.putExtra("params", "download")
                    mIntent.putExtra(AppConfig.DOWNLOAD_URL, mUrl)
                    context?.startService(mIntent)
                }

            }
        }
    }

}


