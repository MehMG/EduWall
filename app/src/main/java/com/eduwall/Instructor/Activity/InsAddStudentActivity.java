package com.eduwall.Instructor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by codesture on 13/6/17.
 */
public class InsAddStudentActivity extends BaseActivity {

    private EditText edtStudentId;
    private EditText edtStudentName;
    private Button btnAddStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        initComponent();
        initData();
        initClickListener();
        initActionBar("Add Student");
        initBackButton();
    }

    @Override
    public void initComponent() {
        edtStudentId = (EditText) findViewById(R.id.edtStudentId);
        edtStudentName = (EditText) findViewById(R.id.edtStudentName);
        btnAddStudent = (Button) findViewById(R.id.btnAddStudent);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Add Student By Instructor Id(input instructor_id,country_id,institute_id,course_id,student_name,student_id,index_no,status)
                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/AddStudentbyInstructorId

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("instructor_id", preference.getUserData().getId());
                map.put("institute_id", preference.getInstitute().getId());
                map.put("student_name", edtStudentName.getText().toString());
                map.put("course_id", preference.getInstitute().getCourseid());
                map.put("subject_id", preference.getInstitute().getSubjectid());
                map.put("module_id", preference.getInstitute().getModuleid());
                map.put("index_no", edtStudentId.getText().toString());
                map.put("status", "1");

                callAPiActivity.doPost((Activity) mContext, map, Constant.AddStudentbyInstructorId, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                        appDialogs.setSuccessToast("Student Added Successfully.");
                        Intent intent = new Intent(InsAddStudentActivity.this, InsStudentsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Parent", Constant.Course);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }
                });
            }
        });
    }
}
