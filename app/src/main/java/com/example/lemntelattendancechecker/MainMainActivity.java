package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lemntelattendancechecker.HelperClass.CountHelper;
import com.example.lemntelattendancechecker.HelperClass.ScanActivityGetResult;
import com.example.lemntelattendancechecker.HelperClass.childNameHelper;
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

import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainMainActivity extends AppCompatActivity {

    TextView adminDisplay;
    TextView date;
    TextView count;
    ImageButton btnPayroll;
    FirebaseAuth firebaseAuth;
    String adminUsername;

    String scannedResult;
    String currentAdmin;

    int prevCount;
    int newCount;
    String finalCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
         firebaseAuth = FirebaseAuth.getInstance();


        adminDisplay = findViewById(R.id.admin);
        adminDisplay.setText("Admin: " + firebaseAuth.getCurrentUser().getEmail());
        currentAdmin = firebaseAuth.getCurrentUser().getEmail();

        Date TodayChildName = new Date();
        SimpleDateFormat format2 = new SimpleDateFormat("MMM d yyyy (EEEE)");
        String dateToday = format2.format(TodayChildName);

        date = findViewById(R.id.dateToday);
        date.setText("DATE: " + dateToday);

        btnPayroll = findViewById(R.id.payroll);

        count = findViewById(R.id.count);


    }


    public void btnAddEmployee(View view) {
        Intent i = new Intent(MainMainActivity.this, AddEmployee.class);
        i.putExtra("Username", adminUsername);
        startActivity(i);
    }

    public void btnLogout(View view) {
        firebaseAuth.signOut();
        Intent i = new Intent(MainMainActivity.this, MainActivity.class);
        finish();
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

        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("Attendance_Count/" + scannedResult);
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String get = snapshot.child("count").getValue().toString();
                    Toast.makeText(MainMainActivity.this, get , Toast.LENGTH_SHORT).show();
                  count.setText(get);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String get = count.getText().toString();
//        prevCount = Integer.parseInt(get);
//        if(prevCount == 0)
//        {
//            finalCount = Integer.toString(1);
//            DatabaseReference upload = FirebaseDatabase.getInstance().getReference("Attendance_Count");
//            CountHelper countHelper = new CountHelper(finalCount);
//            upload.child(scannedResult).setValue(countHelper)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            prevCount = 0;
//                        }
//                    });
//        }
//        if(prevCount != 0)
//        {
//            newCount = prevCount + 1;
//            finalCount = Integer.toString(newCount);
//
//            DatabaseReference upload = FirebaseDatabase.getInstance().getReference("Attendance_Count");
//            CountHelper countHelper = new CountHelper(finalCount);
//            upload.child(scannedResult).setValue(countHelper)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                                prevCount = 0;
//                        }
//                    });
//        }


            Date TodayChildName = new Date();
            SimpleDateFormat format2 = new SimpleDateFormat("MMM d yyyy (EEEE)");
            final String childName = format2.format(TodayChildName);
//        final String ChildName = " ' " + childName + " ' ";


//        Toast.makeText(this, childName, Toast.LENGTH_LONG).show();

//        Date today = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
//        final String date = format.format(today);
//
//        Date today1 = new Date();
//        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
//        String addformat = format1.format(today1);
//
//       final String final_date = date + " (" + addformat + ")";

            final String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());


            DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + scannedResult);
            getRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        final String id = snapshot.child("id").getValue().toString().trim();
                        final String name = snapshot.child("name").getValue().toString().trim();
                        String photoUrl = snapshot.child("photoUrl").getValue().toString().trim();

                        final DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Employee_Attendance");
                        ScanActivityGetResult scanActivityGetResult = new ScanActivityGetResult(id, name, photoUrl, currentTime);
                        uploadRef.child(childName).push().setValue(scanActivityGetResult)
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

    public void btnCA(View view) {
        Intent i = new Intent(MainMainActivity.this, CashAdvance.class);
        startActivity(i);
    }

    public void btnViewCA(View view) {
        Intent i = new Intent(MainMainActivity.this, ViewCashAdvance.class);
        startActivity(i);
    }

    public void btnPayroll(View view) {
        Date today1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String date = format1.format(today1);
        Toast.makeText(MainMainActivity.this, date,Toast.LENGTH_LONG).show();

        if(!date.equals("Friday"))
        {
            Toast.makeText(MainMainActivity.this, "Payroll is only available on Friday!",Toast.LENGTH_LONG).show();
        }
        if(date .equals("Friday"))
        {
            Intent i = new Intent(MainMainActivity.this, PayRoll.class);
            startActivity(i);
        }

    }
}