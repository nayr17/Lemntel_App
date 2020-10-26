package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;



import com.google.firebase.auth.FirebaseAuth;


public class MainMainActivity extends AppCompatActivity {

    TextView adminDisplay;
    FirebaseAuth firebaseAuth;
    String adminUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
         firebaseAuth = FirebaseAuth.getInstance();
         Intent i = getIntent();
         adminUsername = i.getStringExtra("username");

        adminDisplay = findViewById(R.id.admin);
        adminDisplay.setText("Admin: " + firebaseAuth.getCurrentUser().getEmail());
    }

    public void btnAddEmployee(View view) {
        Intent i = new Intent(MainMainActivity.this, AddEmployee.class);
        i.putExtra("Username", adminUsername);
        startActivity(i);
    }

    public void btnLogout(View view) {
        firebaseAuth.signOut();
        Intent i = new Intent(MainMainActivity.this, MainMainActivity.class);
        startActivity(i);
        finish();
    }

}