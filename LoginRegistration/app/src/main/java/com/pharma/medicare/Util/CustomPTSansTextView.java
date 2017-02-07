package com.pharma.medicare.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomPTSansTextView extends TextView {
    Context context;
    public CustomPTSansTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public CustomPTSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomPTSansTextView(Context context) {
        super(context);
        init();
    }


    public void init() {
        setTypeface(Utility.getPTSansFontStyle(getContext()), 1);

    }

}