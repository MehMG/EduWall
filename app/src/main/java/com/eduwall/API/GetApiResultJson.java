package com.eduwall.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by PC10 on 8/3/2016.
 */
public interface GetApiResultJson {
    public  void onSuccesResult(JSONObject result) throws JSONException, IOException;
    public  void onFailureResult(String messgae) throws JSONException;
}
