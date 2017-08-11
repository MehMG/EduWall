package com.eduwall.Student.Models;

/**
 * Created by PC10 on 08-Jul-17.
 */

public class PostAttachment {
    String attachmentPath;
    String extension;

    int videoPlayPositon;




    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public int getVideoPlayPositon() {
        return videoPlayPositon;
    }

    public void setVideoPlayPositon(int videoPlayPositon) {
        this.videoPlayPositon = videoPlayPositon;
    }
}
