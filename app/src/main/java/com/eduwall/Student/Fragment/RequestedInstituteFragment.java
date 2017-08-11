package com.eduwall.Student.Fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduwall.R;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Adapter.ReqInstituteAdapter;
import com.eduwall.Student.Models.RequestedInstitute;

import java.util.ArrayList;

public class RequestedInstituteFragment extends BaseFragment {
    RecyclerView rcl_all_post;
    ArrayList<RequestedInstitute> requestedInstitutes;
    ReqInstituteAdapter adapter;

    public RequestedInstituteFragment(ArrayList<RequestedInstitute> requestedInstitutesarray) {
        this.requestedInstitutes = requestedInstitutesarray;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_requested_institute, container, false);

        initComponent(view);
        initData();
        initClickListener();
        return view;

    }

    @Override
    public void initComponent(View view) {
        rcl_all_post = (RecyclerView) view.findViewById(R.id.rcl_add_module);
        rcl_all_post.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void initData() {

        adapter = new ReqInstituteAdapter(getActivity(), requestedInstitutes);
        rcl_all_post.setAdapter(adapter);
    }
    @Override
    public void initClickListener() {

    }
}
