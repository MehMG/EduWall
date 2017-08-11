package com.eduwall.PrivateUnit.Model;

/**
 * Created by PC10 on 10-Jul-17.
 */

public class Requests {

    String requestId;
    String name;
    String program;
    String indexno;

    String course;
    String subject;
    String module;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getIndexno() {
        return indexno;
    }

    public void setIndexno(String indexno) {
        this.indexno = indexno;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
