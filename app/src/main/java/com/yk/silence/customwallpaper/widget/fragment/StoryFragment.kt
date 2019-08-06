package com.yk.silence.customwallpaper.widget.fragment

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseFragment
import com.yk.silence.customwallpaper.impl.OnItemClickListener
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.widget.adapter.StoryAdapter
import kotlinx.android.synthetic.main.fragment_story_layout.*

class StoryFragment : BaseFragment(), OnItemClickListener {

    private var mList: MutableList<NoteBean>? = ArrayList()
    private var mAdapter: StoryAdapter? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_story_layout
    }

    override fun initView() {
        val noteBean = NoteBean()
        mList = noteBean.getNoteBeans()
        mAdapter = StoryAdapter(context!!, mList!!)
        mAdapter?.mListener = this
        rlv_story.layoutManager = GridLayoutManager(context, 3,
                RecyclerView.VERTICAL, false)
        rlv_story.adapter = mAdapter

    }

    override fun itemClick(position: Int, itemView: View) {
        val mBean = mList?.get(position)
        Toast.makeText(context, mBean.toString(), Toast.LENGTH_LONG).show()

    }
}