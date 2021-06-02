package com.example.a2ndprototypeidp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LectureClass extends AppCompatActivity {
    private static final String TAG = "LectureClass";
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    Button onHotspot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_class);
        Log.d(TAG, "onCreate: inside");
        initialize();
        fetch();
    }

    private void initialize() {
        Log.d(TAG, "initialize: inside");
        onHotspot = findViewById(R.id.btn_hotspot);
        recyclerView = findViewById(R.id.studentlist);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtMatric;

        public ImageView checkMark;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.student_list_root);
            txtMatric = itemView.findViewById(R.id.matricNo);
            checkMark = itemView.findViewById(R.id.checkmark);
        }

        public void setTxtMatric(String string){txtMatric.setText(string);}
    }

    private void fetch() {
        Log.d(TAG, "fetch: inside");
        String userID = FirebaseAuth.getInstance().getUid();
        String classCode = getIntent().getExtras().getString("classCode");

        Query query = FirebaseDatabase.getInstance()
                .getReference("Lecture")
                .child(userID).child(classCode).child("students");

        FirebaseRecyclerOptions<StudentListModel> options =
                new FirebaseRecyclerOptions.Builder<StudentListModel>()
                        .setQuery(query, snapshot -> {
                            //Log.d(TAG, "parseSnapshot: "+ snapshot.child("studentMatricNo").getValue().toString());
                            return new StudentListModel(snapshot.child("id").getValue().toString(),
                                    snapshot.child("studentMatricno").getValue().toString());

                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<StudentListModel, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_item_list, parent, false);

                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull StudentListModel model) {
                holder.setTxtMatric(model.getmMatricNo());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(LectureClass.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
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
}