package com.example.a2ndprototypeidp;

public class GuideStudentAddSubject {
    public String subName, subCode;

    public GuideStudentAddSubject(){

    }

    public GuideStudentAddSubject(String subName, String subCode) {
        this.subName = subName;
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }
}
