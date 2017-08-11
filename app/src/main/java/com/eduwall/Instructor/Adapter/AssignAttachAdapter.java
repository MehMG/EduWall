package com.eduwall.Instructor.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduwall.Instructor.Activity.InsAssignmentTaskActivity;
import com.eduwall.Instructor.Models.AssignAttach;
import com.eduwall.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by codesture on 13/6/17.
 */
public class AssignAttachAdapter extends RecyclerView.Adapter<AssignAttachAdapter.MyViewHolder> {

    Context context;
    ArrayList<AssignAttach> assignAttaches;


    public AssignAttachAdapter(Context insAssignmentTaskActivity, ArrayList<AssignAttach> assignAttachArrayList) {
        this.context = insAssignmentTaskActivity;
        this.assignAttaches = assignAttachArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_assign_attach, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtHeader;
        private TextView txtGradeLabel;
        private TextView txtGrade;
        private TextView txtMarks;
        private LinearLayout lnvGrade;
        private RelativeLayout rlvView;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);
            txtGradeLabel = (TextView) itemView.findViewById(R.id.txtGradeLabel);
            txtGrade = (TextView) itemView.findViewById(R.id.txtGrade);
            txtMarks = (TextView) itemView.findViewById(R.id.txtMarks);
            lnvGrade = (LinearLayout) itemView.findViewById(R.id.lnvGrade);
            rlvView = (RelativeLayout) itemView.findViewById(R.id.rlvView);

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position == 1 || position == 5) {
            holder.rlvView.setVisibility(View.VISIBLE);
            holder.lnvGrade.setVisibility(View.GONE);
        } else {
            holder.rlvView.setVisibility(View.GONE);
            holder.lnvGrade.setVisibility(View.VISIBLE);
        }
    }

}
