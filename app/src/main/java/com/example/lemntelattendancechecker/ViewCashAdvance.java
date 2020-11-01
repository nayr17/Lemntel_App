package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lemntelattendancechecker.HelperClass.Adapter;
import com.example.lemntelattendancechecker.HelperClass.CAbalanceHelper;
import com.example.lemntelattendancechecker.HelperClass.Model;
import com.example.lemntelattendancechecker.HelperClass.ViewCAadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ViewCashAdvance extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewCAadapter adapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cash_advance);

        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.CArecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CAbalanceHelper> options = new FirebaseRecyclerOptions.Builder<CAbalanceHelper>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Employee_Balance"),CAbalanceHelper.class)
                .build();

        adapter = new ViewCAadapter(options);
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