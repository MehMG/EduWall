package com.eduwall.PrivateUnit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.eduwall.Instructor.Activity.InsAllCourseActivity;
import com.eduwall.Instructor.Activity.InsPostActivity;
import com.eduwall.Instructor.Activity.InsSettingsActivity;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.InboxActivity;
import com.eduwall.Student.Activity.MyProfileActivity;

public class PUHomeActivity extends BaseActivity {

    private LinearLayout lnvPUMyProfile;
    private LinearLayout lnvPUPost;
    private LinearLayout lnvPUPayment;
    private LinearLayout lnvPUCourse;
    private LinearLayout lnvPUStudent;
    private LinearLayout lnvPUInstructor;
    private LinearLayout lnvPUMailbox;
    private LinearLayout lnvPUSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puhome);

        initComponent();
        initData();
        initActionBar("Home");
        imgHome.setVisibility(View.VISIBLE);
        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
            imgNotification.setVisibility(View.VISIBLE);
        }
        initClickListener();
    }

    @Override
    public void initComponent() {

        lnvPUMyProfile = (LinearLayout) findViewById(R.id.lnvPUMyProfile);
        lnvPUPost = (LinearLayout) findViewById(R.id.lnvPUPost);
        lnvPUPayment = (LinearLayout) findViewById(R.id.lnvPUPayment);
        lnvPUCourse = (LinearLayout) findViewById(R.id.lnvPUCourse);
        lnvPUStudent = (LinearLayout) findViewById(R.id.lnvPUStudent);
        lnvPUInstructor = (LinearLayout) findViewById(R.id.lnvPUInstructor);
        lnvPUMailbox = (LinearLayout) findViewById(R.id.lnvPUMailbox);
        lnvPUSettings = (LinearLayout) findViewById(R.id.lnvPUSettings);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {


        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, PURequestActivity.class));
            }
        });

        lnvPUMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, MyProfileActivity.class));
            }
        });
        lnvPUPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, InsPostActivity.class));
            }
        });
        lnvPUPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, PUPaymentActivity.class));
            }
        });
        lnvPUCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, InsAllCourseActivity.class));
            }
        });
        lnvPUStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PUHomeActivity.this, PUStudentsActivity.class);
                i.putExtra("category", Constant.Student);
                startActivity(i);
            }
        });
        lnvPUInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PUHomeActivity.this, PUStudentsActivity.class);
                i.putExtra("category", Constant.Instructor);
                startActivity(i);
            }
        });
        lnvPUMailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, InboxActivity.class));
            }
        });
        lnvPUSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PUHomeActivity.this, InsSettingsActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
