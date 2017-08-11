package com.eduwall.API;

import android.app.Activity;
import android.content.Context;

import com.eduwall.Interface.GetCourseResponse;
import com.eduwall.Interface.GetInstituteResponse;
import com.eduwall.Interface.GetModuleResponse;
import com.eduwall.Interface.GetSubjectResponse;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PC10 on 06-Jul-17.
 */

public class GetCommonAPI {


    private Context context;
    private BaseActivity baseActivity;

    ArrayList<String> insIdArray;
    ArrayList<String> insNameArray;

    ArrayList<String> courseIdArray;
    ArrayList<String> courseNameArray;

    ArrayList<String> subjectIdArray;
    ArrayList<String> subjectNameArray;

    ArrayList<String> moduleIdArray;
    ArrayList<String> moduleNameArray;

    public GetCommonAPI(Context context) {
        this.context = context;
        this.baseActivity = (BaseActivity) context;
    }

    public void getInstitute(String studentID, final GetInstituteResponse getInstituteResponse) {

        HashMap<String, String> map = new HashMap<>();
        map.put("student_id", studentID);

        baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.Getinstitutelist, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("institutelist");

                insIdArray = new ArrayList<>();
                insNameArray = new ArrayList<>();
                insIdArray.add("0");
                insNameArray.add("Select Institute");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    insIdArray.add(jsonObject.getString("user_id"));
                    insNameArray.add(jsonObject.getString("name"));
                }
                getInstituteResponse.getInstitute(insIdArray, insNameArray);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }

    public void getCource(String instituteID, final GetCourseResponse getCourseResponse) {

        HashMap<String, String> map = new HashMap<>();
        map.put("institute_id", instituteID);

        baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.Getcourse, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("courselist");

                courseIdArray = new ArrayList<>();
                courseNameArray = new ArrayList<>();
                courseIdArray.add("0");
                courseNameArray.add("Select Course");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    courseIdArray.add(jsonObject.getString("course_id"));
                    courseNameArray.add(jsonObject.getString("name"));

                }
                getCourseResponse.getCourse(courseIdArray, courseNameArray);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }

    public void getSubject(String instituteID, String courseID, final GetSubjectResponse getSubjectResponse) {

        HashMap<String, String> map = new HashMap<>();
        map.put("institute_id", instituteID);
        map.put("course_id", courseID);

        baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.Getsubject, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("subjectlist");

                subjectIdArray = new ArrayList<String>();
                subjectNameArray = new ArrayList<String>();
                subjectIdArray.add("0");
                subjectNameArray.add("Select Subject");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    subjectNameArray.add(jsonObject.getString("name"));
                    subjectIdArray.add(jsonObject.getString("subject_id"));
                }
                getSubjectResponse.getSubject(subjectIdArray, subjectNameArray);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }

    public void getModule(String instituteID, String courseID, String subjectID, final GetModuleResponse getModuleResponse) {

        HashMap<String, String> map = new HashMap<>();
        map.put("institute_id", instituteID);
        map.put("course_id", courseID);
        map.put("subject_id", subjectID);

        baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.Getmodule, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("modulelist");

                moduleIdArray = new ArrayList<String>();
                moduleNameArray = new ArrayList<String>();
                moduleIdArray.add("0");
                moduleNameArray.add("Select Module");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    moduleIdArray.add(jsonObject.getString("module_id"));
                    moduleNameArray.add(jsonObject.getString("name"));
                }
                getModuleResponse.getModule(moduleIdArray,moduleNameArray);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }
}
