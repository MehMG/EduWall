package com.eduwall.Student.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.eduwall.Interface.GetModuleResponse;
import com.eduwall.Interface.GetSubjectResponse;
import com.eduwall.PrivateUnit.Model.Course;
import com.eduwall.PrivateUnit.Model.Module;
import com.eduwall.PrivateUnit.Model.Subject;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Session.Model.AllpostData;
import com.eduwall.Student.Activity.MyInstituteActivity;
import com.eduwall.Student.Activity.ShowAllPostActivity;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectSubModuleFragment extends BaseFragment {

    private Spinner spinnerSelectSubject;
    private Spinner spinnerSelectModule;
    private Button btnUpdate;

    ArrayList<String> subjectsNameArray = new ArrayList<>();
    ArrayList<String> subjectIdArray = new ArrayList<>();

    ArrayList<String> moduleNameArray = new ArrayList<>();
    ArrayList<String> moduleIdArray = new ArrayList<>();

    String InstituteID;
    String CourseID;
    String SubjectID;
    String ModuleID;

    public SelectSubModuleFragment(String instituteID, String courseID) {
        this.InstituteID = instituteID;
        this.CourseID = courseID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_course, container, false);
        baseActivity = (BaseActivity) getActivity();
        initComponent(view);
        initData();
        initSpinner();
        initClickListener();

        return view;
    }


    @Override
    public void initComponent(View view) {

        spinnerSelectSubject = (Spinner) view.findViewById(R.id.spinnerSelectSubject);
        spinnerSelectModule = (Spinner) view.findViewById(R.id.spinnerSelectModule);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);

    }

    @Override
    public void initData() {

        subjectIdArray.add("0");
        subjectsNameArray.add("Select Subject");

        moduleIdArray.add("0");
        moduleNameArray.add("Select Module");

    }

    private void initSpinner() {


        getCommonAPI.getSubject(InstituteID, CourseID, new GetSubjectResponse() {
            @Override
            public void getSubject(ArrayList<String> subjectArray1, ArrayList<String> subjectArray2) throws JSONException {
                subjectIdArray = subjectArray1;
                subjectsNameArray = subjectArray2;

                ArrayAdapter subjectAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item, subjectsNameArray);
                subjectAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerSelectSubject.setAdapter(subjectAdapter);
            }

        });


    }

    @Override
    public void initClickListener() {


        spinnerSelectSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                } else {
                    SubjectID = subjectIdArray.get(i);

                    getCommonAPI.getModule(InstituteID, CourseID, SubjectID, new GetModuleResponse() {
                        @Override
                        public void getModule(ArrayList<String> moduleArray1, ArrayList<String> moduleArray2) throws JSONException {

                            moduleIdArray = moduleArray1;
                            moduleNameArray = moduleArray2;

                            ArrayAdapter moduleAdapter = new ArrayAdapter(getActivity(), R.layout.simple_spinner_item, moduleNameArray);
                            moduleAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerSelectModule.setAdapter(moduleAdapter);
                        }

                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSelectModule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModuleID = moduleIdArray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllpostData data = new AllpostData();
                data.setInstituteID(InstituteID);
                data.setCourseID(CourseID);
                data.setSubjectid(SubjectID);
                data.setModuleID(ModuleID);
                preference.setPostIDData(data);


                startActivity(new Intent(getActivity(), ShowAllPostActivity.class));
            }
        });
    }

}
