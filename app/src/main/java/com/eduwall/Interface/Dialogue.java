package com.eduwall.Interface;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.R;

/**
 * Created by codesture on 14/6/17.
 */
public class Dialogue {

    private Context mContext;


    public Dialogue(Context mContext) {
        this.mContext = mContext;
    }


    public void setAlertDialogue(String title, String positive, String negative, final AlertDialogueClickListner alertDialogueClickListner) {


        TextView txtTitle;
        Button txtPositive;
        Button txtNagative;

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_confirm);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        txtTitle = (TextView) dialog.findViewById(R.id.txtDialogTitle);
        txtPositive = (Button) dialog.findViewById(R.id.txtPositive);
        txtNagative = (Button) dialog.findViewById(R.id.txtNagative);

        txtTitle.setText(title);
        txtPositive.setText(positive);
        txtNagative.setText(negative);


        txtPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialogueClickListner.onPositive(dialog);

            }
        });


        txtNagative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialogueClickListner.onNegative(dialog);

            }
        });

        dialog.show();
    }

}
