package com.example.a2ndprototypeidp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LectureAddSubject extends AppCompatActivity {
    private static final String TAG = "LectureAddSubject";
    EditText start_hr, start_min, end_hr, end_min;
    ToggleButton start_time, end_time;
    CheckBox mon, tue, wed, thur, fri;
    Button add;
    TextInputLayout subName;
    private FirebaseDatabase rootNode;
    private DatabaseReference reff;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_add_subject);
        initialized();

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


            //upload time to database
            uploadTime(Classcode);


            Toast.makeText(LectureAddSubject.this, "Subject successfully created.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LectureViewClass.class));
        });


    }

    private void uploadTime(String Classcode) {
        String startTime, endTime, combine_startTime, combine_endTime;
        String ID = userID;


        if(start_time.isChecked()){
            startTime = "PM";
        } else {
            startTime = "AM";
        }

        if(end_time.isChecked()){
            endTime = "PM";
        } else {
            endTime = "AM";
        }

        combine_startTime = start_hr.getText().toString() + " : " + start_min.getText().toString() +" " + startTime;
        combine_endTime = end_hr.getText().toString() + " : " + end_min.getText().toString() +" " + endTime;
        String realStartTime = start_hr.getText().toString() + " : " + start_min.getText().toString() +" " + startTime;
        Log.d(TAG, "uploadTime: The real Start Time is " + realStartTime);

        LectureTimeSlot timeSlot =
                new LectureTimeSlot(startTime, endTime,
                        start_hr.getText().toString(),
                        start_min.getText().toString(),
                        end_hr.getText().toString(),
                        end_min.getText().toString(), combine_startTime, combine_endTime);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Lecture").child(ID);
        reference.child(Classcode).child("TimeSlot").setValue(timeSlot);

        uploadDay(ID, Classcode);


    }

    private void uploadDay(String ID, String Classcode) {
        Log.d(TAG, "uploadDay: inside");
        ArrayList<String> days = new ArrayList<>();

        if(mon.isChecked()){
            days.add("Monday");
            Log.d(TAG, "onClick: monday ");
        }
        if(tue.isChecked()){
            days.add("Tuesday");
            Log.d(TAG, "onClick: tuesday");
        }
        if(wed.isChecked()){
            days.add("Wednesday");
        }
        if(thur.isChecked()){
            days.add("Thursday");
        }
        if(fri.isChecked()){
            days.add("Friday");
        }

        for(int i = 0; i < days.size(); i++){
            Log.d(TAG, "uploadDay: days = " + days.get(i));
        }


        LectureDaySlot lectureDaySlot = new LectureDaySlot(days);
        DatabaseReference refF = FirebaseDatabase.getInstance().getReference("Lecture");
        refF.child(ID).child(Classcode).child("TimeSlot").child("DaySlot").setValue(lectureDaySlot);
    }

    private void initialized() {
        add = findViewById(R.id.add_btn);
        subName = findViewById(R.id.sub_name);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        //Class Time
        start_hr = findViewById(R.id.start_hour);
        start_min = findViewById(R.id.start_min);
        end_hr = findViewById(R.id.end_hour);
        end_min = findViewById(R.id.end_min);
        start_time = findViewById(R.id.start_time_toggle);
        end_time = findViewById(R.id.end_time_toggle);
        mon = findViewById(R.id.day_monday);
        tue = findViewById(R.id.day_tuesday);
        wed = findViewById(R.id.day_wednesday);
        thur = findViewById(R.id.day_thursday);
        fri = findViewById(R.id.day_friday);

    }

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString() {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(7);
        for(int i = 0; i< 7; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    private class LectureTimeSlot {
        String startTime, endTime, start_hour, start_min, end_hour, end_min, real_startTime, real_endTime;

        public String getReal_startTime() {
            return real_startTime;
        }

        public void setReal_startTime(String real_startTime) {
            this.real_startTime = real_startTime;
        }

        public LectureTimeSlot(String startTime, String endTime, String start_hour, String start_min, String end_hour, String end_min, String real_startTime, String real_endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.start_hour = start_hour;
            this.start_min = start_min;
            this.end_hour = end_hour;
            this.end_min = end_min;
            this.real_startTime = real_startTime;
            this.real_endTime = real_endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStart_hour() {
            return start_hour;
        }

        public void setStart_hour(String start_hour) {
            this.start_hour = start_hour;
        }

        public String getStart_min() {
            return start_min;
        }

        public void setStart_min(String start_min) {
            this.start_min = start_min;
        }

        public String getEnd_hour() {
            return end_hour;
        }

        public void setEnd_hour(String end_hour) {
            this.end_hour = end_hour;
        }

        public String getEnd_min() {
            return end_min;
        }

        public void setEnd_min(String end_min) {
            this.end_min = end_min;
        }

        public String getReal_endTime() {
            return real_endTime;
        }

        public void setReal_endTime(String real_endTime) {
            this.real_endTime = real_endTime;
        }
    }

    private class LectureDaySlot {
        ArrayList<String> Days;


        public LectureDaySlot(ArrayList<String> days) {
            Days = days;
        }

        public ArrayList<String> getDays() {
            return Days;
        }

        public void setDays(ArrayList<String> days) {
            Days = days;
        }
    }
}

