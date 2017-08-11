package com.eduwall.PrivateUnit.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Models.StudentList;
import com.eduwall.PrivateUnit.Adapter.PU_StudentListAdapter;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PUStudentsActivity extends BaseActivity {

    private ArrayList<StudentList> studentLists;
    PU_StudentListAdapter studentListAdapter;

    private TextView txtInsTotal;
    private TextView txtStudent;
    private RecyclerView recyclerStudents;
    String category;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_students);
        initComponent();
        category = getIntent().getStringExtra("category");
        initData();
        initClickListener();

    }

    @Override
    public void initComponent() {
        txtInsTotal = (TextView) findViewById(R.id.txtInsTotal);
        txtStudent = (TextView) findViewById(R.id.txtInsStudent);
        recyclerStudents = (RecyclerView) findViewById(R.id.recyclerStudents);
        recyclerStudents.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void initData() {

        if (category.equalsIgnoreCase(Constant.Instructor)) {

            initActionBar("Instructor");
            txtStudent.setText("Instructor");

            //(input institute_id)
            //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getinstructorlistbyinstituteid

            HashMap<String, String> map = new HashMap<>();
            map.put("institute_id", preference.getUserData().getId());

            callAPiActivity.doPost((Activity) mContext, map, Constant.Getinstructorlistbyinstituteid, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    txtInsTotal.setText(result.getString("TotalInstructor"));
                    JSONArray array = result.getJSONArray("Instructorlist");
                    studentLists = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject list = array.getJSONObject(i);
                        StudentList studentList = new StudentList();
                        studentList.setStatus(list.getString("status"));
                        studentList.setId(list.getString("pkid"));
                        studentList.setImage("image");
                        studentList.setName(list.getString("Instructor"));
                        studentList.setInsName(list.getString("Institute"));
                        studentList.setCourse(list.getString("Course"));
                        studentLists.add(studentList);

                    }

                    studentListAdapter = new PU_StudentListAdapter(PUStudentsActivity.this, studentLists, category);
                    recyclerStudents.setAdapter(studentListAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {
                }
            });

        } else if (category.equalsIgnoreCase(Constant.Student)) {

            initActionBar("Students");
            txtStudent.setText("Student");
            //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getStudentListbyInstituteId

            HashMap<String, String> map = new HashMap<>();
            map.put("institute_id", preference.getUserData().getId());

            callAPiActivity.doPost((Activity) mContext, map, Constant.GetStudentListbyInstituteId, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    txtInsTotal.setText(result.getString("Totalstudent"));
                    JSONArray array = result.getJSONArray("Studentlist");

                    studentLists = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject list = array.getJSONObject(i);

                        StudentList studentList = new StudentList();
                        studentList.setCourse(list.getString("Course"));
                        studentList.setStatus(list.getString("Status"));
                        studentList.setId(list.getString("id"));
                        studentList.setImage(list.getString("profile_image"));
                        studentList.setName(list.getString("Student Name"));
                        studentList.setInsName(list.getString("Institute Name"));
                        studentList.setIndexno(list.getString("Index No"));

                        studentLists.add(studentList);
                    }
                    studentListAdapter = new PU_StudentListAdapter(PUStudentsActivity.this, studentLists, category);
                    recyclerStudents.setAdapter(studentListAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {
                }
            });
        }
        imgNewJoin.setVisibility(View.VISIBLE);
        initBackButton();
    }

    @Override
    public void initClickListener() {

    }

}
