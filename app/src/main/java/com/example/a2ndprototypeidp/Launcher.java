package com.example.a2ndprototypeidp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Launcher extends AppCompatActivity {
    //declare variables
    Button login, register ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);//hooks button variables
        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.reg_btn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Register = new Intent(getApplicationContext(), SelectRegister.class);
                startActivity(Register);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Login  = new Intent(getApplicationContext(), StudentLoginActivity.class);
                startActivity(Login);
            }
        });




    }
}