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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    private String getEmail;
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

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


    }

    public void linkCreateAccount(View view)
    {
        Intent i = new Intent(MainActivity.this, CreateAccountAdmin.class);
        finish();
        startActivity(i);

    }

    public void btnLogin(View view)
    {
        getEmail = email.getText().toString().trim();
        getPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(getEmail)) {
            email.setError("enter username");
            return;
        }
        if (TextUtils.isEmpty(getPassword)) {
            password.setError("enter password");
            return;
        }
        if(getEmail.length() != 0 && getPassword.length() != 0)
        {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(getEmail, getPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent i = new Intent(MainActivity.this, MainMainActivity.class);
                            startActivity(i);
                        }
                    });
        }
    }


}