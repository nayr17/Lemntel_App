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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountAdmin extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText ConfirmPass;

    private String getEmail;
    private String getPassword;
    private String getConfirmppass;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_admin);

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
        getEmail = email.getText().toString().trim();
        getPassword = password.getText().toString().trim();
        getConfirmppass = ConfirmPass.getText().toString();

        if(TextUtils.isEmpty(getEmail)){
            email.setError("enter email");
            return;
        }validateEmailAddress(email);
        if(TextUtils.isEmpty(getPassword)){
            password.setError("enter password");
            return;
        }checkPasswordChar(password);
        if(TextUtils.isEmpty(getConfirmppass)){
            ConfirmPass.setError("enter password");
            return;
        }checkConfirmPasswordChar(ConfirmPass);
        if(!getPassword.equals(getConfirmppass)){
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
        }
        if(getPassword.equals(getConfirmppass) && !getPassword.equals(null) && !getConfirmppass.equals(null))
        {
            FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent i = new Intent(CreateAccountAdmin.this, MainMainActivity.class);
                            startActivity(i);
                            finish();

                        }
                    });

        }



        }


}