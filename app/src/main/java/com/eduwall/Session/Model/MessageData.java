package com.eduwall.Session.Model;

import com.eduwall.Student.Models.Attach;

import java.util.ArrayList;

/**
 * Created by PC10 on 07-Jul-17.
 */

public class MessageData {
    String name;
    String profile;
    String title;
    String description;
    ArrayList<Attach> attachment;
    String email;
    String time;
    String theradID;
    String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTheradID() {
        return theradID;
    }

    public void setTheradID(String theradID) {
        this.theradID = theradID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Attach> getAttachment() {
        return attachment;
    }

    public void setAttachment(ArrayList<Attach> attachment) {
        this.attachment = attachment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
