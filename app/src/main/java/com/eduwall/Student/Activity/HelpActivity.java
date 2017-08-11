package com.eduwall.Student.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Student.Adapter.HelpAdapter;
import com.eduwall.Student.Models.Help;

import java.util.ArrayList;

public class HelpActivity extends BaseActivity {

RecyclerView recyclerHelp;

    ArrayList<Help> helpArrayList;
    HelpAdapter helpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        initComponent();
        initData();
        initActionBar("Help");
        initBackButton();
        initClickListener();
        
    }

    @Override
    public void initComponent() {

        recyclerHelp = (RecyclerView) findViewById(R.id.recyclerHelp);
        recyclerHelp.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void initData() {


        helpAdapter = new HelpAdapter(mContext,helpArrayList);
        recyclerHelp.setAdapter(helpAdapter);

    }

    @Override
    public void initClickListener() {

    }

}
