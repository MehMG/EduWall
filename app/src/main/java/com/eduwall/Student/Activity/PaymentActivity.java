package com.eduwall.Student.Activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

import java.util.ArrayList;

public class PaymentActivity extends BaseActivity {
    Spinner spin_institute, spin_instructor, spin_p_unit;
    ArrayList<String> courses = new ArrayList<>();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mContext = PaymentActivity.this;
        initActionBar("Payments");
        initBackButton();
        initComponent();
        initData();
        initSpinner();
        initClickListener();
    }


    @Override
    public void initComponent() {
        spin_institute = (Spinner) findViewById(R.id.spin_institute);
        spin_p_unit = (Spinner) findViewById(R.id.spin_p_unit);
        spin_instructor = (Spinner) findViewById(R.id.spin_instructor);


    }

    @Override
    public void initData() {


    }

    private void initSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PaymentActivity.this, R.array.spinnerInstitute, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spin_institute.setSelection(0);
        spin_institute.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(PaymentActivity.this, R.array.spinnerPUnit, R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spin_p_unit.setSelection(0);
        spin_p_unit.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(PaymentActivity.this, R.array.spinnerInstructor, R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spin_instructor.setSelection(0);
        spin_instructor.setAdapter(adapter2);
    }


    @Override
    public void initClickListener() {

    }

}
