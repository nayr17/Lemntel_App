package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PayRoll extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_roll);

        Week();
    }

    public void Week(){

        Date today1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String addformat = format1.format(today1);

        Date today = new Date(Calendar.DAY_OF_WEEK);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(today);

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, 6);
        String week = c.getTime().toString();

        Toast.makeText(PayRoll.this, week, Toast.LENGTH_LONG).show();


    }
}