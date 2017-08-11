package com.eduwall.PrivateUnit.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.PrivateUnit.Fragment.PaymentInstructorFragment;
import com.eduwall.PrivateUnit.Fragment.PaymentStudentFragment;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PUPaymentActivity extends BaseActivity {
    private ViewPager viewPager;

    TextView txtHeaderSent;
    TextView txtHeaderReceived;

    ImageView ivLineSent;
    ImageView ivLineReceived;

    LinearLayout lnvHeaderReceived;

    @Override
    public void initComponent() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        txtHeaderSent = (TextView) findViewById(R.id.txtHeaderSent);
        txtHeaderReceived = (TextView) findViewById(R.id.txtHeaderReceived);
        ivLineSent = (ImageView) findViewById(R.id.ivLineSent);
        ivLineReceived = (ImageView) findViewById(R.id.ivLineReceived);
        lnvHeaderReceived = (LinearLayout) findViewById(R.id.lnvHeaderReceived);

    }

    @Override
    public void initData() {

        setupViewPager(viewPager);
        initViewPager();

    }


    @Override
    public void initClickListener() {

        txtHeaderSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        lnvHeaderReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pu);

        initActionBar("Payment");
        initBackButton();
        initComponent();
        initData();
        initClickListener();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaymentStudentFragment(), "Student");
        adapter.addFragment(new PaymentInstructorFragment(), "Instructor");
        viewPager.setAdapter(adapter);


    }


    private void initViewPager() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (viewPager.getCurrentItem() == 0) {
                    txtHeaderSent.setTextColor(getResources().getColor(R.color.colorPrimary));
                    ivLineSent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    txtHeaderReceived.setTextColor(getResources().getColor(R.color.white));
                    ivLineReceived.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    txtHeaderReceived.setTextColor(getResources().getColor(R.color.colorPrimary));
                    ivLineReceived.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    txtHeaderSent.setTextColor(getResources().getColor(R.color.white));
                    ivLineSent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
