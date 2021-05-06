package com.example.a2ndprototypeidp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StudentAddSubject extends AppCompatActivity {
    private static final String TAG ="MainActivityTAG " ;
    Button add;
    private TextInputLayout class_code; //temp;
    FirebaseAuth fAuth;
    private FirebaseDatabase rootNode;
    private DatabaseReference lectreff, studreff;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_subject);

        add = findViewById(R.id.add_btn);
        //temp = findViewById(R.id.matricNo);
        class_code = findViewById(R.id.class_code);
        fAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        //docRef = fStore.collection("users").document(userID);

        add.setOnClickListener(this::onClick);


    }

    public void readData(Callback Callback) {
        String classCode = class_code.getEditText().getText().toString();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Lecture");

        final Query query = mDatabase.child("GuideID").orderByChild("classCode").equalTo(classCode);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String codeKey = snapshot.child(classCode).child("classCode").getValue(String.class);
                    String subKey = snapshot.child(classCode).child("subject").getValue(String.class);
                    String idKey = snapshot.child(classCode).child("userID").getValue(String.class);

                    Callback.onCallback(codeKey,subKey,idKey);
                    Log.d(TAG, "Inside onDataChange @ readData() " + codeKey + "\n" + idKey);

                } else {
                    class_code.setError("There is no such code");
                    class_code.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void onClick(View view) {
        readData(new Callback() {
            @Override
            public void onCallback(String classCode, String subName, String lectID) {
                Log.d(TAG, "Inside readData @ onCreate " + lectID + "\n" + subName);

                Intent intent2 = getIntent();
                String matricNo = intent2.getStringExtra("matricNo");
                String studID = fAuth.getCurrentUser().getUid();
                //lectID = HiaHjGw1WqfonxZG9bmYVzencPs1

                rootNode = FirebaseDatabase.getInstance();
                lectreff = rootNode.getReference("Lecture").child(lectID).child(classCode);

                StudentHelperClass studentDetails = new StudentHelperClass(studID, matricNo);
                lectreff.child("students").child(matricNo).setValue(studentDetails);


                studreff = rootNode.getReference("Student");
                studreff.child(studID).setValue(studentDetails);


                GuideStudentAddSubject addClass = new GuideStudentAddSubject(subName, classCode);
                studreff.child(studID).child("Class").child(classCode).setValue(addClass);
                //DatabaseReference studentreff = FirebaseDatabase.getInstance().getReference("Student").child(studID).child("Class");


                Toast.makeText(StudentAddSubject.this, "Subject Has Been Added.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), StudentViewClass.class);
                intent.putExtra("matricNo", matricNo);
                startActivity(intent);
            }
        });
    }

    private interface Callback{
        void onCallback(String classCode, String subName, String lectID);

    }



    //private void studentDatabase(StudentHelperClass studentDetails, String subjectName, String classCode) {
    //        String studID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //
    //        DatabaseReference studReff = FirebaseDatabase.getInstance().getReference("Student").child(studID);
    //        studReff.setValue(studentDetails);
    //
    //        DatabaseReference studentreff = FirebaseDatabase.getInstance().getReference("Student").child(studID).child("Class");
    //        GuideStudentAddSubject addClass = new GuideStudentAddSubject(subjectName,classCode);
    //        studentreff.child(classCode).setValue(addClass);
    //
    //        //HashMap map = new HashMap();
    //        //        map.put("code", classCode);
    //        //        map.put("subject", subjectName);
    //        //        studentreff.child(classCode).updateChildren(map);
    //
    //        //GuideStudentAddSubject addSubName = new GuideStudentAddSubject(subjectName,classCode);
    //        //studentreff.child(classCode).setValue(addSubName);
    //    }


    //public String addSubject(String lectID, String subjectName){
    //
    //        Intent intent2 = getIntent();
    //        String matricNo = intent2.getStringExtra("matricNo");
    //        String classCode = class_code.getEditText().getText().toString();
    //        String studID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //        //lectID = HiaHjGw1WqfonxZG9bmYVzencPs1
    //
    //        DatabaseReference pathReff;
    //        pathReff = FirebaseDatabase.getInstance().getReference("Lecture").child(lectID).child(classCode);
    //
    //        StudentHelperClass studentDetails = new StudentHelperClass(studID,matricNo);
    //        pathReff.child("students").child(matricNo).setValue(studentDetails);
    //
    //
    //        studentDatabase(studentDetails,subjectName,classCode);
    //
    //        return matricNo;
    //    }





























    // Callback key = new Callback();
    //
    //                Toast.makeText(StudentAddSubject.this, "lectID is "+ key.getUserID() +"\n"+"subjectName is "+ key.getSubject(), Toast.LENGTH_SHORT).show();
    //
    //                addSubject(key.getUserID(),key.getSubject());         //Still null for getUserID & getSubject

    //public void addSubject(String lectID, String subjectName){
    //
    //        Intent intent2 = getIntent();
    //
    //        String matricNo = intent2.getStringExtra("matricNo");
    //        String classCode = Objects.requireNonNull(class_code.getEditText()).getText().toString();
    //        String studID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
    //
    //        DatabaseReference pathReff = FirebaseDatabase.getInstance().getReference("Lecture").child(lectID).child(classCode);
    //
    //        StudentHelperClass studentDetails = new StudentHelperClass(studID,matricNo);
    //        pathReff.child("students").child(matricNo).setValue(studentDetails);
    //
    //
    //        studentDatabase(studentDetails,subjectName,classCode);
    //
    //
    //        Toast.makeText(StudentAddSubject.this, "Subject successfully created.", Toast.LENGTH_SHORT).show();
    //        startActivity(new Intent(getApplicationContext(), StudentViewClass.class));
    //    }
    //
    //    private void studentDatabase(StudentHelperClass studentDetails, String subjectName, String classCode) {
    //
    //        FirebaseAuth auth = FirebaseAuth.getInstance();
    //        DatabaseReference studReff = FirebaseDatabase.getInstance().getReference("Student").child(auth.getCurrentUser().getUid());
    //
    //        studReff.setValue(studentDetails);
    //
    //        //Problem the add subject will get replace..not updated
    //        //Toast.makeText(this, "Subject Name is " + subjectName, Toast.LENGTH_SHORT).show();
    //
    //
    //        GuideStudentAddSubject addSubName = new GuideStudentAddSubject(subjectName,classCode);
    //        studReff.child("Subject").child(classCode).setValue(addSubName);
    //
    //
    //
    //
    //
    //    }
    //
    //    public void getDataSnapShot() {
    //        String classCode = Objects.requireNonNull(class_code.getEditText()).getText().toString();
    //
    //        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Lecture");
    //
    //        final Query query = mDatabase.child("GuideID").orderByChild("classCode").equalTo(classCode);
    //
    //        query.addValueEventListener(new ValueEventListener() {
    //            @Override
    //            public void onDataChange(@NonNull DataSnapshot snapshot) {
    //                if(snapshot.exists()){
    //                    String lectID = snapshot.child("userID").getValue(String.class);
    //                    String subName = snapshot.child("subject").getValue(String.class);
    //
    //
    //                    addSubject(lectID,subName);
    //
    //                    //Callback callback = snapshot.getValue(Callback.class);
    //
    //                } else {
    //                    class_code.setError("There is no such code");
    //                    class_code.requestFocus();
    //                }
    //            }
    //
    //            @Override
    //            public void onCancelled(@NonNull DatabaseError error) {
    //
    //            }
    //        });
    //
    //    }
    //
    //    public static class Callback{
    //        String classCode, subject, userID;
    //
    //        public Callback(){
    //
    //        }
    //
    //        public Callback(String classCode, String subject, String userID) {
    //            this.classCode = classCode;
    //            this.subject = subject;
    //            this.userID = userID;
    //        }
    //
    //        public String getClassCode() {
    //            return classCode;
    //        }
    //
    //        public void setClassCode(String classCode) {
    //            this.classCode = classCode;
    //        }
    //
    //        public String getSubject() {
    //            return subject;
    //        }
    //
    //        public void setSubject(String subject) {
    //            this.subject = subject;
    //        }
    //
    //        public String getUserID() {
    //            return userID;
    //        }
    //
    //        public void setUserID(String userID) {
    //            this.userID = userID;
    //        }
    //    }

}