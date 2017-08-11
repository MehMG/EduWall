package com.eduwall.Session.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.R;

/**
 * Created by codesture on 27/6/17.
 */
public class BoxActivity extends BaseActivity {


    private ImageView ivClosePopUp;
    private TextView txtMesssage;
    String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);

        message = getIntent().getStringExtra("message");
        initComponent();
        initData();
        initClickListener();

    }

    @Override
    public void initComponent() {
        ivClosePopUp = (ImageView) findViewById(R.id.ivClosePopUp);
        txtMesssage = (TextView) findViewById(R.id.txtMesssage);
    }

    @Override
    public void initData() {
        txtMesssage.setText(message);
    }

    @Override
    public void initClickListener() {
        ivClosePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
