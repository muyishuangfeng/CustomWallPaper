package com.yk.silence.customwallpaper.ui.mediapicture.model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseMediaEntity implements Parcelable {

    protected enum TYPE {
        IMAGE,
        VIDEO
    }

    protected String path;
    protected String id;
    protected String size;
    public Boolean isSelected = false;

    public abstract TYPE getMediaType();

    public BaseMediaEntity() {

    }

    public BaseMediaEntity(String path, String id) {
        this.path = path;
        this.id = id;
    }

    public BaseMediaEntity(Parcel in) {
        this.path = in.readString();
        this.id = in.readString();
        this.size = in.readString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.path);
        dest.writeString(this.size);
    }


}
