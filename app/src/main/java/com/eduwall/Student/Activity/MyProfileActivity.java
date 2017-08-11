package com.eduwall.Student.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Authentication.Activity.AuthenticationActivity;
import com.eduwall.Authentication.Models.User;
import com.eduwall.Interface.AlertDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.InstituteAdapter;
import com.eduwall.Student.Models.Institute;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture on 2/6/17.
 */
public class MyProfileActivity extends BaseActivity {
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtSpecialization;
    private TextView txtSpecialization;
    private EditText edtQualification;
    private TextView txtQualification;
    private RecyclerView recyclerInstitute;
    private EditText edtCountry;
    private EditText edtPhoneNumber;
    private ArrayList<Institute> instituteArrayList;
    InstituteAdapter instituteAdapter;
    private ImageView ivProfile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        initActionBar("My Profile");
        imgLogout.setVisibility(View.VISIBLE);
        initBackButton();
        initComponent();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        recyclerInstitute = (RecyclerView) findViewById(R.id.recyclerInstitute);
        recyclerInstitute.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerInstitute.setNestedScrollingEnabled(false);

        edtCountry = (EditText) findViewById(R.id.edtCountry);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtSpecialization = (EditText) findViewById(R.id.edtSpecialization);
        txtSpecialization = (TextView) findViewById(R.id.txtSpecialization);
        edtQualification = (EditText) findViewById(R.id.edtQualification);
        txtQualification = (TextView) findViewById(R.id.txtQualification);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
    }

    @Override
    public void initData() {

        initUIUpdate();
        initProfileData();
        if (!preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
            getInstituteData();
        }

    }


    private void initUIUpdate() {
        instituteArrayList = new ArrayList<>();
        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
            txtQualification.setVisibility(View.GONE);
            edtQualification.setVisibility(View.GONE);
            txtSpecialization.setVisibility(View.GONE);
            edtSpecialization.setVisibility(View.GONE);
        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
            txtQualification.setVisibility(View.VISIBLE);
            edtQualification.setVisibility(View.VISIBLE);
            txtSpecialization.setVisibility(View.GONE);
            edtSpecialization.setVisibility(View.GONE);
        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {
            txtQualification.setVisibility(View.GONE);
            edtQualification.setVisibility(View.GONE);
            txtSpecialization.setVisibility(View.VISIBLE);
            edtSpecialization.setVisibility(View.VISIBLE);
        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
            recyclerInstitute.setVisibility(View.GONE);
            txtQualification.setVisibility(View.GONE);
            edtQualification.setVisibility(View.GONE);
            txtSpecialization.setVisibility(View.VISIBLE);
            edtSpecialization.setVisibility(View.VISIBLE);
        }
    }

    private void initProfileData() {
        edtName.setText(preference.getUserData().getName());
        edtEmail.setText(preference.getUserData().getEmailid());
        edtPhoneNumber.setText(preference.getUserData().getPhonenumber());
        edtSpecialization.setText(preference.getUserData().getSpecialization());
        edtQualification.setText(preference.getUserData().getQualification());
        edtCountry.setText(preference.getUserData().getCountry());

        Picasso.with(mContext).load(preference.getUserData().getProfile()).resize(250, 250).into(ivProfile);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getInstituteData() {

        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
            //input student_id
            //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getInstituteByStudentid

            HashMap<String, String> map = new HashMap<>();
            map.put("student_id", preference.getUserData().getId());

            callAPiActivity.doPost((Activity) mContext, map, Constant.GetInstituteByStudentid, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    JSONArray jsonArray = result.getJSONArray("Institutelist");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Institute institute = new Institute();
                        institute.setId(jsonObject.getString("institute_id"));
                        institute.setName(jsonObject.getString("name"));
                        institute.setCourse(jsonObject.getString("Course"));
                        institute.setIndexno(jsonObject.getString("index_no"));
                        institute.setNumber(String.valueOf(i + 1));
                        instituteArrayList.add(institute);
                    }
                    instituteAdapter = new InstituteAdapter(MyProfileActivity.this, instituteArrayList);
                    recyclerInstitute.setAdapter(instituteAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {
                }
            });

        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {

            //(input instructor_id)
            // http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getInstitutelistByInstructorid

            HashMap<String, String> map = new HashMap<>();
            map.put("instructor_id", preference.getUserData().getId());

            callAPiActivity.doPost((Activity) mContext, map, Constant.GetInstitutelistByInstructorid, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    JSONArray jsonArray = result.getJSONArray("info");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Institute institute = new Institute();
                        institute.setId(jsonObject.getString("institute_id"));
                        institute.setName(jsonObject.getString("institute"));
                        institute.setCourse(jsonObject.getString("course"));
                        institute.setCourseid(jsonObject.getString("course_id"));
                        institute.setSubject(jsonObject.getString("subject"));
                        institute.setSubjectid(jsonObject.getString("subject_id"));
                        institute.setModule(jsonObject.getString("module"));
                        institute.setModuleid(jsonObject.getString("module_id"));
                        institute.setNumber(String.valueOf(i + 1));
                        institute.setCount(jsonObject.getString("totalstudent"));
                        instituteArrayList.add(institute);
                    }

                    instituteAdapter = new InstituteAdapter(MyProfileActivity.this, instituteArrayList);
                    recyclerInstitute.setAdapter(instituteAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {

                }
            });
        } else {

            //(input instructor_id)
            // http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getInstitutelistByInstructorid

            HashMap<String, String> map = new HashMap<>();
            map.put("instructor_id", preference.getUserData().getId());

            callAPiActivity.doPost((Activity) mContext, map, Constant.GetInstitutelistByInstructorid, new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    JSONArray jsonArray = result.getJSONArray("Institutelist");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Institute institute = new Institute();
                        institute.setId(jsonObject.getString("institute_id"));
                        institute.setName(jsonObject.getString("institute"));
                        institute.setCourse(jsonObject.getString("course"));
                        institute.setCourseid(jsonObject.getString("course_id"));
                        institute.setSubject(jsonObject.getString("subject"));
                        institute.setSubjectid(jsonObject.getString("subject_id"));
                        institute.setModule(jsonObject.getString("module"));
                        institute.setModuleid(jsonObject.getString("module_id"));
                        institute.setNumber(String.valueOf(i + 1));
                        institute.setCount(jsonObject.getString("totalstudent"));
                        instituteArrayList.add(institute);
                    }

                    instituteAdapter = new InstituteAdapter(MyProfileActivity.this, instituteArrayList);
                    recyclerInstitute.setAdapter(instituteAdapter);
                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {

                }
            });
        }
    }

    @Override
    public void initClickListener() {

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialogue.setAlertDialogue("Are you sure you want to logout?", "No", "Yes", new AlertDialogueClickListner() {
                    @Override
                    public void onPositive(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(Dialog dialog) {
                        preference.setUserData(null);
                        Intent intent = new Intent(mContext, AuthenticationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
            }

        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permissions.getReadWritePermisssion() == true && permissions.getCameraPermisssion() == true) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 10);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            selectedImagePath = getRealPathFromURI(selectedImageUri);


            //http://visatwebsolution.com/eduwall/testapi/index.php/api/api/updateUserImage
            Log.e("PATH", selectedImagePath);
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", preference.getUserData().getId());
            ArrayList<String> strings = new ArrayList<>();
            strings.add(selectedImagePath);

            callAPiActivity.doPostWithFiles((Activity) mContext, map, Constant.ChangeProfille, strings, "image", new GetApiResultJson() {
                @Override
                public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    User user = preference.getUserData();
                    user.setProfile(result.getString("Image"));
                    preference.setUserData(user);
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                    ivProfile.setImageBitmap(bitmap);

                }

                @Override
                public void onFailureResult(String messgae) throws JSONException {
                    appDialogs.setErrorToast(messgae);
                }
            });
        }
    }

    public String getRealPathFromURI(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

}