package com.eduwall.Instructor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.InboxActivity;
import com.eduwall.Student.Activity.MyProfileActivity;

public class InsHomeActivity extends BaseActivity {

    private LinearLayout lnvInsMyProfile;
    private LinearLayout lnvInsPost;
    private LinearLayout lnvInsSubmission;
    private LinearLayout lnvInsCourse;
    private LinearLayout lnvInsMailBox;
    private LinearLayout lnvInsPayment;
    private LinearLayout lnvInsMyStudents;
    private LinearLayout lnvInsSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_home);
        initComponent();
        initActionBar("Home");
        imgHome.setVisibility(View.VISIBLE);
        initData();
        initClickListener();


    }

    @Override
    public void initComponent() {

        lnvInsMyProfile = (LinearLayout) findViewById(R.id.lnvInsMyProfile);
        lnvInsPost = (LinearLayout) findViewById(R.id.lnvInsPost);
        lnvInsSubmission = (LinearLayout) findViewById(R.id.lnvInsSubmission);
        lnvInsCourse = (LinearLayout) findViewById(R.id.lnvInsCourse);
        lnvInsMailBox = (LinearLayout) findViewById(R.id.lnvInsMailBox);
        lnvInsPayment = (LinearLayout) findViewById(R.id.lnvInsPayment);
        lnvInsMyStudents = (LinearLayout) findViewById(R.id.lnvInsMyStudents);
        lnvInsSettings = (LinearLayout) findViewById(R.id.lnvInsSettings);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {


        lnvInsMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InsHomeActivity.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });
        lnvInsPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsHomeActivity.this, InsPostActivity.class));
            }
        });
        lnvInsSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsHomeActivity.this, InsSelectClassActivity.class);
                intent.putExtra("Parent", "Submission");
                startActivity(intent);

            }
        });
        lnvInsCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsHomeActivity.this, InsAllCourseActivity.class));
            }
        });
        lnvInsMailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsHomeActivity.this, InboxActivity.class);
//                intent.putExtra("Type", Constant.Instructor);
                startActivity(intent);
            }
        });
        lnvInsPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsHomeActivity.this, InsPaymentActivity.class));
            }
        });
        lnvInsMyStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsHomeActivity.this, InsStudentsActivity.class);
                intent.putExtra("Parent", Constant.Home);
                startActivity(intent);

            }
        });
        lnvInsSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InsHomeActivity.this, InsSettingsActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
