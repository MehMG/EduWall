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
public class InstructorNotificationFragment extends BaseFragment {

    String type = "2";     //Instructor List
    RecyclerView recyclerRequest;
    LinearLayout lnvMessage;
    ArrayList<Requests> requestArrayList;
    RequestAdapter requestAdapter;

    public InstructorNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_instructor_notification, container, false);

        initComponent(view);
        initData();
        initClickListener();

        return view;

    }

    @Override
    public void initComponent(View view) {
        recyclerRequest = (RecyclerView) view.findViewById(R.id.recyclerInsRequest);
        recyclerRequest.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        lnvMessage = (LinearLayout) view.findViewById(R.id.lnvInsMessage);
    }

    @Override
    public void initData() {
        requestArrayList = new ArrayList<>();

        //pending request of Instructors will be fetched here....
        //input institute_id
        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getinstructorlistbyinstituteid

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("institute_id", preference.getUserData().getId());

        callAPiActivity.doPost((Activity) getActivity(), map, Constant.Getinstructorlistbyinstituteid, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("Instructorlist");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("status").equalsIgnoreCase(Constant.requested)) {
                        Requests requests = new Requests();
                        requests.setName(jsonObject.getString("Instructor"));
                        requests.setRequestId(jsonObject.getString("pkid"));
                        requests.setCourse(jsonObject.getString("Course"));
                        requests.setSubject(jsonObject.getString("Subject"));
                        requests.setModule(jsonObject.getString("Module"));
                        requestArrayList.add(requests);
                    }
                }
                requestAdapter = new RequestAdapter(getActivity(), requestArrayList, type);
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
