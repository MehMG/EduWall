package com.eduwall.PrivateUnit.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.PrivateUnit.Adapter.RequestAdapter;
import com.eduwall.PrivateUnit.Model.Requests;
import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentNotificationFragment extends BaseFragment {

    String type = "1";   //StudentList
    RecyclerView recyclerRequest;
    LinearLayout lnvMessage;
    ArrayList<Requests> requestArrayList;
    RequestAdapter requestAdapter;

    public StudentNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_notification, container, false);

        initComponent(view);
        initData();
        initClickListener();

        return view;
    }


    @Override
    public void initComponent(View view) {
        recyclerRequest = (RecyclerView) view.findViewById(R.id.recyclerRequest);
        recyclerRequest.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        lnvMessage = (LinearLayout) view.findViewById(R.id.lnvMessage);
    }

    @Override
    public void initData() {
        requestArrayList = new ArrayList<>();


        //input institute_id
        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getstudentlist

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("institute_id", preference.getUserData().getId());

        callAPiActivity.doPost((Activity) getActivity(), map, Constant.Getstudentlist, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("pending");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Requests requests = new Requests();
                    requests.setName(jsonObject.getString("student_name"));
                    requests.setRequestId(jsonObject.getString("id"));
                    requests.setProgram(jsonObject.getString("program"));
                    requests.setIndexno(jsonObject.getString("index_no"));

                    requestArrayList.add(requests);
                }
                requestAdapter = new RequestAdapter(getActivity(), requestArrayList,type);
                recyclerRequest.setAdapter(requestAdapter);

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });

    }

    @Override
    public void initClickListener() {

    }
}
