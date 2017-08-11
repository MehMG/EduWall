package com.eduwall.Student.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NewJoinActivity extends BaseActivity {

    private Spinner spinnerCountry;
    private Spinner spinnerInstitute;
    private EditText edtStudentName;
    //    private EditText edtProgram;
    private EditText edtIndexNo;
    private Button btnSendRequest;

    ArrayList country = new ArrayList();
    ArrayList countryID = new ArrayList();
    ArrayAdapter countryAdapter;

    ArrayList institute = new ArrayList();
    ArrayList instituteID = new ArrayList();
    ArrayAdapter instituteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_join);
        initActionBar("New Join");
        initBackButton();
        initComponent();
        initData();
        initSpinner();
        initClickListener();
    }


    @Override
    public void initComponent() {


        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        spinnerInstitute = (Spinner) findViewById(R.id.spinnerInstitute);
        edtStudentName = (EditText) findViewById(R.id.edtStudentName);
//        edtProgram = (EditText) findViewById(R.id.edtProgram);
        edtIndexNo = (EditText) findViewById(R.id.edtIndexNo);
        btnSendRequest = (Button) findViewById(R.id.btnSendRequest);
    }

    @Override
    public void initData() {


    }

    private void initSpinner() {

        HashMap<String, String> map = new HashMap<>();

        map.put("Extra", "");

        countryID.add("0");
        country.add("Select Country");


        callAPiActivity.doPost(NewJoinActivity.this, map, Constant.User_country, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                JSONArray countryArray = result.getJSONArray("country");
                for (int i = 0; i < countryArray.length(); i++) {
                    JSONObject countryObj = countryArray.getJSONObject(i);
                    countryID.add(countryObj.getString("country_id"));
                    country.add(countryObj.getString("name"));
                }

                countryAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, country);
                countryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerCountry.setAdapter(countryAdapter);

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }

        });

        instituteID.add("0");
        institute.add("Select Institute");

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("Extra", "");

        callAPiActivity.doPost(NewJoinActivity.this, map, Constant.getInstitute, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                JSONArray instituteArray = result.getJSONArray("InstituteName");
                for (int i = 0; i < instituteArray.length(); i++) {
                    JSONObject insObj = instituteArray.getJSONObject(i);
                    instituteID.add(insObj.getString("user_id"));
                    institute.add(insObj.getString("name"));
                }

                instituteAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, institute);
                instituteAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerInstitute.setAdapter(instituteAdapter);

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }

        });

    }


    @Override
    public void initClickListener() {

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                (input country_id,institute_id,student_name,student_id,program,index_no)
                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/add_student_request

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("country_id", (String) countryID.get(spinnerCountry.getSelectedItemPosition()));
                map.put("institute_id", (String) instituteID.get(spinnerInstitute.getSelectedItemPosition()));
                map.put("student_name", edtStudentName.getText().toString());
                map.put("student_id", preference.getUserData().getId());
                map.put("status", Constant.pending);
                map.put("index_no", edtIndexNo.getText().toString());

                callAPiActivity.doPost((Activity) mContext, map, Constant.Add_student_request, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                        appDialogs.setSuccessToast(result.getString("message"));
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }
                });
                startActivity(new Intent(NewJoinActivity.this, MyInstituteActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
