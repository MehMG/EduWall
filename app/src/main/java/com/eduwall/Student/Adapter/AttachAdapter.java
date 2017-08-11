package com.eduwall.Student.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Models.Attach;

import java.util.ArrayList;

/**
 * Created by codesture on 16/6/17.
 */
public class AttachAdapter extends RecyclerView.Adapter<AttachAdapter.MyViewHolder> {
    Context context;
    ArrayList<Attach> attachArrayList;
    String type;

    public AttachAdapter(Context context, ArrayList<Attach> attachArrayList, String viewMessage) {
        this.context = context;
        this.attachArrayList = attachArrayList;
        this.type = viewMessage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attach, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtAttachName;
        private ImageView ivAttachCancel;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtAttachName = (TextView) itemView.findViewById(R.id.txtAttachName);
            ivAttachCancel = (ImageView) itemView.findViewById(R.id.ivAttachCancel);

        }
    }


    @Override
    public int getItemCount() {
        return attachArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txtAttachName.setText(attachArrayList.get(position).getName());

        if (type.equalsIgnoreCase(Constant.ViewMessage)) {

            String array[] = attachArrayList.get(position).getName().split("\\^");
            holder.txtAttachName.setText(array[array.length - 1]);

            holder.ivAttachCancel.setVisibility(View.GONE);
        } else {
            holder.ivAttachCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attachArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, attachArrayList.size());

                }
            });
        }
    }

}
