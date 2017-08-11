package com.eduwall.Student.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.InstituteInfoActivity;
import com.eduwall.Student.Activity.MyProfileActivity;
import com.eduwall.Student.Models.Institute;

import java.util.ArrayList;

/**
 * Created by codesture on 7/6/17.
 */
public class InstituteAdapter extends RecyclerView.Adapter<InstituteAdapter.MyViewHolder> {

    Context context;
    ArrayList<Institute> instituteArrayList;
    BaseActivity baseActivity;

    public InstituteAdapter(Context myProfileActivity, ArrayList<Institute> instituteArrayList) {
        this.context = myProfileActivity;
        this.instituteArrayList = instituteArrayList;
        baseActivity = (BaseActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_institute, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtInstituteNo;
        Button btnMoreInfo;
        TextView txtInstituteName;
        TextView txtProgram;
        TextView txtIndexNo;
        TextView txtCourse;
        TextView txtSubject;
        TextView txtModule;
        LinearLayout lnvStudent;
        LinearLayout lnvOther;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtInstituteNo = (TextView) itemView.findViewById(R.id.txtInstituteNo);
            btnMoreInfo = (Button) itemView.findViewById(R.id.btnMoreInfo);
            txtInstituteName = (TextView) itemView.findViewById(R.id.txtInstituteName);
            txtProgram = (TextView) itemView.findViewById(R.id.txtProgram);
            txtIndexNo = (TextView) itemView.findViewById(R.id.txtIndexNo);
            txtCourse = (TextView) itemView.findViewById(R.id.txtCourse);
            txtSubject = (TextView) itemView.findViewById(R.id.txtSubject);
            txtModule = (TextView) itemView.findViewById(R.id.txtModule);
            lnvStudent = (LinearLayout) itemView.findViewById(R.id.lnvStudent);
            lnvOther = (LinearLayout) itemView.findViewById(R.id.lnvOther);
        }
    }

    @Override
    public int getItemCount() {
        return instituteArrayList.size();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtInstituteNo.setText(instituteArrayList.get(position).getNumber());
        holder.txtInstituteName.setText(instituteArrayList.get(position).getName());

        if (baseActivity.preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
            holder.lnvStudent.setVisibility(View.VISIBLE);
            holder.txtProgram.setText(instituteArrayList.get(position).getCourse());
            holder.txtIndexNo.setText(instituteArrayList.get(position).getIndexno());
        } else {
            holder.lnvOther.setVisibility(View.VISIBLE);
            holder.txtCourse.setText(instituteArrayList.get(position).getCourse());
            holder.txtSubject.setText(instituteArrayList.get(position).getSubject());
            holder.txtModule.setText(instituteArrayList.get(position).getModule());
        }

        holder.btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InstituteInfoActivity.class);
                intent.putExtra("Institute Name", "Name");
                context.startActivity(intent);
            }
        });
    }
}
