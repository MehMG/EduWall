package com.eduwall.Student.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

public class TaskActivity extends BaseActivity {


    private Button btnHomeWork;
    private Button btnAssignment;
    private Button btnTest;
    private Button btnQuiz;
    private Button btnProjectWork;
    private Button btnPresenation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initComponent();
        initData();
        initClickListener();
        initActionBar("Task");
        initBackButton();
        initBackButton();
    }

    @Override
    public void initComponent() {
        btnHomeWork = (Button) findViewById(R.id.btnHomeWork);
        btnAssignment = (Button) findViewById(R.id.btnAssignment);
        btnTest = (Button) findViewById(R.id.btnTest);
        btnQuiz = (Button) findViewById(R.id.btnQuiz);
        btnProjectWork = (Button) findViewById(R.id.btnProjectWork);
        btnPresenation = (Button) findViewById(R.id.btnPresenation);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClickListener() {

        btnHomeWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, HomeworkActivity.class);
                intent.putExtra(Constant.Parent, Constant.Homework);
                startActivity(intent);
            }
        });
        btnAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, HomeworkActivity.class);
                intent.putExtra(Constant.Parent, Constant.Assignment);
                startActivity(intent);
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskActivity.this, TestActivity.class));
            }
        });
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TaskActivity.this, QuizActivity.class));

            }
        });
        btnProjectWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, ProjectWorkActivity.class);
                intent.putExtra(Constant.Parent, Constant.ProjectWork);
                startActivity(intent);
            }
        });
        btnPresenation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskActivity.this, ProjectWorkActivity.class);
                intent.putExtra(Constant.Parent, Constant.Presentation);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
