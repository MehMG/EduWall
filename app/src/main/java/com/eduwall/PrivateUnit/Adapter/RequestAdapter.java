package com.eduwall.PrivateUnit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.PrivateUnit.Model.Requests;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PC10 on 10-Jul-17.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {


    String type;
    Context context;
    ArrayList<Requests> requestsArrayList;
    BaseActivity baseActivity;
    String strMethod;

    public RequestAdapter(Context mContext, ArrayList<Requests> requestArrayList, String type) {

        this.context = mContext;
        this.requestsArrayList = requestArrayList;
        this.type = type;
        baseActivity = (BaseActivity) context;

        if (type.equalsIgnoreCase("1")) {
            strMethod = Constant.accept;
        } else {
            strMethod = Constant.Acceptinstructor;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_request, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txtReqStudentName;
        private TextView txtReqProgram;
        private TextView txtReqIndexNo;
        private ImageView ivReject;
        private ImageView ivAccept;

        private TextView txtReqInsName;
        private TextView txtReqInsCourse;
        private TextView txtReqInsSubject;
        private TextView txtReqInsModule;

        LinearLayout lnvReqStudent;
        LinearLayout lnvReqInstructor;


        public MyViewHolder(View itemView) {

            super(itemView);

            lnvReqStudent = (LinearLayout) itemView.findViewById(R.id.lnvReqStudent);
            lnvReqInstructor = (LinearLayout) itemView.findViewById(R.id.lnvReqInstructor);

            txtReqStudentName = (TextView) itemView.findViewById(R.id.txtReqStudentName);
            txtReqProgram = (TextView) itemView.findViewById(R.id.txtReqProgram);
            txtReqIndexNo = (TextView) itemView.findViewById(R.id.txtReqIndexNo);

            txtReqInsName = (TextView) itemView.findViewById(R.id.txtReqInsName);
            txtReqInsCourse = (TextView) itemView.findViewById(R.id.txtReqInsCourse);
            txtReqInsSubject = (TextView) itemView.findViewById(R.id.txtReqInsSubject);
            txtReqInsModule = (TextView) itemView.findViewById(R.id.txtReqInsModule);

            ivReject = (ImageView) itemView.findViewById(R.id.ivReject);
            ivAccept = (ImageView) itemView.findViewById(R.id.ivAccept);
        }
    }


    @Override
    public int getItemCount() {
        return requestsArrayList.size();
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (type.equalsIgnoreCase("1")) {

            holder.lnvReqStudent.setVisibility(View.VISIBLE);
            holder.lnvReqInstructor.setVisibility(View.GONE);

            holder.txtReqStudentName.setText(requestsArrayList.get(position).getName());
            holder.txtReqProgram.setText(requestsArrayList.get(position).getProgram());
            holder.txtReqIndexNo.setText(requestsArrayList.get(position).getIndexno());

        } else {
            holder.lnvReqInstructor.setVisibility(View.VISIBLE);
            holder.lnvReqStudent.setVisibility(View.GONE);

            holder.txtReqInsName.setText(requestsArrayList.get(position).getName());
            holder.txtReqInsCourse.setText(requestsArrayList.get(position).getCourse());
            holder.txtReqInsSubject.setText(requestsArrayList.get(position).getSubject());
            holder.txtReqInsModule.setText(requestsArrayList.get(position).getModule());
        }


        holder.ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(position, Constant.joined);

            }
        });

        holder.ivReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(position, Constant.declined);
            }
        });
    }

    public void setResult(final int position, String type) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", requestsArrayList.get(position).getRequestId());
        map.put("status", type);

        baseActivity.callAPiActivity.doPost((Activity) context, map, strMethod, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                requestsArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, requestsArrayList.size());

            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });
    }
}
