package com.eduwall.Interface;

import com.eduwall.Instructor.Models.InstituteCoursesList;
import com.eduwall.PrivateUnit.Model.Course;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by PC10 on 06-Jul-17.
 */

public interface GetCourseResponse {

    public void getCourse(ArrayList<String> courseArray1,ArrayList<String> courseArray2) throws JSONException;

}
