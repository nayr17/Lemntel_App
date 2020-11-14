package com.example.lemntelattendancechecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdjustBalance extends AppCompatActivity {

    CircleImageView image;
    TextView id;
    TextView balance;
    EditText amount;

    String idRef;
    String getBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_balance);

        image = findViewById(R.id.employeePhoto_adjustBalance);
        id = findViewById(R.id.id_adjustBalance);
        balance = findViewById(R.id.balance_adjustBalance);
        amount = findViewById(R.id.amount_adjustBalance);

        Intent i = getIntent();
        idRef = i.getStringExtra("id");
        getBalance = i.getStringExtra("balance");

        DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + idRef);
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String photoUrl = snapshot.child("photoUrl").getValue().toString();
                    Picasso.get()
                            .load(photoUrl)
                            .into(image);

                    id.setText(idRef);
                    balance.setText("Balance: " + getBalance);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void btnUpdate(View view) {
        String getAmount = amount.getText().toString();
        if(TextUtils.isEmpty(getAmount))
        {
            amount.setError("field cannot be empty");
            return;
        }

        int previousBalance = Integer.parseInt(getBalance);
        int deduction = Integer.parseInt(getAmount);
        int newBalance = previousBalance - deduction;
        final String finalBalance = Integer.toString(newBalance);

        if(getAmount != null && getBalance != null)
        {
            final DatabaseReference update = FirebaseDatabase.getInstance().getReference("Employee_Balance/" + idRef + "/balance");
            update.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        update.setValue(finalBalance)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdjustBalance.this, "balance has been updated", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent i = new Intent(AdjustBalance.this, MainMainActivity.class);
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