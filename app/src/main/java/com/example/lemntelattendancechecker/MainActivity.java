package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    private String getUsername;
    private String getPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            Intent i = new Intent(MainActivity.this, MainMainActivity.class);
            startActivity(i);
            finish();
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


    }

    public void linkCreateAccount(View view) {
        Intent i = new Intent(MainActivity.this, CreateAccountAdmin.class);
        startActivity(i);
        finish();
    }

    public void btnLogin(View view) {
        getUsername = username.getText().toString().trim();
        getPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(getUsername)){
            username.setError("enter username");
            return;
        }
        if(TextUtils.isEmpty(getPassword)){
            password.setError("enter password");
            return;
        }

        final DatabaseReference loginRef = FirebaseDatabase.getInstance().getReference("Admin");
        loginRef.orderByChild("username").equalTo(getUsername)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            loginRef.orderByValue().equalTo(getPassword)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()){
                                                DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference("Admin/" + getUsername + "/email");
                                                emailRef.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists()){
                                                            String getEmail = dataSnapshot.getValue().toString().trim();
                                                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                                            firebaseAuth.signInWithEmailAndPassword(getEmail,getPassword)
                                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                                    if(task.isSuccessful()){
                                                                        Intent i = new Intent(MainActivity.this, MainMainActivity.class);
                                                                        i.putExtra("username", getUsername);
                                                                        startActivity(i);
                                                                        finish();
                                                                    }

                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                        }
                        else {
                            Toast.makeText(MainActivity.this,"User not found!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }



}