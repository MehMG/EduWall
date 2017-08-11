package com.eduwall.Student.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.Interface.GetCourseResponse;
import com.eduwall.PrivateUnit.Model.Course;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Student.Activity.MyInstituteActivity;
import com.eduwall.Student.Fragment.SelectSubModuleFragment;
import com.eduwall.Student.Models.JoinedInstitute;
import com.eduwall.Student.Activity.ShowAllPostActivity;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by codesture on 8/6/17.
 */
public class JoinedAdapter extends RecyclerView.Adapter<JoinedAdapter.MyViewHolder> {
    Context context;
    ArrayList<JoinedInstitute> joinedInstitutes;
    MyInstituteActivity myInstituteActivity;
    BaseActivity baseActivity;

    String instituteID;
    String courseID;
    ArrayList<String> courseNameArray = new ArrayList<>();
    ArrayList<String> courseIdArray = new ArrayList<>();


    public JoinedAdapter(Context getActivity, ArrayList<JoinedInstitute> joinedInstitutes) {
        this.context = getActivity;
        this.joinedInstitutes = joinedInstitutes;
        myInstituteActivity = (MyInstituteActivity) context;
        baseActivity = (BaseActivity) context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_joined_institute, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtJoinedInsName;
        Spinner spinnerSelectCourse;
        Button btnEnterClass;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtJoinedInsName = (TextView) itemView.findViewById(R.id.txtJoinedInsName);
            spinnerSelectCourse = (Spinner) itemView.findViewById(R.id.spinnerSelectCourse);
            btnEnterClass = (Button) itemView.findViewById(R.id.btnEnterClass);
        }

    }


    @Override
    public int getItemCount() {
        return joinedInstitutes.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtJoinedInsName.setText(joinedInstitutes.get(position).getName());
        instituteID = joinedInstitutes.get(position).getInstituteID();

        Log.e("INSIDADAPTER", instituteID);

        baseActivity.getCommonAPI.getCource(instituteID, new GetCourseResponse() {
            @Override
            public void getCourse(ArrayList<String> courseArray1, ArrayList<String> courseArray2) throws JSONException {
                courseIdArray = courseArray1;
                courseNameArray = courseArray2;

                ArrayList arrayList = new ArrayList();

                for (int i = 0; i < courseNameArray.size(); i++) {

                    if (joinedInstitutes.get(position).getCourse().equalsIgnoreCase(courseIdArray.get(i))) {
                        arrayList.add(courseNameArray.get(position));
                        break;
                    }
                }
                ArrayAdapter courseAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, arrayList);
                courseAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                holder.spinnerSelectCourse.setAdapter(courseAdapter);
            }

        });

        holder.spinnerSelectCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                courseID = courseIdArray.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.btnEnterClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseActivity.addFragment(new SelectSubModuleFragment(joinedInstitutes.get(position).getInstituteID(), courseID), R.id.lnvFrame);

                //context.startActivity(new Intent(context, ShowAllPostActivity.class));
            }
        });

    }

}
