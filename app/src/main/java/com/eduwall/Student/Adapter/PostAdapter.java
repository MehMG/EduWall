package com.eduwall.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.AnswerCommentActivity;
import com.eduwall.Student.Activity.CommentsActivity;
import com.eduwall.Student.Models.ShowPost;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture18 on 6/2/2017.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    Context context;
    private ArrayList<ShowPost> showPostList;
    BaseActivity baseActivity;

    PostAttachmentAdapter attachAdapter;
    ArrayList<GetAttach> getAttachArrayList;

    String post = "0";

    int screenWidth;


    public PostAdapter(Context showAllPostActivity, ArrayList<ShowPost> showPostArrayList) {
        this.showPostList = showPostArrayList;
        this.context = showAllPostActivity;
        baseActivity = (BaseActivity) showAllPostActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_post, parent, false);
        return new MyViewHolder(itemView);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView imgAskProfile;
        private TextView txtAskedBy;
        private TextView txtAskDate;
        private TextView txtTopicName;
        private TextView txtAskedQuestion;
        private RecyclerView recyclerAskAttachment;
        private RecyclerView recyclerAttachment;
        private LinearLayout txtAskComment;
        private TextView txtAskHide;
        private TextView txtAskReport;
        private LinearLayout lnvPostAnswer;
        private CardView lnvAskData;
        private CardView lnvPostData;

        public EditText edtStartDate;
        public EditText edtEndDate;

        public ImageView imgProfile;
        public TextView txtInstName;
        public TextView txtDate;
        public TextView txtPostTitle;
        public TextView txtPostDesc;
        public LinearLayout txtComment;
        public TextView txtHide;
        public TextView txtReport;


        public MyViewHolder(View view) {
            super(view);

            lnvPostData = (CardView) view.findViewById(R.id.lnvShowAllPostData);
            imgProfile = (ImageView) view.findViewById(R.id.img_profile);
            txtInstName = (TextView) view.findViewById(R.id.txt_inst_name);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            txtPostTitle = (TextView) view.findViewById(R.id.txt_post_title);
            txtPostDesc = (TextView) view.findViewById(R.id.txt_post_desc);
            txtComment = (LinearLayout) view.findViewById(R.id.txt_comment);
            txtHide = (TextView) view.findViewById(R.id.txt_hide);
            txtReport = (TextView) view.findViewById(R.id.txt_report);
            recyclerAttachment = (RecyclerView) view.findViewById(R.id.recyclerShowPostAttachment);


            LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(recyclerAttachment);
            recyclerAttachment.setLayoutManager(baseActivity.getLayoutManager(1, LinearLayoutManager.HORIZONTAL));

            lnvAskData = (CardView) view.findViewById(R.id.lnvShowAllAskData);
            imgAskProfile = (ImageView) view.findViewById(R.id.imgAskProfile);
            txtAskedBy = (TextView) view.findViewById(R.id.txtAskedBy);
            txtAskDate = (TextView) view.findViewById(R.id.txtAskDate);
            txtTopicName = (TextView) view.findViewById(R.id.txtTopicName);
            txtAskedQuestion = (TextView) view.findViewById(R.id.txtAskedQuestion);
            recyclerAskAttachment = (RecyclerView) view.findViewById(R.id.recyclerAskAttachment);
            recyclerAskAttachment.setLayoutManager(baseActivity.getLayoutManager(1, LinearLayoutManager.HORIZONTAL));
            linearSnapHelper.attachToRecyclerView(recyclerAskAttachment);
            txtAskComment = (LinearLayout) view.findViewById(R.id.txtAskComment);
            txtAskHide = (TextView) view.findViewById(R.id.txtAskHide);
            txtAskReport = (TextView) view.findViewById(R.id.txtAskReport);
            lnvPostAnswer = (LinearLayout) view.findViewById(R.id.lnvPostAnswer);

        }
    }


    @Override
    public int getItemCount() {
        return showPostList.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        screenWidth = holder.recyclerAttachment.getWidth();
        holder.recyclerAttachment.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return getRecyclerView(velocityX, holder);
            }
        });
        holder.recyclerAskAttachment.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return getRecyclerView(velocityX, holder);

            }


        });

        if (baseActivity.preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
            holder.txtReport.setText("Delete");
            holder.txtAskReport.setText("Delete");
        }

        if (showPostList.get(position).getPostType().equalsIgnoreCase("")) {
        } else {
            holder.edtStartDate.setText(showPostList.get(position).getStartDate());
            holder.edtEndDate.setText(showPostList.get(position).getEndDate());
        }

        if (showPostList.get(position).getType().equalsIgnoreCase(post)) {
            holder.lnvPostData.setVisibility(View.VISIBLE);
            holder.lnvAskData.setVisibility(View.GONE);
            try {
                Picasso.with(context).load(showPostList.get(position).getProfile()).resize(250, 250).into(holder.imgProfile);
            } catch (Exception e) {
            }
            holder.txtInstName.setText(showPostList.get(position).getName());
            holder.txtDate.setText(showPostList.get(position).getDate());
            holder.txtPostTitle.setText(showPostList.get(position).getTitle());
            holder.txtPostDesc.setText(showPostList.get(position).getDescription());
            getAttachArrayList = showPostList.get(position).getAttachments();

            attachAdapter = new PostAttachmentAdapter(context, getAttachArrayList);
            holder.recyclerAttachment.setAdapter(attachAdapter);


        } else {
            holder.lnvAskData.setVisibility(View.VISIBLE);
            holder.lnvPostData.setVisibility(View.GONE);
            try {
                Picasso.with(context).load(showPostList.get(position).getProfile()).resize(250, 250).into(holder.imgAskProfile);
            } catch (Exception e) {
            }
            holder.txtAskedBy.setText(showPostList.get(position).getName());
            holder.txtAskDate.setText(showPostList.get(position).getDate());
            holder.txtTopicName.setText(showPostList.get(position).getTitle());
            holder.txtAskedQuestion.setText(showPostList.get(position).getDescription());
            getAttachArrayList = showPostList.get(position).getAttachments();

            attachAdapter = new PostAttachmentAdapter(context, getAttachArrayList);
            holder.recyclerAskAttachment.setAdapter(attachAdapter);

        }

        holder.txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPost showPost = showPostList.get(position);
                showComments(showPost);

            }
        });

        holder.txtReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Attachment1111", getAttachArrayList.get(position).getFilename());
            }
        });

        holder.txtAskComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPost showPost = showPostList.get(position);
                showComments(showPost);
            }
        });

        holder.lnvPostAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowPost showPost = showPostList.get(position);
                baseActivity.preference.setCommentPostData(showPost);
                Intent intent = new Intent(context, AnswerCommentActivity.class);
                context.startActivity(intent);
            }
        });

        holder.txtHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPost showPost = showPostList.get(position);
                hidePost(showPostList.get(position).getPostID(), position);
            }
        });

        holder.txtAskHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hidePost(showPostList.get(position).getPostID(), position);
            }
        });

    }

    public boolean getRecyclerView(int velocityX, MyViewHolder holder) {
        try {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) holder.recyclerAttachment.getLayoutManager();
            int lastVisibleView = linearLayoutManager.findLastVisibleItemPosition();
            int firstVisibleView = linearLayoutManager.findFirstVisibleItemPosition();
            View firstView = linearLayoutManager.findViewByPosition(firstVisibleView);
            View lastView = linearLayoutManager.findViewByPosition(lastVisibleView);

            int leftEdge = lastView.getLeft();
            int rightEdge = firstView.getRight();
            int scrollDistanceLeft = leftEdge;
            int scrollDistanceRight = -rightEdge + firstView.getWidth();

            if (velocityX > 0) holder.recyclerAttachment.smoothScrollBy(scrollDistanceLeft, 0);
            else holder.recyclerAttachment.smoothScrollBy(-scrollDistanceRight, 0);

        } catch (Exception e) {
        }
        return true;
    }


    private void hidePost(final String postID, final int position) {
        //input user_id,post_id
        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/hidepost

        HashMap<String, String> map = new HashMap<>();
        map.put("post_id", postID);
        map.put("user_id", baseActivity.preference.getUserData().getId());

        baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.Hidepost, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                showPostList.remove(position);
                notifyDataSetChanged();
                notifyItemRemoved(position);
                notifyItemRangeChanged(showPostList.size(), showPostList.size());

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                baseActivity.appDialogs.setErrorToast(messgae);
            }
        });

    }

    private void showComments(ShowPost showPost) {

        baseActivity.preference.setCommentPostData(showPost);

        Intent intent = new Intent(context, CommentsActivity.class);
        context.startActivity(intent);
    }
}