package com.yk.silence.customwallpaper.widget.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import kotlinx.android.synthetic.main.item_note_layout.view.*

class NodeAdapter(context: Context, list: MutableList<NoteBean>) : RecyclerView.Adapter<NodeAdapter.NoteViewHolder>() {

    var mContext: Context? = context
    var mList: MutableList<NoteBean>? = ArrayList()
    var mInflater: LayoutInflater? = null
    private val TYPE_TAG = 0
    private val TYPE_CONTENT = 1

    init {
        this.mList = list
        this.mInflater = LayoutInflater.from(mContext)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(mInflater!!.inflate(R.layout.item_note_layout, parent,
                false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_TAG) {
            holder.itemView.txt_note_line.visibility = View.GONE
        } else if (getItemViewType(position) == TYPE_CONTENT) {
            holder.itemView.txt_note_line.visibility = View.VISIBLE
        }
        holder.itemView.txt_note_month.text = mList?.get(position)?.userMonth
        holder.itemView.txt_note_content.text = mList?.get(position)?.userContent
        if (!TextUtils.isEmpty(mList?.get(position)?.userUrl)) {
            mList?.get(position)?.userUrl?.let {
                GlideUtil.loadUrlToImage(mContext!!,
                        it,
                        R.drawable.ic_no,
                        DiskCacheStrategy.ALL,
                        holder.itemView.img_note_url)
            }
        }
        holder.itemView.txt_note_location.text = mList?.get(position)?.userLocation


    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_TAG
        } else {
            return TYPE_CONTENT
        }
    }





    /**
     * ViewHolder
     */
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}