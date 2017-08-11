package com.eduwall.Student.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Student.Adapter.QuizAdapter;
import com.eduwall.Student.Adapter.QuizListAdapter;
import com.eduwall.Student.Models.QuizList;

import java.util.ArrayList;

public class QuizActivity extends BaseActivity {

    private TextView txtQuizClassName;
    private TextView txtQuizInsName;
    private RecyclerView recyclerQuizList;

    ArrayList<QuizList> quizListArrayList;
    QuizListAdapter quizListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initComponent();
        initActionBar("Quiz");
        initBackButton();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {

        txtQuizClassName = (TextView) findViewById(R.id.txtQuizClassName);
        txtQuizInsName = (TextView) findViewById(R.id.txtQuizInsName);
        recyclerQuizList = (RecyclerView) findViewById(R.id.recyclerQuizList);
        recyclerQuizList.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void initData() {

        quizListArrayList = new ArrayList<>();

        quizListAdapter = new QuizListAdapter(mContext,quizListArrayList);
        recyclerQuizList.setAdapter(quizListAdapter);

    }

    @Override
    public void initClickListener() {

    }

}
