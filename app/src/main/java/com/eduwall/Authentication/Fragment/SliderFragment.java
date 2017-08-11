package com.eduwall.Authentication.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduwall.R;
import com.eduwall.Session.Fragment.BaseFragment;

/**
 * Created by codesture on 23/5/17.
 */
public class SliderFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slider_image, container, false);
        initComponent(view);
        initData();
        initClickListener();

        return view;
    }

    @Override
    public void initComponent(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

    }
}
