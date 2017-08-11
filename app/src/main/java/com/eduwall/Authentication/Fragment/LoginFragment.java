package com.eduwall.Authentication.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Authentication.Models.User;
import com.eduwall.Instructor.Activity.InsHomeActivity;
import com.eduwall.PrivateUnit.Activity.PUHomeActivity;
import com.eduwall.R;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Activity.HomeActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by codesture on 24/5/17.
 */
public class LoginFragment extends BaseFragment {
    @Nullable
    private EditText edtEmailLogin;
    private EditText edtPassword;
    private TextView txtForgotPassword;
    private Button btnLogin;
    private Button btnRegister;
    private ImageView ivFirst, ivSecond, ivThird;
    private int currentpage;
    Handler handler = new Handler();


    private ViewPager viewPagerLogin;

    //0 - Increment
    //1 - Decrement

    public int status = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initComponent(view);
        initData();
        initClickListener();


        return view;
    }

    @Override
    public void initComponent(View view) {

        edtEmailLogin = (EditText) view.findViewById(R.id.edtEmailLogin);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        txtForgotPassword = (TextView) view.findViewById(R.id.txtForgotPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        viewPagerLogin = (ViewPager) view.findViewById(R.id.viewpagerLogin);

        ivFirst = (ImageView) view.findViewById(R.id.ivFirst);
        ivSecond = (ImageView) view.findViewById(R.id.ivSecond);
        ivThird = (ImageView) view.findViewById(R.id.ivThird);
    }

    @Override
    public void initData() {

        setupViewPager(viewPagerLogin);
        getSlider();

        if (!FirebaseApp.getApps(getActivity()).isEmpty()) {

            baseActivity.pushToken = FirebaseInstanceId.getInstance().getToken();

            if (baseActivity.pushToken != null) {
                Log.e("Firebase Token", baseActivity.pushToken);
            }
        }

        if (preference.getUserData() != null) {
            if (preference.getUserData().getParent().equalsIgnoreCase("Registration")) {
                edtEmailLogin.setText(preference.getUserData().getEmailid());
                edtPassword.setText(preference.getUserData().getPassword());
            }
        }
    }

    @Override
    public void initClickListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doValid()) {
                    doLogin();
                }
            }
        });

        viewPagerLogin.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                currentpage = position;

                if (position == 0) {
                    ivFirst.setBackgroundResource(R.drawable.active_dot);
                    ivSecond.setBackgroundResource(R.drawable.notactive_dot);
                    ivThird.setBackgroundResource(R.drawable.notactive_dot);
                } else if (position == 1) {
                    ivFirst.setBackgroundResource(R.drawable.notactive_dot);
                    ivSecond.setBackgroundResource(R.drawable.active_dot);
                    ivThird.setBackgroundResource(R.drawable.notactive_dot);
                } else {
                    ivFirst.setBackgroundResource(R.drawable.notactive_dot);
                    ivSecond.setBackgroundResource(R.drawable.notactive_dot);
                    ivThird.setBackgroundResource(R.drawable.active_dot);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new RegistrationFragment(), R.id.authentication);
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doForgot();
            }
        });


    }

    public void doForgot() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.row_forgot);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText edtForgotEmail = (EditText) dialog.findViewById(R.id.edtForgotEmail);
        ImageView ivForgotClose = (ImageView) dialog.findViewById(R.id.ivForgotClose);
        Button btnForgotChange = (Button) dialog.findViewById(R.id.btnForgotChange);

        ivForgotClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnForgotChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtForgotEmail.getText().toString().equalsIgnoreCase("")) {
                    edtForgotEmail.setError("Enter Emailid");
                    edtForgotEmail.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(edtForgotEmail.getText().toString()).matches()) {
                    edtForgotEmail.setError("Invalid Emailid");
                    edtForgotEmail.requestFocus();
                    return;
                } else {


                    HashMap<String, String> map = new HashMap<>();
                    map.put("email", edtForgotEmail.getText().toString());


                    callAPiActivity.doPost(getActivity(), map, Constant.Forget_password, new GetApiResultJson() {
                        @Override
                        public void onSuccesResult(JSONObject result) throws JSONException {

                            appDialogs.setSuccessToast(result.getString("message"));
                        }

                        @Override
                        public void onFailureResult(String messgae) throws JSONException {
                            appDialogs.setErrorToast(messgae);

                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void doLogin() {

        HashMap<String, String> map = new HashMap<>();
        map.put("email", edtEmailLogin.getText().toString());
        map.put("password", edtPassword.getText().toString());
        map.put("device_id", baseActivity.pushToken);
        map.put("device_type", "1");


        callAPiActivity.doPost(getActivity(), map, Constant.Login, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                JSONArray jsonArray = result.getJSONArray("data");
                JSONObject resultObj = jsonArray.getJSONObject(0);

                User user = new User();
                user.setId(resultObj.getString("user_id"));
                user.setName(resultObj.getString("name"));
                user.setType(resultObj.getString("user_type"));
                user.setEmailid(resultObj.getString("email"));
                user.setPassword(edtPassword.getText().toString());
                user.setCountry(resultObj.getString("country"));
                user.setQualification(resultObj.getString("qualification"));
                user.setPhonenumber(resultObj.getString("contact"));
                user.setSpecialization(resultObj.getString("specialization"));
                user.setProfile(resultObj.getString("profile_image"));
                user.setParent("");

                preference.setUserData(user);
                appDialogs.setSuccessToast(result.getString("message"));


                if (resultObj.getString("user_type").equalsIgnoreCase(Constant.Student)) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (resultObj.getString("user_type").equalsIgnoreCase(Constant.Instructor)) {

                    Intent intent = new Intent(getActivity(), InsHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (resultObj.getString("user_type").equalsIgnoreCase(Constant.PrivateUnit)) {

                    Intent intent = new Intent(getActivity(), PUHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (resultObj.getString("user_type").equalsIgnoreCase(Constant.Institute)) {

                    Intent intent = new Intent(getActivity(), PUHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);

            }
        });


    }

    private boolean doValid() {
        if (edtEmailLogin.getText().toString().equalsIgnoreCase("")) {
            edtEmailLogin.setError("Enter Username");
            edtEmailLogin.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmailLogin.getText().toString()).matches()) {
            edtEmailLogin.setError("Invalid Emailid");
            edtEmailLogin.requestFocus();
            return false;
        } else if (edtPassword.getText().toString().equalsIgnoreCase("")) {
            edtPassword.setError("Enter Password");
            edtPassword.requestFocus();
            return false;
        } else if (edtPassword.getText().toString().length() < 6) {
            edtPassword.setError("Atleast 6 characters required");
            edtPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }


    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new SliderFragment(), "");
        adapter.addFragment(new SliderFragment(), "");
        adapter.addFragment(new SliderFragment(), "");
        viewPager.setAdapter(adapter);

    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
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

    public void getSlider() {


        final Runnable update = new Runnable() {
            public void run() {


                if (currentpage == 2) {
                    status = 1;
                } else if (currentpage == 0) {
                    status = 0;
                }

                if (status == 0) {
                    viewPagerLogin.setCurrentItem(currentpage++, true);

                } else {
                    viewPagerLogin.setCurrentItem(currentpage--, true);

                }
            }
        };


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 2000);


    }


}
