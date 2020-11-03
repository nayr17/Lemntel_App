package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lemntelattendancechecker.HelperClass.Adapter;
import com.example.lemntelattendancechecker.HelperClass.DateAttendanceHelper;
import com.example.lemntelattendancechecker.HelperClass.Model;
import com.example.lemntelattendancechecker.HelperClass.ScanActivityGetResult;
import com.example.lemntelattendancechecker.HelperClass.ViewAttendanceAdpater;
import com.example.lemntelattendancechecker.HelperClass.ViewDateAttendanceAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendance extends AppCompatActivity
{

    List<DateAttendanceHelper> getData;

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
        getData = new ArrayList<>();

//        FirebaseRecyclerOptions<ScanActivityGetResult> options = new FirebaseRecyclerOptions.Builder<ScanActivityGetResult>()
//                .setQuery(FirebaseDatabase.getInstance().getReference("Employee_Attendance"),ScanActivityGetResult.class)
//                .build();
//
//        adapter = new ViewAttendanceAdpater(options);
//        recyclerView.setAdapter(adapter);

        DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employee_Attendance");
        getRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    DateAttendanceHelper data = ds.getValue(DateAttendanceHelper.class);
                    getData.add(data);
                }
                ViewDateAttendanceAdapter  viewDateAttendanceAdapter = new ViewDateAttendanceAdapter(getData);
                recyclerView.setAdapter(viewDateAttendanceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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