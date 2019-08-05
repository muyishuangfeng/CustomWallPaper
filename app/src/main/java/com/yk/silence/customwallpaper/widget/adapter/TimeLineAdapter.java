package com.yk.silence.customwallpaper.widget.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yk.silence.customwallpaper.R;
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteBean;
import com.yk.silence.customwallpaper.mvp.model.bean.note.NoteGroupBean;
import com.yk.silence.customwallpaper.ui.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.widget.AppCompatImageView;


public class TimeLineAdapter extends BaseExpandableListAdapter {

    private List<NoteGroupBean> mGroupList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public TimeLineAdapter(Context context, List<NoteGroupBean> groupList) {
        this.mContext = context;
        this.mGroupList = groupList;
        this.mInflater = LayoutInflater.from(mContext);

    }


    @Override
    public int getGroupCount() {
        return mGroupList == null ? 0 : mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupList.get(groupPosition).getList() == null ? 0 :
                Objects.requireNonNull(mGroupList.get(groupPosition).getList()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(mGroupList.get(groupPosition).getList()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.item_group_note_layout, parent, false);
            holder.mTxtYear = convertView.findViewById(R.id.txt_note_year);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.mTxtYear.setText(Objects.requireNonNull(mGroupList.get(groupPosition).getList())
                .get(groupPosition).getUserYear());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = mInflater.inflate(R.layout.item_note_layout, parent, false);
            holder.mImgUrl = convertView.findViewById(R.id.img_note_url);
            holder.mTxtContent = convertView.findViewById(R.id.txt_note_content);
            holder.mTxtLocation = convertView.findViewById(R.id.txt_note_location);
            holder.mTxtMonth = convertView.findViewById(R.id.txt_note_month);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.mTxtMonth.setText(Objects.requireNonNull(mGroupList.get(groupPosition).getList()).get(childPosition).getUserMonth());
        holder.mTxtLocation.setText(Objects.requireNonNull(mGroupList.get(groupPosition).getList()).get(childPosition).getUserLocation());
        holder.mTxtContent.setText(Objects.requireNonNull(mGroupList.get(groupPosition).getList()).get(childPosition).getUserContent());
        GlideUtil.INSTANCE.loadUrlToImage(mContext,
                Objects.requireNonNull((Objects.requireNonNull(mGroupList.get(groupPosition).getList())).get(childPosition).getUserUrl()),
                R.drawable.ic_no,
                DiskCacheStrategy.ALL,
                holder.mImgUrl);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 子布局
     */
    class ChildViewHolder {
        TextView mTxtContent, mTxtMonth, mTxtLocation;
        AppCompatImageView mImgUrl;
    }

    /**
     * 父布局
     */
    class GroupViewHolder {
        TextView mTxtYear;
    }
}
