package com.eduwall.Instructor.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Interface.GetResult;
import com.eduwall.Interface.RecordDialogueClickListner;
import com.eduwall.R;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Student.Activity.ShowAllPostActivity;
import com.eduwall.Student.Adapter.AttachAdapter;
import com.eduwall.Student.Adapter.QuestionAdapter;
import com.eduwall.Student.Models.Attach;
import com.eduwall.Student.Models.Questions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.N)
public class InsAddPostActivity extends BaseActivity {

    public int count = 1;

    private Spinner spinnerInsTask;
    private LinearLayout lnvPostPost;
    private LinearLayout lnvPostHomework;
    private EditText edtPostHomeworkDate;
    private LinearLayout lnvPostHomeworkNotes;
    private EditText edtPostHomeworkNotes;
    private LinearLayout lnvPostTest;
    private EditText edtPostTestMarks;
    private EditText edtPostTestDuration;
    private EditText edtPostTestNotes;
    private LinearLayout lnvPostQuiz;
    private EditText edtPostQuizMarks;
    private EditText edtPostQuizDuration;
    private EditText edtPostQuizDesc;
    private LinearLayout lnvProjectWork;
    private EditText edtPostProjectDate;
    private EditText edtPostProjectTopic;
    private LinearLayout lnvProjectDescription;
    private EditText edtPostProjectDesc;
    private EditText edtPostNewQuestionMark;
    private LinearLayout lnvPostAttachment;
    private RecyclerView recyclerPostAttachment;
    private LinearLayout lnvInsPostAddMain;
    private LinearLayout lnvPostQuestions;
    private Button btnAddPostMedia;
    private LinearLayout lnvInsAddPostMedia;
    private TextView txtAddPhoto;
    private TextView txtAddAudio;
    private TextView txtAddVideo;
    private TextView txtAddDocument;
    private TextView txtAddVoiceNote;
    private RecyclerView recyclerPostQueList;
    private ImageView ivAddNewQue;
    private EditText edtPostNewQuestion;

    private String clickbox = "";

    ArrayList<Attach> attachArrayList;
    AttachAdapter attachAdapter;

    ArrayList<Questions> questionArrayList;
    QuestionAdapter questionAdapter;

    Calendar calendar = Calendar.getInstance();

    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DATE);

    ArrayList<String> mAttachments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrutor_add_post);
        initComponent();
        initSpinner();
        initUIUpdate();
        initData();
        initActionBar("Add Post");
        initClickListener();
        initBackButton();
        txtPost.setVisibility(View.VISIBLE);
    }

    @Override
    public void initComponent() {

        spinnerInsTask = (Spinner) findViewById(R.id.spinnerInsTask);
        lnvPostPost = (LinearLayout) findViewById(R.id.lnvPostPost);
        lnvPostHomework = (LinearLayout) findViewById(R.id.lnvPostHomework);
        edtPostHomeworkDate = (EditText) findViewById(R.id.edtPostHomeworkDate);
        lnvPostHomeworkNotes = (LinearLayout) findViewById(R.id.lnvPostHomeworkNotes);
        edtPostHomeworkNotes = (EditText) findViewById(R.id.edtPostHomeworkNotes);
        lnvPostTest = (LinearLayout) findViewById(R.id.lnvPostTest);
        edtPostTestMarks = (EditText) findViewById(R.id.edtPostTestMarks);
        edtPostTestDuration = (EditText) findViewById(R.id.edtPostTestDuration);
        edtPostTestNotes = (EditText) findViewById(R.id.edtPostTestNotes);
        lnvPostQuiz = (LinearLayout) findViewById(R.id.lnvPostQuiz);
        edtPostQuizMarks = (EditText) findViewById(R.id.edtPostQuizMarks);
        edtPostQuizDuration = (EditText) findViewById(R.id.edtPostQuizDuration);
        edtPostQuizDesc = (EditText) findViewById(R.id.edtPostQuizDesc);
        edtPostNewQuestionMark = (EditText) findViewById(R.id.edtPostNewQuestionMark);
        lnvProjectWork = (LinearLayout) findViewById(R.id.lnvProjectWork);
        edtPostProjectDate = (EditText) findViewById(R.id.edtPostProjectDate);
        edtPostProjectTopic = (EditText) findViewById(R.id.edtPostProjectTopic);
        lnvProjectDescription = (LinearLayout) findViewById(R.id.lnvProjectDescription);
        edtPostProjectDesc = (EditText) findViewById(R.id.edtPostProjectDesc);
        edtPostNewQuestion = (EditText) findViewById(R.id.edtPostNewQuestion);
        lnvPostAttachment = (LinearLayout) findViewById(R.id.lnvPostAttachment);
        recyclerPostAttachment = (RecyclerView) findViewById(R.id.recyclerPostAttachment);
        recyclerPostAttachment.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        recyclerPostQueList = (RecyclerView) findViewById(R.id.recyclerPostQueList);
        recyclerPostQueList.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        ivAddNewQue = (ImageView) findViewById(R.id.ivAddNewQue);
        lnvInsPostAddMain = (LinearLayout) findViewById(R.id.lnvInsPostAddMain);
        lnvPostQuestions = (LinearLayout) findViewById(R.id.lnvPostQuestions);
        btnAddPostMedia = (Button) findViewById(R.id.btnAddPostMedia);
        lnvInsAddPostMedia = (LinearLayout) findViewById(R.id.lnvInsAddPostMedia);
        txtAddPhoto = (TextView) findViewById(R.id.txtAddPhoto);
        txtAddAudio = (TextView) findViewById(R.id.txtAddAudio);
        txtAddVideo = (TextView) findViewById(R.id.txtAddVideo);
        txtAddDocument = (TextView) findViewById(R.id.txtAddDocument);
        txtAddVoiceNote = (TextView) findViewById(R.id.txtAddVoiceNote);

    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InsAddPostActivity.this, R.array.spinnerPostType1, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerInsTask.setSelection(0);
        spinnerInsTask.setAdapter(adapter);
    }

    private void initUIUpdate() {

        spinnerInsTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    lnvPostPost.setVisibility(View.VISIBLE);
                    lnvPostHomework.setVisibility(View.GONE);
                    lnvPostQuiz.setVisibility(View.GONE);
                    lnvPostTest.setVisibility(View.GONE);
                    lnvProjectWork.setVisibility(View.GONE);
                    lnvPostAttachment.setVisibility(View.VISIBLE);
                    lnvInsPostAddMain.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.GONE);
                } else if (i == 1) {
                    lnvPostPost.setVisibility(View.GONE);
                    lnvPostHomework.setVisibility(View.VISIBLE);
                    lnvPostHomeworkNotes.setVisibility(View.VISIBLE);
                    lnvPostQuiz.setVisibility(View.GONE);
                    lnvPostTest.setVisibility(View.GONE);
                    lnvProjectWork.setVisibility(View.GONE);
                    lnvPostAttachment.setVisibility(View.GONE);
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                    lnvInsPostAddMain.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.GONE);
                } else if (i == 2) {
                    lnvPostPost.setVisibility(View.GONE);
                    lnvPostHomeworkNotes.setVisibility(View.GONE);
                    lnvPostHomework.setVisibility(View.VISIBLE);
                    lnvPostQuiz.setVisibility(View.GONE);
                    lnvPostTest.setVisibility(View.GONE);
                    lnvProjectWork.setVisibility(View.GONE);
                    lnvPostAttachment.setVisibility(View.GONE);
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                    lnvInsPostAddMain.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.GONE);
                } else if (i == 3) {
                    lnvPostPost.setVisibility(View.GONE);
                    lnvPostHomework.setVisibility(View.GONE);
                    lnvPostQuiz.setVisibility(View.GONE);
                    lnvPostTest.setVisibility(View.VISIBLE);
                    lnvProjectWork.setVisibility(View.GONE);
                    lnvPostAttachment.setVisibility(View.GONE);
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                    lnvInsPostAddMain.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.VISIBLE);
                } else if (i == 4) {
                    lnvPostPost.setVisibility(View.GONE);
                    lnvPostHomework.setVisibility(View.GONE);
                    lnvPostQuiz.setVisibility(View.VISIBLE);
                    lnvPostTest.setVisibility(View.GONE);
                    lnvProjectWork.setVisibility(View.GONE);
                    lnvPostAttachment.setVisibility(View.GONE);
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                    lnvInsPostAddMain.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.GONE);
                } else if (i == 5) {
                    lnvPostPost.setVisibility(View.GONE);
                    lnvPostHomework.setVisibility(View.GONE);
                    lnvPostQuiz.setVisibility(View.GONE);
                    lnvProjectDescription.setVisibility(View.VISIBLE);
                    lnvPostTest.setVisibility(View.GONE);
                    lnvProjectWork.setVisibility(View.VISIBLE);
                    lnvPostAttachment.setVisibility(View.GONE);
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                    lnvInsPostAddMain.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.GONE);
                } else if (i == 6) {
                    lnvPostPost.setVisibility(View.GONE);
                    lnvPostHomework.setVisibility(View.GONE);
                    lnvPostQuiz.setVisibility(View.GONE);
                    lnvPostTest.setVisibility(View.GONE);
                    lnvProjectDescription.setVisibility(View.GONE);
                    lnvProjectWork.setVisibility(View.VISIBLE);
                    lnvPostAttachment.setVisibility(View.GONE);
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                    lnvInsPostAddMain.setVisibility(View.GONE);
                    lnvPostQuestions.setVisibility(View.VISIBLE);
                    edtPostNewQuestionMark.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void initData() {
        mAttachments = new ArrayList<>();
        attachArrayList = new ArrayList<>();

        questionArrayList = new ArrayList<>();

        questionAdapter = new QuestionAdapter(mContext, questionArrayList, Constant.Instructor);
        recyclerPostQueList.setAdapter(questionAdapter);

        attachAdapter = new AttachAdapter(InsAddPostActivity.this, attachArrayList, Constant.Addpost);
        recyclerPostAttachment.setAdapter(attachAdapter);
    }

    @Override
    public void initClickListener() {

        ivAddNewQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Questions questions = new Questions();
                questions.setQuestion(edtPostNewQuestion.getText().toString());

                if (spinnerInsTask.getSelectedItem().toString().equalsIgnoreCase(Constant.Test)) {
                    questions.setMarks(edtPostNewQuestionMark.getText().toString());
                    edtPostNewQuestionMark.setText("");
                }
                questionArrayList.add(questions);

                questionAdapter.notifyDataSetChanged();
                questionAdapter.notifyItemRangeChanged(questionArrayList.size(), questionArrayList.size());

                edtPostNewQuestion.setText("");
                count++;

            }
        });

        edtPostHomeworkDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbox = "Homework";
                onCreateDialog(1);
            }
        });

        edtPostProjectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbox = "Project";
                onCreateDialog(2);
            }
        });

        btnAddPostMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissions.getReadWritePermisssion()) {
                    if (lnvInsAddPostMedia.getVisibility() == View.GONE) {
                        StartAnimations();
                        lnvInsAddPostMedia.setVisibility(View.VISIBLE);
                    } else {
                        lnvInsAddPostMedia.setVisibility(View.GONE);
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
        });
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
                lnvInsAddPostMedia.setVisibility(View.GONE);

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
        txtPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                (input title,description,images,institute_id,course_id,subject_id,module_id,user_id)
//                http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/addpost

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("institute_id", preference.getPostIDData().getInstituteID());
                map.put("course_id", preference.getPostIDData().getCourseID());
                map.put("subject_id", preference.getPostIDData().getSubjectid());
                map.put("module_id", preference.getPostIDData().getModuleID());
                map.put("user_id", preference.getUserData().getId());
                map.put("is_post", "0");

                callAPiActivity.doPostWithFiles((Activity) mContext, map, Constant.Addpost, mAttachments, "images[]", new GetApiResultJson() {
                    @Override
                    public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                        appDialogs.setSuccessToast(result.getString("message"));
                        startActivity(new Intent(InsAddPostActivity.this, ShowAllPostActivity.class));
                    }

                    @Override
                    public void onFailureResult(String messgae) throws JSONException {
                    }
                });
            }
        });

        btnAddPostMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnvInsAddPostMedia.getVisibility() == View.GONE) {
                    StartAnimations();
                    lnvInsAddPostMedia.setVisibility(View.VISIBLE);
                } else {
                    lnvInsAddPostMedia.setVisibility(View.GONE);
                }
            }
        });


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

        lnvInsAddPostMedia.setVisibility(View.GONE);

        Log.e("PATH", selectedImagePath);
        mAttachments.add(selectedImagePath);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        DatePickerDialog dpd = new DatePickerDialog(this, pickerListener, year, month, day);
        DatePicker dp = dpd.getDatePicker();

        dp.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.MONTH, 6);
        dp.setMaxDate(calendar.getTimeInMillis());
        return dpd;

    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date

            if (clickbox.equalsIgnoreCase("Homework")) {


                edtPostHomeworkDate.setText(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
            } else {
                edtPostProjectDate.setText(new StringBuilder().append(day)
                        .append("-").append(month + 1).append("-").append(year)
                        .append(" "));
            }
        }
    };

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(InsAddPostActivity.this, R.anim.slide_up);
        lnvInsPostAddMain.startAnimation(anim);

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
        if (lnvInsAddPostMedia.getVisibility() == View.VISIBLE) {
            lnvInsAddPostMedia.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
