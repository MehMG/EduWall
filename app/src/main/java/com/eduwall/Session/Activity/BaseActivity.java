package com.eduwall.Session.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.API.CallAPiActivity;
import com.eduwall.API.GetCommonAPI;
import com.eduwall.Interface.Dialogue;
import com.eduwall.Interface.GetResult;
import com.eduwall.Interface.RecordDialog;
import com.eduwall.R;
import com.eduwall.Session.AppDialogs;
import com.eduwall.Session.Constant;
import com.eduwall.Session.GetExternalData;
import com.eduwall.Session.Permissions;
import com.eduwall.Session.SharePreference;

import java.util.List;

/**
 * Created by codesture on 23/5/17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public CallAPiActivity callAPiActivity;
    public GetCommonAPI getCommonAPI;

    public abstract void initComponent();

    public AppDialogs appDialogs;

    public String pushToken = "";

    public abstract void initData();

    public abstract void initClickListener();

    public SharePreference preference;
    public Context mContext;
    public Toolbar toolbar;
    public TextView toolbar_title;
    public ImageView imgBack;
    public ImageView imgLogout;
    public ImageView imgSave;
    public ImageView imgNewJoin;
    public ImageView imgSendEmail;
    public ImageView imgTask;
    public ImageView imgHome;
    public ImageView imgReply;
    public ImageView imgNotification;
    public ImageView imgDelete;
    public TextView txtPost;
    public TextView txtAddPostButton;

    public String actualName;
    public String selectedImagePath;

    public Dialogue dialogue;
    public RecordDialog recordDialog;
    public Permissions permissions;

    public String type;
    public GetResult getResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new SharePreference(this);
        mContext = BaseActivity.this;


        Log.e("Class Name", "----------------" + mContext.getClass().getSimpleName() + "--------------");

        dialogue = new Dialogue(BaseActivity.this);
        recordDialog = new RecordDialog(BaseActivity.this);
        permissions = new Permissions(mContext);

        appDialogs = new AppDialogs(mContext);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        callAPiActivity = new CallAPiActivity(mContext);
        getCommonAPI = new GetCommonAPI(mContext);

        preference.setActive(true);
        Log.e("ActiveCreate", preference.getActive().toString());

    }

    @Override
    protected void onStart() {
        super.onStart();
        preference.setActive(true);
        Log.e("ActiveStart", preference.getActive().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        preference.setActive(true);
        Log.e("ActiveResume", preference.getActive().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        preference.setActive(true);
        Log.e("ActiveRestart", preference.getActive().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addFragment(Fragment fragment, int layout) {
        String backStateName = fragment.getClass().getName();


        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);


        if (!fragmentPopped) {


            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(layout, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();


        }
    }

    public void initActionBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        imgBack = (ImageView) toolbar.findViewById(R.id.imgBack);
        imgLogout = (ImageView) toolbar.findViewById(R.id.imgLogout);
        imgSendEmail = (ImageView) toolbar.findViewById(R.id.imgSendEmail);
        imgNewJoin = (ImageView) toolbar.findViewById(R.id.imgNewJoin);
        imgTask = (ImageView) toolbar.findViewById(R.id.imgTask);
        imgSave = (ImageView) toolbar.findViewById(R.id.imgSave);
        imgHome = (ImageView) toolbar.findViewById(R.id.imgHome);
        imgReply = (ImageView) toolbar.findViewById(R.id.imgReply);
        imgDelete = (ImageView) toolbar.findViewById(R.id.imgDelete);
        imgNotification = (ImageView) toolbar.findViewById(R.id.imgNotification);
        txtAddPostButton = (TextView) toolbar.findViewById(R.id.txtAddPostButton);
        txtPost = (TextView) toolbar.findViewById(R.id.txtPost);
        toolbar_title.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

    }

    public void initBackButton() {
        imgBack.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public GridLayoutManager getLayoutManager(int span, int scrollType) {
        return new GridLayoutManager(BaseActivity.this, span, scrollType, false);
    }


    public String getAttachment(String type1, GetResult getResult) {


        this.getResult = getResult;
        type = type1;

        if (type == Constant.Image) {
            if (Build.VERSION.SDK_INT <= 19) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, 10);
            } else if (Build.VERSION.SDK_INT > 19) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        } else if (type == Constant.Audio) {
            if (Build.VERSION.SDK_INT <= 19) {
                Intent i = new Intent();
                i.setType("audio/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, 10);
            } else if (Build.VERSION.SDK_INT > 19) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        } else if (type == Constant.Video) {
            if (Build.VERSION.SDK_INT <= 19) {
                Intent i = new Intent();
                i.setType("video/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, 10);
            } else if (Build.VERSION.SDK_INT > 19) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 10);
            }
        } else if (type == Constant.Document) {

            Intent intent = new Intent();
            //sets the select file to all types of files
            intent.setType("*/*");
            //allows to select data and return it
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //starts new activity to select file and return data
            startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), 10);
        } else {

        }

        return selectedImagePath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Log.e("selectedImageUriData", data.toString());
            Uri selectedImageUri = data.getData();
            Log.e("selectedImageUri", selectedImageUri.toString());
            selectedImagePath = GetExternalData.getPath(mContext, selectedImageUri);
            getResult.getSuccess(selectedImagePath);

        }
    }
}