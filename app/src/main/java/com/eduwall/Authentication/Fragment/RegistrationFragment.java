package com.eduwall.Authentication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Authentication.Models.User;
import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Session.SharePreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by codesture on 24/5/17.
 */
public class RegistrationFragment extends BaseFragment {

    @Nullable
    private ImageView ivRBack;
    private CircleImageView ivlogo;
    private LinearLayout lnvRegister;
    private TextView txtRegister;
    private EditText edtRUsername;
    private EditText edtREmail;
    private EditText edtRPassword;
    private EditText edtRIndexNo;
    private Button btnRRegister;
    private Spinner spinnerType;
    private Spinner spinnerCountry;

    ArrayList country = new ArrayList();
    ArrayList type = new ArrayList();
    ArrayList<String> mCountryId;
    ArrayList<String> mTypeId;


    String typeId;
    String countryId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        initComponent(view);
        initData();
        initSpinner();
        initClickListener();
        return view;
    }


    @Override
    public void initComponent(View view) {


        ivRBack = (ImageView) view.findViewById(R.id.ivRBack);
        ivlogo = (CircleImageView) view.findViewById(R.id.ivlogo);
        lnvRegister = (LinearLayout) view.findViewById(R.id.lnvRegister);
        txtRegister = (TextView) view.findViewById(R.id.txtRegister);
        edtRUsername = (EditText) view.findViewById(R.id.edtRUsername);
        edtREmail = (EditText) view.findViewById(R.id.edtREmail);
        edtRPassword = (EditText) view.findViewById(R.id.edtRPassword);
        edtRIndexNo = (EditText) view.findViewById(R.id.edtRIndexNo);
        btnRRegister = (Button) view.findViewById(R.id.btnRRegister);
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        spinnerCountry = (Spinner) view.findViewById(R.id.spinnerCountry);
    }

    @Override
    public void initData() {

    }


    private void initSpinner() {

        HashMap<String, String> map = new HashMap<>();

        map.put("Extra", "");

        callAPiActivity.doPost(getActivity(), map, Constant.User_country, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                mCountryId = new ArrayList<String>();
                mTypeId = new ArrayList<String>();
                type.add("Select Type");
                mTypeId.add("0");
                mCountryId.add("0");
                country.add("Select Country");

                JSONArray countryArray = result.getJSONArray("country");
                for (int i = 0; i < countryArray.length(); i++) {
                    JSONObject countryObj = countryArray.getJSONObject(i);
                    country.add(countryObj.getString("name"));
                    mCountryId.add(countryObj.getString("country_id"));
                }
                ArrayAdapter countryAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, country);
                spinnerCountry.setAdapter(countryAdapter);

                JSONArray typeArray = result.getJSONArray("user_type");
                for (int i = 0; i < typeArray.length(); i++) {
                    JSONObject typeObj = typeArray.getJSONObject(i);
                    type.add(typeObj.getString("name"));
                    mTypeId.add(typeObj.getString("type_id"));
                }
                ArrayAdapter typeAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, type);
                spinnerType.setAdapter(typeAdapter);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }

        });


    }


    @Override
    public void initClickListener() {

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 1) {
                    edtRIndexNo.setVisibility(View.VISIBLE);
                } else {
                    edtRIndexNo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i > 0) {
                    countryId = mCountryId.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivRBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new LoginFragment(), R.id.authentication);
            }
        });
        btnRRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValid()) {
                    doRegistration();

                }
            }
        });
    }

    private boolean doValid() {
        if (edtRUsername.getText().toString().equalsIgnoreCase("")) {
            edtRUsername.setError("Field Required");
            edtRUsername.requestFocus();
            return false;
        } else if (edtREmail.getText().toString().equalsIgnoreCase("")) {
            edtREmail.setError("Enter Emailid");
            edtREmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtREmail.getText().toString()).matches()) {
            edtREmail.setError("Enter valid Emailid");
            edtREmail.requestFocus();
            return false;
        } else if (edtRPassword.getText().toString().equalsIgnoreCase("")) {
            edtRPassword.setError("Enter Password");
            edtRPassword.requestFocus();
            return false;
        } else if (edtRPassword.getText().toString().length() < 6) {
            edtRPassword.setError("Atleast 6 characters Required");
            edtRPassword.requestFocus();
            return false;
        } else if (edtRIndexNo.getText().toString().length() < 1) {
            edtRIndexNo.setError("Enter Index No.");
            edtRIndexNo.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private void doRegistration() {

        //http://localhost/eduwall/api/api.php?method=registration&name=xyz&email=abc@gmail.com&password=123456&user_type=1&country=india

        HashMap<String, String> map = new HashMap<>();
        map.put("name", edtRUsername.getText().toString());
        map.put("email", edtREmail.getText().toString());
        map.put("password", edtRPassword.getText().toString());
        map.put("user_type", typeId);
        map.put("index_no", edtRIndexNo.getText().toString());
        map.put("country", spinnerCountry.getSelectedItem().toString());


        callAPiActivity.doPost(getActivity(), map, Constant.Registration, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                JSONArray jsonArray = result.getJSONArray("data");
                JSONObject resultObj = jsonArray.getJSONObject(0);

                User user = new User();
                user.setId(resultObj.getString("user_id"));
                user.setName(resultObj.getString("name"));
                user.setType(resultObj.getString("user_type"));
                user.setEmailid(resultObj.getString("email"));
                user.setPassword(edtRPassword.getText().toString());
                user.setCountry(resultObj.getString("country"));
                user.setQualification(resultObj.getString("qualification"));
                user.setPhonenumber(resultObj.getString("contact"));
                user.setSpecialization(resultObj.getString("specialization"));
                user.setProfile(resultObj.getString("profile_image"));
                user.setIndexNo(resultObj.getString("index_no"));
                user.setParent("Registration");
                preference.setUserData(user);

                appDialogs.setSuccessToast(result.getString("message"));

                addFragment(new LoginFragment(), R.id.authentication);

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }
        });

    }
}
