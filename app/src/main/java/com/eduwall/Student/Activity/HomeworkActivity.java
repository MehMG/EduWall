package com.eduwall.Student.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Interface.GetResult;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.AttachAdapter;
import com.eduwall.Student.Adapter.QuestionAdapter;
import com.eduwall.Student.Models.Attach;
import com.eduwall.Student.Models.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeworkActivity extends BaseActivity {

    private TextView txtHomeworkClassName;
    private TextView txtHomeworkInsName;
    private TextView txtHomeworkDate;
    private TextView txtAssignmentDate;
    private TextView txtHomeworkNotes;
    private RecyclerView recyclerHomeworkQuestions;
    private Button btnHomeWorkAttach;
    private RecyclerView recyclerHomeworkAttach;
    private Button btnHomeWorkSubmit;

    LinearLayout lnvAssignment;
    LinearLayout lnvHomework;

    ArrayList<Questions> questionsArrayList;
    QuestionAdapter questionAdapter;

    ArrayList<Attach> attachArrayList;
    AttachAdapter attachAdapter;

    ArrayList<String> attachList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        initActionBar("Homework");
        initBackButton();
        initComponent();
        initData();
        initClickListener();

    }

    @Override
    public void initComponent() {

        txtHomeworkClassName = (TextView) findViewById(R.id.txtHomeworkClassName);
        txtHomeworkInsName = (TextView) findViewById(R.id.txtHomeworkInsName);
        txtHomeworkDate = (TextView) findViewById(R.id.txtHomeworkDate);
        txtHomeworkNotes = (TextView) findViewById(R.id.txtHomeworkNotes);
        txtAssignmentDate = (TextView) findViewById(R.id.txtAssignmentDate);
        btnHomeWorkAttach = (Button) findViewById(R.id.btnHomeWorkAttach);
        btnHomeWorkSubmit = (Button) findViewById(R.id.btnHomeWorkSubmit);
        lnvAssignment = (LinearLayout) findViewById(R.id.lnvAssignment);
        lnvHomework = (LinearLayout) findViewById(R.id.lnvHomework);

        recyclerHomeworkAttach = (RecyclerView) findViewById(R.id.recyclerHomeworkAttach);
        recyclerHomeworkAttach.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerHomeworkQuestions = (RecyclerView) findViewById(R.id.recyclerHomeworkQuestions);
        recyclerHomeworkQuestions.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void initData() {

        if (getIntent().getStringExtra(Constant.Parent).equalsIgnoreCase(Constant.Homework)) {
            lnvHomework.setVisibility(View.VISIBLE);
            lnvAssignment.setVisibility(View.GONE);
        } else {
            lnvHomework.setVisibility(View.GONE);
            lnvAssignment.setVisibility(View.VISIBLE);
        }

        questionsArrayList = new ArrayList<>();
        attachArrayList = new ArrayList<>();


        attachAdapter = new AttachAdapter(mContext, attachArrayList, Constant.Homework);
        recyclerHomeworkAttach.setAdapter(attachAdapter);

        questionAdapter = new QuestionAdapter(mContext, questionsArrayList, Constant.Instructor);
        recyclerHomeworkQuestions.setAdapter(questionAdapter);

        HashMap<String, String> map = new HashMap<>();
        map.put("", "");

        callAPiActivity.doPost((Activity) mContext, map, Constant.DeleteUserByUserId, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                txtHomeworkClassName.setText("Econom");
                txtHomeworkInsName.setText("Instructor");
                txtHomeworkDate.setText("12/12/1212");
                txtHomeworkNotes.setText("13213213");

                JSONArray jsonArray = result.getJSONArray("Questions");
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });

    }

    @Override
    public void initClickListener() {

        btnHomeWorkAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAttachment(Constant.Document, new GetResult() {
                    @Override
                    public void getSuccess(String path) {

                        String[] path1 = selectedImagePath.split("/");
                        actualName = path1[path1.length - 1];

                        Attach attach = new Attach();
                        attach.setName(actualName);
                        attachArrayList.add(attach);

                        attachAdapter.notifyDataSetChanged();
                        attachAdapter.notifyItemRangeChanged(attachArrayList.size(), attachArrayList.size());

                        attachList.add(selectedImagePath);

                    }
                });
            }
        });

        btnHomeWorkSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("", "");

                callAPiActivity.doPostWithFiles((Activity) mContext, map, Constant.DeleteUserByUserId, attachList, "attachment", new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {

                    }
                });
            }
        });

    }
}
