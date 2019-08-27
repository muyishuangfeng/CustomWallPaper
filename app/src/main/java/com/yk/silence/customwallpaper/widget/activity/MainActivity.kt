package com.yk.silence.customwallpaper.widget.activity

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.mvp.model.bean.TabEntity
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.customwallpaper.util.DrawerUtil
import com.yk.silence.customwallpaper.widget.adapter.FragmentActionAdapter
import com.yk.silence.customwallpaper.widget.fragment.HomeFragment
import com.yk.silence.customwallpaper.widget.fragment.ReadFragment
import com.yk.silence.customwallpaper.widget.fragment.NodeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.includ_main_layout.*

class MainActivity : BaseActivity(), OnTabSelectListener, ViewPager.OnPageChangeListener, View.OnClickListener {


    private var mAdapter: FragmentActionAdapter? = null
    private val mList: MutableList<Fragment>? = ArrayList()
    private var homeFragment: HomeFragment? = null
    private var myselfFragment: ReadFragment? = null
    private var nodeFragment: NodeFragment? = null
    private val mTitles = arrayOf("日记", "首页", "我的")
    //未选中
    private val mIconUnselectIds = intArrayOf(R.drawable.ic_node, R.drawable.ic_read, R.drawable.ic_all)
    //选中
    private val mIconSelectIds = intArrayOf(R.drawable.ic_node_selected, R.drawable.ic_read_selected,
            R.drawable.ic_all_selected)
    private val mTabEntities = java.util.ArrayList<CustomTabEntity>()

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        txt_mine_setting.setOnClickListener(this)
        txt_mine_model.setOnClickListener(this)
        txt_mine_contact.setOnClickListener(this)
        drawer_mine.setScrimColor(Color.TRANSPARENT);
        img_mine_avatar.setOnClickListener(this)
        homeFragment = HomeFragment()
        myselfFragment = ReadFragment()
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
        DrawerUtil.instance.setDrawerEdgeSize(this, drawer_mine,
                0.2f)
        initUserData()
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

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.txt_mine_setting -> {
                startActivity(SettingActivity::class.java)
            }
            R.id.txt_mine_model -> {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            R.id.txt_mine_contact -> {
                startActivity(ContactActivity::class.java)
            }
            R.id.img_mine_avatar -> {
                startActivity(MyselfActivity::class.java)
                drawer_mine.closeDrawers()
            }
        }
    }

    /**
     * 设置用户数据
     */
    private fun initUserData() {
        if (Constants.USER_AVATAR.isNotEmpty()) {
            GlideUtil.loadAvatar(this,
                    Constants.USER_AVATAR,
                    R.drawable.ic_no,
                    DiskCacheStrategy.ALL,
                    img_mine_avatar)
        }

    }
}
