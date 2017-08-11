package com.eduwall.Student.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ParallelExecutorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Activity.StartQuizActivity;
import com.eduwall.Student.Models.QuizList;

import java.util.ArrayList;

/**
 * Created by PC10 on 08-Aug-17.
 */

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.MyViewHolder> {
    Context context;
    ArrayList<QuizList> quizListArrayList;

    public QuizListAdapter(Context mContext, ArrayList<QuizList> quizListArrayList) {

        this.context = mContext;
        this.quizListArrayList = quizListArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_quiz_list, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtQuizListIndex;
        private TextView txtQuizListDesc;
        private Button btnQuizListPlay;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtQuizListIndex = (TextView) itemView.findViewById(R.id.txtQuizListIndex);
            txtQuizListDesc = (TextView) itemView.findViewById(R.id.txtQuizListDesc);
            btnQuizListPlay = (Button) itemView.findViewById(R.id.btnQuizListPlay);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.btnQuizListPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, StartQuizActivity.class));
            }
        });

    }
}
