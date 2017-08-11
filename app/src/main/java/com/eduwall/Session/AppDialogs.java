package com.eduwall.Session;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.R;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * Created by mahadev on 6/23/16.
 */
public class AppDialogs {

    Context mContext;
    ProgressDialog pDialog;
    RecordAudio recordAudio;
    int count = 0;
    String filepath;

    public AppDialogs(Context mContext) {
        this.mContext = mContext;
    }

    public void showProgressDialog() {
        if (mContext != null) {
            if (pDialog == null) {
                pDialog = new ProgressDialog(mContext);
            }
            pDialog.setMessage("Please wait..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

    }

    public void hideProgressDialog() {
        if (mContext != null) {
            if (pDialog != null) {
                pDialog.dismiss();
                pDialog = null;
            }
        }
    }

    public void setSuccessToast(String messgae) {

        TastyToast.makeText(mContext, messgae, 2, TastyToast.SUCCESS);
    }

    public void setErrorToast(String messgae) {

        TastyToast.makeText(mContext, messgae, 2, TastyToast.ERROR);
    }
}
