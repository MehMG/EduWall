package com.eduwall.PrivateUnit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.PrivateUnit.Model.Fees;
import com.eduwall.R;

import java.util.ArrayList;

/**
 * Created by codesture on 2/6/17.
 */
public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.MyViewHolder> {

    ArrayList<Fees> feesArrayList;
    Context context;

      public FeesAdapter(Context feesActivity, ArrayList<Fees> feesArrayList) {
        this.feesArrayList = feesArrayList;
        this.context = feesActivity;
    }

    @Override
    public FeesAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_fees_pu, parent, false);


        final MyViewHolder holder = new MyViewHolder(view);


        return holder;
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

    @Override
    public int getItemCount() {
        return 10;
    }



    @Override
    public void onBindViewHolder(FeesAdapter.MyViewHolder holder, int position) {

    }


}
