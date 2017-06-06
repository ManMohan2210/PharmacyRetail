package com.medicare.app.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class CustomPTSansButtonViewUtil extends AppCompatButton {
    Context context;
    public CustomPTSansButtonViewUtil(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public CustomPTSansButtonViewUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPTSansButtonViewUtil(Context context) {
        super(context);
        init();
    }


    public void init() {
        setTypeface(UtilityUtil.getPTSansFontStyle(getContext()), 1);

    }

}