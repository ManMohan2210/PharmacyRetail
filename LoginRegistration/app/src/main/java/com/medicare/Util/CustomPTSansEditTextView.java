package com.medicare.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class CustomPTSansEditTextView extends AppCompatEditText {
    Context context;
    public CustomPTSansEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public CustomPTSansEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPTSansEditTextView(Context context) {
        super(context);
        init();
    }


    public void init() {
        setTypeface(Utility.getPTSansFontStyle(getContext()), 1);

    }

}