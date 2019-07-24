package com.yk.silence.customwallpaper.widget.adapter


import java.util.ArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentActionAdapter(fm: FragmentManager, list: MutableList<Fragment>) : FragmentPagerAdapter(fm) {

    // 用来添加Fragment
    var mList: List<Fragment>? = ArrayList()
    private val mTitles = arrayOf("日记", "首页", "我的")

    init {
        mList = list
    }


    override fun getItem(position: Int): Fragment? {
        return if (mList != null && mList!!.isNotEmpty()) mList!![position] else null
    }


    override fun getCount(): Int {
        return if (mList != null && mList!!.isNotEmpty()) mList!!.size else 0
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }


}
