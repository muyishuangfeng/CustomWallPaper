package com.yk.silence.customwallpaper.widget.adapter

import android.content.Context

import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.widget.fragment.StoryFragment
import com.yk.silence.customwallpaper.widget.fragment.TimeLineFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter



class FragmentNoteAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {
    private val mTitles: Array<String> = context.resources.getStringArray(R.array.notes)


    override fun getItem(position: Int): Fragment {
        return if (position == 1) {
            StoryFragment()
        } else TimeLineFragment()
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
