package com.puma.nextwhere.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.Typefaces;

/**
 * Created by rajesh on 31/5/17.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // Load attributes
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomWidget, 0, 0);
        try {
            String fontInAssets = ta.getString(R.styleable.CustomWidget_customFont);
            if (fontInAssets != null) {
                setTypeface(Typefaces.get(context, fontInAssets));
            }
        } finally {
            ta.recycle();
        }
    }

}
