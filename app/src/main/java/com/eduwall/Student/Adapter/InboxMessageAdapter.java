package com.eduwall.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Model.MessageData;
import com.eduwall.Student.Activity.InboxActivity;
import com.eduwall.Student.Activity.NewEmailActivity;
import com.eduwall.Student.Activity.ViewMessageActivity;
import com.eduwall.Student.Models.InboxMessege;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture on 2/6/17.
 */
public class InboxMessageAdapter extends RecyclerView.Adapter<InboxMessageAdapter.MyViewHolder> {

    ArrayList<InboxMessege> inboxMesseges;
    Context context;
    BaseActivity baseActivity;
    String type;

    public InboxMessageAdapter(Context inboxActivity, ArrayList<InboxMessege> inboxArrayList, String type) {
        this.inboxMesseges = inboxArrayList;
        this.context = inboxActivity;
        this.type = type;
        baseActivity = (BaseActivity) context;
    }

    @Override
    public InboxMessageAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_inbox, parent, false);
        final SwipeLayout item = (SwipeLayout) view.findViewById(R.id.swipe_item);

        item.setRightSwipeEnabled(false);
        item.setShowMode(SwipeLayout.ShowMode.PullOut);
        item.addDrag(SwipeLayout.DragEdge.Left, item.findViewById(R.id.row_inbox_left));
        return new MyViewHolder(view);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivprofile;
        TextView txtName;
        TextView txtTime;
        TextView txtTitle;
        TextView txtMessage;
        ImageView ivDelete;
        ImageView ivReply;
        LinearLayout lnvMessageView;

        public MyViewHolder(final View itemView) {
            super(itemView);

            ivprofile = (ImageView) itemView.findViewById(R.id.ivprofile);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
            lnvMessageView = (LinearLayout) itemView.findViewById(R.id.lnvMessageView);

            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
            ivReply = (ImageView) itemView.findViewById(R.id.ivReply);


        }
    }

    @Override
    public int getItemCount() {
        return inboxMesseges.size();
    }


    @Override
    public void onBindViewHolder(final InboxMessageAdapter.MyViewHolder holder, final int position) {

        if (type.equalsIgnoreCase(Constant.sent)) {
            holder.ivReply.setImageResource(R.drawable.icon_forward);
        } else {
            holder.ivReply.setImageResource(R.drawable.icon_replay);
        }

        final InboxMessege messege = inboxMesseges.get(position);

        holder.txtName.setText(inboxMesseges.get(position).getSender_name());
        holder.txtTitle.setText(inboxMesseges.get(position).getTitle());
        holder.txtMessage.setText(inboxMesseges.get(position).getDescription());
        holder.txtTime.setText(inboxMesseges.get(position).getSend_at());
        Picasso.with(context).load(inboxMesseges.get(position).getSender_image()).resize(250, 250).into(holder.ivprofile);

        holder.lnvMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageData data = new MessageData();
                data.setName(inboxMesseges.get(position).getSender_name());
                data.setEmail(inboxMesseges.get(position).getSender_email());
                data.setProfile(inboxMesseges.get(position).getSender_image());
                data.setTitle(inboxMesseges.get(position).getTitle());
                data.setDescription(inboxMesseges.get(position).getDescription());
                data.setTime(inboxMesseges.get(position).getSend_at());
                data.setTheradID(inboxMesseges.get(position).getThread_id());
                data.setUserType(type);
                data.setAttachment(inboxMesseges.get(position).getAttachArrayList());

                baseActivity.preference.setUserMessageData(data);

                Log.e("MessageDataaa", inboxMesseges.get(position).getAttachArrayList().toString());

                context.startActivity(new Intent(context, ViewMessageActivity.class));
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/mailDelete_post
                baseActivity.preference.setMessageData(messege);

                HashMap<String, String> map = new HashMap<>();
                map.put("user_type", type);
                map.put("id", messege.getThread_id());

                baseActivity.callAPiActivity.doPost((Activity) context, map, Constant.MailDelete_post, new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException {

                        Log.e("Result", result.toString());
                        inboxMesseges.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, inboxMesseges.size());
                        baseActivity.appDialogs.setSuccessToast("Removed");
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                        baseActivity.appDialogs.setErrorToast(messgae);
                    }
                });
            }
        });

        holder.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase(Constant.sent)) {
                    Intent intent = new Intent(context, NewEmailActivity.class);

                    InboxMessege inboxMessege = new InboxMessege();
                    inboxMessege.setTitle(inboxMesseges.get(position).getTitle());
                    inboxMessege.setDescription(inboxMesseges.get(position).getDescription());
                    inboxMessege.setAttachArrayList(inboxMesseges.get(position).getAttachArrayList());
                    baseActivity.preference.setMessageData(inboxMessege);

                    intent.putExtra("Parent", "Forward");
                    context.startActivity(intent);

                } else {
                    Intent intent = new Intent(context, NewEmailActivity.class);
                    intent.putExtra("Parent", "ViewMessage");
                    context.startActivity(intent);
                }
            }

        });

    }

}
