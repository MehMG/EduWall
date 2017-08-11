package com.eduwall.Interface;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.R;
import com.eduwall.Session.RecordAudio;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by PC10 on 09-Aug-17.
 */

public class RecordDialog {
    Context mContext;
    ProgressDialog pDialog;
    RecordAudio recordAudio;
    int count = 0;
    String filepath;

    public RecordDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void showRecordDialog(Context context, final RecordDialogueClickListner recordDialogueClickListner) {

        recordAudio = new RecordAudio(context);

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_record);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView ivDialogRecording;
        ImageView ivDialogClose;
        final TextView txtDialogMessage;
        final TextView txtDialogPath;
        Button btnDialogAttach;

        ivDialogClose = (ImageView) dialog.findViewById(R.id.ivDialogClose);
        ivDialogRecording = (ImageView) dialog.findViewById(R.id.ivDialogRecording);
        txtDialogMessage = (TextView) dialog.findViewById(R.id.txtDialogMessage);
        txtDialogPath = (TextView) dialog.findViewById(R.id.txtDialogPath);
        btnDialogAttach = (Button) dialog.findViewById(R.id.btnDialogAttach);

        ivDialogRecording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    recordAudio.startRecording();
                    txtDialogMessage.setText("Press button to Stop Recording!");
                    count++;
                } else {
                    filepath = recordAudio.stopRecording();
                    txtDialogMessage.setText("Press button to Start Recording!");
                    txtDialogPath.setText(filepath);
                    count = 0;
                }
            }
        });

        btnDialogAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordDialogueClickListner.onAttachClick(dialog, filepath);
            }
        });

        ivDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordDialogueClickListner.onCancelClick(dialog, filepath);
            }
        });

        dialog.show();
    }
}
