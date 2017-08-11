package com.eduwall.Student.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduwall.API.GetApiResultJson;
import com.eduwall.Instructor.Activity.InsAddPostActivity;
import com.eduwall.Instructor.Activity.InsHomeActivity;
import com.eduwall.Instructor.Activity.InsPostActivity;
import com.eduwall.PrivateUnit.Activity.PUHomeActivity;
import com.eduwall.Session.Activity.BaseActivity;
import com.eduwall.Session.Constant;
import com.eduwall.Session.Model.AllpostData;
import com.eduwall.Student.Adapter.GetAttach;
import com.eduwall.Student.Adapter.PostAdapter;
import com.eduwall.R;
import com.eduwall.Student.Models.Attach;
import com.eduwall.Student.Models.ShowPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ShowAllPostActivity extends BaseActivity {
    RecyclerView rcl_all_post;

    TextView txtAddPost;
    TextView txtAskQuestion;
    TextView InstructorName;
    LinearLayout txtNoMessage;
    TextView txtClassName;
    ImageView ivBackWhite;
    ImageView ivGoLive;

    RelativeLayout lnvStudentTotal;

    LinearLayout lnvStuOptions;
    LinearLayout lnvInsOptions;

    ArrayList<ShowPost> showPostArrayList;
    ArrayList<GetAttach> attachment;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_post);
        initActionBar("Institute Name 1");

        initComponent();
        initUIUpdate();

        initData();
        initClickListener();
    }


    @Override
    public void initComponent() {
        rcl_all_post = (RecyclerView) findViewById(R.id.rcl_all_post);
        rcl_all_post.setLayoutManager(getLayoutManager(1, LinearLayoutManager.VERTICAL));
        rcl_all_post.setItemAnimator(new DefaultItemAnimator());

        txtAddPost = (TextView) findViewById(R.id.txtStuAddPost);
        txtClassName = (TextView) findViewById(R.id.txtClassName);
        InstructorName = (TextView) findViewById(R.id.InstructorName);
        txtNoMessage = (LinearLayout) findViewById(R.id.txtNoMessage);
        txtAskQuestion = (TextView) findViewById(R.id.txtAskQuestion);
        lnvStuOptions = (LinearLayout) findViewById(R.id.lnvStuOptions);
        lnvInsOptions = (LinearLayout) findViewById(R.id.lnvInsOptions);
        ivBackWhite = (ImageView) findViewById(R.id.ivBackWhite);
        ivGoLive = (ImageView) findViewById(R.id.ivGoLive);
        lnvStudentTotal = (RelativeLayout) findViewById(R.id.lnvStudentTotal);
    }

    private void initUIUpdate() {

        if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {

            txtClassName.setText(preference.getPostIDData().getCourseName());
            InstructorName.setText(preference.getPostIDData().getInstituteName());

            ivBackWhite.setVisibility(View.VISIBLE);
            imgHome.setVisibility(View.VISIBLE);
            lnvStudentTotal.setVisibility(View.GONE);
            imgTask.setVisibility(View.VISIBLE);
            ivGoLive.setVisibility(View.GONE);
            lnvStuOptions.setVisibility(View.VISIBLE);
            lnvInsOptions.setVisibility(View.GONE);
        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
            imgTask.setVisibility(View.GONE);
            imgHome.setVisibility(View.VISIBLE);
            ivBackWhite.setVisibility(View.GONE);
            lnvStudentTotal.setVisibility(View.VISIBLE);
            ivGoLive.setVisibility(View.VISIBLE);
            lnvStuOptions.setVisibility(View.GONE);
            lnvInsOptions.setVisibility(View.VISIBLE);

        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {
            initBackButton();
            ivBackWhite.setVisibility(View.GONE);
            lnvStudentTotal.setVisibility(View.GONE);
            ivGoLive.setVisibility(View.GONE);
            imgTask.setVisibility(View.GONE);
            imgHome.setVisibility(View.GONE);
            lnvStuOptions.setVisibility(View.GONE);
            lnvInsOptions.setVisibility(View.VISIBLE);

        } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Institute)) {
            initBackButton();
            ivBackWhite.setVisibility(View.GONE);
            lnvStudentTotal.setVisibility(View.GONE);
            ivGoLive.setVisibility(View.GONE);
            imgTask.setVisibility(View.GONE);
            imgHome.setVisibility(View.GONE);
            lnvStuOptions.setVisibility(View.GONE);
            lnvInsOptions.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void initData() {

        showPostArrayList = new ArrayList<>();
        //(input institute_id,course_id,subject_id,module_id)
        //http://dragonwebsol.com/workspace/eduwall/testapi/index.php/api/api/getpost

        HashMap<String, String> map = new HashMap<>();
        map.put("institute_id", preference.getPostIDData().getInstituteID());
        map.put("course_id", preference.getPostIDData().getCourseID());
        map.put("subject_id", preference.getPostIDData().getSubjectid());
        map.put("module_id", preference.getPostIDData().getModuleID());
        map.put("user_id", preference.getUserData().getId());

        callAPiActivity.doPost((Activity) mContext, map, Constant.Getpost, new GetApiResultJson() {
            @Override
            public void onSuccesResult(JSONObject result) throws JSONException, IOException {
                JSONArray jsonArray = result.getJSONArray("Postlist");

                if (jsonArray.length() > 0) {
                    rcl_all_post.setVisibility(View.VISIBLE);
                    txtNoMessage.setVisibility(View.GONE);
                } else {
                    rcl_all_post.setVisibility(View.GONE);
                    txtNoMessage.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    attachment = new ArrayList<>();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    ShowPost showPost = new ShowPost();
                    showPost.setPostID(jsonObject.getString("post_id"));
                    showPost.setIsPost(jsonObject.getString("is_post"));
                    showPost.setStartDate(jsonObject.getString("start_date"));
                    showPost.setEndDate(jsonObject.getString("end_date"));
                    showPost.setType(jsonObject.getString("is_post"));
                    showPost.setId(jsonObject.getString("user_id"));
                    showPost.setProfile(jsonObject.getString("profile_image"));
                    showPost.setName(jsonObject.getString("username"));
                    showPost.setDate(jsonObject.getString("created_at"));
                    showPost.setTitle(jsonObject.getString("title"));
                    showPost.setDescription(jsonObject.optString("description"));
                    showPost.setUserType(jsonObject.getString("user_type"));
                    showPost.setPostType(jsonObject.getString("post_type"));

                    JSONArray jsonArray1 = jsonObject.getJSONArray("attachment");

                    for (int j = 0; j < jsonArray1.length(); j++) {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                        GetAttach attach = new GetAttach();
                        attach.setFilename(jsonObject1.getString("image"));
                        attach.setExtension(jsonObject1.getString("ext"));
                        attachment.add(attach);
                        Log.e("Array" + j + "", jsonArray1.getString(j));
                    }
                    showPost.setAttachments(attachment);
                    showPostArrayList.add(showPost);

                }
                Collections.reverse(showPostArrayList);
                postAdapter = new PostAdapter(ShowAllPostActivity.this, showPostArrayList);
                rcl_all_post.setAdapter(postAdapter);
            }

            @Override
            public void onFailureResult(String messgae) throws JSONException {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void initClickListener() {

        ivBackWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowAllPostActivity.this, MyInstituteActivity.class));
            }
        });

        lnvInsOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowAllPostActivity.this, InsAddPostActivity.class));
            }
        });


        imgTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowAllPostActivity.this, TaskActivity.class));
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           if (preference.getUserData().getType().equalsIgnoreCase(Constant.Student)) {
                                               startActivity(new Intent(ShowAllPostActivity.this, HomeActivity.class));
                                           } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.Instructor)) {
                                               startActivity(new Intent(ShowAllPostActivity.this, InsHomeActivity.class));
                                           } else if (preference.getUserData().getType().equalsIgnoreCase(Constant.PrivateUnit)) {
                                               startActivity(new Intent(ShowAllPostActivity.this, PUHomeActivity.class));
                                           }
                                       }
                                   }
        );

        txtAddPost.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View view) {
                                              AllpostData allpostData = preference.getPostIDData();
                                              allpostData.setAddPostType("Post");
                                              preference.setPostIDData(allpostData);
                                              Intent intent = new Intent(ShowAllPostActivity.this, PostQuestionActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );

        txtAskQuestion.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View view) {
                                                  AllpostData allpostData = preference.getPostIDData();
                                                  allpostData.setAddPostType("Question");
                                                  preference.setPostIDData(allpostData);
                                                  Intent intent = new Intent(ShowAllPostActivity.this, PostQuestionActivity.class);
                                                  startActivity(intent);
                                              }
                                          }
        );
    }
}
