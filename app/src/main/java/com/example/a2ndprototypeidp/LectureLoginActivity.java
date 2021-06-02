package com.example.a2ndprototypeidp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LectureLoginActivity extends AppCompatActivity {
    private Button login;
    private TextView register;
    private TextInputLayout inputemail, inputpassword;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_login);

        login = findViewById(R.id.button);
        register = findViewById(R.id.new_acc);
        inputemail = findViewById(R.id.email);
        inputpassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SelectRegister.class));
            }
        });








    }

    public void userLogin(View view){
        String email = inputemail.getEditText().getText().toString().trim();
        String password = inputpassword.getEditText().getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            inputemail.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            inputpassword.setError("Password is Required.");
            return;
        }

        if(password.length() < 6){
            inputpassword.setError("Password Must be >=6 Characters");
            return;
        }
        //
        progressBar.setVisibility(View.VISIBLE);








        //Authenticate the user

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {




                    Toast.makeText(LectureLoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                } else{
                    Toast.makeText(LectureLoginActivity.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });








    }

    public void studLogin(View view){
        startActivity(new Intent(getApplicationContext(),StudentLoginActivity.class));
        finish();
    }

}