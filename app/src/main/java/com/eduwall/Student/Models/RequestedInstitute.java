package com.eduwall.Student.Models;

import java.util.ArrayList;

/**
 * Created by codesture on 9/6/17.
 */
public class RequestedInstitute {
    String id;
    String instituteID;
    String name;
    String status;
    String courseID;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}
