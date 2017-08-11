package com.eduwall.Student.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.AttachAdapter;
import com.eduwall.Student.Models.Attach;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewMessageActivity extends BaseActivity {

    private TextView txtTitle;
    private CircleImageView ivViewProfile;
    private TextView txtViewName;
    private TextView txtViewDate;
    private TextView txtViewDescription;
    private TextView txtViewEmail;
    private RecyclerView recyclerViewAttach;
    ArrayList<Attach> attachArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);


        initComponent();
        initActionBar("Mailbox");
        initBackButton();
        imgReply.setVisibility(View.VISIBLE);
        imgDelete.setVisibility(View.VISIBLE);
        initData();
        initClickListener();
    }


    @Override
    public void initComponent() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        ivViewProfile = (CircleImageView) findViewById(R.id.ivViewProfile);
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewDate = (TextView) findViewById(R.id.txtViewDate);
        txtViewEmail = (TextView) findViewById(R.id.txtViewEmail);
        txtViewDescription = (TextView) findViewById(R.id.txtViewDescription);
        recyclerViewAttach = (RecyclerView) findViewById(R.id.recyclerViewAttach);
        recyclerViewAttach.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void initData() {

        txtViewName.setText(preference.getUserMessageData().getName());
        txtViewDate.setText(preference.getUserMessageData().getTime());
        txtTitle.setText(preference.getUserMessageData().getTitle());
        txtViewDescription.setText(preference.getUserMessageData().getDescription());
        Picasso.with(mContext).load(preference.getUserMessageData().getProfile()).into(ivViewProfile);
        txtViewEmail.setText(preference.getUserMessageData().getEmail());

        attachArrayList = new ArrayList<>();

        Log.e("MessageDataaa111", String.valueOf(preference.getUserMessageData().getAttachment()));
        attachArrayList = preference.getUserMessageData().getAttachment();

        AttachAdapter attachAdapter = new AttachAdapter(ViewMessageActivity.this, attachArrayList, Constant.ViewMessage);
        recyclerViewAttach.setAdapter(attachAdapter);
    }

    @Override
    public void initClickListener() {

        imgReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewMessageActivity.this, NewEmailActivity.class);
                intent.putExtra("Parent", "ViewMessage");
                startActivity(intent);
            }
        });


        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/mailDelete_post

                HashMap<String, String> map = new HashMap<>();
                map.put("user_type", preference.getUserMessageData().getUserType());
                map.put("id", preference.getUserMessageData().getTheradID());

                callAPiActivity.doPost((Activity) mContext, map, Constant.MailDelete_post, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {
                        startActivity(new Intent(ViewMessageActivity.this, InboxActivity.class));
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        appDialogs.setErrorToast(messgae);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
