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

import com.example.lemntelattendancechecker.HelperClass.CAbalanceHelper;
import com.example.lemntelattendancechecker.HelperClass.CAhelper;
import com.example.lemntelattendancechecker.HelperClass.ScanActivityGetResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CashAdvance extends AppCompatActivity {

    EditText ID;
    EditText amount;
    TextView name;
    CircleImageView photo;
    Button save;
    TextView balance;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    String scannedResult;
    String currentAdmin;

    String getBalance;
    String bal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_advance);
        currentAdmin = firebaseAuth.getCurrentUser().getEmail();

        ID = findViewById(R.id.scanID);
        name = findViewById(R.id.employeeName);
        amount = findViewById(R.id.editTextAmount);
        photo = findViewById(R.id.userPhoto);
        save = findViewById(R.id.saveCA);
        balance = findViewById(R.id.balance);

        name.setVisibility(View.INVISIBLE);
        amount.setVisibility(View.INVISIBLE);
        photo.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        balance.setVisibility(View.INVISIBLE);


    }

    public void btnScanCA(View view) {
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
                ID.setVisibility(View.VISIBLE);
                ID.setText(scannedResult);
                name.setVisibility(View.VISIBLE);
                amount.setVisibility(View.VISIBLE);
                photo.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);


                DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + scannedResult);
                getRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {

                            name.setText(snapshot.child("name").getValue().toString());
                            Picasso.get().load(snapshot.child("photoUrl").getValue().toString())
                                    .into(photo);

                            DatabaseReference getCAref = FirebaseDatabase.getInstance().getReference("Employee_Balance/" + scannedResult);
                            getCAref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists())
                                    {
                                        if(snapshot.child("balance").getValue() != null )
                                        {
                                            bal = snapshot.child("balance").getValue().toString();
                                            balance.setText("Balance: " + snapshot.child("balance").getValue().toString());
                                            balance.setVisibility(View.VISIBLE);

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(CashAdvance.this,"Employee has not been registered", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



//                getRefScan();
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


    public void saveCA(View view) {

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        final String date = format.format(today);

        Date today1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String addformat = format1.format(today1);
        final String final_date = date + " (" + addformat + ")";

        if(TextUtils.isEmpty(amount.getText().toString()))
        {
            amount.setError("field cannot be empty");
            return;
        }
        if(bal != null && amount.length() != 0)
        {
            int previousBalance = Integer.parseInt(bal);
            int newBalance = Integer.parseInt(amount.getText().toString());
            int add = previousBalance + newBalance;
            getBalance = Integer.toString(add);

            DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + scannedResult);
            getRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                       final String name = snapshot.child("name").getValue().toString().trim();
                       final String photoUrl = snapshot.child("photoUrl").getValue().toString().trim();
                        final String CA_Amount = amount.getText().toString().trim();
                        DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Cash_Advance");
                        CAhelper cAhelper = new CAhelper(scannedResult, name, photoUrl, CA_Amount, final_date);
                        uploadRef.child(scannedResult).push().setValue(cAhelper)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        final DatabaseReference upload = FirebaseDatabase.getInstance().getReference("Employee_Balance/" + scannedResult);
                                        upload.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists())
                                                {
                                                    CAbalanceHelper helper = new CAbalanceHelper(getBalance,name,scannedResult,photoUrl);
                                                    upload.setValue(helper)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Intent i = new Intent(CashAdvance.this, MainMainActivity.class);
                                                                    finish();
                                                                    Toast.makeText(CashAdvance.this, "Cash advance save!", Toast.LENGTH_SHORT).show();
                                                                    startActivity(i);
                                                                }
                                                            });
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CashAdvance.this, "error", Toast.LENGTH_SHORT).show();
                                Intent i = getIntent();
                                finish();
                                startActivity(i);
                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(bal == null && amount.length() != 0)
        {
            int newBalance = Integer.parseInt(amount.getText().toString());
            getBalance = Integer.toString(newBalance);

            DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + scannedResult);
            getRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        final String name = snapshot.child("name").getValue().toString().trim();
                        final String photoUrl = snapshot.child("photoUrl").getValue().toString().trim();
                        final String CA_Amount = amount.getText().toString().trim();
                        DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Cash_Advance");
                        CAhelper cAhelper = new CAhelper(scannedResult, name, photoUrl, CA_Amount, final_date);
                        uploadRef.child(scannedResult).push().setValue(cAhelper)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        DatabaseReference upload = FirebaseDatabase.getInstance().getReference("Employee_Balance/" + scannedResult);
                                        CAbalanceHelper helper = new CAbalanceHelper(getBalance, name, scannedResult, photoUrl);
                                        upload.setValue(helper).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i = new Intent(CashAdvance.this, MainMainActivity.class);
                                                finish();
                                                Toast.makeText(CashAdvance.this, "Cash advance save!", Toast.LENGTH_SHORT).show();
                                                startActivity(i);
                                            }
                                        });



                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CashAdvance.this, "error", Toast.LENGTH_SHORT).show();
                                Intent i = getIntent();
                                finish();
                                startActivity(i);
                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }



}