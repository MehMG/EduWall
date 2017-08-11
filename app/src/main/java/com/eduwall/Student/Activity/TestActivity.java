package com.eduwall.Student.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

public class TestActivity extends BaseActivity {

    private TextView txtTestClassName;
    private TextView txtTestInsName;
    private TextView txtTestMarks;
    private TextView txtTestDuration;
    private TextView txtTestNotes;
    private Button btnTestStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initComponent();
        initActionBar("Test");
        initBackButton();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {
        txtTestClassName = (TextView) findViewById(R.id.txtTestClassName);
        txtTestInsName = (TextView) findViewById(R.id.txtTestInsName);
        txtTestMarks = (TextView) findViewById(R.id.txtTestMarks);
        txtTestDuration = (TextView) findViewById(R.id.txtTestDuration);
        txtTestNotes = (TextView) findViewById(R.id.txtTestNotes);
        btnTestStart = (Button) findViewById(R.id.btnTestStart);
    }

    @Override
    public void initData() {

        //API for Test Details...

    }

    @Override
    public void initClickListener() {

        btnTestStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestActivity.this, StartTestActivity.class));
            }
        });

    }

}
