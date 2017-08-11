package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Models.Tests;

import java.util.ArrayList;

/**
 * Created by PC10 on 08-Aug-17.
 */

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Tests> testsArrayList;

    public TestsAdapter(Context mContext, ArrayList<Tests> testArrayList) {
        this.context = mContext;
        this.testsArrayList = testArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_test, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTestIndex;
        private TextView txtTestQuestion;
        private TextView txtTestMarks;
        private EditText edtTestAnswer;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTestIndex = (TextView) itemView.findViewById(R.id.txtTestIndex);
            txtTestQuestion = (TextView) itemView.findViewById(R.id.txtTestQuestion);
            txtTestMarks = (TextView) itemView.findViewById(R.id.txtTestMarks);
            edtTestAnswer = (EditText) itemView.findViewById(R.id.edtTestAnswer);

        }
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }
}
