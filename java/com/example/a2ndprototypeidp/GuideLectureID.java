package com.example.a2ndprototypeidp;

public class GuideLectureID{
    String userID, classCode, subject;

    public GuideLectureID(String userID, String classCode, String subject) {
        this.userID = userID;
        this.classCode = classCode;
        this.subject = subject;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
