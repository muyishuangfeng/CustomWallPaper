package com.yk.silence.customwallpaper.widget.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yk.silence.customwallpaper.R;
import com.yk.silence.customwallpaper.impl.OnItemInstallListener;
import com.yk.silence.customwallpaper.mvp.model.bean.AppBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class FileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AppBean> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemInstallListener mListener;

    public FileAdapter(List<AppBean> mList, Context context) {
        this.mList = mList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new EmptyViewHolder(mInflater.inflate(R.layout.empty_view, parent,
                    false));
        } else {
            return new MyViewHolder(mInflater.inflate(R.layout.layout_book_item, parent,
                    false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            AppBean infoModel = mList.get(position);
            ((MyViewHolder) holder).mTvAppName.setText(infoModel.getName() + "(v" + infoModel.getVersion() + ")");
            ((MyViewHolder) holder).mTvAppSize.setText(infoModel.getSize());
            ((MyViewHolder) holder).mTvAppPath.setText(infoModel.getPath());
            ((MyViewHolder) holder).ivIcon.setImageDrawable(infoModel.getIcon());

            ((MyViewHolder) holder).mTvAppInstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onInstallClick(position);
                    }
                }
            });
            ((MyViewHolder) holder).mTvAppDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onUnInstallClick(position);
                    }
                }
            });

            if (infoModel.getInstalled()) {
                ((MyViewHolder) holder).mTvAppDelete.setVisibility(View.VISIBLE);
            } else {
                ((MyViewHolder) holder).mTvAppDelete.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() > 0 ? 1 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() == 0) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvAppName;
        TextView mTvAppSize;
        TextView mTvAppInstall;
        TextView mTvAppDelete;
        TextView mTvAppPath;
        ImageView ivIcon;

        MyViewHolder(View view) {
            super(view);
            mTvAppName = view.findViewById(R.id.tv_name);
            mTvAppSize = view.findViewById(R.id.tv_size);
            mTvAppInstall = view.findViewById(R.id.tv_install);
            mTvAppPath = view.findViewById(R.id.tv_path);
            mTvAppDelete = view.findViewById(R.id.tv_delete);
            ivIcon = view.findViewById(R.id.iv_icon);
        }
    }

    /**
     * 回调
     */
    public void setOnInstallListener(OnItemInstallListener listener) {
        this.mListener = listener;
    }
}
