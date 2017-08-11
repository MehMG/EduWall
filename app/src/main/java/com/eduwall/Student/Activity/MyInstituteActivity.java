package com.eduwall.Student.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.JoinedAdapter;
import com.eduwall.Student.Fragment.JoinedInstituteFragment;
import com.eduwall.R;
import com.eduwall.Student.Fragment.RequestedInstituteFragment;
import com.eduwall.Student.Models.JoinedInstitute;
import com.eduwall.Student.Models.RequestedInstitute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyInstituteActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    public ViewPager viewPager;

    ArrayList<JoinedInstitute> joinedInstitutesarray;
    ArrayList<RequestedInstitute> requestedInstitutesarray;

    public String pending = "2";
    public String requested = "0";
    public String joined = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_institute);

        initActionBar("My Institute");
        initBackButton();
        imgNewJoin.setVisibility(View.VISIBLE);
        initComponent();
        initData();
        initClickListener();
    }


    @Override
    public void initComponent() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Joined"));
        tabLayout.addTab(tabLayout.newTab().setText("Requested"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);


    }

    @Override
    public void initData() {

        joinedInstitutesarray = new ArrayList<>();
        requestedInstitutesarray = new ArrayList<>();

        //input student_id
        //http://visatwebsolution.com/eduwall/testapi/index.php/api/api/getInstituteByStudentid

        HashMap<String, String> map = new HashMap<>();
        map.put("student_id", preference.getUserData().getId());

        callAPiActivity.doPost((Activity) mContext, map, Constant.GetInstituteByStudentid, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                JSONArray jsonArray = result.getJSONArray("Institutelist");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("Status").equalsIgnoreCase(Constant.pending)) {
                        RequestedInstitute reqinstitute = new RequestedInstitute();
                        reqinstitute.setName(jsonObject.getString("Institute Name"));
                        reqinstitute.setInstituteID(jsonObject.getString("Institute Id"));
                        reqinstitute.setId(jsonObject.getString("id"));
                        reqinstitute.setStatus(jsonObject.getString("Status"));
                        requestedInstitutesarray.add(reqinstitute);
                    } else if (jsonObject.getString("Status").equalsIgnoreCase(Constant.joined)) {
                        JoinedInstitute institute = new JoinedInstitute();
                        institute.setName(jsonObject.getString("Institute Name"));
                        institute.setInstituteID(jsonObject.getString("Institute Id"));
                        institute.setId(jsonObject.getString("id"));
                        institute.setStatus(jsonObject.getString("Status"));
                        institute.setCourse(jsonObject.getString("Course Id"));
                        joinedInstitutesarray.add(institute);
                    }

                    Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.setOnTabSelectedListener(MyInstituteActivity.this);

                }
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });

    }

    @Override
    public void initClickListener() {

        imgNewJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyInstituteActivity.this, NewJoinActivity.class));
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public class Pager extends FragmentStatePagerAdapter {

        int tabCount;

        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    JoinedInstituteFragment tab1 = new JoinedInstituteFragment(joinedInstitutesarray);
                    return tab1;
                case 1:
                    RequestedInstituteFragment tab2 = new RequestedInstituteFragment(requestedInstitutesarray);
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
