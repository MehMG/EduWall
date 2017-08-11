package com.eduwall.PrivateUnit.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.PrivateUnit.Adapter.RequestAdapter;
import com.eduwall.PrivateUnit.Fragment.InstructorNotificationFragment;
import com.eduwall.PrivateUnit.Fragment.StudentNotificationFragment;
import com.eduwall.PrivateUnit.Model.Requests;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.InboxActivity;
import com.eduwall.Student.Fragment.ReceivedMessageFragment;
import com.eduwall.Student.Fragment.SentMessageFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PURequestActivity extends BaseActivity {


    private ViewPager viewPager;

    ImageView ivLineStudent;
    ImageView ivLineInstructor;

    TextView txtHeaderStudent;
    TextView txtHeaderInstructor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initComponent();
        initActionBar("Request");
        initBackButton();
        initData();
        initClickListener();
    }

    @Override
    public void initComponent() {

        viewPager = (ViewPager) findViewById(R.id.viewpagerNotification);
        ivLineStudent = (ImageView) findViewById(R.id.ivLineStudent);
        ivLineInstructor = (ImageView) findViewById(R.id.ivLineInstructor);
        txtHeaderStudent = (TextView) findViewById(R.id.txtHeaderStudent);
        txtHeaderInstructor = (TextView) findViewById(R.id.txtHeaderInstructor);

    }

    @Override
    public void initData() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StudentNotificationFragment(), "Student");
        adapter.addFragment(new InstructorNotificationFragment(), "Instructor");
        viewPager.setAdapter(adapter);
        initViewPager();

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

    private void initViewPager() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (viewPager.getCurrentItem() == 0) {
                    txtHeaderStudent.setTextColor(getResources().getColor(R.color.colorPrimary));
                    ivLineStudent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    txtHeaderInstructor.setTextColor(getResources().getColor(R.color.white));
                    ivLineInstructor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                } else {
                    txtHeaderInstructor.setTextColor(getResources().getColor(R.color.colorPrimary));
                    ivLineInstructor.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                    txtHeaderStudent.setTextColor(getResources().getColor(R.color.white));
                    ivLineStudent.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
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

    @Override
    public void initClickListener() {
        txtHeaderStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        txtHeaderInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
    }
}
