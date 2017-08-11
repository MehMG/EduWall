package com.eduwall.Student.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Activity.NewEmailActivity;
import com.eduwall.Student.Adapter.InboxMessageAdapter;
import com.eduwall.Student.Models.InboxMessege;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by codesture on 7/6/17.
 */
public class ReceivedMessageFragment extends BaseFragment {

    RecyclerView recyclerInbox;
    FloatingActionButton floatingNew;
    ArrayList<InboxMessege> inboxArrayList;
    InboxMessageAdapter inboxAdapter;
    String type;

    public ReceivedMessageFragment(ArrayList<InboxMessege> receivedArrayList, String receiver) {
        this.inboxArrayList = receivedArrayList;
        this.type = receiver;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received_messages, null);
        initComponent(view);
        initData();
        initClickListener();
        return view;
    }

    @Override
    public void initComponent(View view) {
        floatingNew = (FloatingActionButton) view.findViewById(R.id.floatingNew);
        recyclerInbox = (RecyclerView) view.findViewById(R.id.recyclerInbox);
        recyclerInbox.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
    }

    @Override
    public void initData() {

        Collections.reverse(inboxArrayList);
        inboxAdapter = new InboxMessageAdapter(getActivity(), inboxArrayList,type);
        recyclerInbox.setAdapter(inboxAdapter);
    }

    @Override
    public void initClickListener() {
        floatingNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewEmailActivity.class);
                intent.putExtra("Parent","NewEmail");
                startActivity(intent);
            }
        });
    }


}
