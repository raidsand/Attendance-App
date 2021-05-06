package com.example.a2ndprototypeidp;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

//PROBLEMS!!!!!!
public class StudentRegisterActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    //Variables
    private TextInputLayout regName, regMatric, regEmail, regPhone, regPassword;
    private ProgressBar progressBar;
    private Button regBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        //Hooks to all xml elements in activity_main.xml
        regName = findViewById(R.id.reg_studname);
        regMatric = findViewById(R.id.reg_matric);
        regPhone = findViewById(R.id.reg_phoneNo);
        regEmail = findViewById(R.id.reg_studemail);
        regPassword = findViewById(R.id.reg_studpsswd);
        progressBar = findViewById(R.id.progressBar);
        regBtn = findViewById(R.id.reg_btn);


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
                String matricNo = regMatric.getEditText().getText().toString();

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
                            Toast.makeText(StudentRegisterActivity.this,"User Created.", Toast.LENGTH_SHORT).show();

                            //get user ID from authentication
                            userID = fAuth.getCurrentUser().getUid();
                            //create reff to location to store data
                            DocumentReference docReff = fStore.collection("students").document(userID);

                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("matricNo",matricNo);
                            user.put("password",password);
                            user.put("userID",userID);



                            docReff.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"onSuccess: Student Profile is created for "+ matricNo);
                                }
                            });



                            startActivity(new Intent(getApplicationContext(),MainActivityStudent.class));

                        } else{
                            Toast.makeText(StudentRegisterActivity.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }



        });


    }
}