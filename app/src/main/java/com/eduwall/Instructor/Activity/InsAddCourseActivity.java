package com.eduwall.Instructor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
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

import javax.crypto.spec.DHGenParameterSpec;

/**
 * Created by codesture on 13/6/17.
 */
public class InsAddCourseActivity extends BaseActivity {


    private Spinner spinnerSelect;
    private Spinner spinnerCourse;
    private Spinner spinnerCourseYear;
    private Spinner spinnerSubject;
    private Spinner spinnerSubjectYear;
    private Spinner spinnerModule;
    private Spinner spinnerModuleYear;
    private Button btnAddCourse;
    private TextView txtCourseLabel;
    private TextView txtSubjectLabel;
    private TextView txtModuleLabel;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_add_course);
        initComponent();
        initSpinner();
        initData();
        initActionBar("Add Course");
        imgNewJoin.setVisibility(View.VISIBLE);
        initBackButton();
        initClickListener();
    }


    @Override
    public void initComponent() {

        spinnerSelect = (Spinner) findViewById(R.id.spinnerSelect);
        spinnerCourse = (Spinner) findViewById(R.id.spinnerCourse);
        spinnerCourseYear = (Spinner) findViewById(R.id.spinnerCourseYear);
        spinnerSubject = (Spinner) findViewById(R.id.spinnerSubject);
        spinnerSubjectYear = (Spinner) findViewById(R.id.spinnerSubjectYear);
        spinnerModule = (Spinner) findViewById(R.id.spinnerModule);
        spinnerModuleYear = (Spinner) findViewById(R.id.spinnerModuleYear);
        btnAddCourse = (Button) findViewById(R.id.btnAddCourse);
        txtCourseLabel = (TextView) findViewById(R.id.txtCourseLabel);
        txtSubjectLabel = (TextView) findViewById(R.id.txtSubjectLabel);
        txtModuleLabel = (TextView) findViewById(R.id.txtModuleLabel);
    }

    @Override
    public void initData() {
        spinnerCourse.setVisibility(View.GONE);
        spinnerCourseYear.setVisibility(View.GONE);
        spinnerSubject.setVisibility(View.GONE);
        spinnerSubjectYear.setVisibility(View.GONE);
        spinnerModule.setVisibility(View.GONE);
        spinnerModuleYear.setVisibility(View.GONE);
        btnAddCourse.setVisibility(View.GONE);
    }

    private void initSpinner() {

        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
            insIdArray = new ArrayList<>();
            insNameArray = new ArrayList<>();

            insIdArray.add(preference.getUserData().getId());
            insNameArray.add(preference.getUserData().getName());

            InstituteID = preference.getUserData().getId();

            ArrayAdapter instituteAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, insNameArray);
            instituteAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            spinnerSelect.setAdapter(instituteAdapter);
        } else {

            //Student_id
            getCommonAPI.getInstitute("0", new GetInstituteResponse() {
                @Override
                public void getInstitute(ArrayList<String> insArray1, ArrayList<String> insArray2) throws JSONException {

                    insIdArray = insArray1;
                    insNameArray = insArray2;

                    ArrayAdapter instituteAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, insNameArray);
                    instituteAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinnerSelect.setAdapter(instituteAdapter);
                }
            });

        }


        courseIdArray = new ArrayList<>();
        courseNameArray = new ArrayList<>();
        courseIdArray.add("0");
        courseNameArray.add("Select Course");

        subjectIdArray = new ArrayList<>();
        subjectsNameArray = new ArrayList<>();
        subjectIdArray.add("0");
        subjectsNameArray.add("Select Subject");

        moduleIdArray = new ArrayList<>();
        moduleNameArray = new ArrayList<>();
        moduleIdArray.add("0");
        moduleNameArray.add("Select Module");


    }

    @Override
    public void initClickListener() {

        spinnerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    txtCourseLabel.setVisibility(View.GONE);
                    txtSubjectLabel.setVisibility(View.GONE);
                    txtModuleLabel.setVisibility(View.GONE);
                    spinnerCourse.setVisibility(View.GONE);
                    spinnerCourseYear.setVisibility(View.GONE);
                    spinnerSubject.setVisibility(View.GONE);
                    spinnerSubjectYear.setVisibility(View.GONE);
                    spinnerModule.setVisibility(View.GONE);
                    spinnerModuleYear.setVisibility(View.GONE);
                    btnAddCourse.setVisibility(View.GONE);
                } else {
                    InstituteID = insIdArray.get(i);

                    getCommonAPI.getCource(InstituteID, new GetCourseResponse() {
                        @Override
                        public void getCourse(ArrayList<String> courseArray1, ArrayList<String> courseArray2) throws JSONException {

                            courseIdArray = courseArray1;
                            courseNameArray = courseArray2;

                            ArrayAdapter courseAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, courseNameArray);
                            courseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerCourse.setAdapter(courseAdapter);

                        }
                    });
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(InsAddCourseActivity.this, R.array.spinnerCourseYear, R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinnerCourseYear.setSelection(0);
                    spinnerCourseYear.setAdapter(adapter2);

                    txtCourseLabel.setVisibility(View.VISIBLE);
                    spinnerCourse.setVisibility(View.VISIBLE);
                    spinnerCourseYear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    txtSubjectLabel.setVisibility(View.GONE);
                    txtModuleLabel.setVisibility(View.GONE);
                    spinnerSubject.setVisibility(View.GONE);
                    spinnerSubjectYear.setVisibility(View.GONE);
                    spinnerModule.setVisibility(View.GONE);
                    spinnerModuleYear.setVisibility(View.GONE);
                    btnAddCourse.setVisibility(View.GONE);
                } else {

                    CourseID = courseIdArray.get(i);

                    subjectIdArray = new ArrayList<>();
                    subjectsNameArray = new ArrayList<>();
                    subjectIdArray.add("0");
                    subjectsNameArray.add("Select Subject");

                    getCommonAPI.getSubject(InstituteID, CourseID, new GetSubjectResponse() {
                        @Override
                        public void getSubject(ArrayList<String> subjectArray1, ArrayList<String> subjectArray2) throws JSONException {
                            subjectIdArray = subjectArray1;
                            subjectsNameArray = subjectArray2;

                            ArrayAdapter subjectAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, subjectsNameArray);
                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerSubject.setAdapter(subjectAdapter);
                        }

                    });

                    ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(InsAddCourseActivity.this, R.array.spinnerSubjectYear, R.layout.simple_spinner_item);
                    adapter4.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinnerSubjectYear.setSelection(0);
                    spinnerSubjectYear.setAdapter(adapter4);

                    if (spinnerCourseYear.getSelectedItemId() == 0) {

                    } else {
                        txtSubjectLabel.setVisibility(View.VISIBLE);
                        spinnerSubject.setVisibility(View.VISIBLE);
                        spinnerSubjectYear.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerCourseYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    txtSubjectLabel.setVisibility(View.GONE);
                    txtModuleLabel.setVisibility(View.GONE);
                    spinnerSubject.setVisibility(View.GONE);
                    spinnerSubjectYear.setVisibility(View.GONE);
                    spinnerModule.setVisibility(View.GONE);
                    spinnerModuleYear.setVisibility(View.GONE);
                    btnAddCourse.setVisibility(View.GONE);
                } else {
                    if (spinnerCourse.getSelectedItemId() == 0) {
                    } else {
                        txtSubjectLabel.setVisibility(View.VISIBLE);
                        spinnerSubject.setVisibility(View.VISIBLE);
                        spinnerSubjectYear.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    txtModuleLabel.setVisibility(View.GONE);
                    spinnerModule.setVisibility(View.GONE);
                    spinnerModuleYear.setVisibility(View.GONE);
                    btnAddCourse.setVisibility(View.GONE);
                } else {
                    SubjectID = subjectIdArray.get(i);

                    moduleIdArray = new ArrayList<>();
                    moduleNameArray = new ArrayList<>();
                    moduleIdArray.add("0");
                    moduleNameArray.add("Select Module");

                    getCommonAPI.getModule(InstituteID, CourseID, SubjectID, new GetModuleResponse() {
                        @Override
                        public void getModule(ArrayList<String> moduleArray1, ArrayList<String> moduleArray2) throws JSONException {
                            moduleIdArray = moduleArray1;
                            moduleNameArray = moduleArray2;

                            ArrayAdapter moduleAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, moduleNameArray);
                            moduleAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerModule.setAdapter(moduleAdapter);
                        }

                    });

                    ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(InsAddCourseActivity.this, R.array.spinnerModuleYear, R.layout.simple_spinner_item);
                    adapter6.setDropDownViewResource(android.R.layout.simple_list_item_1);
                    spinnerModuleYear.setSelection(0);
                    spinnerModuleYear.setAdapter(adapter6);

                    if (spinnerModuleYear.getSelectedItemId() == 0) {

                    } else {
                        txtModuleLabel.setVisibility(View.VISIBLE);
                        spinnerModule.setVisibility(View.VISIBLE);
                        spinnerModuleYear.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSubjectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    txtModuleLabel.setVisibility(View.GONE);
                    spinnerModule.setVisibility(View.GONE);
                    spinnerModuleYear.setVisibility(View.GONE);
                    btnAddCourse.setVisibility(View.GONE);
                } else {
                    if (spinnerCourse.getSelectedItemId() == 0) {
                    } else {
                        txtModuleLabel.setVisibility(View.VISIBLE);
                        spinnerModule.setVisibility(View.VISIBLE);
                        spinnerModuleYear.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    btnAddCourse.setVisibility(View.GONE);
                } else {

                    ModuleID = moduleIdArray.get(i);

                    if (spinnerModuleYear.getSelectedItemId() == 0) {

                    } else {
                        btnAddCourse.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerModuleYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    btnAddCourse.setVisibility(View.GONE);
                } else {
                    if (spinnerModule.getSelectedItemId() == 0) {
                        btnAddCourse.setVisibility(View.GONE);
                    } else {
                        btnAddCourse.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //input institute_id,course_id,subject_id,module_id,year,term,semester,instructor_id)
                //http://visatwebsolution.com/eduwall/testapi/index.php/api/api/add_instructor_request

                HashMap<String, String> map = new HashMap<>();
                map.put("institute_id", insIdArray.get((int) spinnerSelect.getSelectedItemId()));
                map.put("instructor_id", preference.getUserData().getId());
                map.put("course_id", courseIdArray.get((int) spinnerCourse.getSelectedItemId()));
                map.put("subject_id", subjectIdArray.get((int) spinnerSubject.getSelectedItemId()));
                map.put("module_id", moduleIdArray.get((int) spinnerModule.getSelectedItemId()));
                map.put("year", spinnerCourseYear.getSelectedItem().toString());
                map.put("term", spinnerSubjectYear.getSelectedItem().toString());
                map.put("semester", spinnerModuleYear.getSelectedItem().toString());

                callAPiActivity.doPost(InsAddCourseActivity.this, map, Constant.Add_instructor_request, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {

                        //Notification to Institute/Private Unit
                        appDialogs.setSuccessToast(result.getString("message"));

                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }

                });

                Intent intent = new Intent(InsAddCourseActivity.this, InsAllCourseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
        imgNewJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
                    startActivity(new Intent(InsAddCourseActivity.this, InsAddStudentActivity.class));
                } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
                    startActivity(new Intent(InsAddCourseActivity.this, InsAddNewCourseActivity.class));
                } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {
                    startActivity(new Intent(InsAddCourseActivity.this, InsAddNewCourseActivity.class));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initSpinner();
    }
}
