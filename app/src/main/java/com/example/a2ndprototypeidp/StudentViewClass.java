package com.example.a2ndprototypeidp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class StudentViewClass extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_class);

        recyclerView = findViewById(R.id.list);

        //Setup the RecyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //fetch kene olah bagi sesuai utk stud punya need
        //fetch();
    }












    public void studAddSub(View view){
        //passing String MatricNo
        Intent receivematric = getIntent();
        String matricNo = receivematric.getStringExtra("matricNo");
        Intent passmatric = new Intent(getApplicationContext(),StudentAddSubject.class);
        passmatric.putExtra("matricNo", matricNo);

        startActivity(passmatric);
        finish();
    }
}