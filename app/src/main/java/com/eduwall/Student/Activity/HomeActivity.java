package com.eduwall.Student.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by codesture on 2/6/17.
 */
public class HomeActivity extends BaseActivity {
    private LinearLayout lnvStuMyProfile;
    private LinearLayout lnvStuInbox;
    private LinearLayout lnvStuPayment;
    private LinearLayout lnvStuMyInstitute;
    private LinearLayout lnvStuSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initActionBar("Home");
        imgHome.setVisibility(View.VISIBLE);
        initComponent();
        initData();
        initClickListener();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token", refreshedToken);
    }

    @Override
    public void initComponent() {
        lnvStuMyProfile = (LinearLayout) findViewById(R.id.lnvStuMyProfile);
        lnvStuInbox = (LinearLayout) findViewById(R.id.lnvStuInbox);
        lnvStuPayment = (LinearLayout) findViewById(R.id.lnvStuPayment);
        lnvStuMyInstitute = (LinearLayout) findViewById(R.id.lnvStuMyInstitute);
        lnvStuSettings = (LinearLayout) findViewById(R.id.lnvStuSettings);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initClickListener() {
        lnvStuMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MyProfileActivity.class));
            }
        });
        lnvStuInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, InboxActivity.class));
            }
        });
        lnvStuPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PaymentActivity.class));
            }
        });
        lnvStuMyInstitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MyInstituteActivity.class));
            }
        });
        lnvStuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}