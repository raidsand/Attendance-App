package com.example.a2ndprototypeidp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class LectureAddSubject extends AppCompatActivity {
    Button add;
    TextInputLayout subName;
    private FirebaseDatabase rootNode;
    private DatabaseReference reff;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_add_subject);

        add = findViewById(R.id.add_btn);
        subName = findViewById(R.id.sub_name);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        //FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();



        add.setOnClickListener(view -> {
            String Subname = Objects.requireNonNull(subName.getEditText()).getText().toString();
            String Classcode = getRandomString();
            String ID = userID;

            rootNode = FirebaseDatabase.getInstance();
            reff = rootNode.getReference("Lecture").child(ID);

            LectureHelperClass helperClass = new LectureHelperClass(Subname,Classcode,ID);
            reff.child(Classcode).setValue(helperClass);
            //rootNode.getReference(Classcode).setValue(helperClass);

            GuideLectureID guideID = new GuideLectureID(ID,Classcode,Subname);
            rootNode.getReference("Lecture").child("GuideID").child(Classcode).setValue(guideID);




            Toast.makeText(LectureAddSubject.this, "Subject successfully created.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LectureViewClass.class));
        });


    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString() {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(7);
        for(int i = 0; i< 7; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}

