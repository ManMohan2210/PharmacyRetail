package com.medicare.app.activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by satveer on 13-06-2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "fcmsharedprefdemo";
    private static final String KEY_ACCES_TOKEN = "token";
    private static Context mCtx;
    private static SharedPrefManager mInstance;

    private SharedPrefManager(Context context){
        mCtx=context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null)
            mInstance= new SharedPrefManager(context);
            return  mInstance;
        }
        public boolean storeToken (String token){
            SharedPreferences sharedPrefrences= mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPrefrences.edit();
            editor.putString(KEY_ACCES_TOKEN,token);
            editor.apply();
            return true;

        }
        public String getToken(){
            SharedPreferences sharedPrefrences= mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPrefrences.getString(KEY_ACCES_TOKEN,null);
        }
}
