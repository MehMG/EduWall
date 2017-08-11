package com.eduwall.Instructor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Adapter.CoursesAdapter;
import com.eduwall.Instructor.Models.InstituteCoursesList;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.MyProfileActivity;
import com.eduwall.Student.Adapter.InstituteAdapter;
import com.eduwall.Student.Models.Institute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture on 13/6/17.
 */
public class InsAllCourseActivity extends BaseActivity {

    ArrayList<Institute> instituteCoursesListArrayList;
    CoursesAdapter coursesAdapter;

    RecyclerView recyclerCourses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_all_course);
        initComponent();
        initData();
        initActionBar("InstituteCoursesList");
        initBackButton();
        imgNewJoin.setVisibility(View.VISIBLE);
        initClickListener();
    }

    @Override
    public void initComponent() {

        recyclerCourses = (RecyclerView) findViewById(R.id.recyclerCourses);
        recyclerCourses.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        instituteCoursesListArrayList = new ArrayList<>();
    }

    @Override
    public void initData() {


        //(input instructor_id)
        // http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getInstitutelistByInstructorid

        HashMap<String, String> map = new HashMap<>();
        map.put("instructor_id", preference.getUserData().getId());

        callAPiActivity.doPost((Activity) mContext, map, Constant.GetInstitutelistByInstructorid, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("Institutelist");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.getString("status").equalsIgnoreCase(Constant.joined)) {
                        Institute coursesList = new Institute();
                        coursesList.setId(jsonObject.getString("institute_id"));
                        coursesList.setName(jsonObject.getString("institute"));
                        coursesList.setCourse(jsonObject.getString("course"));
                        coursesList.setCourseid(jsonObject.getString("course_id"));
                        coursesList.setSubject(jsonObject.getString("subject"));
                        coursesList.setSubjectid(jsonObject.getString("subject_id"));
                        coursesList.setModule(jsonObject.getString("module"));
                        coursesList.setModuleid(jsonObject.getString("module_id"));
                        coursesList.setCount(jsonObject.optString("totalstudent"));
                        coursesList.setStatus(jsonObject.getString("status"));
                        instituteCoursesListArrayList.add(coursesList);
                    }
                }
                coursesAdapter = new CoursesAdapter(InsAllCourseActivity.this, instituteCoursesListArrayList);
                recyclerCourses.setAdapter(coursesAdapter);
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
                Intent intent = new Intent(InsAllCourseActivity.this, InsAddCourseActivity.class);
                intent.putExtra("Parent", Constant.InstituteCoursesList);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
