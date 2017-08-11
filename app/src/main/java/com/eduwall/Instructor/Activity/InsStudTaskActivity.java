package com.eduwall.Instructor.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

/**
 * Created by codesture on 13/6/17.
 */
public class InsStudTaskActivity extends BaseActivity {


    private TextView txtCourseName;
    private RelativeLayout rlvHomework;
    private TextView txtHomeWork;
    private RelativeLayout rlvAssignment;
    private TextView txtAssignment;
    private RelativeLayout rlvTest;
    private TextView txtTest;
    private RelativeLayout rlvQuiz;
    private TextView txtQuiz;
    private RelativeLayout rlvProjectWork;
    private TextView txtProjectWork;
    private RelativeLayout rlvPresention;
    private TextView txtPresention;
    private LinearLayout lnvTotal;
    private LinearLayout lnvUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_stud_task);
        initComponent();
        initData();
        initActionBar("Student Name");
        initBackButton();
        initClickListener();
    }

    @Override
    public void initComponent() {


        txtCourseName = (TextView) findViewById(R.id.txtCourseName);
        rlvHomework = (RelativeLayout) findViewById(R.id.rlvHomework);
        txtHomeWork = (TextView) findViewById(R.id.txtHomeWork);
        rlvAssignment = (RelativeLayout) findViewById(R.id.rlvAssignment);
        txtAssignment = (TextView) findViewById(R.id.txtAssignment);
        rlvTest = (RelativeLayout) findViewById(R.id.rlvTest);
        txtTest = (TextView) findViewById(R.id.txtTest);
        rlvQuiz = (RelativeLayout) findViewById(R.id.rlvQuiz);
        txtQuiz = (TextView) findViewById(R.id.txtQuiz);
        rlvProjectWork = (RelativeLayout) findViewById(R.id.rlvProjectWork);
        txtProjectWork = (TextView) findViewById(R.id.txtProjectWork);
        rlvPresention = (RelativeLayout) findViewById(R.id.rlvPresention);
        txtPresention = (TextView) findViewById(R.id.txtPresention);

        lnvTotal = (LinearLayout) findViewById(R.id.lnvTotal);
        lnvUpdate = (LinearLayout) findViewById(R.id.lnvUpdate);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

    }
}
