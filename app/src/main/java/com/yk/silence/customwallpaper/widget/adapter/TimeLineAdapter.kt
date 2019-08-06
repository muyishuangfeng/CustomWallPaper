package com.yk.silence.customwallpaper.widget.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteGroupBean
import com.yk.silence.customwallpaper.ui.glide.GlideUtil

import java.util.ArrayList
import java.util.Objects

import androidx.appcompat.widget.AppCompatImageView


class TimeLineAdapter(context: Context, groupList: List<NoteGroupBean>) : BaseExpandableListAdapter() {

    private var mGroupList = ArrayList<NoteGroupBean>()
    private val mInflater: LayoutInflater
    private var mContext = context

    init {
        this.mGroupList = groupList as ArrayList<NoteGroupBean>
        this.mInflater = LayoutInflater.from(mContext)

    }


    override fun getGroupCount(): Int {
        return mGroupList.size
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun getChildrenCount(groupPosition: Int): Int {
        return if (mGroupList[groupPosition].list == null)
            0
        else
            Objects.requireNonNull<List<NoteBean>>(mGroupList[groupPosition].list).size
    }

    override fun getGroup(groupPosition: Int): Any {
        return mGroupList[groupPosition]
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return Objects.requireNonNull<List<NoteBean>>(mGroupList[groupPosition].list)[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var mView=convertView
        val holder: GroupViewHolder
        if (convertView == null) {
            holder = GroupViewHolder()
            mView = mInflater.inflate(R.layout.item_group_note_layout, parent, false)
            holder.mTxtYear = mView.findViewById(R.id.txt_note_year)
            holder.mTxtOpen = mView.findViewById(R.id.txt_note_tips)
            holder.mImgOpen = mView.findViewById(R.id.img_note_open)
            mView?.tag = holder
        } else {
            holder = mView?.tag as GroupViewHolder
        }
        if (isExpanded) {
            holder.mImgOpen?.setImageResource(R.drawable.ic_close)
            holder.mTxtOpen?.text = mContext.resources.getString(R.string.text_close)
        } else {
            holder.mTxtOpen?.text = mContext.resources.getString(R.string.text_open)
            holder.mImgOpen?.setImageResource(R.drawable.ic_open)
        }
        holder.mTxtYear?.text = mGroupList[groupPosition].list?.get(groupPosition)?.userYear
        return mView!!
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var mView = convertView
        val holder: ChildViewHolder
        if (convertView == null) {
            holder = ChildViewHolder()
            mView = mInflater.inflate(R.layout.item_note_layout, parent, false)
            holder.mImgUrl = mView.findViewById(R.id.img_note_url)
            holder.mTxtContent = mView.findViewById(R.id.txt_note_content)
            holder.mTxtLocation = mView.findViewById(R.id.txt_note_location)
            holder.mTxtMonth = mView.findViewById(R.id.txt_note_month)
            mView.tag = holder
        } else {
            holder = mView?.tag as ChildViewHolder
        }
        holder.mTxtMonth?.text = Objects.requireNonNull<List<NoteBean>>(mGroupList[groupPosition].list)[childPosition].userMonth
        holder.mTxtLocation?.text = Objects.requireNonNull<List<NoteBean>>(mGroupList[groupPosition].list)[childPosition].userLocation
        holder.mTxtContent?.text = Objects.requireNonNull<List<NoteBean>>(mGroupList[groupPosition].list)[childPosition].userContent
        GlideUtil.loadUrlToImage(mContext,
                Objects.requireNonNull<String>(Objects.requireNonNull<List<NoteBean>>(mGroupList[groupPosition].list)[childPosition].userUrl),
                R.drawable.ic_no,
                DiskCacheStrategy.ALL,
                holder.mImgUrl!!)
        return mView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    /**
     * 子布局
     */
    internal inner class ChildViewHolder {
        var mTxtContent: TextView? = null
        var mTxtMonth: TextView? = null
        var mTxtLocation: TextView? = null
        var mImgUrl: AppCompatImageView? = null
    }

    /**
     * 父布局
     */
    internal inner class GroupViewHolder {
        var mTxtYear: TextView? = null
        var mTxtOpen: TextView? = null
        var mImgOpen: AppCompatImageView? = null
    }
}
