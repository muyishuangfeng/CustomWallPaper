package com.yk.silence.customwallpaper.mvp.model.bean.note

import org.litepal.crud.LitePalSupport
import java.io.Serializable


open class NoteBean : LitePalSupport(), Serializable {
    var id: Long? = 0
    //月份
    var userMonth: String? = null
    //用户名
    var noteName: String? = null
    //用户内容
    var userContent: String? = null
    //描述
    var description: String? = null
    //分类
    var category: String? = null
    //用户图片
    var userUrl: String? = null
    //用户地点
    var userLocation: String? = null
    //年份
    var userYear: String? = null
    //描述url
    var descUrl: String? = null
    //分组
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
        nodeBean.noteName = "木易"
        nodeBean.userUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        mList?.add(nodeBean)
        val nodeBean2 = NoteBean()
        nodeBean2.userYear = "2019"
        nodeBean2.userMonth = "8月"
        nodeBean2.userContent = "天若有情天亦老"
        nodeBean2.userLocation = "莱安城"
        nodeBean2.noteName = "木易"
        nodeBean2.userUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        mList?.add(nodeBean2)
        nodeGroupBean.list = mList
        mGroupList?.add(nodeGroupBean)
        return mGroupList!!
    }

    fun getNoteBeans(): MutableList<NoteBean> {
        val mList: MutableList<NoteBean>? = ArrayList()
        val nodeBean = NoteBean()
        val nodeGroupBean = NoteGroupBean()
        nodeBean.category = "工作"
        nodeBean.userMonth = "6月"
        nodeBean.userContent = "天若有情天亦老"
        nodeBean.userLocation = "莱安城"
        nodeBean.noteName = "天若有情"
        nodeBean.userUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        nodeBean.descUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        mList?.add(nodeBean)
        val nodeBean2 = NoteBean()
        nodeBean2.category = "生活"
        nodeBean2.userYear = "2019"
        nodeBean2.userMonth = "8月"
        nodeBean2.userContent = "天若有情天亦老"
        nodeBean2.userLocation = "莱安城"
        nodeBean2.noteName = "天亦老"
        nodeBean2.userUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        nodeBean2.descUrl = "http://mmbiz.qpic.cn/mmbiz/PwIlO51l7wuFyoFwAXfqPNETWCibjNACIt6ydN7vw8LeIwT7IjyG3eeribmK4rhibecvNKiaT2qeJRIWXLuKYPiaqtQ/0"
        mList?.add(nodeBean2)
        nodeGroupBean.list = mList
        return mList!!
    }

    override fun toString(): String {
        return "NoteBean(id=$id, userMonth=$userMonth, noteName=$noteName, userContent=$userContent, description=$description, category=$category, userUrl=$userUrl, userLocation=$userLocation, userYear=$userYear, descUrl=$descUrl, noteGroup=$noteGroup)"
    }


}

