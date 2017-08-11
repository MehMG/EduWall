package com.eduwall.Student.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

/**
 * Created by codesture on 7/6/17.
 */
public class InstituteInfoActivity extends BaseActivity {

    TextView txtIndexLabel;
    TextView txtIndexNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institute_info);
        initComponent();
        initData();
        initClickListener();
        initActionBar("Institute Name");
        initBackButton();
    }

    @Override
    public void initComponent() {
        txtIndexLabel = (TextView) findViewById(R.id.txtIndexLabel);
        txtIndexNo = (TextView) findViewById(R.id.txtIndexNo);

    }

    @Override
    public void initData() {
        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
            txtIndexLabel.setVisibility(View.VISIBLE);
            txtIndexNo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initClickListener() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
