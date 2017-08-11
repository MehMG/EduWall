package com.eduwall.Student.Models;

/**
 * Created by codesture on 16/6/17.
 */
public class Attach {
    String filename;
    String extension;

    public String getName() {
        return filename;
    }

    public void setName(String name) {
        this.filename = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
