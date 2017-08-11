package com.eduwall.Interface;

import com.eduwall.PrivateUnit.Model.Subject;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by PC10 on 06-Jul-17.
 */

public interface GetSubjectResponse {

    public void getSubject(ArrayList<String> subjectArray1,ArrayList<String> subjectArray2) throws JSONException;

}
