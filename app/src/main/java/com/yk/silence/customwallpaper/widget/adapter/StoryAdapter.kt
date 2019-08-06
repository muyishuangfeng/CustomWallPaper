package com.yk.silence.customwallpaper.widget.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.impl.OnItemClickListener
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import kotlinx.android.synthetic.main.item_story_layout.view.*

class StoryAdapter(context: Context, list: MutableList<NoteBean>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var mContext = context
    private var mInflater: LayoutInflater? = null
    private var mList: MutableList<NoteBean>? = ArrayList()
    var mListener: OnItemClickListener? = null

    init {
        this.mList = list
        this.mInflater = LayoutInflater.from(mContext)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(mInflater!!.inflate(R.layout.item_story_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        if (mList?.get(position)?.category.isNullOrEmpty()) {
            holder.itemView.txt_item_story_category.text = mContext.resources.getString(R.string.text_no_name)
        } else {
            holder.itemView.txt_item_story_category.text = mList?.get(position)?.category
        }
        holder.itemView.txt_item_story_number.text = mContext.resources.getString(R.string.text_story)
        mList?.get(position)?.descUrl?.let {
            GlideUtil.loadUrlToImage(mContext,
                    it,
                    R.drawable.ic_no,
                    DiskCacheStrategy.ALL,
                    holder.itemView.img_item_story_url)
        }

        if (mListener != null) {
            holder.itemView.setOnClickListener {
                mListener?.itemClick(position, holder.itemView)
            }
        }

    }

    /**
     * viewHolder
     */
    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}