package com.eduwall.Instructor.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Authentication.Activity.AuthenticationActivity;
import com.eduwall.Authentication.Models.User;
import com.eduwall.Interface.AlertDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.SettingsActivity;
import com.eduwall.Student.Fragment.NotificationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InsSettingsActivity extends BaseActivity {


    private FrameLayout lnvInsSettings;
    private LinearLayout rlvChange;
    private Button btnChange;
    private LinearLayout lnvChange1;
    private LinearLayout lnvChange2;
    private EditText edtInsName;
    private EditText edtInsEmail;
    private EditText edtInsQual;
    private EditText edtInsPassword;
    private LinearLayout lnvInsEye;
    private EditText edtInsPhoneNo;
    private EditText edtSpecialization;
    private Spinner spinnerInsCountry;
    private TextView txtDelete1;
    private TextView txtDelete2;
    private TextView txtHelp;
    private TextView txtFaq;
    private TextView txtAppInfo;
    private TextView txtSuspend;
    private TextView txtQualification;
    private TextView txtSpecialization;
    private Button btnNotification;
    private RelativeLayout rlvDeleteAccount;
    private Button btnClose;

    ArrayList country = new ArrayList();
    ArrayAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_settings);
        initComponent();
        initSpinner();
        initData();
        initActionBar("Settings");
        initBackButton();
        initClickListener();

        imgSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void initComponent() {

        lnvInsSettings = (FrameLayout) findViewById(R.id.lnvInsSettings);
        rlvChange = (LinearLayout) findViewById(R.id.rlvChange);
        btnChange = (Button) findViewById(R.id.btnChange);
        lnvChange1 = (LinearLayout) findViewById(R.id.lnvChange1);
        lnvChange2 = (LinearLayout) findViewById(R.id.lnvChange2);
        edtInsName = (EditText) findViewById(R.id.edtInsName);
        edtInsEmail = (EditText) findViewById(R.id.edtInsEmail);
        edtInsQual = (EditText) findViewById(R.id.edtInsQual);
        edtInsPassword = (EditText) findViewById(R.id.edtInsPassword);
        lnvInsEye = (LinearLayout) findViewById(R.id.lnvInsEye);
        edtInsPhoneNo = (EditText) findViewById(R.id.edtInsPhoneNo);
        edtSpecialization = (EditText) findViewById(R.id.edtInsSpecial);
        spinnerInsCountry = (Spinner) findViewById(R.id.spinnerInsCountry);
        txtDelete1 = (TextView) findViewById(R.id.txtDelete1);
        txtDelete2 = (TextView) findViewById(R.id.txtDelete2);
        txtHelp = (TextView) findViewById(R.id.txtHelp);
        txtQualification = (TextView) findViewById(R.id.txtInsQual);
        txtSpecialization = (TextView) findViewById(R.id.txtInsSpecial);
        txtSuspend = (TextView) findViewById(R.id.txtSuspend);
        txtFaq = (TextView) findViewById(R.id.txtFaq);
        txtAppInfo = (TextView) findViewById(R.id.txtAppInfo);
        btnNotification = (Button) findViewById(R.id.btnNotification);
        rlvDeleteAccount = (RelativeLayout) findViewById(R.id.rlvDeleteAccount);
        btnClose = (Button) findViewById(R.id.btnClose);

    }

    private void initSpinner() {

        HashMap<String, String> map = new HashMap<>();

        map.put("Extra", "");

        callAPiActivity.doPost(InsSettingsActivity.this, map, Constant.User_country, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                JSONArray countryArray = result.getJSONArray("country");
                for (int i = 0; i < countryArray.length(); i++) {
                    JSONObject countryObj = countryArray.getJSONObject(i);
                    country.add(countryObj.getString("name"));
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, country);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spinnerInsCountry.setAdapter(arrayAdapter);

                for (int i = 0; i < spinnerInsCountry.getCount(); i++) {
                    if (spinnerInsCountry.getItemAtPosition(i).toString().equalsIgnoreCase(preference.getUserData().getCountry())) {
                        spinnerInsCountry.setSelection(i);
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

        initUIUpdate();
        initUserData();
    }


    private void initUIUpdate() {
        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
            txtSpecialization.setVisibility(View.GONE);
            txtQualification.setVisibility(View.VISIBLE);
            edtSpecialization.setVisibility(View.GONE);
            edtInsQual.setVisibility(View.VISIBLE);
            txtSuspend.setVisibility(View.GONE);

        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {
            txtSpecialization.setVisibility(View.VISIBLE);
            txtQualification.setVisibility(View.GONE);
            edtSpecialization.setVisibility(View.VISIBLE);
            edtInsQual.setVisibility(View.GONE);
            txtDelete1.setVisibility(View.GONE);
            txtDelete2.setVisibility(View.GONE);
            txtAppInfo.setVisibility(View.GONE);

        } else {
            txtSpecialization.setVisibility(View.GONE);
            txtQualification.setVisibility(View.GONE);
            edtSpecialization.setVisibility(View.GONE);
            edtInsQual.setVisibility(View.GONE);
            txtDelete1.setVisibility(View.GONE);
            txtDelete2.setVisibility(View.GONE);
            txtAppInfo.setVisibility(View.GONE);
        }
    }

    private void initUserData() {
        edtInsName.setText(preference.getUserData().getName());
        edtInsPhoneNo.setText(preference.getUserData().getPhonenumber());
        edtInsEmail.setText(preference.getUserData().getEmailid());
        edtInsPassword.setText(preference.getUserData().getPassword());
        edtInsQual.setText(preference.getUserData().getQualification());
        edtSpecialization.setText(preference.getUserData().getSpecialization());


        String compareValue = preference.getUserData().getCountry();
        spinnerInsCountry.setSelection(country.indexOf(compareValue));
    }


    @Override
    public void initClickListener() {

        rlvDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //input user_id)
                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/deleteUserByUserId

                dialogue.setAlertDialogue("Are you sure you want to delete your Account?", "No", "Yes", new AlertDialogueClickListner() {
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
                                Intent intent = new Intent(InsSettingsActivity.this, AuthenticationActivity.class);
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

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnvChange1.getVisibility() == View.GONE) {
                    lnvChange1.setVisibility(View.VISIBLE);
                    lnvChange2.setVisibility(View.VISIBLE);
                } else {
                    lnvChange1.setVisibility(View.GONE);
                    lnvChange2.setVisibility(View.GONE);
                }
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   addFragment(new NotificationFragment(), R.id.lnvInsSettings);
                                               }
                                           }

        );

        imgSave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           //http://localhost/eduwall/api/api.php?method=updateprofile&name=xyz&country=usa&user_id=1&user_type=1&password=123456&specialization=test&qualification=tes&phonenumber=8555

                                           HashMap<String, String> map = new HashMap<>();
                                           map.put("name", edtInsName.getText().toString());
                                           map.put("country", spinnerInsCountry.getSelectedItem().toString());
                                           map.put("user_id", preference.getUserData().getId());
                                           map.put("user_type", preference.getUserData().getType());
                                           map.put("password", edtInsPassword.getText().toString());
                                           map.put("phonenumber", edtInsPhoneNo.getText().toString());


                                           callAPiActivity.doPost(InsSettingsActivity.this, map, Constant.Updateprofile, new GetApiResultJson() {
                                               @Override
                                               public void onSuccesResult(JSONObject result) throws JSONException {

                                                   User user = preference.getUserData();
                                                   user.setName(edtInsName.getText().toString());
                                                   user.setPassword(edtInsPassword.getText().toString());
                                                   user.setCountry(spinnerInsCountry.getSelectedItem().toString());
                                                   user.setPhonenumber(edtInsPhoneNo.getText().toString());
                                                   user.setQualification(edtInsQual.getText().toString());
                                                   user.setSpecialization(edtSpecialization.getText().toString());
                                                   preference.setUserData(user);

                                                   appDialogs.setSuccessToast(result.getString("message"));

                                               }

                                               @Override
                                               public void onFailureResult(String messgae) throws JSONException {
                                                   appDialogs.setErrorToast(messgae);
                                               }

                                           });
                                       }
                                   }
        );
    }
}
