package com.example.a2ndprototypeidp;

public class LectureHelperClass {
    String subject;
    String ClassCode;
    String LectureID;
    String Student;

    public LectureHelperClass(String subject, String classCode, String lectureID) {
        this.subject = subject;
        this.ClassCode = classCode;
        this.LectureID = lectureID;
        //this.Student = student;
    }


    //public String getStudent() { return Student; }
    //public void setStudent(String student) { Student = student; }



    public String getID() {
        return LectureID;
    }
    public void setID(String LectureId) {
        this.LectureID = LectureId;
    }



    public String getSubject() {
        return subject;
    }
    public void setSubject(String SubjectName) {
        this.subject = SubjectName;
    }



    public String getCode() {
        return ClassCode;
    }
    public void setCode(String ClassCode) {
        this.ClassCode = ClassCode;
    }
}
