package com.eduwall.Instructor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduwall.Instructor.Activity.InsStudentsActivity;
import com.eduwall.Instructor.Models.InstituteCoursesList;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Models.Institute;

import java.util.ArrayList;

/**
 * Created by codesture on 13/6/17.
 */
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Institute> instituteCoursesListArrayList;
    BaseActivity baseActivity;

    public CoursesAdapter(Context insAllCourseActivity, ArrayList<Institute> instituteCoursesListArrayList) {

        this.context = insAllCourseActivity;
        this.instituteCoursesListArrayList = instituteCoursesListArrayList;
        baseActivity = (BaseActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_courses, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSubStuInstituteName;
        private TextView txtCourse;
        private TextView txtSubject;
        private TextView txtModule;
        private RelativeLayout rlvTotal;
        private TextView txtStudentNo;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtSubStuInstituteName = (TextView) itemView.findViewById(R.id.txtCoursesSubStuInstituteName);
            txtCourse = (TextView) itemView.findViewById(R.id.txtCoursesCourse);
            txtSubject = (TextView) itemView.findViewById(R.id.txtCoursesSubject);
            txtModule = (TextView) itemView.findViewById(R.id.txtCoursesModule);
            rlvTotal = (RelativeLayout) itemView.findViewById(R.id.rlvCoursesTotal);
            txtStudentNo = (TextView) itemView.findViewById(R.id.txtCoursesStudentNo);

        }
    }

    @Override
    public int getItemCount() {
        return instituteCoursesListArrayList.size();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (baseActivity.preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
            holder.txtSubStuInstituteName.setVisibility(View.VISIBLE);
            holder.txtSubStuInstituteName.setText(instituteCoursesListArrayList.get(position).getName());
        }
        holder.txtCourse.setText(instituteCoursesListArrayList.get(position).getCourse());
        holder.txtSubject.setText(instituteCoursesListArrayList.get(position).getSubject());
        holder.txtModule.setText(instituteCoursesListArrayList.get(position).getModule());

        try {
            holder.txtStudentNo.setText(instituteCoursesListArrayList.get(position).getCount());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Institute institute = instituteCoursesListArrayList.get(position);
                baseActivity.preference.setInstitute(institute);

                Intent intent = new Intent(context, InsStudentsActivity.class);
                intent.putExtra("Parent", Constant.Course);

                context.startActivity(intent);
            }
        });

        if (baseActivity.preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {

            holder.rlvTotal.setVisibility(View.GONE);

        }
    }


}
