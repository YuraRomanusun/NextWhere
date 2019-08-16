package com.puma.nextwhere.customview;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontRobotoBold extends android.support.v7.widget.AppCompatTextView {


    public CustomFontRobotoBold(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bold.ttf"));
    }
}
