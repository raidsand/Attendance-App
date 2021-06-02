package com.example.a2ndprototypeidp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class LectureViewClass extends AppCompatActivity {
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_view_class);

        recyclerView = findViewById(R.id.list);

        //Setup the RecyclerView
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

    }

    //Child of Adapter
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtSubject;
        public TextView txtCode;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtSubject = itemView.findViewById(R.id.textSubject);
            txtCode = itemView.findViewById(R.id.textCode);
        }

        public void setTxtSubject(String string) {
            txtSubject.setText(string);
        }


        public void setTxtCode(String string) {
            txtCode.setText(string);
        }
    }

    //function fetch()
    private void fetch() {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String userID = fAuth.getCurrentUser().getUid();
        Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();

        Query query = FirebaseDatabase.getInstance()
                .getReference("Lecture")
                .child(userID);

        FirebaseRecyclerOptions<LectureModel> options =
                new FirebaseRecyclerOptions.Builder<LectureModel>()
                        .setQuery(query, new SnapshotParser<LectureModel>() {
                            @NonNull
                            @Override
                            public LectureModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new LectureModel(snapshot.child("id").getValue().toString(),
                                        snapshot.child("subject").getValue().toString(),
                                        snapshot.child("code").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<LectureModel, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.lecture_class_item_list, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, LectureModel model) {
                holder.setTxtSubject(model.getmSubject());
                holder.setTxtCode(model.getmCode());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LectureClass.class);
                        intent.putExtra("classCode", model.getmCode());
                        startActivity(intent);

                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
    }

    //To see data add these
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //function addSubject() used in lecture view class.xml Onclick Button
    public void addSubject(View view){
        Intent addSub = new Intent(getApplicationContext(), LectureAddSubject.class);
        startActivity(addSub);
        finish();
    }

}