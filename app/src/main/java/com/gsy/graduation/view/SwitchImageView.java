package com.gsy.graduation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 */
public class SwitchImageView extends ImageView {

    private boolean mIsChecked = false;

    public SwitchImageView(Context context) {
        super(context);
    }

    public SwitchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
