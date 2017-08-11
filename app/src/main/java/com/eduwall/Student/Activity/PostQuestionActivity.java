package com.eduwall.Student.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Interface.GetResult;
import com.eduwall.Interface.RecordDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Adapter.AttachAdapter;
import com.eduwall.Student.Models.Attach;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PostQuestionActivity extends BaseActivity {


    private TextView txtLabel;
    private TextView txtName;
    private TextView txtTopic;
    private EditText edtPostTopic;
    private TextView txtQuestion;
    private EditText edtPostQuestion;
    private RecyclerView recyclerPostAttachment;
    private LinearLayout lnvAddMain;
    private Button btnAddPostMedia;
    private LinearLayout lnvAddPostMedia;
    private TextView txtAddPhoto;
    private TextView txtAddAudio;
    private TextView txtAddVideo;
    private TextView txtAddDocument;
    private TextView txtAddVoiceNote;

    ArrayList<Attach> attachArrayList;
    AttachAdapter attachAdapter;

    ArrayList<String> mAttachments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_question);
        initComponent();
        initData();
        initUIUpdate();
        txtAddPostButton.setVisibility(View.VISIBLE);
        initBackButton();
        initClickListener();


    }


    @Override
    public void initComponent() {


        txtLabel = (TextView) findViewById(R.id.txtLabel);
        txtName = (TextView) findViewById(R.id.txtName);
        txtTopic = (TextView) findViewById(R.id.txtTopic);
        edtPostTopic = (EditText) findViewById(R.id.edtPostTopic);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        edtPostQuestion = (EditText) findViewById(R.id.edtPostQuestion);
        lnvAddMain = (LinearLayout) findViewById(R.id.lnvAddMain);
        btnAddPostMedia = (Button) findViewById(R.id.btnAddPostMedia);
        lnvAddPostMedia = (LinearLayout) findViewById(R.id.lnvAddPostMedia);
        txtAddPhoto = (TextView) findViewById(R.id.txtAddPhoto);
        txtAddAudio = (TextView) findViewById(R.id.txtAddAudio);
        txtAddVideo = (TextView) findViewById(R.id.txtAddVideo);
        txtAddDocument = (TextView) findViewById(R.id.txtAddDocument);
        txtAddVoiceNote = (TextView) findViewById(R.id.txtAddVoiceNote);

        recyclerPostAttachment = (RecyclerView) findViewById(R.id.recyclerPostAttachment);
        recyclerPostAttachment.setLayoutManager(getLayoutManager(1, LinearLayoutManager.HORIZONTAL));
    }

    @Override
    public void initData() {

        txtName.setText(preference.getUserData().getName());

        attachArrayList = new ArrayList<>();
        mAttachments = new ArrayList<>();

        attachAdapter = new AttachAdapter(PostQuestionActivity.this, attachArrayList, "");
        recyclerPostAttachment.setAdapter(attachAdapter);

    }

    private void initUIUpdate() {

        if (preference.getPostIDData().getAddPostType().equalsIgnoreCase("Post")) {

            initActionBar("Add Post");
            txtLabel.setText("Post Added By:");
            txtTopic.setText("Post Title");
            txtQuestion.setText("Description");
            txtAddPostButton.setText("Post");
        } else {
            initActionBar("Ask Question");
            txtLabel.setText("Asked By:");
            txtTopic.setText("Topic");
            txtQuestion.setText("Question");
            txtAddPostButton.setText("Ask");
        }
    }


    @Override
    public void initClickListener() {

        txtAddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                (input title,description,images,institute_id,course_id,subject_id,module_id,user_id)
//                http://visatwebsolution.com/eduwall/testapi/index.php/api/api/addpost


                HashMap<String, String> map = new HashMap<String, String>();
                map.put("title", edtPostTopic.getText().toString());
                map.put("description", edtPostQuestion.getText().toString());
                map.put("start_date", "");
                map.put("end_date", "");
                map.put("institute_id", preference.getPostIDData().getInstituteID());
                map.put("course_id", preference.getPostIDData().getCourseID());
                map.put("subject_id", preference.getPostIDData().getSubjectid());
                map.put("module_id", preference.getPostIDData().getModuleID());
                map.put("user_id", preference.getUserData().getId());
                map.put("post_type", "");

                if (preference.getPostIDData().getAddPostType().equalsIgnoreCase("Post")) {
                    map.put("is_post", "0");
                } else {
                    map.put("is_post", "1");
                }

                callAPiActivity.doPostWithFiles((Activity) mContext, map, Constant.Addpost, mAttachments, "images[]", new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {

                        appDialogs.setSuccessToast(result.getString("message"));
                        startActivity(new Intent(PostQuestionActivity.this, ShowAllPostActivity.class));
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {

                    }
                });

            }
        });


        edtPostQuestion.setOnTouchListener(new View.OnTouchListener() {
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

        btnAddPostMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permissions.getReadWritePermisssion()) {


                    if (lnvAddPostMedia.getVisibility() == View.GONE) {
                        StartAnimations();
                        lnvAddPostMedia.setVisibility(View.VISIBLE);
                    } else {
                        lnvAddPostMedia.setVisibility(View.GONE);
                    }
                }
            }
        });

        txtAddPhoto.setOnClickListener(new View.OnClickListener() {
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

        txtAddAudio.setOnClickListener(new View.OnClickListener() {
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
        txtAddVideo.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               getAttachment(Constant.Video, new GetResult() {
                                                   @Override
                                                   public void getSuccess(String path) {
                                                       updateAdapter();
                                                   }

                                               });

                                           }
                                       }
        );
        txtAddDocument.setOnClickListener(new View.OnClickListener() {
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

        txtAddVoiceNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnvAddPostMedia.setVisibility(View.GONE);

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
                            mAttachments.add(path);
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
    }

    private void updateAdapter() {

        try {
            String[] path1 = selectedImagePath.split("/");
            actualName = path1[path1.length - 1];

            Attach attach = new Attach();
            attach.setName(actualName);
            attachArrayList.add(attach);

            attachAdapter.notifyDataSetChanged();
            attachAdapter.notifyItemRangeChanged(attachArrayList.size(), attachArrayList.size());

            lnvAddPostMedia.setVisibility(View.GONE);

            Log.e("PATH", selectedImagePath);
            mAttachments.add(selectedImagePath);
        } catch (Exception e) {

        }


    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(PostQuestionActivity.this, R.anim.slide_up);
        lnvAddMain.startAnimation(anim);

        Thread splashTread;
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        splashTread.start();
    }

    @Override
    public void onBackPressed() {
        if (lnvAddPostMedia.getVisibility() == View.VISIBLE) {
            lnvAddPostMedia.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
