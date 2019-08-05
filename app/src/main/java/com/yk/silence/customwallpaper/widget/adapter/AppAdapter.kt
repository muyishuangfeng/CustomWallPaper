package com.yk.silence.customwallpaper.widget.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.impl.OnItemInstallListener
import com.yk.silence.customwallpaper.mvp.model.bean.AppBean
import kotlinx.android.synthetic.main.item_app_layout.view.*
import com.yk.silence.customwallpaper.widget.adapter.AppAdapter.AppViewHolder as AppViewHolder1

class AppAdapter(context: Context, list: MutableList<AppBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: MutableList<AppBean>? = ArrayList()
    private var mContext: Context? = null
    private var mInflater: LayoutInflater? = null
    private val TYPE_NORMAL: Int = 0
    private val TYPE_EMPTY: Int = 1
    var mListener: OnItemInstallListener? = null

    init {
        this.mContext = context
        this.mList = list
        this.mInflater = LayoutInflater.from(mContext)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_EMPTY) {
            EmptyViewHolder(mInflater?.inflate(R.layout.item_empty_layout, parent, false))
        } else {
            AppViewHolder(mInflater?.inflate(R.layout.item_app_layout, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AppViewHolder) {
            holder.itemView.tv_app_name.text = mList?.get(position)?.name + "(v" + mList?.get(position)?.version + ")"
            holder.itemView.tv_app_path.text = mList?.get(position)?.path
            holder.itemView.tv_app_size.text = mList?.get(position)?.size
            holder.itemView.iv_app_icon.setImageDrawable(mList?.get(position)?.icon)
            holder.itemView.tv_app_install.setOnClickListener {
                if (mListener != null) {
                    mListener?.onInstallClick(position)
                }
            }

            holder.itemView.tv_app_delete.setOnClickListener {
                if (mListener != null) {
                    mListener?.onUnInstallClick(position)
                }
            }

            if (mList?.get(position)?.installed == true) {
                holder.itemView.tv_app_delete.visibility = View.VISIBLE
            } else {
                holder.itemView.tv_app_delete.visibility = View.GONE
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (mList?.size == 0) {
            TYPE_EMPTY
        } else {
            TYPE_NORMAL
        }
    }

    /**
     * viewHolder
     */
    class AppViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    /**
     * viewHolder
     */
    class EmptyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)


}