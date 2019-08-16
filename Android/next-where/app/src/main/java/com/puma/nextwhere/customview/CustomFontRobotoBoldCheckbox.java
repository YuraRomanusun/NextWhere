package com.puma.nextwhere.customview;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomFontRobotoBoldCheckbox extends android.support.v7.widget.AppCompatCheckBox {


    public CustomFontRobotoBoldCheckbox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bold.ttf"));
    }
}
