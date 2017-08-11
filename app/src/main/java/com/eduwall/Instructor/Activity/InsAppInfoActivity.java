package com.eduwall.Instructor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

public class InsAppInfoActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        initComponent();
        initData();
        initActionBar("AppInfo");
        initBackButton();
        initClickListener();

    }

    @Override
    public void initComponent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

    }

}
