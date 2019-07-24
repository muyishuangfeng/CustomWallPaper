package com.yk.silence.customwallpaper.ui.mediapicture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.customwallpaper.ui.mediapicture.impl.OnLocalVideoSelectListener
import com.yk.silence.customwallpaper.ui.mediapicture.model.VideoMediaEntity
import kotlinx.android.synthetic.main.item_local_media_layout.view.*

/**
 * <pre>
 *    desc:   本地视频的适配器
 * <pre/>
 */
class LocalVideoAdapter : RecyclerView.Adapter<LocalVideoAdapter.LocalVideoViewHolder>() {
    lateinit var context: Context
    private var mSelectedPosition: Int = -1
    var mListener: OnLocalVideoSelectListener? = null
    private lateinit var data: List<VideoMediaEntity>
    private var checkState: HashSet<Int> = HashSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalVideoViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_local_media_layout,
                parent, false)
        return LocalVideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LocalVideoViewHolder, position: Int) {
        holder.itemView.ckb_item_local_checked.isChecked = checkState.contains(position)
        GlideUtil.loadUrlToImage(context,
                data[position].path,
                R.mipmap.ic_launcher,
                DiskCacheStrategy.NONE,
                holder.itemView.img_item_local_thumbnail )

        holder.itemView.ckb_item_local_checked.setOnClickListener {

            if (mSelectedPosition != position) {
                //先取消上个item的勾选状态
                checkState.remove(mSelectedPosition)
                notifyItemChanged(mSelectedPosition)
                //设置新Item的勾选状态
                mSelectedPosition = position
                checkState.add(mSelectedPosition)
                notifyItemChanged(mSelectedPosition)
            } else if (holder.itemView.ckb_item_local_checked.isChecked) {
                checkState.add(position)

            } else if (!holder.itemView.ckb_item_local_checked.isChecked) {

                checkState.remove(position)
            }
            if (mListener != null) {
                mListener!!.onVideoSelect(holder.view, position)

            }
        }
    }

    fun setData(data: List<VideoMediaEntity>) {
        this.data = data
        for (i in 0 until data.size) {
            if (data[i].isSelected) {
                mSelectedPosition = i
            }
        }
    }


    class LocalVideoViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    fun setOnItemSelectListener(listener: OnLocalVideoSelectListener) {
        this.mListener = listener
    }
}