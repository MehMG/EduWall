package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Models.Comments;
import com.eduwall.Student.Activity.CommentsActivity;

import java.util.ArrayList;

/**
 * Created by codesture on 7/6/17.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Comments> commentsArrayList;

    public CommentsAdapter(Context commentsActivity, ArrayList<Comments> commentLists) {
        this.context = commentsActivity;
        this.commentsArrayList = commentLists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comments, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtComment;
        TextView txtDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtComment = (TextView) itemView.findViewById(R.id.txtComment);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        }
    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtName.setText(commentsArrayList.get(position).getName());
        holder.txtComment.setText(commentsArrayList.get(position).getMessage());
        holder.txtDate.setText(commentsArrayList.get(position).getDate());
    }
}
