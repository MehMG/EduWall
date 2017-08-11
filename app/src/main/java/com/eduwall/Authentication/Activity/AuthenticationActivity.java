package com.eduwall.Authentication.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.eduwall.Authentication.Fragment.LoginFragment;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

/**
 * Created by codesture on 24/5/17.
 */
public class AuthenticationActivity extends BaseActivity {
    BaseActivity baseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        initComponent();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void initData() {
        Fragment fragment = new LoginFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.authentication, fragment).commit();
    }

    @Override
    public void initClickListener() {
    }
}
