package com.eduwall.Instructor.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Activity.InsStudTaskActivity;
import com.eduwall.Instructor.Activity.InsStudentsActivity;
import com.eduwall.Instructor.Models.StudentList;
import com.eduwall.PrivateUnit.Adapter.RequestAdapter;
import com.eduwall.PrivateUnit.Model.Requests;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture on 12/6/17.
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentList> studentLists;
    Boolean clicked;
    String parent;
    BaseActivity baseActivity;

    public StudentListAdapter(Context insStudentsActivity, ArrayList<StudentList> studentLists, String parent) {
        this.context = insStudentsActivity;
        this.studentLists = studentLists;
        this.parent = parent;
        baseActivity = (BaseActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_list, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private PorterShapeImageView ivProfile;
        private TextView txtIndexNo;
        private TextView txtStudentName;
        private TextView txtGrade;
        private TextView txtMarks;
        private LinearLayout lnvParent;
        private Button btnSuspend;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivProfile = (PorterShapeImageView) itemView.findViewById(R.id.ivStudentListProfile);
            txtIndexNo = (TextView) itemView.findViewById(R.id.txtIndexNo);
            txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentName);
            txtGrade = (TextView) itemView.findViewById(R.id.txtGrade);
            lnvParent = (LinearLayout) itemView.findViewById(R.id.lnvParent);
            btnSuspend = (Button) itemView.findViewById(R.id.btnSuspend);
            txtMarks = (TextView) itemView.findViewById(R.id.txtMarks);

        }
    }

    @Override
    public int getItemCount() {
        return studentLists.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtStudentName.setText(studentLists.get(position).getName());
        holder.txtGrade.setText(studentLists.get(position).getGrade());
        holder.txtMarks.setText(studentLists.get(position).getMarks());
        holder.txtIndexNo.setText(studentLists.get(position).getIndexno());
        try {
            Picasso.with(context).load(studentLists.get(position).getImage()).into(holder.ivProfile);
        } catch (Exception e) {
        }

        if (parent.equalsIgnoreCase(Constant.Home)) {
            holder.btnSuspend.setVisibility(View.GONE);
            holder.lnvParent.setBackgroundColor(context.getResources().getColor(R.color.white));

        } else if (parent.equalsIgnoreCase(Constant.CourseList)) {
            holder.btnSuspend.setVisibility(View.VISIBLE);
            holder.lnvParent.setBackgroundColor(context.getResources().getColor(R.color.white));

            if (studentLists.get(position).getStatus().equalsIgnoreCase(Constant.joined)) {
                Log.e("SUSPEND", studentLists.get(position).getStatus());
                holder.btnSuspend.setText("Suspend");
                holder.btnSuspend.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red));

            } else {
                Log.e("SUSPEND2111", studentLists.get(position).getStatus());
                holder.btnSuspend.setText("Unsuspend");
                holder.btnSuspend.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red_unsuspend));
            }

        } else {
            if (!studentLists.get(position).getInstructor_id().equalsIgnoreCase("0")) { //student added by instructor  get Student by institute id
                holder.lnvParent.setBackgroundColor(context.getResources().getColor(R.color.green));
            } else {
                holder.lnvParent.setBackgroundColor(context.getResources().getColor(R.color.white));
            }

            holder.btnSuspend.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, InsStudTaskActivity.class));
                }
            });
        }

        holder.btnSuspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studentLists.get(position).getStatus().equalsIgnoreCase(Constant.suspended)) {
                    holder.btnSuspend.setText("Suspend");

                    holder.btnSuspend.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red));
                    studentLists.get(position).setStatus(Constant.unsuspended);
                    setSuspend(position, Constant.unsuspended);

                } else {
                    holder.btnSuspend.setText("Unsuspend");
                    holder.btnSuspend.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.btn_red_unsuspend));
                    studentLists.get(position).setStatus(Constant.suspended);
                    setSuspend(position, Constant.suspended);

                }
            }
        });

    }

    public void setSuspend(final int position, String type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", studentLists.get(position).getId());
        map.put("status", type);

        baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.accept, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }
}
