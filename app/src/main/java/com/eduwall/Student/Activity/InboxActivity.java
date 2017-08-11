package com.eduwall.Student.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.InboxMessageAdapter;
import com.eduwall.Student.Fragment.ReceivedMessageFragment;
import com.eduwall.Student.Fragment.SentMessageFragment;
import com.eduwall.Student.Models.Attach;
import com.eduwall.Student.Models.InboxMessege;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by codesture on 2/6/17.
 */
public class InboxActivity extends BaseActivity {

    private ViewPager viewPager;

    TextView txtHeaderSent;
    TextView txtHeaderReceived;

    ImageView ivLineSent;
    ImageView ivLineReceived;

    LinearLayout lnvHeaderReceived;

    ArrayList<InboxMessege> receivedArrayList;
    ArrayList<InboxMessege> sentArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        initActionBar("Mailbox");
        initBackButton();
        initComponent();
        initData();
        initClickListener();
    }

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

        receivedArrayList = new ArrayList<>();
        sentArrayList = new ArrayList<>();
        initMessageData();

        initViewPager();

    }

    private void initMessageData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", preference.getUserData().getId());

        callAPiActivity.doPost(InboxActivity.this, map, Constant.Email_list, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                Log.e("Result", result.toString());

                JSONArray receivedArray = result.getJSONArray("received_array");
                for (int i = 0; i < receivedArray.length(); i++) {
                    JSONObject receivedObj = receivedArray.getJSONObject(i);

                    InboxMessege messege = new InboxMessege();
                    messege.setThread_id(receivedObj.getString("thread_id"));
                    messege.setEmail_id(receivedObj.getString("email_id"));
                    messege.setSender_name(receivedObj.getString("sender_name"));
                    messege.setSender_image(receivedObj.getString("sender_image"));
                    messege.setSender_email(receivedObj.getString("sender_email"));
                    messege.setTitle(receivedObj.getString("title"));

                    JSONArray attachArray = receivedObj.getJSONArray("attachment");
                    ArrayList<Attach> attachList = new ArrayList<Attach>();
                    for (int j = 0; j < attachArray.length(); j++) {
                        JSONObject attachObject = attachArray.getJSONObject(j);
                        Attach attach = new Attach();
                        attach.setExtension(attachObject.getString("ext"));
                        attach.setName(attachObject.getString("image"));
                        attachList.add(attach);
                    }

                    messege.setAttachArrayList(attachList);
                    messege.setDescription(receivedObj.getString("description"));
                    messege.setSend_at(receivedObj.getString("send_at"));

                    receivedArrayList.add(messege);
                }

                JSONArray sentArray = result.getJSONArray("sent_array");
                for (int i = 0; i < sentArray.length(); i++) {
                    JSONObject sentObj = sentArray.getJSONObject(i);

                    InboxMessege messege1 = new InboxMessege();
                    messege1.setThread_id(sentObj.getString("thread_id"));
                    messege1.setEmail_id(sentObj.getString("email_id"));
                    messege1.setSender_name(sentObj.getString("sender_name"));
                    messege1.setSender_image(sentObj.getString("sender_image"));
                    messege1.setSender_email(sentObj.getString("sender_email"));
                    messege1.setTitle(sentObj.getString("title"));

                    JSONArray attachArray = sentObj.getJSONArray("attachment");
                    ArrayList<Attach> attachList = new ArrayList<Attach>();
                    for (int j = 0; j < attachArray.length(); j++) {
                        JSONObject attachObject = attachArray.getJSONObject(j);
                        Attach attach = new Attach();
                        attach.setExtension(attachObject.optString("ext"));
                        attach.setName(attachObject.getString("image"));
                        attachList.add(attach);
                    }

                    messege1.setAttachArrayList(attachList);

                    messege1.setDescription(sentObj.getString("description"));
                    messege1.setSend_at(sentObj.getString("send_at"));

                    sentArrayList.add(messege1);
                }


                ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
                adapter.addFragment(new SentMessageFragment(sentArrayList, Constant.sent), Constant.sent);
                adapter.addFragment(new ReceivedMessageFragment(receivedArrayList, Constant.received), Constant.received);
                viewPager.setAdapter(adapter);


            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }
        });

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
