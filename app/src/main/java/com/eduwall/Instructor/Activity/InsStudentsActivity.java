package com.eduwall.Instructor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Adapter.StudentListAdapter;
import com.eduwall.Instructor.Models.StudentList;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InsStudentsActivity extends BaseActivity {

    private ArrayList<StudentList> studentLists;
    StudentListAdapter studentListAdapter;

    private TextView txtStudentNo;
    private RecyclerView recyclerStudents;

    String total;
    String parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_students);
        initComponent();
        initActionBar("Students");
        initBackButton();
        parent = getIntent().getStringExtra("Parent");
        if (parent.equalsIgnoreCase(Constant.Course)) {
            imgNewJoin.setVisibility(View.VISIBLE);

        } else {
            imgNewJoin.setVisibility(View.GONE);

        }
        initData();
        initClickListener();

    }

    @Override
    public void initComponent() {
        txtStudentNo = (TextView) findViewById(R.id.txtInsTotal);
        recyclerStudents = (RecyclerView) findViewById(R.id.recyclerStudents);
        recyclerStudents.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void initData() {


        studentLists = new ArrayList<>();

        if (parent.equalsIgnoreCase(Constant.Course)) {

            Log.e("Condition", Constant.Course);

            //40.Get Students List By Institute Id(input institute_id,course_id,subject_id,module_id)
            //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getStudentsByInstituteId

            HashMap<String, String> map = new HashMap<>();
            map.put("instructor_id", preference.getUserData().getId());
            map.put("institute_id", preference.getInstitute().getId());
            map.put("course_id", preference.getInstitute().getCourseid());
            map.put("subject_id", preference.getInstitute().getSubjectid());
            map.put("module_id", preference.getInstitute().getModuleid());

            callAPiActivity.doPost((Activity) mContext, map, Constant.GetStudentsByInstituteId, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                    JSONArray jsonArray = result.getJSONArray("Studentlist");

                    txtStudentNo.setText(jsonArray.length() + "");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        if ((jsonObject.getString("Status").equalsIgnoreCase(Constant.joined)) || (jsonObject.getString("Status").equalsIgnoreCase(Constant.suspended))) {
                            StudentList studentList = new StudentList();
                            studentList.setImage(jsonObject.optString("profile"));
                            studentList.setId(jsonObject.getString("id"));
                            studentList.setName(jsonObject.getString("Student Name"));
                            studentList.setIndexno(jsonObject.getString("Index No"));
                            studentList.setStatus(jsonObject.getString("Status"));
                            studentList.setGrade("A+");
                            studentList.setMarks("25");
                            studentList.setInstructor_id(jsonObject.getString("instructor_id"));
                            studentLists.add(studentList);
                        }
                    }
                    studentListAdapter = new StudentListAdapter(InsStudentsActivity.this, studentLists, Constant.CourseList);
                    recyclerStudents.setAdapter(studentListAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {

                }
            });

        } else {

            //39.Get Students List By Instructor Id(input instructor_id)
            //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getstudentbyinstructorid

            HashMap<String, String> map = new HashMap<>();
            map.put("instructor_id", preference.getUserData().getId());
            map.put("instructor_id", preference.getUserData().getId());

            callAPiActivity.doPost((Activity) mContext, map, Constant.Getstudentbyinstructorid, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                    JSONArray jsonArray = result.getJSONArray("Studentlist");
                    total = String.valueOf(jsonArray.length());
                    txtStudentNo.setText(total);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        StudentList studentList = new StudentList();
                        studentList.setImage(jsonObject.optString("profile_image"));
                        studentList.setId(jsonObject.getString("id"));
                        studentList.setName(jsonObject.getString("Student Name"));
                        studentList.setIndexno(jsonObject.getString("Index No"));
                        studentList.setStatus(jsonObject.getString("Status"));
                        studentList.setInstructor_id(jsonObject.optString("instructor_id"));
                        studentList.setGrade("A+");
                        studentList.setMarks("25");
                        studentLists.add(studentList);

                    }
                    studentListAdapter = new StudentListAdapter(InsStudentsActivity.this, studentLists, parent);
                    recyclerStudents.setAdapter(studentListAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {

                }
            });
        }
    }

    @Override
    public void initClickListener() {
        imgNewJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsStudentsActivity.this, InsAddStudentActivity.class));
            }
        });
    }

}
