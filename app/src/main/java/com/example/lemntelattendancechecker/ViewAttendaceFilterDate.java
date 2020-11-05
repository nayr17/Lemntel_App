package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lemntelattendancechecker.HelperClass.ScanActivityGetResult;
import com.example.lemntelattendancechecker.HelperClass.ViewAttendanceAdpater;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAttendaceFilterDate extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewAttendanceAdpater adapter;
    FirebaseAuth firebaseAuth;

    String dateSelected;

    TextView date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendace_filter_date);

        Intent i = getIntent();
        dateSelected = i.getStringExtra("dateSelected");

        date = findViewById(R.id.filerdate_date);
        date.setText(dateSelected);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recycleView_filterdate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<ScanActivityGetResult> options = new FirebaseRecyclerOptions.Builder<ScanActivityGetResult>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Employee_Attendance/" + dateSelected),ScanActivityGetResult.class)
                .build();

        adapter = new ViewAttendanceAdpater(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}