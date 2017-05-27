package com.medicare.Util;

import android.graphics.Typeface;

import com.medicare.activity.SignUp;

import java.lang.reflect.Field;

/**
 * Created by satveer on 17-02-2017.
 */

public class ReplaceFont {
    public static void replaceDefaultFont(SignUp context, String nameOfFontBeingReplaced, String nameOfFontInAsset){
        Typeface custtomFontTypeface=Typeface.createFromAsset(context.getAssets(),nameOfFontInAsset);
        replaceFont(nameOfFontBeingReplaced,custtomFontTypeface);
    }

    private static void replaceFont(String nameOfFontBeingReplaced, Typeface custtomFontTypeface) {
        try {
        Field myfield=Typeface.class.getDeclaredField(nameOfFontBeingReplaced);
        myfield.setAccessible(true);
            myfield.set(null,custtomFontTypeface);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
