package com.example.a2ndprototypeidp;

public class StudentListModel {
    public String mId, mMatricNo;

    public StudentListModel(){

    }

    public StudentListModel(String mId, String mMatricNo) {
        this.mId = mId;
        this.mMatricNo = mMatricNo;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmMatricNo() {
        return mMatricNo;
    }

    public void setmMatricNo(String mMatricNo) {
        this.mMatricNo = mMatricNo;
    }
}
