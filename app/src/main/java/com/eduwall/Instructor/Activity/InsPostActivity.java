package com.eduwall.Instructor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.Interface.GetCourseResponse;
import com.eduwall.Interface.GetInstituteResponse;
import com.eduwall.Interface.GetModuleResponse;
import com.eduwall.Interface.GetSubjectResponse;
import com.eduwall.PrivateUnit.Model.Course;
import com.eduwall.PrivateUnit.Model.Module;
import com.eduwall.PrivateUnit.Model.Subject;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Model.AllpostData;
import com.eduwall.Student.Activity.ShowAllPostActivity;
import com.eduwall.Student.Models.InstituteList;

import org.json.JSONException;

import java.util.ArrayList;

public class InsPostActivity extends BaseActivity {

    private Spinner spinnerInsPrivateUnit;
    private Spinner spinnerInsCourse;
    private Spinner spinnerInsSubject;
    private Spinner spinnerInsModule;
    private Button btnInsEnterClass;
    private TextView txtPostSubject;
    private TextView txtCourseLabel1;
    private TextView txtPostModule;

    ArrayList<String> insNameArray;
    ArrayList<String> insIdArray;

    ArrayList<String> courseNameArray;
    ArrayList<String> courseIdArray;

    ArrayList<String> subjectsNameArray;
    ArrayList<String> subjectIdArray;

    ArrayList<String> moduleNameArray;
    ArrayList<String> moduleIdArray;

    String InstituteID;
    String CourseID;
    String SubjectID;
    String ModuleID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_post);
        initComponent();
        initSpinner();
        initData();
        initClickListener();
        initActionBar("Select Class");
        initBackButton();
    }


    @Override
    public void initComponent() {
        spinnerInsPrivateUnit = (Spinner) findViewById(R.id.spinnerInsPrivateUnit);
        spinnerInsCourse = (Spinner) findViewById(R.id.spinnerInsCourse);
        spinnerInsSubject = (Spinner) findViewById(R.id.spinnerInsSubject);
        spinnerInsModule = (Spinner) findViewById(R.id.spinnerInsModule);
        btnInsEnterClass = (Button) findViewById(R.id.btnInsEnterClass);
        txtPostSubject = (TextView) findViewById(R.id.txtPostSubject);
        txtPostModule = (TextView) findViewById(R.id.txtPostModule);
        txtCourseLabel1 = (TextView) findViewById(R.id.txtCourseLabel1);
    }


    private void initSpinner() {

        getCommonAPI.getInstitute(preference.getUserData().getId(), new GetInstituteResponse() {
            @Override
            public void getInstitute(ArrayList<String> insArray1, ArrayList<String> insArray2) throws JSONException {
                insIdArray = insArray1;
                insNameArray = insArray2;

                ArrayAdapter instituteAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, insNameArray);
                instituteAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerInsPrivateUnit.setAdapter(instituteAdapter);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

        spinnerInsPrivateUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InstituteID = insIdArray.get(i);

                if (i == 0) {
                    txtCourseLabel1.setVisibility(View.GONE);
                    spinnerInsCourse.setVisibility(View.GONE);
                } else {
                    getCommonAPI.getCource(InstituteID, new GetCourseResponse() {
                        @Override
                        public void getCourse(ArrayList<String> courseArray1, ArrayList<String> courseArray2) throws JSONException {
                            courseIdArray = courseArray1;
                            courseNameArray = courseArray2;

                            ArrayAdapter courseAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, courseNameArray);
                            courseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerInsCourse.setAdapter(courseAdapter);
                        }
                    });
                    txtCourseLabel1.setVisibility(View.VISIBLE);
                    spinnerInsCourse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerInsCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CourseID = courseIdArray.get(i);

                if (i == 0) {
                    spinnerInsSubject.setVisibility(View.GONE);
                    spinnerInsModule.setVisibility(View.GONE);
                    btnInsEnterClass.setVisibility(View.GONE);
                    txtPostSubject.setVisibility(View.GONE);
                    txtPostModule.setVisibility(View.GONE);
                } else {
                    getSubjectData();
                    spinnerInsSubject.setVisibility(View.VISIBLE);
                    spinnerInsModule.setVisibility(View.GONE);
                    txtPostSubject.setVisibility(View.VISIBLE);
                    txtPostModule.setVisibility(View.GONE);
                    btnInsEnterClass.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerInsSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SubjectID = subjectIdArray.get(i);

                if (i == 0) {
                    spinnerInsModule.setVisibility(View.GONE);
                    txtPostModule.setVisibility(View.GONE);
                } else {
                    spinnerInsModule.setVisibility(View.VISIBLE);
                    txtPostModule.setVisibility(View.VISIBLE);
                    btnInsEnterClass.setVisibility(View.GONE);
                    getModuleData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerInsModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModuleID = moduleIdArray.get(i);
                if (i == 0) {
                    btnInsEnterClass.setVisibility(View.GONE);
                } else {
                    btnInsEnterClass.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnInsEnterClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AllpostData id = new AllpostData();
                id.setInstituteName(spinnerInsPrivateUnit.getSelectedItem().toString());
                id.setCourseName(spinnerInsCourse.getSelectedItem().toString());
                id.setSubjectName(spinnerInsSubject.getSelectedItem().toString());
                id.setModuleName(spinnerInsModule.getSelectedItem().toString());
                id.setInstituteID(InstituteID);
                id.setCourseID(CourseID);
                id.setSubjectid(SubjectID);
                id.setModuleID(ModuleID);
                preference.setPostIDData(id);

                startActivity(new Intent(InsPostActivity.this, ShowAllPostActivity.class));
            }
        });
    }

    private void getSubjectData() {
        getCommonAPI.getSubject(InstituteID, CourseID, new GetSubjectResponse() {
            @Override
            public void getSubject(ArrayList<String> subjectArray1, ArrayList<String> subjectArray2) throws JSONException {
                subjectIdArray = subjectArray1;
                subjectsNameArray = subjectArray2;

                ArrayAdapter subjectAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, subjectsNameArray);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerInsSubject.setAdapter(subjectAdapter);
            }

        });
    }

    private void getModuleData() {
        getCommonAPI.getModule(InstituteID, CourseID, SubjectID, new GetModuleResponse() {
            @Override
            public void getModule(ArrayList<String> moduleArray1, ArrayList<String> moduleArray2) throws JSONException {

                moduleIdArray = moduleArray1;
                moduleNameArray = moduleArray2;

                ArrayAdapter moduleAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, moduleNameArray);
                moduleAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerInsModule.setAdapter(moduleAdapter);
            }

        });
    }
}
