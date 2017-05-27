package com.medicare.Util;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class CustomPTSansButtonView extends AppCompatButton {
    Context context;
    public CustomPTSansButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public CustomPTSansButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPTSansButtonView(Context context) {
        super(context);
        init();
    }


    public void init() {
        setTypeface(Utility.getPTSansFontStyle(getContext()), 1);

    }

}