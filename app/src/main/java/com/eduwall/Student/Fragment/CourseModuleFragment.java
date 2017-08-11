package com.eduwall.Student.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Activity.InsAddCourseActivity;
import com.eduwall.Interface.GetCourseResponse;
import com.eduwall.Interface.GetModuleResponse;
import com.eduwall.Interface.GetSubjectResponse;
import com.eduwall.PrivateUnit.Model.Course;
import com.eduwall.PrivateUnit.Model.Module;
import com.eduwall.PrivateUnit.Model.Subject;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Activity.MyInstituteActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseModuleFragment extends BaseFragment {

    private Spinner spinnerCourseCourse;
    private Spinner spinnerCourseSubject;
    private Spinner spinnerCourseModule;

    private TextView txtCourseSubject;
    private TextView txtCourseModule;

    private Button btnSelectCourse;

    ArrayList<String> courseNameArray;
    ArrayList<String> courseIdArray;

    ArrayList<String> subjectIdArray;
    ArrayList<String> subjectNameArray;

    ArrayList<String> moduleIdArray;
    ArrayList<String> moduleNameArray;

    String requested = "0";
    String instituteID;
    String courseID;
    String subjectID;
    String moduleID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_module, container, false);

        initComponent(view);
        initData();
        initSpinner();
        initClickListener();
        return view;
    }


    @Override
    public void initComponent(View view) {

        spinnerCourseCourse = (Spinner) view.findViewById(R.id.spinnerCourseCourse);
        spinnerCourseSubject = (Spinner) view.findViewById(R.id.spinnerCourseSubject);
        spinnerCourseModule = (Spinner) view.findViewById(R.id.spinnerCourseModule);
        btnSelectCourse = (Button) view.findViewById(R.id.btnSelectCourse);
        txtCourseSubject = (TextView) view.findViewById(R.id.txtCourseSubject);
        txtCourseModule = (TextView) view.findViewById(R.id.txtCourseModule);

    }

    @Override
    public void initData() {

        instituteID = preference.getInstituteData().getInstituteID();


    }

    private void initSpinner() {


        getCommonAPI.getCource(instituteID, new GetCourseResponse() {
            @Override
            public void getCourse(ArrayList<String> courseArray1, ArrayList<String> courseArray2) throws JSONException {
                courseIdArray = courseArray1;
                courseNameArray = courseArray2;

                ArrayAdapter courseAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item, courseNameArray);
                courseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerCourseCourse.setAdapter(courseAdapter);
            }
        });
    }

    @Override
    public void initClickListener() {

        spinnerCourseCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    spinnerCourseSubject.setVisibility(View.GONE);
                    txtCourseSubject.setVisibility(View.GONE);
                    spinnerCourseModule.setVisibility(View.GONE);
                    txtCourseModule.setVisibility(View.GONE);
                } else {

                    courseID = courseIdArray.get(i);

                    getCommonAPI.getSubject(instituteID, courseID, new GetSubjectResponse() {
                        @Override
                        public void getSubject(ArrayList<String> subjectArray1, ArrayList<String> subjectArray2) throws JSONException {

                            subjectIdArray = subjectArray1;
                            subjectNameArray = subjectArray2;

                            ArrayAdapter subjectAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item, subjectNameArray);
                            subjectAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerCourseCourse.setAdapter(subjectAdapter);
                        }
                    });

                    spinnerCourseSubject.setVisibility(View.VISIBLE);
                    txtCourseSubject.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCourseSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    spinnerCourseModule.setVisibility(View.GONE);
                    txtCourseModule.setVisibility(View.GONE);
                } else {

                    subjectID = subjectIdArray.get(i);

                    getCommonAPI.getModule(instituteID, courseID, subjectID, new GetModuleResponse() {
                        @Override
                        public void getModule(ArrayList<String> moduleArray1, ArrayList<String> moduleArray2) throws JSONException {

                            moduleIdArray = moduleArray1;
                            moduleNameArray = moduleArray2;

                            ArrayAdapter moduleAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item, moduleNameArray);
                            moduleAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerCourseCourse.setAdapter(moduleAdapter);
                        }

                    });
                    spinnerCourseModule.setVisibility(View.VISIBLE);
                    txtCourseModule.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //http://visatwebsolution.com/eduwall/testapi/index.php/api/api/acceptstudent
                if (spinnerCourseCourse.getSelectedItemId() != 0 && spinnerCourseSubject.getSelectedItemId() != 0 && spinnerCourseModule.getSelectedItemId() != 0) {

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("id", preference.getInstituteData().getId());
                    map.put("status", requested);
                    map.put("course_id", courseIdArray.get((int) spinnerCourseCourse.getSelectedItemId()));
                    map.put("subject_id", subjectIdArray.get((int) spinnerCourseSubject.getSelectedItemId()));
                    map.put("module_id", moduleIdArray.get((int) spinnerCourseModule.getSelectedItemId()));

                    callAPiActivity.doPost(getActivity(), map, Constant.accept, new GetApiResultJson() {
                        @Override
                        public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                            //Send Notification to Institute Remaining
                            getActivity().startActivity(new Intent(getActivity(), MyInstituteActivity.class));
                        }

                        @Override
                        public void onFailureResult(String messgae) throws JSONException {
                            appDialogs.setErrorToast(messgae);
                        }
                    });
                } else {
                    appDialogs.setErrorToast("Please select all data.");
                }
            }
        });

    }

}
