package com.yk.silence.customwallpaper.widget.activity

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.mvp.model.bean.TabEntity
import com.yk.silence.customwallpaper.widget.adapter.FragmentActionAdapter
import com.yk.silence.customwallpaper.widget.fragment.HomeFragment
import com.yk.silence.customwallpaper.widget.fragment.MyselfFragment
import com.yk.silence.customwallpaper.widget.fragment.NodeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnTabSelectListener, ViewPager.OnPageChangeListener {


    var mAdapter: FragmentActionAdapter? = null
    val mList: MutableList<Fragment>? = ArrayList()
    var homeFragment: HomeFragment? = null
    var myselfFragment: MyselfFragment? = null
    var nodeFragment: NodeFragment? = null
    private val mTitles = arrayOf("日记", "首页", "我的")
    //未选中
    val mIconUnselectIds = intArrayOf(R.drawable.ic_node, R.drawable.ic_read, R.drawable.ic_all)
    //选中
    val mIconSelectIds = intArrayOf(R.drawable.ic_node_selected, R.drawable.ic_read_selected,
            R.drawable.ic_all_selected)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()

    override fun getLayoutID(): Int {

        return R.layout.activity_main
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        homeFragment = HomeFragment()
        myselfFragment = MyselfFragment()
        nodeFragment = NodeFragment()
        mList?.add(nodeFragment!!)
        mList?.add(homeFragment!!)
        mList?.add(myselfFragment!!)
        mAdapter = FragmentActionAdapter(supportFragmentManager, mList!!)
        vp_main.adapter = mAdapter
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        common_table.setTabData(mTabEntities)
        common_table.setOnTabSelectListener(this)
        vp_main.addOnPageChangeListener(this)
    }


    override fun onTabSelect(position: Int) {
        vp_main.currentItem = position
    }

    override fun onTabReselect(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        common_table.currentTab = position;
    }


}
