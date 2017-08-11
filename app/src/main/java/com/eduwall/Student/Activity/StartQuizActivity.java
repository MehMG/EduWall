package com.eduwall.Student.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Student.Adapter.QuizAdapter;
import com.eduwall.Student.Models.Quiz;

import java.util.ArrayList;

public class StartQuizActivity extends BaseActivity {

    private TextView txtStartQuizMarks;
    private TextView txtStartQuizTime;
    private RecyclerView recyclerStartQuizQuestions;
    private Button btnStartQuizSubmit;

    ArrayList<Quiz> quizArrayList;
    QuizAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        initComponent();
        initActionBar("Quiz");
        initBackButton();
        initData();
        initClickListener();

    }


    @Override
    public void initComponent() {

        txtStartQuizMarks = (TextView) findViewById(R.id.txtStartQuizMarks);
        txtStartQuizTime = (TextView) findViewById(R.id.txtStartQuizTime);
        recyclerStartQuizQuestions = (RecyclerView) findViewById(R.id.recyclerStartQuizQuestions);
        recyclerStartQuizQuestions.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        btnStartQuizSubmit = (Button) findViewById(R.id.btnStartQuizSubmit);

    }

    @Override
    public void initData() {

        quizArrayList = new ArrayList<>();

        quizAdapter = new QuizAdapter(mContext, quizArrayList);
        recyclerStartQuizQuestions.setAdapter(quizAdapter);

    }

    @Override
    public void initClickListener() {

    }
}