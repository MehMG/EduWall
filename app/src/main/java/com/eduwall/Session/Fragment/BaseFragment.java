package com.eduwall.Session.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduwall.API.CallAPiActivity;
import com.eduwall.API.GetCommonAPI;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.AppDialogs;
import com.eduwall.Session.Permissions;
import com.eduwall.Session.SharePreference;

/**
 * Created by codesture on 23/5/17.
 */
public abstract class BaseFragment extends Fragment {

    public CallAPiActivity callAPiActivity;

    public AppDialogs appDialogs;

    public abstract void initComponent(View view);

    public abstract void initData();

    public abstract void initClickListener();


    public BaseActivity baseActivity;

    android.support.v4.app.FragmentManager manager;
    public SharePreference preference;
    public Permissions permissions;
    public GetCommonAPI getCommonAPI;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = new SharePreference(getActivity());
        manager = getActivity().getSupportFragmentManager();
        baseActivity = (BaseActivity) getActivity();
        permissions = new Permissions(getActivity());

        callAPiActivity = new CallAPiActivity(getActivity());
        appDialogs = new AppDialogs(getActivity());

        getCommonAPI = new GetCommonAPI(getActivity());


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void addFragment(Fragment fragment, int layout) {
        String backStateName = fragment.getClass().getName();


        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);


        if (!fragmentPopped) {


            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(layout, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();


        }
    }

    public GridLayoutManager getLayoutManager(int span, int scrollType) {
        return new GridLayoutManager(getActivity(), span, scrollType, false);
    }

}
