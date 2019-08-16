package com.puma.nextwhere.customview;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomFontRobotoRegularEditText extends android.support.v7.widget.AppCompatEditText {


    public CustomFontRobotoRegularEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/regular.ttf"));
    }
}
