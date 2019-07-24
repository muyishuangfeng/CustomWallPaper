package com.yk.silence.customwallpaper.ui.mediapicture.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import com.yk.silence.customwallpaper.ui.mediapicture.impl.OnLocalImageSelectListener
import com.yk.silence.customwallpaper.ui.mediapicture.model.ImageMediaEntity
import kotlinx.android.synthetic.main.item_local_media_layout.view.*

class LocalImageAdapter(context: Context) : RecyclerView.Adapter<LocalImageAdapter.LocalImageViewHolder>() {

    lateinit var mContext: Context
    var mSelectPosition: Int = -1
    private var mListener: OnLocalImageSelectListener? = null
    private lateinit var mList: List<ImageMediaEntity>
    /** 存储选中的图片 */
    private var mChosenImages: HashMap<Int, String> = HashMap()
    /** 存储选中的状态 */
    private var mCheckStates: HashMap<Int, Boolean> = HashMap()

    init {
        this.mContext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalImageViewHolder {
        return LocalImageViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_local_media_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size ?: 0
    }

    override fun onBindViewHolder(holder: LocalImageViewHolder, position: Int) {
        /** 通过map存储checkbox选中状态,放置rv复用机制导致的状态混乱状态 */
        holder.itemView.ckb_item_local_checked.setOnCheckedChangeListener(null)
        holder.itemView.ckb_item_local_checked.isChecked = mCheckStates.containsKey(position)
        GlideUtil.loadUrlToImage(mContext,
                mList[position].thumbnailPath,
                R.mipmap.ic_launcher,
                DiskCacheStrategy.ALL,
                holder.itemView.img_item_local_thumbnail)
        holder.itemView.ckb_item_local_checked.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mCheckStates[position] = true
                mList[position].isSelected = true
                // 将当前选中的图片存入map
                mChosenImages[position] = mList[position].path
            } else {
                // 从选中列表中移除
                mChosenImages.remove(position)
                mCheckStates.remove(position)
            }

            if (mListener != null) {
                val selectImages = ArrayList<String>()
                for (values in mChosenImages.values) {
                    selectImages.add(values)
                }
                mListener!!.onImageSelect(holder.itemView, position, selectImages)
            }
        }
    }

    /**
     * 设置数据
     */
    fun setData(data: List<ImageMediaEntity>) {
        this.mList = data
        for (i in 0 until data.size) {
            if (data[i].isSelected) {
                mSelectPosition = i
            }
        }

    }


    /**
     * ViewHolder
     */
    class LocalImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setOnItemSelectListener(listener: OnLocalImageSelectListener) {
        this.mListener = listener
    }
}