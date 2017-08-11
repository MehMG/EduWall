package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.Instructor.Activity.InsAddPostActivity;
import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Models.Questions;

import java.util.ArrayList;

/**
 * Created by PC10 on 04-Aug-17.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    Context context;
    ArrayList<Questions> questionsArrayList;
    String type;

    public QuestionAdapter(Context mContext, ArrayList<Questions> questionsArrayList, String type) {
        this.context = mContext;
        this.questionsArrayList = questionsArrayList;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_questions_list, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtQuestionNumber;
        private TextView txtAdapterQuestion;
        private ImageView ivQuestionRemove;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtQuestionNumber = (TextView) itemView.findViewById(R.id.txtQuestionNumber);
            txtAdapterQuestion = (TextView) itemView.findViewById(R.id.txtAdapterQuestion);
            ivQuestionRemove = (ImageView) itemView.findViewById(R.id.ivQuestionRemove);
        }
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtAdapterQuestion.setText(questionsArrayList.get(position).getQuestion());
        holder.txtQuestionNumber.setText(String.valueOf(position + 1));

        if (type.equalsIgnoreCase(Constant.Instructor)) {
            holder.ivQuestionRemove.setVisibility(View.VISIBLE);
        } else {
            holder.ivQuestionRemove.setVisibility(View.GONE);
        }

        holder.ivQuestionRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                questionsArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(questionsArrayList.size(), questionsArrayList.size());
                notifyDataSetChanged();
            }
        });

    }
}
