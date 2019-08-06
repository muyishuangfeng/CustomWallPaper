package com.yk.silence.customwallpaper.widget.activity

import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.customwallpaper.widget.fragment.TimeLineDetailFragment
import kotlinx.android.synthetic.main.activity_time_line_detail.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TimeLineDetailActivity : BaseActivity(), View.OnClickListener {

    private var mBean: NoteBean? = null

    override fun getLayoutID(): Int {
        return R.layout.activity_time_line_detail
    }

    override fun initData() {
        img_note_detail_content.setOnClickListener(this)
        if (intent != null && intent.extras != null) {
            mBean = intent.extras.getSerializable(Constants.ARG_NUMBER) as NoteBean
            txt_note_detail_content.text = mBean?.userContent
            txt_note_detail_location.text = mBean?.userLocation
            txt_note_detail_week.text = mBean?.userMonth
            mBean?.userUrl?.let {
                GlideUtil.loadUrlToImage(this,
                        it, R.drawable.ic_no,
                        DiskCacheStrategy.ALL,
                        img_note_detail_content)
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.img_note_detail_content -> {
                if (mBean != null) {
                    val mBundle=Bundle()
                    mBundle.putString(Constants.ARG_NUMBER,mBean?.userUrl)
                    startActivity(UIActivity::class.java,mBundle)
                }

            }
        }
    }

}
