package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.example.lemntelattendancechecker.HelperClass.ScanActivityGetResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainMainActivity extends AppCompatActivity {

    TextView adminDisplay;
    FirebaseAuth firebaseAuth;
    String adminUsername;

    String scannedResult;
    String currentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
         firebaseAuth = FirebaseAuth.getInstance();


        adminDisplay = findViewById(R.id.admin);
        adminDisplay.setText("Admin: " + firebaseAuth.getCurrentUser().getEmail());
        currentAdmin = firebaseAuth.getCurrentUser().getEmail();
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
    }

    public void btnViewEmployees(View view) {
        Intent i = new Intent(MainMainActivity.this, ViewEmployees.class);
        startActivity(i);
    }

    public void btnScanAttendance(View view) {
        scanCode();
    }

    public void scanCode()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setCaptureActivity(CaptureActivity.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
        {
            if(result.getContents() != null)
            {
                scannedResult = result.getContents().trim();
                getRefScan();
            }
            else
            {
                Toast.makeText(this,"No Result found", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void getRefScan()
    {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        final String date = format.format(today);

        Date today1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String addformat = format1.format(today1);

       final String final_date = date + " (" + addformat + ")";

        final String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());

        DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + scannedResult);
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String id = snapshot.child("id").getValue().toString().trim();
                    final String name = snapshot.child("name").getValue().toString().trim();
                    String photoUrl = snapshot.child("photoUrl").getValue().toString().trim();

                    DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Employee_Attendance");
                    ScanActivityGetResult scanActivityGetResult = new ScanActivityGetResult(id, name, photoUrl, final_date, currentTime);
                    uploadRef.push().setValue(scanActivityGetResult)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                  if(task.isSuccessful())
                                  {
                                      Toast.makeText(MainMainActivity.this, "'" + name + "'" + " has time in", Toast.LENGTH_LONG).show();
                                  }
                                  else
                                  {
                                      Toast.makeText(MainMainActivity.this,"Employee has not been registered", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void btnViewAttendance(View view) {
        Intent i = new Intent(MainMainActivity.this, ViewAttendance.class);
        startActivity(i);
    }
}