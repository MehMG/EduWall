package com.eduwall.Instructor.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.Instructor.Adapter.AssignAttachAdapter;
import com.eduwall.Instructor.Models.AssignAttach;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

import java.util.ArrayList;

public class InsAssignmentTaskActivity extends BaseActivity {


    private TextView txtCourseName;
    private LinearLayout lnvTotal;
    private LinearLayout lnvUpdate;


    RecyclerView recyclerAssignAttach;
    ArrayList<AssignAttach> assignAttachArrayList;
    AssignAttachAdapter assignAttachAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_assignment_task);
        initComponent();
        initActionBar("Student Name");
        initBackButton();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {

        txtCourseName = (TextView) findViewById(R.id.txtCourseName);
        recyclerAssignAttach = (RecyclerView) findViewById(R.id.recyclerAssignAttach);
        recyclerAssignAttach.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        lnvTotal = (LinearLayout) findViewById(R.id.lnvTotal);
        lnvUpdate = (LinearLayout) findViewById(R.id.lnvUpdate);
    }

    @Override
    public void initData() {

assignAttachArrayList = new ArrayList<>();
        assignAttachAdapter = new AssignAttachAdapter(InsAssignmentTaskActivity.this,assignAttachArrayList);
        recyclerAssignAttach.setAdapter(assignAttachAdapter);

    }

    @Override
    public void initClickListener() {

    }

}
