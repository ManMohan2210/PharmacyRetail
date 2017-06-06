package com.medicare.app.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class CustomPTSansTextViewUtil extends AppCompatTextView {
    Context context;
    public CustomPTSansTextViewUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public CustomPTSansTextViewUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPTSansTextViewUtil(Context context) {
        super(context);
        init();
    }


    public void init() {
        setTypeface(UtilityUtil.getPTSansFontStyle(getContext()), 1);

    }

}