package com.eduwall.Student.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eduwall.Interface.AlertDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Student.Adapter.TestsAdapter;
import com.eduwall.Student.Models.Tests;

import java.util.ArrayList;

public class StartTestActivity extends BaseActivity {
    private TextView txtStartTestMarks;
    private TextView txtStartTestDuration;
    private RecyclerView recyclerStartTestQue;
    private Button btnStartTestSubmit;

    TestsAdapter testsAdapter;
    ArrayList<Tests> testArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        initComponent();
        initData();
        initActionBar("Test");
        initBackButton();
        initClickListener();
    }

    @Override
    public void initComponent() {
        txtStartTestMarks = (TextView) findViewById(R.id.txtStartTestMarks);
        txtStartTestDuration = (TextView) findViewById(R.id.txtStartTestDuration);
        recyclerStartTestQue = (RecyclerView) findViewById(R.id.recyclerStartTestQue);
        recyclerStartTestQue.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        btnStartTestSubmit = (Button) findViewById(R.id.btnStartTestSubmit);
    }

    @Override
    public void initData() {

        testArrayList = new ArrayList<>();

        testsAdapter = new TestsAdapter(mContext, testArrayList);
        recyclerStartTestQue.setAdapter(testsAdapter);
    }

    @Override
    public void initClickListener() {

        btnStartTestSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogue.setAlertDialogue("Are you sure you want to submit Test?", "No", "Yes", new AlertDialogueClickListner() {
                    @Override
                    public void onPositive(Dialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(Dialog dialog) {

                        /*Intent intent = new Intent(mContext, AuthenticationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
