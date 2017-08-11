package com.eduwall.Student.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Authentication.Activity.AuthenticationActivity;
import com.eduwall.Authentication.Models.User;
import com.eduwall.Instructor.Activity.InsSettingsActivity;
import com.eduwall.Interface.AlertDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Fragment.NotificationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingsActivity extends BaseActivity {

    private Button btnChange;
    private Button btnManage;
    private Button btnNotification;
    private Button btnClose;
    private LinearLayout lnvChange;
    private LinearLayout lnvEye;
    private RelativeLayout rlvClose;
    private EditText edtChangeName;
    private EditText edtChangeEmail;
    private EditText edtChangePassword;
    private EditText edtChangePhoneNo;
    private Spinner spinnerChangeCountry;
    private LinearLayout lnvManage;
    private TextView txtLeave;
    private TextView txtDelete;
    private TextView txtMarkComplete;

    ArrayList country = new ArrayList();
    ArrayAdapter countryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initActionBar("Settings");
        imgSave.setVisibility(View.VISIBLE);
        initBackButton();
        initComponent();
        initSpinner();
        initData();
        initClickListener();

    }


    @Override
    public void initComponent() {

        lnvChange = (LinearLayout) findViewById(R.id.lnvChange);
        lnvEye = (LinearLayout) findViewById(R.id.lnvEye);
        edtChangeName = (EditText) findViewById(R.id.edtChangeName);
        edtChangeEmail = (EditText) findViewById(R.id.edtChangeEmail);
        edtChangePassword = (EditText) findViewById(R.id.edtChangePassword);
        edtChangePhoneNo = (EditText) findViewById(R.id.edtChangePhoneNo);
        spinnerChangeCountry = (Spinner) findViewById(R.id.spinnerChangeCountry);
        lnvManage = (LinearLayout) findViewById(R.id.lnvManage);
        rlvClose = (RelativeLayout) findViewById(R.id.rlvClose);
        txtLeave = (TextView) findViewById(R.id.txtLeave);
        txtDelete = (TextView) findViewById(R.id.txtDelete);
        txtMarkComplete = (TextView) findViewById(R.id.txtMarkComplete);

        btnChange = (Button) findViewById(R.id.btnChange);
        btnManage = (Button) findViewById(R.id.btnManage);
        btnNotification = (Button) findViewById(R.id.btnNotification);
        btnClose = (Button) findViewById(R.id.btnClose);


    }

    private void initSpinner() {

        HashMap<String, String> map = new HashMap<>();

        map.put("Extra", "");

        callAPiActivity.doPost(SettingsActivity.this, map, Constant.User_country, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                JSONArray countryArray = result.getJSONArray("country");
                for (int i = 0; i < countryArray.length(); i++) {
                    JSONObject countryObj = countryArray.getJSONObject(i);
                    country.add(countryObj.getString("name"));
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerChangeCountry.setAdapter(arrayAdapter);

                for (int i = 0; i < spinnerChangeCountry.getCount(); i++) {
                    if (spinnerChangeCountry.getItemAtPosition(i).toString().equalsIgnoreCase(preference.getUserData().getCountry())) {
                        spinnerChangeCountry.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }

        });

    }


    @Override
    public void initData() {


        initProfileData();

    }


    private void initProfileData() {

        edtChangeName.setText(preference.getUserData().getName().toString());
        edtChangeEmail.setText(preference.getUserData().getEmailid().toString());
        edtChangePassword.setText(preference.getUserData().getPassword().toString());
        edtChangePhoneNo.setText(preference.getUserData().getPhonenumber().toString());


//        String compareValue = preference.getUserData().getCountry();
//        spinnerChangeCountry.setSelection(country.indexOf(compareValue));

    }

    @Override
    public void initClickListener() {


        rlvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //input user_id)
                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/deleteUserByUserId

                dialogue.setAlertDialogue("Are you sure you want to close your Account?", "No", "Yes", new AlertDialogueClickListner() {
                    @Override
                    public void onPositive(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(Dialog dialog) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("user_id", preference.getUserData().getId());

                        callAPiActivity.doPost((Activity) mContext, map, Constant.DeleteUserByUserId, new GetApiResultJson() {
                            @Override
                            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                                preference.setUserData(null);
                                Intent intent = new Intent(SettingsActivity.this, AuthenticationActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailureResult(String messgae) throws JSONException {

                            }
                        });
                    }
                });

            }
        });
        imgSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //http://localhost/eduwall/api/api.php?method=updateprofile&name=xyz&country=usa&user_id=1&user_type=1&password=123456&specialization=test&qualification=tes&phonenumber=8555

                HashMap<String, String> map = new HashMap<>();
                map.put("name", edtChangeName.getText().toString());
                map.put("country", spinnerChangeCountry.getSelectedItem().toString());
                map.put("user_id", preference.getUserData().getId());
                map.put("user_type", preference.getUserData().getType());
                map.put("password", edtChangePassword.getText().toString());
                map.put("phonenumber", edtChangePhoneNo.getText().toString());

                callAPiActivity.doPost(SettingsActivity.this, map, Constant.Updateprofile, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {

                        User user = preference.getUserData();
                        user.setName(edtChangeName.getText().toString());
                        user.setPassword(edtChangePassword.getText().toString());
                        user.setCountry(spinnerChangeCountry.getSelectedItem().toString());
                        user.setPhonenumber(edtChangePhoneNo.getText().toString());
                        preference.setUserData(user);

                        appDialogs.setSuccessToast(result.getString("message"));

                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }

                });
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lnvChange.getVisibility() == View.VISIBLE) {
                    lnvChange.setVisibility(View.GONE);
                } else {
                    lnvChange.setVisibility(View.VISIBLE);
                }
            }
        });


        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lnvManage.getVisibility() == View.VISIBLE) {
                    lnvManage.setVisibility(View.GONE);
                } else {
                    lnvManage.setVisibility(View.VISIBLE);
                }
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new NotificationFragment(), R.id.lnvSettings);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        txtLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtMarkComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lnvEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtChangePassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    edtChangePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    edtChangePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
