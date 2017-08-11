package com.eduwall.Student.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Activity.CommentsActivity;
import com.eduwall.Student.Models.Answer;
import com.eduwall.Student.Activity.AnswerCommentActivity;
import com.eduwall.Student.Models.Comments;

import java.util.ArrayList;

/**
 * Created by codesture on 10/6/17.
 */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Comments> answerArrayList;

    public AnswerAdapter(Context answerCommentActivity, ArrayList<Comments> answerArrayList) {
        this.context = answerCommentActivity;
        this.answerArrayList = answerArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer, parent, false);
        return new MyViewHolder(view);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txtAnswerComment;
        private TextView txtAnswerHide;
        private TextView txtAnswerReport;
        private TextView txtCommentName;
        private TextView txtCommentText;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtAnswerComment = (TextView) itemView.findViewById(R.id.txtAnswerComment);
            txtAnswerHide = (TextView) itemView.findViewById(R.id.txtAnswerHide);
            txtAnswerReport = (TextView) itemView.findViewById(R.id.txtAnswerReport);
            txtCommentName = (TextView) itemView.findViewById(R.id.txtCommentName);
            txtCommentText = (TextView) itemView.findViewById(R.id.txtCommentText);

        }
    }

    @Override
    public int getItemCount() {
        return answerArrayList.size();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtCommentName.setText(answerArrayList.get(position).getName());
        holder.txtCommentText.setText(answerArrayList.get(position).getMessage());


        holder.txtAnswerComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CommentsActivity.class));
            }
        });
        holder.txtAnswerHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.txtAnswerReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
