package com.yk.silence.customwallpaper.widget.fragment

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseFragment
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteGroupBean
import com.yk.silence.customwallpaper.widget.activity.TimeLineDetailActivity
import com.yk.silence.customwallpaper.widget.adapter.TimeLineAdapter
import kotlinx.android.synthetic.main.fragment_timeline_layout.*

class TimeLineFragment : BaseFragment(), ExpandableListView.OnChildClickListener {


    private var mGroupList: MutableList<NoteGroupBean>? = ArrayList()
    private var mAdapter: TimeLineAdapter? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_timeline_layout
    }

    override fun initView() {
        val noteBean = NoteBean()
        mGroupList = noteBean.getData()
        mAdapter = TimeLineAdapter(context!!, mGroupList!!)
        elv_timeline.setGroupIndicator(null);
        elv_timeline.setAdapter(mAdapter)
        elv_timeline.setOnChildClickListener(this)
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