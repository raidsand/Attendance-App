package com.example.a2ndprototypeidp;

public class StudentModel {
    public String mSubject, mCode;

    public  StudentModel(){

    }

    public StudentModel(String mSubject, String mCode) {
        this.mSubject = mSubject;
        this.mCode = mCode;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }
}
