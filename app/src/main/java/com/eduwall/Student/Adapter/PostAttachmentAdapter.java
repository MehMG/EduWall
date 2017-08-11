package com.eduwall.Student.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.media.TimedText;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.eduwall.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PC10 on 08-Jul-17.
 */

public class PostAttachmentAdapter extends RecyclerView.Adapter<PostAttachmentAdapter.MyViewHolder> {

    Context context;
    ArrayList<GetAttach> getAttachArrayList;
    String attachmentpath;


    public PostAttachmentAdapter(Context context, ArrayList<GetAttach> getAttachArrayList) {
        this.context = context;
        this.getAttachArrayList = getAttachArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post_attachment, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        VideoView videoView;
        TextView txtContainer;
        ImageView ivImageVIew;
        LinearLayout lnvAttachment;
        RelativeLayout rlvVideoView;
        ImageView ivVideoViewPlay;

        public MyViewHolder(View itemView) {
            super(itemView);

            videoView = (VideoView) itemView.findViewById(R.id.videoView);
            txtContainer = (TextView) itemView.findViewById(R.id.txtContainer);
            ivImageVIew = (ImageView) itemView.findViewById(R.id.ivImageView);
            ivVideoViewPlay = (ImageView) itemView.findViewById(R.id.ivVideoViewPlay);
            lnvAttachment = (LinearLayout) itemView.findViewById(R.id.lnvAttachment);
            rlvVideoView = (RelativeLayout) itemView.findViewById(R.id.rlvVideoView);
        }
    }

    @Override
    public int getItemCount() {
        return getAttachArrayList.size();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final String attchmentType = getAttachArrayList.get(position).getExtension();
        attachmentpath = getAttachArrayList.get(position).getFilename();

        if (attchmentType.equalsIgnoreCase("pdf") || attchmentType.equalsIgnoreCase("doc") || attchmentType.equalsIgnoreCase("ppt") || attchmentType.equalsIgnoreCase("docx") || attchmentType.equalsIgnoreCase("xls") || attchmentType.equalsIgnoreCase("txt") || attchmentType.equalsIgnoreCase("mp3") || attchmentType.equalsIgnoreCase("wav") || attchmentType.equalsIgnoreCase("wv") || attchmentType.equalsIgnoreCase("ogg") || attchmentType.equalsIgnoreCase("sln")) {
            setTextView(holder, attachmentpath);
        } else if (attchmentType.equalsIgnoreCase("jpg") || attchmentType.equalsIgnoreCase("jpeg") || attchmentType.equalsIgnoreCase("gif") || attchmentType.equalsIgnoreCase("png") || attchmentType.equalsIgnoreCase("bmp")) {
            setImageView(holder, attachmentpath);
        } else if (attchmentType.equalsIgnoreCase("mp4") || attchmentType.equalsIgnoreCase("3gp") || attchmentType.equalsIgnoreCase("mpeg") || attchmentType.equalsIgnoreCase("mkv")) {
            setVideoVIew(holder, attachmentpath);
        } else {
            holder.ivImageVIew.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.GONE);
            holder.lnvAttachment.setVisibility(View.GONE);
        }

        holder.ivImageVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.iv_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;

                ImageView ivFullImage = (ImageView) dialog.findViewById(R.id.ivFullImage);
                Picasso.with(context).load(getAttachArrayList.get(position).getFilename()).into(ivFullImage);

                ivFullImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        holder.lnvAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String attchmentType = getAttachArrayList.get(position).getExtension();

                Uri myUri = Uri.parse(getAttachArrayList.get(position).getFilename());

                if (attchmentType.equalsIgnoreCase("pdf") || attchmentType.equalsIgnoreCase("doc") || attchmentType.equalsIgnoreCase("ppt") || attchmentType.equalsIgnoreCase("docx") || attchmentType.equalsIgnoreCase("xls") || attchmentType.equalsIgnoreCase("txt")) {
                    Log.e("TYPE", "DOC");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setDataAndType(myUri, "*/*");
                    context.startActivity(intent1);

                } else if (attchmentType.equalsIgnoreCase("mp3") || attchmentType.equalsIgnoreCase("wav") || attchmentType.equalsIgnoreCase("wv") || attchmentType.equalsIgnoreCase("ogg") || attchmentType.equalsIgnoreCase("sln")) {
                    Log.e("TYPE", "MUSIC");
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(myUri, "audio/*");
                    context.startActivity(intent);
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setVideoVIew(final MyViewHolder holder, String attachmentpath) {
        holder.ivImageVIew.setVisibility(View.GONE);
        holder.rlvVideoView.setVisibility(View.VISIBLE);
        holder.lnvAttachment.setVisibility(View.GONE);

        try {
            android.widget.MediaController mediacontroller = new android.widget.MediaController(context);
            mediacontroller.setAnchorView(holder.videoView);
            Log.e("PATH ATTHACH", attachmentpath);
            Uri video = Uri.parse(attachmentpath);
            holder.videoView.setVideoURI(video);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.rlvVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ivVideoViewPlay.setVisibility(View.GONE);
                holder.videoView.setVisibility(View.VISIBLE);
                try {

                    holder.videoView.setClickable(true);
                    holder.videoView.start();

                    /*holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                        }
                    });*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.ivVideoViewPlay.setVisibility(View.VISIBLE);
                holder.videoView.setVisibility(View.GONE);
            }
        });

    }

    public void setImageView(MyViewHolder holder, String attachmentpath) {
        holder.ivImageVIew.setVisibility(View.VISIBLE);
        Picasso.with(context).load(attachmentpath).into(holder.ivImageVIew);
        holder.videoView.setVisibility(View.GONE);
        holder.lnvAttachment.setVisibility(View.GONE);
    }

    public void setTextView(MyViewHolder holder, String attachmentpath) {
        holder.ivImageVIew.setVisibility(View.GONE);
        holder.videoView.setVisibility(View.GONE);
        holder.lnvAttachment.setVisibility(View.VISIBLE);

        String array[] = attachmentpath.split("\\^");
        holder.txtContainer.setText(array[array.length - 1]);
    }

}
