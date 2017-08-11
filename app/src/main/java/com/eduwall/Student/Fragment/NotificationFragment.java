package com.eduwall.Student.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eduwall.Instructor.Activity.InsSettingsActivity;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Fragment.BaseFragment;
import com.eduwall.Student.Activity.SettingsActivity;

/**
 * Created by codesture on 6/6/17.
 */
public class NotificationFragment extends BaseFragment {
    BaseActivity baseActivity;
    private RadioGroup rgNotification;
    private RadioButton rbSound;
    private RadioButton rbVibrate;
    private RadioButton rbSoundVibrate;
    private RadioButton rbMute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        baseActivity = (BaseActivity) getActivity();
        baseActivity.initActionBar("Notification");
        baseActivity.initBackButton();
        baseActivity.imgSave.setVisibility(View.VISIBLE);
        initComponent(view);
        initData();
        initClickListener();

        return view;
    }

    @Override
    public void initComponent(View view) {

        rgNotification = (RadioGroup) view.findViewById(R.id.rgNotification);
        rbSound = (RadioButton) view.findViewById(R.id.rbSound);
        rbVibrate = (RadioButton) view.findViewById(R.id.rbVibrate);
        rbSoundVibrate = (RadioButton) view.findViewById(R.id.rbSoundVibrate);
        rbMute = (RadioButton) view.findViewById(R.id.rbMute);

    }

    @Override
    public void initData() {
        rbSound.setChecked(true);
    }

    @Override
    public void initClickListener() {

        baseActivity.imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {   //usertype=student
                    startActivity(new Intent(getActivity(), SettingsActivity.class));
                    getActivity().finish();
                } else {
                    startActivity(new Intent(getActivity(), InsSettingsActivity.class));
                    getActivity().finish();
                }
            }
        });

        rgNotification.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (rbSound.isChecked()) {
                    preference.setNotification("Tone");
                } else if (rbVibrate.isChecked()) {
                    preference.setNotification("Vibrate");
                } else if (rbSoundVibrate.isChecked()) {
                    preference.setNotification("PopUp");
                } else {
                    preference.setNotification("Mute");
                }
            }
        });

        baseActivity.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {   //usertype=student
                    startActivity(new Intent(getActivity(), SettingsActivity.class));
                    getActivity().finish();
                } else {
                    startActivity(new Intent(getActivity(), InsSettingsActivity.class));
                    getActivity().finish();
                }
            }
        });
    }
}
