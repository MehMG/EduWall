package com.eduwall.API;

import android.util.Log;

import com.eduwall.Session.Constant;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by PC10 on 12/22/2015.
 */
public class JSONParser {


    public static JSONObject doGet(HashMap<String, String> param, String url) {
        JSONObject result = null;
        String response;
        Set keys = param.keySet();

        int count = 0;
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            count++;
            String key = (String) i.next();
            String value = (String) param.get(key);
            if (count == param.size()) {
                Log.e("Key", key + "");
                Log.e("Value", value + "");
                url += key + "=" + URLEncoder.encode(value);

            } else {
                Log.e("Key-Value", key + " : " + value);

                url += key + "=" + URLEncoder.encode(value) + "&";
            }

        }
        Log.e("URL", url);
        OkHttpClient client = new OkHttpClient();

        Request request;
        try {
            request = new Request.Builder()
                    .url(url)
                    .build();

        } catch (IllegalArgumentException e) {
            JSONObject jsonObject = new JSONObject();

            return jsonObject;
        }


        Response responseClient = null;
        try {


            responseClient = client.newCall(request).execute();
            response = responseClient.body().string();

            result = new JSONObject(response);
            Log.e("response", response + "==============");
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();

            return jsonObject;
        }

        return result;

    }


    public static JSONObject doPost(HashMap<String, String> data, String url) {


        Log.e("URL", url);
        try {
            RequestBody requestBody;
            MultipartBuilder mBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

            if (data != null) {
                for (String key : data.keySet()) {
                    String value = data.get(key);
                    Log.e("************ Key Values ***********", key + " : " + value);
                    mBuilder.addFormDataPart(key, value);
                }
            } else {
                mBuilder.addFormDataPart("temp", "temp");
            }

            requestBody = mBuilder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Log.e("User", responseBody);
            return new JSONObject(responseBody);

        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("LOG7",e.getMessage());
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", "Something went wrong, Try again later.");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message","Something went wrong, Try again later.");
                return  jsonObject;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }


    public static JSONObject doPostRequestWithFile(HashMap<String, String> data,
                                                   String url,
                                                   ArrayList<String> imageList,
                                                   String fileParamName) {


        Log.e("URL", url);

        try {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

            Log.e("Method", "=======");
            RequestBody requestBody;
            MultipartBuilder mBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

            for (String key : data.keySet()) {
                String value = data.get(key);
                Log.e("Key Values", key + "-----------------" + value);

                mBuilder.addFormDataPart(key, value);

            }
            for (int i = 0; i < imageList.size(); i++) {
                File f = new File(imageList.get(i));

                Log.e("File Name :  ", f.getName() + "===========");
                if (f.exists()) {
                    Log.e("File Exists", "===================");
                    mBuilder.addFormDataPart(fileParamName, f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
                }
            }
            requestBody = mBuilder.build();


            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();


            String result = response.body().string();

            Log.e("Response", result + "");
            return new JSONObject(result);

        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e(Constant.TAG, "Error: " + e.getLocalizedMessage());
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", "Something went wrong, Try again later.");
                return jsonObject;

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            Log.e(Constant.TAG, "Other Error: " + e.getMessage());
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", "Something went wrong, Try again later.");
                return jsonObject;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }


}