package com.eduwall.Student.Models;

import java.util.ArrayList;

/**
 * Created by codesture on 2/6/17.
 */
public class InboxMessege {

    private String title;
    private String description;
    private String sender_image;
    private String sender_email;
    private String sender_name;
    private String send_at;
    private String email_id;
    private String thread_id;
    private ArrayList<Attach> attachArrayList;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getSender_image ()
    {
        return sender_image;
    }

    public void setSender_image (String sender_image)
    {
        this.sender_image = sender_image;
    }

    public String getSender_email ()
    {
        return sender_email;
    }

    public void setSender_email (String sender_email)
    {
        this.sender_email = sender_email;
    }

    public String getSender_name ()
    {
        return sender_name;
    }

    public void setSender_name (String sender_name)
    {
        this.sender_name = sender_name;
    }

    public String getSend_at ()
    {
        return send_at;
    }

    public void setSend_at (String send_at)
    {
        this.send_at = send_at;
    }

    public String getEmail_id ()
    {
        return email_id;
    }

    public void setEmail_id (String email_id)
    {
        this.email_id = email_id;
    }

    public String getThread_id ()
    {
        return thread_id;
    }

    public void setThread_id (String thread_id)
    {
        this.thread_id = thread_id;
    }

    public ArrayList<Attach> getAttachArrayList() {
        return attachArrayList;
    }

    public void setAttachArrayList(ArrayList<Attach> attachArrayList) {
        this.attachArrayList = attachArrayList;
    }
}
