package com.eduwall.Student.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.CommentsAdapter;
import com.eduwall.Student.Models.Comments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CommentsActivity extends BaseActivity {

    ArrayList<Comments> commentLists;
    CommentsAdapter commentsAdapter;

    EditText edtComment;
    LinearLayout lnvSend;
    RecyclerView recyclerComments;
    ImageView imgBackComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initComponent();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {
        recyclerComments = (RecyclerView) findViewById(R.id.recyclerComments);
        recyclerComments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
        recyclerComments.setItemAnimator(new DefaultItemAnimator());

        edtComment = (EditText) findViewById(R.id.edtComment);
        lnvSend = (LinearLayout) findViewById(R.id.lnvSend);

        imgBackComment = (ImageView) findViewById(R.id.imgBackComment);
    }

    @Override
    public void initData() {

        commentLists = new ArrayList<>();
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
                    comments.setDate(jsonObject.getString("created_at"));
                    comments.setMessage(jsonObject.getString("comment"));
                    commentLists.add(comments);
                }

                commentsAdapter = new CommentsAdapter(CommentsActivity.this, commentLists);
                recyclerComments.setAdapter(commentsAdapter);

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });


    }

    @Override
    public void initClickListener() {


        lnvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });

        imgBackComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addComment() {

        // (input post_id,user_id,comment)
        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/addcomment

        HashMap<String, String> map = new HashMap<>();
        map.put("post_id", preference.getCommentPostData().getPostID());
        map.put("user_id", preference.getUserData().getId());
        map.put("comment", edtComment.getText().toString());

        callAPiActivity.doPost((Activity) mContext, map, Constant.Addcomment, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                Calendar calendar = Calendar.getInstance();
                Comments comments = new Comments();
                comments.setMessage(edtComment.getText().toString());
                comments.setName(preference.getUserData().getName());
                int hour;
                hour = calendar.get(Calendar.HOUR);
                if (hour > 12) {
                    hour = hour - 12;
                } else if (hour == 0) {
                    hour = 12;
                }

                comments.setDate(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " " + hour + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND));
                commentLists.add(comments);
                commentsAdapter.notifyItemRangeChanged(commentLists.size(), commentLists.size(), comments);

                edtComment.setText("");

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

                appDialogs.setErrorToast(messgae);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
