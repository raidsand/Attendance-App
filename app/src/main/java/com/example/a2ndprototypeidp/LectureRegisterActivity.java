package com.example.a2ndprototypeidp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LectureRegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    //Variables
    private TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    private Button regBtn;
    private ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_register);

        //Hooks to all xml elements in activity_main.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regPhone = findViewById(R.id.reg_phone);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_psswd);
        regBtn = findViewById(R.id.reg_btn);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmail.getEditText().getText().toString().trim();
                String password = regPassword.getEditText().getText().toString().trim();
                String fullName = regName.getEditText().getText().toString();
                String phone = regPhone.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();

                if(TextUtils.isEmpty(email)){
                    regEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    regPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    regPassword.setError("Password Must be >=6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Register the user in Firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LectureRegisterActivity.this,"User Created.", Toast.LENGTH_SHORT).show();

                            //get user ID from authentication
                            userID = fAuth.getCurrentUser().getUid();
                            //create reff to location to store data
                            DocumentReference documentReference = fStore.collection("lectures").document(userID);

                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("userName",username);
                            user.put("password",password);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: user Profile is created for "+ userID);
                                }
                            });



                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        } else{
                            Toast.makeText(LectureRegisterActivity.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        }
                    });
                }



        });



    }
}