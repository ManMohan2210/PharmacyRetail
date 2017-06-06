package com.medicare.app.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;


public class CustomPTSansEditTextViewUtil extends AppCompatEditText {
    Context context;
    public CustomPTSansEditTextViewUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public CustomPTSansEditTextViewUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPTSansEditTextViewUtil(Context context) {
        super(context);
        init();
    }


    public void init() {
        setTypeface(UtilityUtil.getPTSansFontStyle(getContext()), 1);

    }

}