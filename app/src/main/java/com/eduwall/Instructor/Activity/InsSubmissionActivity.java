package com.eduwall.Instructor.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eduwall.Instructor.Adapter.SubmissionAdapter;
import com.eduwall.Instructor.Models.Submission;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by codesture on 10/6/17.
 */
public class InsSubmissionActivity extends BaseActivity {

    ArrayList<Submission> submissionArrayList;
    SubmissionAdapter submissionAdapter;
    private RecyclerView recyclerInsSubmission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_submission);
        initComponent();
        initData();
        initClickListener();
        initActionBar("Submissions");
        initBackButton();
    }

    @Override
    public void initComponent() {

        recyclerInsSubmission = (RecyclerView) findViewById(R.id.recyclerInsSubmission);
        recyclerInsSubmission.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void initData() {

        submissionArrayList = new ArrayList<>();
        submissionAdapter = new SubmissionAdapter(InsSubmissionActivity.this, submissionArrayList);
        recyclerInsSubmission.setAdapter(submissionAdapter);

    }

    @Override
    public void initClickListener() {

    }
}
