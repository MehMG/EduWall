package com.eduwall.Instructor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eduwall.Instructor.Activity.InsAssignGradeActivity;
import com.eduwall.Instructor.Models.Submission;
import com.eduwall.R;

import java.util.ArrayList;

/**
 * Created by codesture on 10/6/17.
 */
public class SubmissionAdapter extends RecyclerView.Adapter<SubmissionAdapter.MyViewHolder> {

    Context context;
    ArrayList<Submission> submissionArrayList;

    public SubmissionAdapter(Context instructorSubmissionActivity, ArrayList<Submission> submissionArrayList) {

        this.context = instructorSubmissionActivity;
        this.submissionArrayList = submissionArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_submission, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSubStudentName;
        private TextView txtSubStuInstituteName;
        private TextView txtSubStuCourse;
        private TextView txtSubStuSubject;
        private TextView txtSubStuModule;
        private TextView txtSubStuType;
        private Button btnSubStuView;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtSubStudentName = (TextView) itemView.findViewById(R.id.txtSubStudentName);
            txtSubStuInstituteName = (TextView) itemView.findViewById(R.id.txtSubStuInstituteName);
            txtSubStuCourse = (TextView) itemView.findViewById(R.id.txtSubStuCourse);
            txtSubStuSubject = (TextView) itemView.findViewById(R.id.txtSubStuSubject);
            txtSubStuModule = (TextView) itemView.findViewById(R.id.txtSubStuModule);
            txtSubStuType = (TextView) itemView.findViewById(R.id.txtSubStuType);
            btnSubStuView = (Button) itemView.findViewById(R.id.btnSubStuView);


        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.btnSubStuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, InsAssignGradeActivity.class));
            }
        });

//        holder.txtSubStudentName.setText("");
//        holder.txtSubStuInstituteName.setText("");
//        holder.txtSubStuCourse.setText("");
//        holder.txtSubStuSubject.setText("");
//        holder.txtSubStuModule.setText("");
//        holder.txtSubStuType.setText("");
    }
}
