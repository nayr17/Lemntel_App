package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectEmployee extends AppCompatActivity {

    CircleImageView photo;
    TextView name;
    EditText setDailyRate;
    Button btnSave;

    String ID;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_employee);

        if(firebaseAuth.getCurrentUser() == null)
        {
            Intent i = new Intent(SelectEmployee.this, MainActivity.class);
            finish();
            startActivity(i);
        }

        photo = findViewById(R.id.selectEmployee_Image);
        name = findViewById(R.id.selectEmployee_name);
        setDailyRate = findViewById(R.id.selectEmployee_dailyRate);
        btnSave = findViewById(R.id.selectEmployee_btnSaveDailyRate);

        Intent i = getIntent();
        ID = i.getStringExtra("ID");

        setEmployee();

    }

    public void setEmployee(){

        DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + ID);
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get()
                        .load(snapshot.child("photoUrl").getValue().toString())
                        .into(photo);

                name.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void UploadRate(){
        final String Rate = setDailyRate.getText().toString().trim();
        if(TextUtils.isEmpty(Rate))
        {
            setDailyRate.setError("Field cannot be empty");
            return;
        }
        if(Rate != null && Rate.length() != 0)
        {
            final DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Employees/" + ID);
            uploadRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    uploadRef.child("rate").setValue(Rate)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SelectEmployee.this, "Daily Rate Saved!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SelectEmployee.this, ViewEmployees.class);
                                    finish();
                                    startActivity(i);
                                }
                            });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }


    public void selectEmployee_btnSave(View view) {

        UploadRate();
    }
}