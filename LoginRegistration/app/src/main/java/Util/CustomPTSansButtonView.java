package Util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;


public class CustomPTSansButtonView extends Button {
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