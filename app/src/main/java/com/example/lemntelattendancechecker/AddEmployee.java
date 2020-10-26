package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lemntelattendancechecker.HelperClass.EmployeeHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmployee extends AppCompatActivity {

    EditText getID;
    EditText getname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        getID = findViewById(R.id.lemntel_ID);
        getname = findViewById(R.id.fullname);

    }


    public void saveEmployee(View view) {
       String ID = getID.getText().toString().trim();
       String name = getname.getText().toString().trim();

        if(TextUtils.isEmpty(ID)){
            getID.setError("field cannot be empty!!!");
            return;
        }
        if(TextUtils.isEmpty(name)){
            getname.setError("field cannot be empty!!!");
            return;
        }
        if(name.length() != 0 && ID.length() !=0 && name != null && ID !=null){
            DatabaseReference addRef = FirebaseDatabase.getInstance().getReference("Employees");
            EmployeeHelper helper = new EmployeeHelper(ID,name);
            addRef.child(ID).setValue(helper).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(AddEmployee.this, "employee is successfully added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddEmployee.this, MainMainActivity.class);
                    startActivity(i);
                }
            });

        }

    }
}