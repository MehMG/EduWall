package com.eduwall.Interface;

import com.eduwall.PrivateUnit.Model.Module;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by PC10 on 06-Jul-17.
 */

public interface GetModuleResponse {

    public void getModule(ArrayList<String> moduleArray1, ArrayList<String> moduleArray2) throws JSONException;
}
