package com.eduwall.Instructor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduwall.Instructor.Models.SubmissionAttach;
import com.eduwall.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by codesture on 12/6/17.
 */
public class SubmissionAttachAdapter extends RecyclerView.Adapter<SubmissionAttachAdapter.MyViewHolder> {

    Context context;
    ArrayList<SubmissionAttach> submissionAttaches;

    public SubmissionAttachAdapter(Context viewSubmissionsActivity, ArrayList<SubmissionAttach> submissionAttachs) {
        this.context = viewSubmissionsActivity;
        this.submissionAttaches = submissionAttachs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_submission_attach, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txtAttachmentName;
        private CircleImageView ivAttachmentLogo;
        private TextView txtSubmissionAttachName;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtAttachmentName = (TextView) itemView.findViewById(R.id.txtAttachmentName);
            ivAttachmentLogo = (CircleImageView) itemView.findViewById(R.id.ivAttachmentLogo);
            txtSubmissionAttachName = (TextView) itemView.findViewById(R.id.txtSubmissionAttachName);

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

}
