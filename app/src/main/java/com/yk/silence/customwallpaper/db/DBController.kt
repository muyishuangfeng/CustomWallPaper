package com.yk.silence.customwallpaper.db

import android.text.TextUtils
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import org.litepal.LitePal
import org.litepal.crud.LitePalSupport
import kotlin.collections.ArrayList
import android.content.ContentValues


class DBController {
    /**
     * 静态方法
     */
    companion object {
        private val sInstance: DBController? = null

        fun getInstance(): DBController {
            if (sInstance == null) {
                synchronized(DBController::class.java) {
                    sInstance == DBController()
                }
            }
            return sInstance!!
        }
    }

    /**
     * 增加
     */
    fun add(noteName: String?, content: String?, location: String?, imgUrl: String?,
            month: String?,year:String?): Boolean {
        val noteBean = NoteBean()
        noteBean.userContent = content
        noteBean.userLocation = location
        noteBean.noteName = noteName
        noteBean.userMonth = month
        noteBean.userYear = year
        if (!TextUtils.isEmpty(imgUrl)) {
            noteBean.userUrl = imgUrl
        }
        return noteBean.save()
    }

    /**
     * 添加集合
     */
    fun <T : LitePalSupport> addList(t: Collection<T>) {
        LitePal.saveAll(t);
    }

    /**
     * 删除
     */
    fun delete(id: Long) {
        LitePal.delete(NoteBean::class.java, id)
    }

    /**
     * 按条件删除
     */
    fun delete(content: String) {
        LitePal.deleteAll(NoteBean::class.java, "userContent = ?", content)
    }

    /**
     * 更新所有的数据
     * @param title 建
     * @param value 值
     * @param where 条件
     * @param content 对应的内容
     */
    fun updateAll(title: String, value: String, where: String, content: String) {
        val values = ContentValues()
        values.put(title, value)
        LitePal.updateAll(NoteBean::class.java, values, where, content)
    }

    /**
     * 更新id等于2的数据
     *
     * @param title 建
     * @param value 值
     * @param id  ID
     */
    fun update(title: String, value: String, id: Long) {
        val values = ContentValues()
        values.put(title, value)
        LitePal.update(NoteBean::class.java, values, id)
    }


    /**
     * 查询第一个
     */
    fun searchFirst(): NoteBean {
        return LitePal.findFirst(NoteBean::class.java)
    }

    /**
     * 查询最后一个
     */
    fun searchLast(): NoteBean {
        return LitePal.findLast(NoteBean::class.java)
    }

    /**
     * 查询所有的数据
     */
    fun searchAll(): MutableList<NoteBean> {
        return LitePal.findAll(NoteBean::class.java)
    }

    /**
     * 查询所有数据（sql）
     */
    fun searchAllData(sql: String): MutableList<NoteBean> {
        val cursor = LitePal.findBySQL(sql)
        val newsList = ArrayList<NoteBean>()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex("id"))
                val noteName = cursor.getString(cursor.getColumnIndex("noteName"))
                val userContent = cursor.getString(cursor.getColumnIndex("userContent"))
                val userMonth = cursor.getString(cursor.getColumnIndex("userMonth"))
                val userYear = cursor.getString(cursor.getColumnIndex("userYear"))
                val userUrl = cursor.getString(cursor.getColumnIndex("userUrl"))
                val userLocation = cursor.getString(cursor.getColumnIndex("userLocation"))
                val noteBean = NoteBean()
                noteBean.id = id
                noteBean.noteName = noteName
                noteBean.userMonth = userMonth
                noteBean.userYear = userYear
                noteBean.userUrl = userUrl
                noteBean.userLocation = userLocation
                noteBean.userContent = userContent
                newsList.add(noteBean)
            } while (cursor.moveToNext())
        }
        return newsList
    }

}