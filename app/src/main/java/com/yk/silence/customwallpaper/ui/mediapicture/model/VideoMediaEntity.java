package com.yk.silence.customwallpaper.ui.mediapicture.model;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.util.Locale;

@SuppressLint("ParcelCreator")
public class VideoMediaEntity extends BaseMediaEntity {


    private static final long MB = 1024 * 1024;

    private String mTitle;
    private String mDuration;
    private String mDateTaken;
    private String mMimeType;


    @Override
    public TYPE getMediaType() {
        return TYPE.VIDEO;
    }


    private VideoMediaEntity(Builder builder) {
        super(builder.mPath, builder.mId);
        this.mTitle = builder.mTitle;
        this.mDuration = builder.mDuration;
        this.mDateTaken = builder.mDateTaken;
        this.mMimeType = builder.mMimeType;
        this.size = builder.mSize;
    }

    public String getDuration() {
        try {
            long duration = Long.parseLong(mDuration);
            return formatTimeWithMin(duration);
        } catch (NumberFormatException e) {
            return "0:00";
        }
    }

    /**
     * 格式化
     */
    private String formatTimeWithMin(long duration) {
        if (duration <= 0) {
            return String.format(Locale.US, "%02d:%02d", 0, 0);
        }
        long totalSeconds = duration / 1000;

        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d", hours * 60 + minutes,
                    seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSizeByUnit() {
        String sizeString = getSize();
        double size = Double.valueOf(sizeString);
        if (size == 0) {
            return "0K";
        }
        if (size >= MB) {
            double sizeInM = size / MB;
            return String.format(Locale.getDefault(), "%.1f", sizeInM) + "M";
        }
        double sizeInK = size / 1024;
        return String.format(Locale.getDefault(), "%.1f", sizeInK) + "K";
    }

    public String getDateTaken() {
        return mDateTaken;
    }

    public String getMimeType() {
        return mMimeType;
    }


    /**
     * Builder
     */
    public static class Builder {
        //id
        private String mId;
        //标题
        private String mTitle;
        //路径
        private String mPath;
        //进度
        private String mDuration;
        //大小
        private String mSize;
        //日期
        private String mDateTaken;
        //类型
        private String mMimeType;

        public Builder(String id, String path) {
            this.mId = id;
            this.mPath = path;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setDuration(String duration) {
            this.mDuration = duration;
            return this;
        }

        public Builder setSize(String size) {
            this.mSize = size;
            return this;
        }

        public Builder setDateTaken(String dateTaken) {
            this.mDateTaken = dateTaken;
            return this;
        }

        public Builder setMimeType(String type) {
            this.mMimeType = type;
            return this;
        }

        public VideoMediaEntity build() {
            return new VideoMediaEntity(this);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mDateTaken);
        dest.writeString(this.mDuration);
        dest.writeString(this.mMimeType);
        dest.writeString(this.mTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private VideoMediaEntity(Parcel in) {
        super(in);
        this.mMimeType = in.readString();
        this.mTitle = in.readString();
        this.mDuration = in.readString();
        this.mDateTaken = in.readString();
    }

    public static final Creator<VideoMediaEntity> CREATOR = new Creator<VideoMediaEntity>() {
        @Override
        public VideoMediaEntity createFromParcel(Parcel source) {
            return new VideoMediaEntity(source);
        }

        @Override
        public VideoMediaEntity[] newArray(int size) {
            return new VideoMediaEntity[size];
        }
    };
}
