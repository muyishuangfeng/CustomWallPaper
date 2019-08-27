package com.yk.silence.customwallpaper.widget.activity

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteGroupBean
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.customwallpaper.widget.adapter.TimeLineAdapter
import kotlinx.android.synthetic.main.activity_story_detail.*
import java.io.File

class StoryDetailActivity : BaseActivity(), View.OnClickListener, ExpandableListView.OnChildClickListener {

    private var mGroupList: MutableList<NoteGroupBean>? = ArrayList()
    private var mAdapter: TimeLineAdapter? = null

    override fun getLayoutID(): Int {
        return R.layout.activity_story_detail
    }

    override fun initData() {
        toolbar_story.setOnClickListener(this)
        initAdapter()
        if (Constants.USER_BG.isNotEmpty()) {
            GlideUtil.loadLocalFile(this,
                    File(Constants.USER_BG),
                    R.drawable.ic_no,
                    R.drawable.ic_no,
                    DiskCacheStrategy.ALL,
                    img_story_photo)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.toolbar_story -> {
                onBackPressed()
            }
        }
    }

    private fun initAdapter() {
        val noteBean = NoteBean()
        mGroupList = noteBean.getData()
        mAdapter = TimeLineAdapter(this, mGroupList!!)
        elv_story.setGroupIndicator(null);
        elv_story.setAdapter(mAdapter)
        elv_story.setOnChildClickListener(this)
    }

    override fun onChildClick(parent: ExpandableListView?, view: View?, groupPosition: Int,
                              childPosition: Int, id: Long): Boolean {
        val mNoteBean = mGroupList?.get(groupPosition)?.list?.get(childPosition)
        val bundle = Bundle()
        bundle.putSerializable(Constants.ARG_NUMBER, mNoteBean)
        startActivity(TimeLineDetailActivity::class.java, bundle)
        return false
    }
}
