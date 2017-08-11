package com.eduwall.Student.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Interface.GetResult;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.AttachAdapter;
import com.eduwall.Student.Models.Attach;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProjectWorkActivity extends BaseActivity {

    private TextView txtProjectClassName;
    private TextView txtProjectInsName;
    private TextView txtProjectDate;
    private TextView txtProjectTopicName;
    private TextView txtProjectDescription;
    private TextView txtProjectTaskList;
    private Button btnProjectAttach;
    private RecyclerView recyclerProjectAttach;
    private Button btnProjectSubmit;

    ArrayList<Attach> attachArrayList;
    AttachAdapter attachAdapter;

    ArrayList<String> attachList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_work);

        initComponent();
        initActionBar("Project Work");
        initBackButton();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {
        txtProjectClassName = (TextView) findViewById(R.id.txtProjectClassName);
        txtProjectInsName = (TextView) findViewById(R.id.txtProjectInsName);
        txtProjectDate = (TextView) findViewById(R.id.txtProjectDate);
        txtProjectTopicName = (TextView) findViewById(R.id.txtProjectTopicName);
        txtProjectDescription = (TextView) findViewById(R.id.txtProjectDescription);
        txtProjectTaskList = (TextView) findViewById(R.id.txtProjectTaskList);
        btnProjectAttach = (Button) findViewById(R.id.btnProjectAttach);
        recyclerProjectAttach = (RecyclerView) findViewById(R.id.recyclerProjectAttach);
        recyclerProjectAttach.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        btnProjectSubmit = (Button) findViewById(R.id.btnProjectSubmit);
    }

    @Override
    public void initData() {

        if (getIntent().getStringExtra(Constant.Parent).equalsIgnoreCase(Constant.ProjectWork)) {
            txtProjectDescription.setVisibility(View.VISIBLE);
        } else {
            txtProjectDescription.setVisibility(View.GONE);
        }

        //API to get Data...

        attachArrayList = new ArrayList<>();

        attachAdapter = new AttachAdapter(mContext, attachArrayList, Constant.ProjectWork);
        recyclerProjectAttach.setAdapter(attachAdapter);

    }

    @Override
    public void initClickListener() {

        btnProjectAttach.setOnClickListener(new View.OnClickListener() {
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

        btnProjectSubmit.setOnClickListener(new View.OnClickListener() {
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
