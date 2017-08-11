package com.eduwall.Student.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.AnswerAdapter;
import com.eduwall.Student.Adapter.GetAttach;
import com.eduwall.Student.Adapter.PostAttachmentAdapter;
import com.eduwall.Student.Models.Comments;
import com.eduwall.Student.Models.ShowPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnswerCommentActivity extends BaseActivity {


    private CardView lnvAnswerData;
    private ImageView imgAnswerProfile;
    private TextView txtAnswerAskedBy;
    private TextView txtAnswerAskTime;
    private TextView txtAnswerAskDate;
    private TextView txtAnswerTopicName;
    private TextView txtAnswerAskedQue;
    private RecyclerView recyclerAnswerAskAttachment;
    private TextView txtAnswerAskComment;
    private TextView txtAnswerAskHide;
    private TextView txtAnswerAskReport;
    private RecyclerView recyclerAnswers;

    LinearLayout lnvAnswerEditText;
    EditText edtAnswerComment;
    LinearLayout lnvAnswerSend;


    ArrayList<Comments> answerArrayList;
    AnswerAdapter answerAdapter;

    PostAttachmentAdapter attachAdapter;

    ArrayList<GetAttach> attachStrings = new ArrayList<>();

    ShowPost post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_comment);
        initComponent();
        post = preference.getCommentPostData();
        initData();
        initClickListener();
        initActionBar("Answers");
        initBackButton();
    }


    @Override
    public void initComponent() {

        lnvAnswerData = (CardView) findViewById(R.id.lnvAnswerData);
        imgAnswerProfile = (ImageView) findViewById(R.id.imgAnswerProfile);
        txtAnswerAskedBy = (TextView) findViewById(R.id.txtAnswerAskedBy);
        txtAnswerAskDate = (TextView) findViewById(R.id.txtAnswerAskDate);
        txtAnswerTopicName = (TextView) findViewById(R.id.txtAnswerTopicName);
        txtAnswerAskedQue = (TextView) findViewById(R.id.txtAnswerAskedQue);

        recyclerAnswerAskAttachment = (RecyclerView) findViewById(R.id.recyclerAnswerAskAttachment);
        recyclerAnswerAskAttachment.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerAnswerAskAttachment.setNestedScrollingEnabled(false);

        txtAnswerAskComment = (TextView) findViewById(R.id.txtAnswerAskComment);
        txtAnswerAskHide = (TextView) findViewById(R.id.txtAnswerAskHide);
        txtAnswerAskReport = (TextView) findViewById(R.id.txtAnswerAskReport);

        recyclerAnswers = (RecyclerView) findViewById(R.id.recyclerAnswers);
        recyclerAnswers.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerAnswers.setNestedScrollingEnabled(false);

        lnvAnswerEditText = (LinearLayout) findViewById(R.id.lnvAnswerEditText);
        lnvAnswerSend = (LinearLayout) findViewById(R.id.lnvAnswerSend);
        edtAnswerComment = (EditText) findViewById(R.id.edtAnswerComment);
    }

    @Override
    public void initData() {


        txtAnswerAskedBy.setText(post.getName());
        txtAnswerAskDate.setText(post.getDate());
        txtAnswerTopicName.setText(post.getTitle());
        txtAnswerAskedQue.setText(post.getDescription());
        attachStrings = post.getAttachments();

        getAttachments();

        attachAdapter = new PostAttachmentAdapter(mContext, attachStrings);
        recyclerAnswerAskAttachment.setAdapter(attachAdapter);

        answerArrayList = new ArrayList<>();

        //input post_id
        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getcomment

        HashMap<String, String> map = new HashMap<>();
        map.put("post_id", preference.getCommentPostData().getPostID());

        callAPiActivity.doPost((Activity) mContext, map, Constant.Getcomment, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                JSONArray jsonArray = result.getJSONArray("Commentlist");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Comments comments = new Comments();
                    comments.setName(jsonObject.getString("name"));
                    comments.setMessage(jsonObject.getString("comment"));
                    answerArrayList.add(comments);
                }

                answerAdapter = new AnswerAdapter(AnswerCommentActivity.this, answerArrayList);
                recyclerAnswers.setAdapter(answerAdapter);

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });


        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
            lnvAnswerEditText.setVisibility(View.GONE);
        } else {
            lnvAnswerEditText.setVisibility(View.VISIBLE);
        }

    }

    public void getAttachments() {

    }


    @Override
    public void initClickListener() {

        lnvAnswerSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // (input post_id,user_id,comment)
                //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/addcomment

                HashMap<String, String> map = new HashMap<>();
                map.put("post_id", preference.getCommentPostData().getPostID());
                map.put("user_id", preference.getUserData().getId());
                map.put("comment", edtAnswerComment.getText().toString());

                callAPiActivity.doPost((Activity) mContext, map, Constant.Addcomment, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                        Comments comments = new Comments();
                        comments.setMessage(edtAnswerComment.getText().toString());
                        comments.setName(preference.getUserData().getName());
                        answerArrayList.add(comments);

                        answerAdapter.notifyItemRangeChanged(answerArrayList.size(), answerArrayList.size(), comments);
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {

                        appDialogs.setErrorToast(messgae);
                    }
                });
            }
        });

        txtAnswerAskComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnswerCommentActivity.this, CommentsActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
