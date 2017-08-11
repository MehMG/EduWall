package com.eduwall.Instructor.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

public class InsSelectClassActivity extends BaseActivity {


    Button btnView;
    Spinner spinnerCourse;
    String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_select_class);
        initComponent();
        parent = getIntent().getStringExtra("Parent");
        initData();
        initSpinner();
        if (parent.equalsIgnoreCase("MyStudents")) {
            initActionBar("My Students");
            btnView.setText("View Students");
        } else if (parent.equalsIgnoreCase("Submission")) {
            initActionBar("Submissions");
            btnView.setText("View Submissions");
        }
        initBackButton();
        initClickListener();
    }


    @Override
    public void initComponent() {
        btnView = (Button) findViewById(R.id.btnView);
        spinnerCourse = (Spinner) findViewById(R.id.spinnerCourse);
    }

    @Override
    public void initData() {

    }

    private void initSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InsSelectClassActivity.this, R.array.spinnerCourse, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerCourse.setSelection(0);
        spinnerCourse.setAdapter(adapter);

    }


    @Override
    public void initClickListener() {

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (parent.equalsIgnoreCase("MyStudents")) {

                    Intent intent = new Intent(InsSelectClassActivity.this, InsStudentsActivity.class);
                    intent.putExtra("Parent", Constant.Home);
                    startActivity(intent);
                } else if (parent.equalsIgnoreCase("Submission")) {
                    startActivity(new Intent(InsSelectClassActivity.this, InsSubmissionActivity.class));
                }
            }
        });

    }

}
