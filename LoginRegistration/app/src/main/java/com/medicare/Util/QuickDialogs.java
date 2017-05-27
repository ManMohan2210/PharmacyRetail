package com.medicare.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager.BadTokenException;

import com.medicare.activity.R;


public class QuickDialogs {

	 public static ProgressDialog showLoadingDialog(Context context, String title, String message) {
		 ProgressDialog dialog = new ProgressDialog(context);
         try {
                 dialog.show();
         } catch (BadTokenException e) {

         }
         dialog.setCancelable(false);
         dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

         dialog.setContentView(R.layout.progresslayout);
         // dialog.setMessage(Message);
         return dialog;
    }
}
