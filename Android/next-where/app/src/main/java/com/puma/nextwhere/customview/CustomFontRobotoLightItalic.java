package com.puma.nextwhere.customview;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontRobotoLightItalic extends TextView {


    public CustomFontRobotoLightItalic(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/light_italic.ttf"));
    }
}
