package com.eduwall.Student.Adapter;

import android.content.Context;
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
import com.eduwall.Student.Fragment.CourseModuleFragment;
import com.eduwall.Student.Models.RequestedInstitute;

import java.util.ArrayList;

/**
 * Created by codesture18 on 6/2/2017.
 */
public class ReqInstituteAdapter extends RecyclerView.Adapter<ReqInstituteAdapter.MyViewHolder> {

    ArrayList<RequestedInstitute> requestedInstitutes;
    Context context;

    public ReqInstituteAdapter(Context activity, ArrayList<RequestedInstitute> requestedInstitutes) {
        this.requestedInstitutes = requestedInstitutes;
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_module, parent, false);

        return new MyViewHolder(itemView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lnvAddCourseModules;
        Button btnAddCourseModule;
        TextView txtInstituteName;

        public MyViewHolder(View view) {
            super(view);

            lnvAddCourseModules = (LinearLayout) view.findViewById(R.id.lnvAddCourseModules);
            btnAddCourseModule = (Button) view.findViewById(R.id.btnAddCourseModule);
            txtInstituteName = (TextView) itemView.findViewById(R.id.txtInstituteName);

        }
    }

    @Override
    public int getItemCount() {
        return requestedInstitutes.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtInstituteName.setText(requestedInstitutes.get(position).getName());

        if (requestedInstitutes.get(position).getStatus().equalsIgnoreCase(Constant.requested)) {
            holder.btnAddCourseModule.setEnabled(false);
            holder.btnAddCourseModule.setText("Request already sent");
        } else {
            holder.btnAddCourseModule.setEnabled(true);

            holder.btnAddCourseModule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BaseActivity baseActivity = (BaseActivity) context;

                    RequestedInstitute instituteData = new RequestedInstitute();
                    instituteData.setId(requestedInstitutes.get(position).getId());
                    instituteData.setName(requestedInstitutes.get(position).getName());
                    instituteData.setInstituteID(requestedInstitutes.get(position).getInstituteID());
                    baseActivity.preference.setInstituteData(instituteData);

                    baseActivity.addFragment(new CourseModuleFragment(), R.id.lnvFrameRequested);

                }
            });
        }
    }
}