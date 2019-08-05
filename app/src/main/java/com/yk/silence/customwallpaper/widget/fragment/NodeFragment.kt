package com.yk.silence.customwallpaper.widget.fragment

import com.google.android.material.tabs.TabLayout
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseFragment
import com.yk.silence.customwallpaper.util.AnimationUtils
import com.yk.silence.customwallpaper.widget.adapter.FragmentNoteAdapter
import kotlinx.android.synthetic.main.fragment_node.*


class NodeFragment : BaseFragment(), TabLayout.OnTabSelectedListener {


    var mAdapter: FragmentNoteAdapter? = null


    override fun getLayoutID(): Int {
        return R.layout.fragment_node
    }

    override fun initView() {
        mAdapter = FragmentNoteAdapter(childFragmentManager, context!!)
        vpg_node.adapter = mAdapter
        node_table.setupWithViewPager(vpg_node)
        node_table.addOnTabSelectedListener(this)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (tab?.position == 0) {
            AnimationUtils.hideAddNote(img_node_add, img_node_time, img_node_edit)
        }else{
            AnimationUtils.showAddNote(img_node_add, img_node_time, img_node_edit)
        }
    }


}
