package com.example.lemntelattendancechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PayRoll extends AppCompatActivity {

    String dateFri = "Friday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_roll);




    }

    public void payrollDate()
    {

        Date today1 = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("EEEE");
        String addformat = format1.format(today1);
    }




}