package com.eduwall.API;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.ConnectionDetector;
import com.eduwall.Session.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CallAPiActivity extends AppCompatActivity {
    public Context context;
    public BaseActivity baseActivity;


    public CallAPiActivity(Context context) {
        this.context = context;
        baseActivity = (BaseActivity) context;
    }


    public void doGet(final Activity activity, final HashMap<String, String> map, final String body, final GetApiResultJson getApiResult) {


        baseActivity.appDialogs.showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject result = JSONParser.doGet(map, Constant.URL1 + body);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        baseActivity.appDialogs.hideProgressDialog();
                        try {
                            if (result.getString("status").equalsIgnoreCase("true")) {
                                getApiResult.onSuccesResult(result);
                            } else {

                                getApiResult.onFailureResult(result.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                getApiResult.onFailureResult(e.getLocalizedMessage());
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        }).start();
    }


    public void doPost(final Activity activity, final HashMap<String, String> map, final String body, final GetApiResultJson getApiResult) {

        if (new ConnectionDetector(context).isConnectingToInternet()) {
            baseActivity.appDialogs.showProgressDialog();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final JSONObject result = JSONParser.doPost(map, Constant.URL1 + body);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            baseActivity.appDialogs.hideProgressDialog();
                            try {
                                if (result.getString("status").equalsIgnoreCase("true")) {
                                    getApiResult.onSuccesResult(result);
                                } else {

                                    getApiResult.onFailureResult(result.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                try {
                                    getApiResult.onFailureResult(e.getLocalizedMessage());
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();
        } else {
            new ConnectionDetector(context).connectiondetect();
        }


    }


    public void doPostWithFiles(final Activity activity, final HashMap<String, String> map, final String body, final ArrayList<String> mFiles, final String fileParamName, final GetApiResultJson getApiResult) {

        if (new ConnectionDetector(context).isConnectingToInternet()) {
            baseActivity.appDialogs.showProgressDialog();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final JSONObject result = JSONParser.doPostRequestWithFile(map, Constant.URL1 + body, mFiles, fileParamName);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.e("Result Do Get", result.length() + "");
                            baseActivity.appDialogs.hideProgressDialog();
                            try {
                                if (result.getString("status").equalsIgnoreCase("true")) {
                                    getApiResult.onSuccesResult(result);
                                } else {

                                    getApiResult.onFailureResult(result.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                try {
                                    getApiResult.onFailureResult(e.getLocalizedMessage());
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }
            }).start();
        } else {
            new ConnectionDetector(context).connectiondetect();
        }

    }


}
