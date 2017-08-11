package com.eduwall.Instructor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Authentication.Models.User;
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
import com.eduwall.Student.Models.InstituteList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class InsAddNewCourseActivity extends BaseActivity {


    private Spinner spinnerAddNew;
    private LinearLayout lnvAddCourse;
    private EditText edtAddCourse;
    private Button btnAddCourse;
    private LinearLayout lnvAddSubject;
    private LinearLayout lnvSelInstitute;
    private Spinner spinnerSelSubCourse;
    private Spinner spinnerSelModCourse;
    private EditText edtAddSubject;
    private Button btnAddSubject;
    private LinearLayout lnvAddModule;
    private Spinner spinnerSelModSubject;
    private Spinner spinnerSelInstitute;
    private EditText edtAddModule;
    private Button btnAddModule;

    String InstituteID;
    String CourseID;
    String SubjectID;

    ArrayList<String> addNewArray = new ArrayList<>();

    ArrayList<String> insNameArray = new ArrayList<>();
    ArrayList<String> insIdArray = new ArrayList<>();

    ArrayList<String> courseNameArray = new ArrayList<>();
    ArrayList<String> courseIdArray = new ArrayList<>();

    ArrayList<String> subjectsNameArray = new ArrayList<>();
    ArrayList<String> subjectIdArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puadd_course);

        initComponent();
        initSpinner();
        initData();
        initActionBar("Add");
        initBackButton();
        initClickListener();
    }


    @Override
    public void initComponent() {

        spinnerAddNew = (Spinner) findViewById(R.id.spinnerAddNew);
        lnvAddCourse = (LinearLayout) findViewById(R.id.lnvAddCourse);
        edtAddCourse = (EditText) findViewById(R.id.edtAddCourse);
        btnAddCourse = (Button) findViewById(R.id.btnAddCourse);
        lnvAddSubject = (LinearLayout) findViewById(R.id.lnvAddSubject);
        lnvSelInstitute = (LinearLayout) findViewById(R.id.lnvSelInstitute);
        spinnerSelSubCourse = (Spinner) findViewById(R.id.spinnerSelSubCourse);
        spinnerSelModCourse = (Spinner) findViewById(R.id.spinnerSelModCourse);
        edtAddSubject = (EditText) findViewById(R.id.edtAddSubject);
        btnAddSubject = (Button) findViewById(R.id.btnAddSubject);
        lnvAddModule = (LinearLayout) findViewById(R.id.lnvAddModule);
        spinnerSelModSubject = (Spinner) findViewById(R.id.spinnerSelModSubject);
        spinnerSelInstitute = (Spinner) findViewById(R.id.spinnerSelInstitute);
        edtAddModule = (EditText) findViewById(R.id.edtAddModule);
        btnAddModule = (Button) findViewById(R.id.btnAddModule);

    }

    private void initSpinner() {

        addNewArray.add("Course");
        addNewArray.add("Subject");
        addNewArray.add("Module");

        insIdArray.add("0");
        insNameArray.add("Select Institute");

        courseIdArray.add("0");
        courseNameArray.add("Select Course");

        subjectIdArray.add("0");
        subjectsNameArray.add("Select Subject");

        ArrayAdapter addNewAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, addNewArray);
        addNewAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerAddNew.setAdapter(addNewAdapter);

        getCommonAPI.getInstitute("1", new GetInstituteResponse() {
            @Override
            public void getInstitute(ArrayList<String> insArray1, ArrayList<String> insArray2) throws JSONException {

                insIdArray = insArray1;
                insNameArray = insArray2;

                ArrayAdapter instituteAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, insNameArray);
                instituteAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerSelInstitute.setAdapter(instituteAdapter);
            }

        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

        spinnerAddNew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                lnvSelInstitute.setVisibility(View.VISIBLE);

                if (spinnerSelInstitute.getSelectedItemId() == 0) {
                    lnvAddCourse.setVisibility(View.GONE);
                    lnvAddSubject.setVisibility(View.GONE);
                    lnvAddModule.setVisibility(View.GONE);
                } else {
                    if (i == 0) {
                        lnvAddCourse.setVisibility(View.VISIBLE);
                        lnvAddSubject.setVisibility(View.GONE);
                        lnvAddModule.setVisibility(View.GONE);
                    } else if (i == 1) {
                        lnvAddCourse.setVisibility(View.GONE);
                        lnvAddSubject.setVisibility(View.VISIBLE);
                        lnvAddModule.setVisibility(View.GONE);
                        getCourseByInstituteID(InstituteID);
                    } else if (i == 2) {
                        lnvAddCourse.setVisibility(View.GONE);
                        lnvAddSubject.setVisibility(View.GONE);
                        lnvAddModule.setVisibility(View.VISIBLE);
                        getCourseByInstituteID(InstituteID);
                        getSubjectByCourseID(InstituteID, CourseID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSelInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                } else {
                    if (spinnerAddNew.getSelectedItemId() == 0) {
                        lnvAddCourse.setVisibility(View.VISIBLE);
                        lnvAddSubject.setVisibility(View.GONE);
                        lnvAddModule.setVisibility(View.GONE);
                    } else if (spinnerAddNew.getSelectedItemId() == 1) {
                        lnvAddCourse.setVisibility(View.GONE);
                        lnvAddSubject.setVisibility(View.VISIBLE);
                        lnvAddModule.setVisibility(View.GONE);
                    } else if (spinnerAddNew.getSelectedItemId() == 2) {
                        lnvAddCourse.setVisibility(View.GONE);
                        lnvAddSubject.setVisibility(View.GONE);
                        lnvAddModule.setVisibility(View.VISIBLE);
                    }
                    getCourseByInstituteID(insIdArray.get(i));
                    InstituteID = insIdArray.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSelSubCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                } else {
                    InstituteID = insIdArray.get((int) spinnerSelInstitute.getSelectedItemId());
                    CourseID = courseIdArray.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerSelModCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InstituteID = insIdArray.get((int) spinnerSelInstitute.getSelectedItemId());
                CourseID = courseIdArray.get(i);
                getSubjectByCourseID(InstituteID, CourseID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSelModSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InstituteID = insIdArray.get((int) spinnerSelInstitute.getSelectedItemId());
                CourseID = courseIdArray.get((int) spinnerSelModCourse.getSelectedItemId());
                SubjectID = subjectIdArray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/addcourse

                HashMap<String, String> map = new HashMap<>();
                map.put("name", edtAddCourse.getText().toString());
                map.put("institute_id", InstituteID);


                callAPiActivity.doPost(InsAddNewCourseActivity.this, map, Constant.Addcourse_post, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {

                        edtAddCourse.setText("");
                        appDialogs.setSuccessToast(result.getString("message"));

                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }

                });

            }
        });
        btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/addsubject_post
                HashMap<String, String> map = new HashMap<>();
                map.put("name", edtAddSubject.getText().toString());
                map.put("institute_id", InstituteID);
                map.put("course_id", CourseID);


                callAPiActivity.doPost(InsAddNewCourseActivity.this, map, Constant.Addsubject_post, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {

                        btnAddSubject.setText("");
                        appDialogs.setSuccessToast(result.getString("message"));

                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }

                });
            }
        });
        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/addmodule_post
                HashMap<String, String> map = new HashMap<>();
                map.put("name", edtAddModule.getText().toString());
                map.put("institute_id", InstituteID);
                map.put("course_id", CourseID);
                map.put("subject_id", SubjectID);


                callAPiActivity.doPost(InsAddNewCourseActivity.this, map, Constant.Addmodule_post, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {

                        edtAddSubject.setText("");
                        appDialogs.setSuccessToast(result.getString("message"));

                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }

                });
            }
        });

    }

    private void getCourseByInstituteID(String instituteID) {

        getCommonAPI.getCource(String.valueOf(instituteID), new GetCourseResponse() {
            @Override
            public void getCourse(ArrayList<String> courseArray1, ArrayList<String> courseArray2) throws JSONException {

                courseIdArray = courseArray1;
                courseNameArray = courseArray2;

                if (spinnerAddNew.getSelectedItemId() == 1) {
                    ArrayAdapter courseAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, courseNameArray);
                    courseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinnerSelSubCourse.setAdapter(courseAdapter);
                } else if (spinnerAddNew.getSelectedItemId() == 2) {
                    ArrayAdapter courseAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, courseNameArray);
                    courseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinnerSelModCourse.setAdapter(courseAdapter);
                }
            }
        });
    }

    private void getSubjectByCourseID(String instituteID, String courseID) {

        getCommonAPI.getSubject(String.valueOf(instituteID), String.valueOf(courseID), new GetSubjectResponse() {
            @Override
            public void getSubject(ArrayList<String> subjectArray1, ArrayList<String> subjectArray2) throws JSONException {
                subjectIdArray = subjectArray1;
                subjectsNameArray = subjectArray2;

                ArrayAdapter subjectAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, subjectsNameArray);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerSelModSubject.setAdapter(subjectAdapter);
            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(InsAddNewCourseActivity.this, InsAddCourseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();

    }
}
