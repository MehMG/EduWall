package com.eduwall.PrivateUnit.Activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.PrivateUnit.Adapter.FeesAdapter;
import com.eduwall.PrivateUnit.Model.Fees;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.ArrayList;

public class FeesActivity extends BaseActivity {


    private CardView lnvPostData;
    private PorterShapeImageView ivProfile;
    private LinearLayout linearIndex;
    private TextView txtIndex;
    private TextView txtStudent;
    private TextView txtInsti;
    private TextView txtCrs;
    private TextView txtCrsFee;
    private Button btnCourseFee;
    private TextView txtOtherFee;
    private Button btnOtherFee;
    private RecyclerView recyclerFees;

    String parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        initComponent();
        parent = getIntent().getStringExtra("category");
        initData();
        initClickListener();

        if (parent.equalsIgnoreCase("INS")) {
            initActionBar("Payment");
        } else {
            initActionBar("Fees");
        }

        initBackButton();
    }

    @Override
    public void initComponent() {


        lnvPostData = (CardView) findViewById(R.id.lnvPostData);
        ivProfile = (PorterShapeImageView) findViewById(R.id.ivProfile);
        linearIndex = (LinearLayout) findViewById(R.id.linear_index);
        txtIndex = (TextView) findViewById(R.id.txt_index);
        txtStudent = (TextView) findViewById(R.id.txt_student);
        txtInsti = (TextView) findViewById(R.id.txt_insti);
        txtCrs = (TextView) findViewById(R.id.txt_crs);
        txtCrsFee = (TextView) findViewById(R.id.txt_crs_fee);
        btnCourseFee = (Button) findViewById(R.id.btn_Course_fee);
        txtOtherFee = (TextView) findViewById(R.id.txt_other_fee);
        btnOtherFee = (Button) findViewById(R.id.btn_Other_fee);
        recyclerFees = (RecyclerView) findViewById(R.id.recyclerFees);

    }

    @Override
    public void initData() {

        if (parent.equalsIgnoreCase("INS")) {

            linearIndex.setVisibility(View.GONE);
            txtCrsFee.setText("Course Payment");
            txtOtherFee.setText("Other Payment");

        } else {
            linearIndex.setVisibility(View.VISIBLE);
            txtCrsFee.setText("Course Fees");
            txtOtherFee.setText("Other Fees");
        }

    }

    @Override
    public void initClickListener() {

    }


}
