package com.eduwall.Student.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Activity.InsStudentsActivity;
import com.eduwall.Instructor.Adapter.StudentListAdapter;
import com.eduwall.Instructor.Models.StudentList;
import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Adapter.JoinedAdapter;
import com.eduwall.Student.Models.JoinedInstitute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JoinedInstituteFragment extends BaseFragment {

    RecyclerView recyclerJoined;
    ArrayList<JoinedInstitute> joinedInstitutes;
    JoinedAdapter joinedAdapter;

    public JoinedInstituteFragment(ArrayList<JoinedInstitute> joinedInstitutesarray) {
        this.joinedInstitutes = joinedInstitutesarray;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_joined_institute, container, false);
        initComponent(view);
        initData();
        initClickListener();
        return view;
    }

    @Override
    public void initComponent(View view) {
        recyclerJoined = (RecyclerView) view.findViewById(R.id.recyclerJoined);
        recyclerJoined.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
    }


    @Override
    public void initData() {

        joinedAdapter = new JoinedAdapter(getActivity(),joinedInstitutes);
        recyclerJoined.setAdapter(joinedAdapter);
    }

    @Override
    public void initClickListener() {


    }
}
