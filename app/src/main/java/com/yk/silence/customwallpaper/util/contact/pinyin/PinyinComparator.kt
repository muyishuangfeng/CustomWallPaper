package com.yk.silence.customwallpaper.util.contact.pinyin

import com.yk.silence.customwallpaper.mvp.model.bean.ContactBean

/**
 * 拼音排序
 */
class PinyinComparator : Comparator<ContactBean> {

    override fun compare(o1: ContactBean?, o2: ContactBean?): Int {
        //这里主要是用来对RecyclerView里面的数据根据ABCDEFG...来排序
        return if (o1?.sortLetters.equals("@")
                || o2?.sortLetters.equals("#")) {
            -1;
        } else if (o1?.sortLetters.equals("#")
                || o2?.sortLetters.equals("@")) {
            1;
        } else {
            o1?.sortLetters!!.compareTo(o2?.sortLetters!!);
        }
    }
}