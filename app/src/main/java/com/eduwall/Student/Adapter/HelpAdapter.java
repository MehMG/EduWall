package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Models.Help;

import java.util.ArrayList;

/**
 * Created by codesture on 23/6/17.
 */
public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.MyViewHolder> {
    Context context;
    ArrayList<Help> helpArrayList;

    public HelpAdapter(Context mContext, ArrayList<Help> helpArrayList) {

        this.context = mContext;
        this.helpArrayList = helpArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_help, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return 5;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivArrow;
        private LinearLayout lnvQuestion;
        private TextView txtQuestion;
        private LinearLayout lnvHelp;
        private TextView txtAnswer;

        public MyViewHolder(View itemView) {
            super(itemView);

            lnvQuestion = (LinearLayout) itemView.findViewById(R.id.lnvQuestion);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            lnvHelp = (LinearLayout) itemView.findViewById(R.id.lnvHelp);
            txtAnswer = (TextView) itemView.findViewById(R.id.txtAnswer);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);

        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.lnvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.lnvHelp.getVisibility() == View.GONE) {
                    holder.lnvHelp.setVisibility(View.VISIBLE);
                    holder.ivArrow.setImageResource(R.drawable.up_arrow1);
                } else {
                    holder.lnvHelp.setVisibility(View.GONE);
                    holder.ivArrow.setImageResource(R.drawable.down_arrow1);
                }

            }

        });
    }

}
