package com.yk.silence.customwallpaper.widget.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.impl.OnItemClickListener
import com.yk.silence.customwallpaper.mvp.model.bean.HomeModel
import com.yk.silence.customwallpaper.ui.glide.GlideUtil
import kotlinx.android.synthetic.main.item_home_layout.view.*

class HomeAdapter(context: Context, list: List<HomeModel>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var mContext: Context? = null
    private var mInflater: LayoutInflater? = null
    private var mList: List<HomeModel>? = ArrayList()
    var mListener: OnItemClickListener? = null


    init {
        this.mContext = context
        this.mList = list
        this.mInflater = LayoutInflater.from(mContext)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(mInflater!!.inflate(R.layout.item_home_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.itemView.txt_item_name.text = mList?.get(position)?.name
        GlideUtil.loadUrlToImage(mContext!!,
                mList!![position].path,
                R.drawable.ic_no,
                DiskCacheStrategy.ALL,
                holder.itemView.img_item_home)
        setUIEvent(holder)
    }


    class HomeViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!);

    /**
     * 设置事件
     *
     * @param holder
     */
    private fun setUIEvent(holder: HomeViewHolder) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(View.OnClickListener {
                val layoutPosition = holder.getLayoutPosition()
                mListener!!.itemClick(layoutPosition, holder.itemView)
            })
        }
    }


}