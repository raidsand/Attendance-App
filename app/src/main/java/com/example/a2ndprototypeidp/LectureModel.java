package com.example.a2ndprototypeidp;

public class LectureModel {
    public String mId, mSubject, mCode;

    public LectureModel(){

    }

    public LectureModel(String mId, String mSubject, String mCode) {
        this.mId = mId;
        this.mSubject = mSubject;
        this.mCode = mCode;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
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
