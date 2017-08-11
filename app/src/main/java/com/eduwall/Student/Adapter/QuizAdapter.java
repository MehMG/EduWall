package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


import com.eduwall.R;
import com.eduwall.Student.Models.Quiz;

import java.util.ArrayList;

/**
 * Created by PC10 on 08-Aug-17.
 */

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder> {
    Context context;
    ArrayList<Quiz> quizArrayList;

    public QuizAdapter(Context mContext, ArrayList<Quiz> quizArrayList) {
        this.context = mContext;
        this.quizArrayList = quizArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_quiz, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtQuizIndex;
        private TextView txtQuizQuestion;
        private RadioButton rbOption1;
        private RadioButton rbOption2;
        private RadioButton rbOption3;
        private RadioButton rbOption4;


        public MyViewHolder(View itemView) {
            super(itemView);
            txtQuizIndex = (TextView) itemView.findViewById(R.id.txtQuizIndex);
            txtQuizQuestion = (TextView) itemView.findViewById(R.id.txtQuizQuestion);
            rbOption1 = (RadioButton) itemView.findViewById(R.id.rbOption1);
            rbOption2 = (RadioButton) itemView.findViewById(R.id.rbOption2);
            rbOption3 = (RadioButton) itemView.findViewById(R.id.rbOption3);
            rbOption4 = (RadioButton) itemView.findViewById(R.id.rbOption4);


        }
    }
}