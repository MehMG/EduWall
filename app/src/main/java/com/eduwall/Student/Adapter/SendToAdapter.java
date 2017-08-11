package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Student.Models.SendTo;

import java.util.ArrayList;

/**
 * Created by codesture on 16/6/17.
 */
public class SendToAdapter extends RecyclerView.Adapter<SendToAdapter.MyViewHolder> {

    Context context;
    ArrayList<SendTo> sendToArrayList;

    public SendToAdapter(Context context, ArrayList<SendTo> sendToArrayList) {
        this.context = context;
        this.sendToArrayList = sendToArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_send_to, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView txtSendTo;
        private ImageView ivCancel;


        public MyViewHolder(View itemView) {
            super(itemView);

            txtSendTo = (TextView) itemView.findViewById(R.id.txtSendTo);
            ivCancel = (ImageView) itemView.findViewById(R.id.ivCancel);

        }
    }

    @Override
    public int getItemCount() {
        return sendToArrayList.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final SendTo sendTo = sendToArrayList.get(position);

        holder.txtSendTo.setText(sendToArrayList.get(position).getEmail());

        holder.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, sendToArrayList.size(), sendTo);
            }
        });
    }


}
