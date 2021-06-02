package com.example.a2ndprototypeidp;

public class StudentHelperClass {
    String StudentID, StudentMatricno;

    public StudentHelperClass(String studentID, String studentMatricno) {
        StudentID = studentID;
        StudentMatricno = studentMatricno;
    }



    public String getStudentMatricno() {
        return StudentMatricno;
    }

    public void setStudentMatricno(String studentMatricno) {
        StudentMatricno = studentMatricno;
    }


    public String getID() {
        return StudentID;
    }

    public void setID(String ID) {
        this.StudentID = ID;
    }
}
