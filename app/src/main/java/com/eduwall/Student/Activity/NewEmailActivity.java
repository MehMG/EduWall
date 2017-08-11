package com.eduwall.Student.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Interface.GetResult;
import com.eduwall.Interface.RecordDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.AttachAdapter;
import com.eduwall.Student.Adapter.SendToAdapter;
import com.eduwall.Student.Models.Attach;
import com.eduwall.Student.Models.SendTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by codesture on 6/6/17.
 */
public class NewEmailActivity extends BaseActivity {

    private Spinner spinnerUserType;
    private Spinner spinnerSelectUser;
    private RecyclerView recyclerTo;
    private EditText edtEmailTitle;
    private EditText edtEmailDescription;
    private RecyclerView recyclerAttachment;
    private TextView txtPhoto;
    private TextView txtAudio;
    private TextView txtVideo;
    private TextView txtDocument;
    private TextView txtVoiceNote;
    private Button btnAddMedia;
    private LinearLayout lnvAddMedia;
    private LinearLayout lnvAddMain;
    ArrayList<String> attachList = new ArrayList<>();

    ArrayList<String> userList;
    ArrayList<String> userIDList;

    ArrayList<SendTo> sendToArrayList;
    SendToAdapter sendToAdapter;

    ArrayList<Attach> attachArrayList;
    AttachAdapter attachAdapter;

    ArrayList<Attach> mAttachments = new ArrayList<>();

    String strReceivers;
    String parent = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_email);
        parent = getIntent().getStringExtra("Parent");

        initComponent();
        initData();
        initSpinner();
        initActionBar("New Mail");
        imgSendEmail.setVisibility(View.VISIBLE);

        initClickListener();
        initBackButton();
    }


    @Override
    public void initComponent() {

        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);
        spinnerSelectUser = (Spinner) findViewById(R.id.spinnerSelectUser);
        edtEmailTitle = (EditText) findViewById(R.id.edtEmailTitle);
        edtEmailDescription = (EditText) findViewById(R.id.edtEmailDescription);
        txtPhoto = (TextView) findViewById(R.id.txtPhoto);
        txtAudio = (TextView) findViewById(R.id.txtAudio);
        txtVideo = (TextView) findViewById(R.id.txtVideo);
        txtDocument = (TextView) findViewById(R.id.txtDocument);
        txtVoiceNote = (TextView) findViewById(R.id.txtVoiceNote);
        btnAddMedia = (Button) findViewById(R.id.btnAddMedia);
        lnvAddMedia = (LinearLayout) findViewById(R.id.lnvAddMedia);
        lnvAddMain = (LinearLayout) findViewById(R.id.lnvAddMain);

        recyclerTo = (RecyclerView) findViewById(R.id.recyclerTo);
        recyclerTo.setLayoutManager(getLayoutManager(1, LinearLayoutManager.HORIZONTAL));

        recyclerAttachment = (RecyclerView) findViewById(R.id.recyclerAttachment);
        recyclerAttachment.setLayoutManager(getLayoutManager(1, LinearLayoutManager.HORIZONTAL));
    }

    @Override
    public void initData() {

        sendToArrayList = new ArrayList<>();
        sendToAdapter = new SendToAdapter(NewEmailActivity.this, sendToArrayList);
        recyclerTo.setAdapter(sendToAdapter);

        attachArrayList = new ArrayList<>();
        attachAdapter = new AttachAdapter(NewEmailActivity.this, attachArrayList, "");
        recyclerAttachment.setAdapter(attachAdapter);

        if (parent.equalsIgnoreCase("ViewMessage")) {
            spinnerSelectUser.setVisibility(View.GONE);
            spinnerUserType.setVisibility(View.GONE);

            SendTo sendTo = new SendTo();
            sendTo.setEmail(preference.getUserMessageData().getEmail());

            sendToArrayList.add(sendTo);
            sendToAdapter.notifyItemRangeChanged(sendToArrayList.size(), sendToArrayList.size(), sendTo);

        }

        if (parent.equalsIgnoreCase("Forward")) {

            Log.e("Parent", "True");
            Log.e("Parent", preference.getMessageData().getTitle());
            Log.e("Parent", preference.getMessageData().getDescription());

            spinnerSelectUser.setVisibility(View.VISIBLE);
            spinnerUserType.setVisibility(View.VISIBLE);

            edtEmailTitle.setText(preference.getMessageData().getTitle());
            edtEmailDescription.setText(preference.getMessageData().getDescription());
            mAttachments = preference.getMessageData().getAttachArrayList();

        }


    }


    private void initSpinner() {


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(NewEmailActivity.this, R.array.spinnerType, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerUserType.setSelection(0);
        spinnerUserType.setAdapter(adapter);

    }


    @Override
    public void initClickListener() {

        spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//        http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/get_email_user_type_list

                if (i == 0) {

                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("user_type", String.valueOf(spinnerUserType.getSelectedItemId()));
                    callAPiActivity.doPost((Activity) mContext, map, Constant.Get_email_user_type_list, new GetApiResultJson() {
                        @Override
                        public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                            JSONArray jsonArray = result.getJSONArray("list");
                            userList = new ArrayList<String>();
                            userIDList = new ArrayList<String>();
                            userList.add("Select Recepient");
                            userIDList.add("0");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                userIDList.add(jsonObject.getString("user_id"));
                                userList.add(jsonObject.getString("email"));
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, R.layout.simple_spinner_item, userList);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                            spinnerSelectUser.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onFailureResult(String messgae) throws JSONException {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSelectUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (spinnerSelectUser.getSelectedItemId() != 0) {

                    if (sendToArrayList.size() == 0) {
                        Log.e("Selected ", spinnerSelectUser.getSelectedItem().toString());
                        addSendToArray();
                    } else {
                        int count = 0;
                        for (int i = 0; i < sendToArrayList.size(); i++) {

                            if (sendToArrayList.get(i).getEmail().equalsIgnoreCase(spinnerSelectUser.getSelectedItem().toString())) {
                                Log.e("Sel", "Yes");
                                count++;
                                break;
                            }
                        }
                        if (count == 0) {
                            addSendToArray();
                        }
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edtEmailDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        btnAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permissions.getReadWritePermisssion()) {


                    if (lnvAddMedia.getVisibility() == View.GONE) {
                        StartAnimations();
                        lnvAddMedia.setVisibility(View.VISIBLE);
                    } else {
                        lnvAddMedia.setVisibility(View.GONE);
                    }
                }
            }
        });

        txtPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getAttachment(Constant.Image, new GetResult() {
                    @Override
                    public void getSuccess(String path) {

                        updateAdapter();
                    }

                });
            }
        });

        txtAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getAttachment(Constant.Audio, new GetResult() {
                    @Override
                    public void getSuccess(String path) {

                        updateAdapter();

                    }

                });
            }
        });
        txtVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getAttachment(Constant.Video, new GetResult() {
                    @Override
                    public void getSuccess(String path) {
                        updateAdapter();
                    }

                });
            }
        });
        txtDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAttachment(Constant.Document, new GetResult() {
                    @Override
                    public void getSuccess(String path) {
                        updateAdapter();

                    }

                });
            }
        });

        txtVoiceNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lnvAddMedia.setVisibility(View.GONE);

                recordDialog.showRecordDialog(mContext, new RecordDialogueClickListner() {
                    @Override
                    public void onAttachClick(Dialog dialog, String path) {
                        if (path.equalsIgnoreCase("")) {

                        } else {

                            Log.e("PATH", path);
                            String[] path1 = path.split("/");
                            actualName = path1[path1.length - 1];

                            Attach attach = new Attach();
                            attach.setName(actualName);
                            attachArrayList.add(attach);

                            attachAdapter.notifyDataSetChanged();
                            attachAdapter.notifyItemRangeChanged(attachArrayList.size(), attachArrayList.size());


                            Log.e("PATH", path);

                            Attach attach1 = new Attach();
                            attach1.setName(path);
                            mAttachments.add(attach1);

                            attachList = new ArrayList<>();
                            for (int j = 0; j < mAttachments.size(); j++) {
                                attachList.add(mAttachments.get(j).getName());
                            }
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelClick(Dialog dialog, String path) {
                        dialog.dismiss();
                    }
                });

            }
        });

        imgSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sendToArrayList.size() > 0) {

                    if (parent.equalsIgnoreCase("Parent")) {
                    } else {
                        getSenderData();
                    }
                    sendEmail();
                } else {
                    Toast.makeText(NewEmailActivity.this, "Select Recepient First..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addSendToArray() {
        Log.e("Selected Method", "True");
        SendTo sendTo = new SendTo();
        sendTo.setId(userIDList.get(spinnerSelectUser.getSelectedItemPosition()));
        sendTo.setEmail(spinnerSelectUser.getSelectedItem().toString());
        sendToArrayList.add(sendTo);
        sendToAdapter.notifyItemRangeChanged(sendToArrayList.size(), sendToArrayList.size(), sendTo);
    }

    private void getSenderData() {

        for (int i = 0; i < sendToArrayList.size(); i++) {
            if (i == 0) {
                strReceivers = sendToArrayList.get(i).getId();
            } else
                strReceivers = strReceivers + "," + sendToArrayList.get(i).getId();
        }
        Log.e("Receivers", strReceivers);
    }

    private void updateAdapter() {

        Log.e("PATH", selectedImagePath);
        String[] path1 = selectedImagePath.split("/");
        actualName = path1[path1.length - 1];

        Attach attach = new Attach();
        attach.setName(actualName);
        attachArrayList.add(attach);

        attachAdapter.notifyDataSetChanged();
        attachAdapter.notifyItemRangeChanged(attachArrayList.size(), attachArrayList.size());

        lnvAddMedia.setVisibility(View.GONE);

        Log.e("PATH", selectedImagePath);

        Attach attach1 = new Attach();
        attach1.setName(selectedImagePath);
        mAttachments.add(attach1);

        attachList = new ArrayList<>();
        for (int j = 0; j < mAttachments.size(); j++) {
            attachList.add(mAttachments.get(j).getName());
        }

    }

    private void sendEmail() {

        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/sendEmail

        HashMap<String, String> map = new HashMap<>();
        map.put("title", edtEmailTitle.getText().toString());
        map.put("description", edtEmailDescription.getText().toString());
        map.put("sender_id", preference.getUserData().getId());
        map.put("receiver_id", strReceivers);

        callAPiActivity.doPostWithFiles(NewEmailActivity.this, map, Constant.SendEmail, attachList, "images[]", new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException {

                Log.e("Result", result.toString());

                Intent intent = new Intent(NewEmailActivity.this, InboxActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {
                appDialogs.setErrorToast(messgae);
            }

        });
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(NewEmailActivity.this, R.anim.slide_up);
        lnvAddMain.startAnimation(anim);

        Thread splashTread;
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        };
        splashTread.start();
    }

    @Override
    public void onBackPressed() {
        if (lnvAddMedia.getVisibility() == View.GONE) {
            super.onBackPressed();
            finish();
        } else {
            lnvAddMedia.setVisibility(View.GONE);
        }

    }

}
