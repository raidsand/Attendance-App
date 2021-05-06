package com.example.a2ndprototypeidp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivityStudent extends AppCompatActivity {
    private TextView fname_txt, matric_txt, email_txt, phone_txt, password_txt;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);

        //hooks TextView
        fname_txt = findViewById(R.id.Fname_txt);
        matric_txt = findViewById(R.id.matric_txt);
        email_txt = findViewById(R.id.email_txt);
        phone_txt = findViewById(R.id.phone_txt);
        password_txt = findViewById(R.id.password_txt);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = fStore.collection("students").document(userID);

        documentReference.addSnapshotListener(this, (value, e) -> {
            if (value != null) {
                fname_txt.setText(value.getString("fName"));
                password_txt.setText(value.getString("password"));
                email_txt.setText(value.getString("email"));
                matric_txt.setText(value.getString("matricNo"));
                phone_txt.setText(value.getString("phone"));
            }

        });


    }

    public void studViewClass(View View){
        Intent passmatric = new Intent(getApplicationContext(),StudentViewClass.class);
        String matric = matric_txt.getText().toString();
        passmatric.putExtra("matricNo", matric);
        startActivity(passmatric);
        finish();
    }




    public void logOut (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Launcher.class));
        finish();
    }
}