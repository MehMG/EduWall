package com.eduwall.PrivateUnit.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduwall.PrivateUnit.Adapter.PaymentStudentAdapter;
import com.eduwall.R;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Activity.NewEmailActivity;

import java.util.ArrayList;

/**
 * Created by codesture on 7/6/17.
 */
public class PaymentStudentFragment extends BaseFragment {

    RecyclerView recyclerInbox;
    FloatingActionButton floatingNew;
    PaymentStudentAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sent_messages,null);
        initComponent(view);
        initData();
        initClickListener();
        return view;
    }

    @Override
    public void initComponent(View view) {
        floatingNew = (FloatingActionButton) view.findViewById(R.id.floatingNew);
        recyclerInbox = (RecyclerView) view.findViewById(R.id.recyclerInbox);
        recyclerInbox.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingNew.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        adapter = new PaymentStudentAdapter(0);
        recyclerInbox.setAdapter(adapter);
    }

    @Override
    public void initClickListener() {

    }

}
