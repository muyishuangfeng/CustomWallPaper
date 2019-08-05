package com.yk.silence.customwallpaper.widget.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseFragment
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteGroupBean
import com.yk.silence.customwallpaper.widget.adapter.NodeAdapter
import com.yk.silence.customwallpaper.widget.adapter.TimeLineAdapter
import kotlinx.android.synthetic.main.fragment_timeline_layout.*

class TimeLineFragment : BaseFragment() {

    //var mAdapter: NodeAdapter? = null
    var mGroupList: MutableList<NoteGroupBean>? = ArrayList()
    var mAdapter: TimeLineAdapter? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_timeline_layout
    }

    override fun initView() {
        val noteBean = NoteBean()
        mGroupList = noteBean.getData()
        mAdapter = TimeLineAdapter(context, mGroupList)
        elv_timeline.setAdapter(mAdapter)
    }


}