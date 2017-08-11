package com.eduwall.Instructor.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

public class InsPaymentActivity extends BaseActivity {

    private Spinner spinnerInsType;
    private EditText edtUsageFee;
    private EditText edtPayment1;
    private EditText edtPayment2;
    private EditText edtFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_payment);

        initComponent();
        initData();
        initSpinner();
        initClickListener();
        initActionBar("Payment");
        initBackButton();

    }


    @Override
    public void initComponent() {


        spinnerInsType = (Spinner) findViewById(R.id.spinnerInsType);
        edtUsageFee = (EditText) findViewById(R.id.edtUsageFee);
        edtPayment1 = (EditText) findViewById(R.id.edtPayment1);
        edtPayment2 = (EditText) findViewById(R.id.edtPayment2);
        edtFees = (EditText) findViewById(R.id.edtFees);


    }

    @Override
    public void initData() {


    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InsPaymentActivity.this, R.array.spinnerType, R.layout.custom_gold_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerInsType.setSelection(0);
        spinnerInsType.setAdapter(adapter);
    }


    @Override
    public void initClickListener() {

    }

}
