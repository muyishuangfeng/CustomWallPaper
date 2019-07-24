package com.yk.silence.customwallpaper.ui.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;

import com.yk.silence.customwallpaper.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;


public class CustomRoundView extends AppCompatImageView {

    float width, height;
    private int defaultRadius = 0;
    private int radius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;


    public CustomRoundView(Context context) {
        this(context, null);
        init(context, null);
    }

    public CustomRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        int maxLeft = Math.max(leftTopRadius, leftBottomRadius);
        int maxRight = Math.max(rightTopRadius, rightBottomRadius);
        int minWidth = maxLeft + maxRight;
        int maxTop = Math.max(leftTopRadius, rightTopRadius);
        int maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
        int minHeight = maxTop + maxBottom;
        if (width >= minWidth && height > minHeight) {
            Path path = new Path();
            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius, 0);
            path.lineTo(width - rightTopRadius, 0);
            path.quadTo(width, 0, width, rightTopRadius);

            path.lineTo(width, height - rightBottomRadius);
            path.quadTo(width, height, width - rightBottomRadius, height);

            path.lineTo(leftBottomRadius, height);
            path.quadTo(0, height, 0, height - leftBottomRadius);

            path.lineTo(0, leftTopRadius);
            path.quadTo(0, 0, leftTopRadius, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.width = getWidth();
        this.height = getHeight();
    }

    /**
     * 初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("CustomViewStyleable")
    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Custom_Round_Image_View);
        radius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_round_radius, defaultRadius);
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_left_top_radius, leftTopRadius);
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_right_top_radius, rightTopRadius);
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_left_bottom_radius, leftBottomRadius);
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.Custom_Round_Image_View_right_bottom_radius, rightBottomRadius);
        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (leftBottomRadius == defaultRadius) {
            leftBottomRadius = radius;
        }
        if (leftTopRadius == defaultRadius) {
            leftTopRadius = radius;
        }
        if (rightBottomRadius == defaultRadius) {
            rightBottomRadius = radius;
        }
        if (rightTopRadius == defaultRadius) {
            rightTopRadius = radius;
        }
        array.recycle();
    }

}
