package Util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;


public class CustomPTSansEditTextView extends EditText {
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