package com.eduwall.PrivateUnit.Adapter;

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
import com.eduwall.Instructor.Models.StudentList;
import com.eduwall.PrivateUnit.Activity.FeesActivity;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture on 12/6/17.
 */
public class PU_StudentListAdapter extends RecyclerView.Adapter<PU_StudentListAdapter.MyViewHolder> {

    Context context;
    ArrayList<StudentList> studentLists;
    String category;
    BaseActivity baseActivity;
    String strMethod;

    public PU_StudentListAdapter(Context c, ArrayList<StudentList> studentLists,
                                 String category) {
        this.context = c;
        this.studentLists = studentLists;
        this.category = category;
        baseActivity = (BaseActivity) context;

        if (category.equalsIgnoreCase(Constant.Student)) {
            strMethod = Constant.accept;
        } else {
            strMethod = Constant.Acceptinstructor;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_list_pu, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private PorterShapeImageView ivProfile;
        private TextView txtIndexNo;
        private TextView txtStudentName;
        private TextView txtInstitue;
        private TextView txtCourse;
        private Button btn_fees;
        private Button btn_remove;
        private Button btn_suspend;
        private LinearLayout lnvIndex;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivProfile = (PorterShapeImageView) itemView.findViewById(R.id.ivStudentProfile);
            txtIndexNo = (TextView) itemView.findViewById(R.id.txtStudentIndexNo);
            txtStudentName = (TextView) itemView.findViewById(R.id.txtStudentListName);
            txtInstitue = (TextView) itemView.findViewById(R.id.txtStudentInstitue);
            txtCourse = (TextView) itemView.findViewById(R.id.txtStudentCourse);
            btn_fees = (Button) itemView.findViewById(R.id.btn_fees);
            btn_suspend = (Button) itemView.findViewById(R.id.btn_suspend);
            btn_remove = (Button) itemView.findViewById(R.id.btn_remove);
            lnvIndex = (LinearLayout) itemView.findViewById(R.id.lnvStudentIndex);

        }
    }

    @Override
    public int getItemCount() {
        return studentLists.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (category.equalsIgnoreCase(Constant.Instructor)) {
            holder.btn_fees.setText("Payment");
            holder.lnvIndex.setVisibility(View.GONE);
            holder.txtCourse.setVisibility(View.GONE);
            Picasso.with(context).load(studentLists.get(position).getImage()).into(holder.ivProfile);
            holder.txtStudentName.setText(studentLists.get(position).getName());
            holder.txtInstitue.setText(studentLists.get(position).getInsName());

        } else {
            holder.lnvIndex.setVisibility(View.VISIBLE);
            Picasso.with(context).load(studentLists.get(position).getImage()).into(holder.ivProfile);
            holder.txtIndexNo.setText(studentLists.get(position).getIndexno());
            holder.txtStudentName.setText(studentLists.get(position).getName());
            holder.txtCourse.setText(studentLists.get(position).getCourse());
            holder.txtInstitue.setText(studentLists.get(position).getInsName());
        }

        if (studentLists.get(position).getStatus().equalsIgnoreCase(Constant.suspended)) {
            Log.e("TAGGGG", studentLists.get(position).getStatus());
            holder.btn_suspend.setText("UnSuspend");
            holder.btn_suspend.setBackground(context.getResources().getDrawable(R.drawable.btn_red_unsuspend));
        } else {
            Log.e("TAGGGG", studentLists.get(position).getStatus());
            holder.btn_suspend.setText("Suspend");
            holder.btn_suspend.setBackground(context.getResources().getDrawable(R.drawable.btn_red));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FeesActivity.class);
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });

        holder.btn_suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (studentLists.get(position).getStatus().equalsIgnoreCase(Constant.suspended)) {
                    setResult(position, Constant.unsuspended, holder);
                } else {
                    setResult(position, Constant.suspended, holder);
                }
            }
        });

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String method;
                if (category.equalsIgnoreCase(Constant.Instructor)) {
                    method = Constant.DeleteInstructorById;
                } else {
                    method = Constant.DeleteStudentById;
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", studentLists.get(position).getId());

                baseActivity.callAPiActivity.doPost((Activity) context, map, method, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                        studentLists.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, studentLists.size());
                        baseActivity.appDialogs.setSuccessToast("Removed");
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {

                    }
                });
            }
        });
    }


    public void setResult(final int position, String type, final MyViewHolder holder) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", studentLists.get(position).getId());
        map.put("status", type);

        baseActivity.callAPiActivity.doPost((Activity) context, map, strMethod, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                if (studentLists.get(position).getStatus().equalsIgnoreCase(Constant.suspended)) {
                    studentLists.get(position).setStatus(Constant.unsuspended);
                    holder.btn_suspend.setText("Suspend");
                    holder.btn_suspend.setBackground(context.getResources().getDrawable(R.drawable.btn_red));
                } else {
                    studentLists.get(position).setStatus(Constant.suspended);
                    holder.btn_suspend.setText("UnSuspend");
                    holder.btn_suspend.setBackground(context.getResources().getDrawable(R.drawable.btn_red_unsuspend));
                }
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }

}
