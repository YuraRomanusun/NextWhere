package com.puma.nextwhere.customview;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class CustomFontRobotoRegularCheckbox extends CheckBox {


    public CustomFontRobotoRegularCheckbox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/regular.ttf"));
    }
}
