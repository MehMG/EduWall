package com.eduwall.PrivateUnit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Activity.InboxActivity;

import java.util.ArrayList;

/**
 * Created by codesture on 2/6/17.
 */
public class PaymentStudentAdapter extends RecyclerView.Adapter<PaymentStudentAdapter.MyViewHolder> {

    Context context;
    int type;


    public PaymentStudentAdapter(int i) {
        this.type = i;
    }

    @Override
    public PaymentStudentAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_pay, parent, false);


        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PaymentStudentAdapter.MyViewHolder holder, int position) {

        if (this.type == 1) {
            holder.linear_index.setVisibility(View.GONE);
            holder.txt_other_fee.setText("Other Payment");
            holder.txt_crs_fee.setText("Course Payment");
        }


    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_crs_fee;
        TextView txt_other_fee;

        LinearLayout linear_index;

        public MyViewHolder(View itemView) {
            super(itemView);

            txt_crs_fee = (TextView) itemView.findViewById(R.id.txt_crs_fee);
            txt_other_fee = (TextView) itemView.findViewById(R.id.txt_other_fee);
            linear_index = (LinearLayout) itemView.findViewById(R.id.linear_index);

        }
    }
}
