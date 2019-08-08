package com.yk.silence.customwallpaper.widget.adapter

import android.content.Context

import com.yk.silence.customwallpaper.R

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yk.silence.customwallpaper.widget.fragment.CollectedFragment
import com.yk.silence.customwallpaper.widget.fragment.LoveFragment
import com.yk.silence.customwallpaper.widget.fragment.SendFragment


class FragmentMyselfAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    private val mTitles: Array<String> = context.resources.getStringArray(R.array.mines)


    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> SendFragment()
            2 -> CollectedFragment()
            else -> LoveFragment()
        }
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    /**
     * ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
     * @param position
     * @return
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}
