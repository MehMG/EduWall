package com.eduwall.Session;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by mahadev on 6/16/17.
 */
public class Permissions {


    public Context mContext;
    public static int WRITE_EXTERNAL_STORAGE_PERMISSON = 1;
    public static int READ_EXTERNAL_STORAGE_PERMISSON = 2;
    public static int CAMERA = 3;


    public Permissions(Context mContext) {
        this.mContext = mContext;
    }

    public boolean getReadWritePermisssion() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSON);
                return false;
            } else if (mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSON);
                return false;
            }
        } else {
            return true;
        }
        return true;
    }

    public boolean getCameraPermisssion() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA}, CAMERA);
                return false;
            }
        } else {
            return true;
        }

        return true;
    }


}
