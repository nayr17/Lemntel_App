package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemntelattendancechecker.HelperClass.Model;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.example.lemntelattendancechecker.HelperClass.Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ViewEmployees extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employees);
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycleViewEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Employees"),Model.class)
                .build();

        adapter = new Adapter(options, getApplicationContext());
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