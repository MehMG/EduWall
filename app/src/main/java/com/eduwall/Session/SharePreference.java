package com.eduwall.Session;

import android.content.Context;
import android.util.Log;

import com.eduwall.Authentication.Models.User;
import com.eduwall.Instructor.Models.StudentList;
import com.eduwall.Session.Model.AllpostData;
import com.eduwall.Session.Model.MessageData;
import com.eduwall.Student.Models.InboxMessege;
import com.eduwall.Student.Models.Institute;
import com.eduwall.Student.Models.RequestedInstitute;
import com.eduwall.Student.Models.ShowPost;
import com.google.gson.Gson;

/**
 * Created by codesture on 23/5/17.
 */
public class SharePreference {
    android.content.SharedPreferences preferences;


    android.content.SharedPreferences.Editor editor;
    Gson gson;
    String Notification = "Vibrate";
    Boolean isActive = false;


    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }

    public SharePreference(Context context) {
        preferences = context.getSharedPreferences("EduWall", Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
    }

    public User getUserData() {
        String json = preferences.getString("user", "");
        Log.e("User", json);
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public void setUserData(User user) {
        String json = gson.toJson(user);
        Log.e("User", json);
        editor.putString("user", json);
        editor.commit();
    }

    public InboxMessege getMessageData() {
        String json = preferences.getString("message", "");
        Log.e("Message", json);
        InboxMessege messege = gson.fromJson(json, InboxMessege.class);
        return messege;
    }

    public void setMessageData(InboxMessege message) {
        String json = gson.toJson(message);
        Log.e("InboxMessege", json);
        editor.putString("message", json);
        editor.commit();
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public AllpostData getPostIDData() {
        String json = preferences.getString("postID", "");
        Log.e("postID", json);
        AllpostData allpostData = gson.fromJson(json, AllpostData.class);
        return allpostData;
    }

    public void setPostIDData(AllpostData allpostData) {
        String json = gson.toJson(allpostData);
        Log.e("postID", json);
        editor.putString("postID", json);
        editor.commit();
    }

    public MessageData getUserMessageData() {
        String json = preferences.getString("Message", "");
        Log.e("Message", json);
        MessageData messageData = gson.fromJson(json, MessageData.class);
        return messageData;
    }

    public void setUserMessageData(MessageData messageData) {
        String json = gson.toJson(messageData);
        Log.e("Message", json);
        editor.putString("Message", json);
        editor.commit();
    }

    public ShowPost getCommentPostData() {
        String json = preferences.getString("ShowPost", "");
        Log.e("ShowPost", json);
        ShowPost showPost = gson.fromJson(json, ShowPost.class);
        return showPost;
    }

    public void setCommentPostData(ShowPost showPost) {
        String json = gson.toJson(showPost);
        Log.e("ShowPost", json);
        editor.putString("ShowPost", json);
        editor.commit();
    }

    public RequestedInstitute getInstituteData() {
        String json = preferences.getString("RequestedInstitute", "");
        Log.e("RequestedInstitute", json);
        RequestedInstitute instituteData = gson.fromJson(json, RequestedInstitute.class);
        return instituteData;
    }

    public void setInstituteData(RequestedInstitute instituteData) {
        String json = gson.toJson(instituteData);
        Log.e("RequestedInstitute", json);
        editor.putString("RequestedInstitute", json);
        editor.commit();
    }

    public Institute getInstitute() {
        String json = preferences.getString("Institute", "");
        Log.e("Institute", json);
        Institute institute = gson.fromJson(json, Institute.class);
        return institute;
    }

    public void setInstitute(Institute institute) {
        String json = gson.toJson(institute);
        Log.e("Institute", json);
        editor.putString("Institute", json);
        editor.commit();
    }

}