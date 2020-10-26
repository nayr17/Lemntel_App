package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lemntelattendancechecker.HelperClass.Adapter;
import com.example.lemntelattendancechecker.HelperClass.Model;
import com.example.lemntelattendancechecker.HelperClass.ScanActivityGetResult;
import com.example.lemntelattendancechecker.HelperClass.ViewAttendanceAdpater;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAttendance extends AppCompatActivity
{

    RecyclerView recyclerView;
    ViewAttendanceAdpater adapter;
    String Username;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycleViewAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ScanActivityGetResult> options = new FirebaseRecyclerOptions.Builder<ScanActivityGetResult>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Employee_Attendance"),ScanActivityGetResult.class)
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