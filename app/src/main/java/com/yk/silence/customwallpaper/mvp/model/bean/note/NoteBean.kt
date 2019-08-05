package com.yk.silence.customwallpaper.mvp.model.bean.note

import org.litepal.crud.LitePalSupport


open class NoteBean : LitePalSupport() {
    var id: Long? = 0
    //月份
    var userMonth: String? = null
    //用户名
    var userName: String? = null
    //用户内容
    var userContent: String? = null
    //用户图片
    var userUrl: String? = null
    //用户地点
    var userLocation: String? = null
    //年份
    var userYear: String? = null
    var noteGroup: NoteGroupBean? = null


    fun getData(): MutableList<NoteGroupBean> {
        val mGroupList: MutableList<NoteGroupBean>? = ArrayList()
        val mList: MutableList<NoteBean>? = ArrayList()
        val nodeBean = NoteBean()
        val nodeGroupBean = NoteGroupBean()
        nodeBean.userYear = "2018"
        nodeBean.userMonth = "6月"
        nodeBean.userContent = "天若有情天亦老"
        nodeBean.userLocation = "莱安城"
        nodeBean.userName = "木易"
        nodeBean.userUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        mList?.add(nodeBean)
        nodeGroupBean.list=mList
        mGroupList?.add(nodeGroupBean)
        val nodeBean2 = NoteBean()
        val nodeGroupBean2 = NoteGroupBean()
        nodeBean2.userYear = "2018"
        nodeBean2.userMonth = "6月"
        nodeBean2.userContent = "天若有情天亦老"
        nodeBean2.userLocation = "莱安城"
        nodeBean2.userName = "木易"
        nodeBean2.userUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        mList?.add(nodeBean2)
        nodeGroupBean2.list=mList
        mGroupList?.add(nodeGroupBean2)
        return mGroupList!!
    }

}

