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

                GuideStudentAddSubject addClass = new GuideStudentAddSubject(subName, classCode, studentDetails.getStudentMatricno(), studentDetails.getID());
                studreff.child(studID).child(classCode).setValue(addClass);
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
}