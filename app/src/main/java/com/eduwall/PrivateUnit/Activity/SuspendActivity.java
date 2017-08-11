package com.eduwall.PrivateUnit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

public class SuspendActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);

        initComponent();
        initData();
        initActionBar("Suspend Student");
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
