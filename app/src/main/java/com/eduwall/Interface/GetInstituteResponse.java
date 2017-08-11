package com.eduwall.Interface;

import com.eduwall.Student.Models.Institute;
import com.eduwall.Student.Models.InstituteList;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by PC10 on 06-Jul-17.
 */

public interface GetInstituteResponse {

    public void getInstitute(ArrayList<String> insArray1, ArrayList<String> insArray2) throws JSONException;
}
