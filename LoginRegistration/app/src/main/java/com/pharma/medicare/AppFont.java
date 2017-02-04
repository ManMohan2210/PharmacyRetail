package com.pharma.medicare;

/**
 * Created by satveer on 16-01-2017.
 */

import android.app.Application;


public class AppFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
      //  TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Aller_Bd.ttf");
      //  FontsOverride.setDefaultFont(this, "DEFAULT", "Aller_Bd.ttf");
        //  This FontsOverride comes from the example I posted above
    }
}
