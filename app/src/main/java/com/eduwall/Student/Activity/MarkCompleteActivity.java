package com.eduwall.Student.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

public class MarkCompleteActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_complete);

        initComponent();
        initData();
        initActionBar("Modules");
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
