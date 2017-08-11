package com.eduwall.Session.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.eduwall.Authentication.Activity.AuthenticationActivity;
import com.eduwall.Instructor.Activity.InsHomeActivity;
import com.eduwall.PrivateUnit.Activity.PUHomeActivity;
import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.HomeActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by codesture on 24/5/17.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initComponent();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!FirebaseApp.getApps(SplashActivity.this).isEmpty()) {
                    pushToken = FirebaseInstanceId.getInstance().getToken();
                }
                if (preference.getUserData() != null) {
                    if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
                        startActivity(new Intent(SplashActivity.this, InsHomeActivity.class));
                    } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {
                        startActivity(new Intent(SplashActivity.this, PUHomeActivity.class));
                    } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
                        startActivity(new Intent(SplashActivity.this, PUHomeActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
                }
                finish();
            }
        }, 3000);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initClickListener() {
    }
}