package com.example.a2ndprototypeidp;

public class GuideStudentAddSubject {
    public String subName, subCode, matricNo, studentID;

    public GuideStudentAddSubject(){

    }

    public GuideStudentAddSubject(String subName, String subCode, String matricNo, String studentID) {
        this.subName = subName;
        this.subCode = subCode;
        this.matricNo = matricNo;
        this.studentID = studentID;
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

    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
