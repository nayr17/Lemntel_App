package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lemntelattendancechecker.HelperClass.CreateAccountHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountAdmin extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText password;
    EditText ConfirmPass;

    private String getUsername;
    private String getEmail;
    private String getPassword;
    private String getConfirmppass;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_admin);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        ConfirmPass = findViewById(R.id.ConfirmPassword);
    }

    private boolean validateEmailAddress(EditText CustomerEmail){
        String email = CustomerEmail.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            CustomerEmail.setError("email is not valid");
            return  false;
        }
    }
    private boolean checkPasswordChar(EditText CustomerPass){
        String password = CustomerPass.getText().toString();
        if(password.length()>=5){
            return  true;
        }else {
            CustomerPass.setError("must be aleast 6 characters");
            return  false;
        }
    }
    private boolean checkConfirmPasswordChar(EditText CustomerConfirmPassword){
        String password = CustomerConfirmPassword.getText().toString();
        if(password.length()>=5){
            return  true;
        }else {
            CustomerConfirmPassword.setError("must be aleast 6 characters");
            return  false;
        }
    }


    public void btnCreate(View view) {
        getUsername = username.getText().toString().trim();
        getEmail = email.getText().toString().trim();
        getPassword = password.getText().toString().trim();
        getConfirmppass = ConfirmPass.getText().toString().trim();

        if(TextUtils.isEmpty(getUsername)){
            username.setError("enter username");
            return;
        }
        if(TextUtils.isEmpty(getEmail)){
            email.setError("enter username");
            return;
        }validateEmailAddress(email);
        if(TextUtils.isEmpty(getPassword)){
            password.setError("enter username");
            return;
        }checkPasswordChar(password);
        if(TextUtils.isEmpty(getConfirmppass)){
            ConfirmPass.setError("enter username");
            return;
        }checkConfirmPasswordChar(ConfirmPass);
        if(!getPassword.equals(getConfirmppass)){
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
        }
        if(getPassword.equals(getConfirmppass) && !getPassword.equals(null) && !getConfirmppass.equals(null)){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Admin");
            userRef.orderByChild("username")
                    .equalTo(getUsername)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                username.setError("pick another username");
                            }
                            else {
                                firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                DatabaseReference getUserRef = firebaseDatabase.getReference("Admin");
                                                CreateAccountHelperClass helperClass = new CreateAccountHelperClass(getUsername,getEmail,getPassword);
                                                getUserRef.child(getUsername).setValue(helperClass);

                                                Intent i = new Intent(CreateAccountAdmin.this, MainActivity.class);
                                                startActivity(i);
                                                finish();
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

}