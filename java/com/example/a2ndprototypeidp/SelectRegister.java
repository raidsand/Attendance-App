package com.example.a2ndprototypeidp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRegister extends AppCompatActivity {
    private Button regstudent, reglecture ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_register);

        regstudent = findViewById(R.id.stud_btn);
        reglecture = findViewById(R.id.lect_btn);

        regstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regstud = new Intent(SelectRegister.this,StudentRegisterActivity.class);
                startActivity(regstud);
            }
        });

        reglecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reglect = new Intent(SelectRegister.this,LectureRegisterActivity.class);
                startActivity(reglect);
            }
        });


    }
}