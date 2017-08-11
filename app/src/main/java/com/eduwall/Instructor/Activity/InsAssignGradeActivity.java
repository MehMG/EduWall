package com.eduwall.Instructor.Activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.Instructor.Adapter.SubmissionAttachAdapter;
import com.eduwall.Instructor.Models.SubmissionAttach;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

import java.util.ArrayList;

public class InsAssignGradeActivity extends BaseActivity {


    ArrayList<SubmissionAttach> submissionAttachs;
    SubmissionAttachAdapter submissionAttachAdapter;

    Spinner spinnerGrade;
    Spinner spinnerMarks;

    private TextView txtAssignmentHeading;
    private RecyclerView recyclerSUbmissionAttachments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_grade);

        initComponent();
        initData();
        initSpinner();
        initClickListener();
        initActionBar("Student Name");
        initBackButton();
    }


    @Override
    public void initComponent() {


        spinnerGrade = (Spinner) findViewById(R.id.spinnerGrade);
        spinnerMarks = (Spinner) findViewById(R.id.spinnerMarks);

        txtAssignmentHeading = (TextView) findViewById(R.id.txtAssignmentHeading);
//        recyclerSUbmissionAttachments = (RecyclerView) findViewById(R.id.recyclerSUbmissionAttachments);
//        recyclerSUbmissionAttachments.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void initData() {

//        submissionAttachAdapter = new SubmissionAttachAdapter(InsAssignGradeActivity.this, submissionAttachs);
//        recyclerSUbmissionAttachments.setAdapter(submissionAttachAdapter);

    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InsAssignGradeActivity.this, R.array.spinnerGrade, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerGrade.setSelection(0);
        spinnerGrade.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(InsAssignGradeActivity.this, R.array.spinnerMarks, R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerMarks.setSelection(0);
        spinnerMarks.setAdapter(adapter1);
    }


    @Override
    public void initClickListener() {

    }

}
