package com.example.lemntelattendancechecker.HelperClass;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lemntelattendancechecker.MainMainActivity;
import com.example.lemntelattendancechecker.R;
import com.example.lemntelattendancechecker.SelectEmployee;
import com.example.lemntelattendancechecker.ViewEmployees;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends FirebaseRecyclerAdapter<Model, Adapter.show_data>
{
    Context context;
    String check;


    String ID;

    int prevCount;
    int newCount;
    String finalCount;
    String name ;
    String getCount;




    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull show_data holder, final int position, @NonNull final Model model)
    {
       holder.id.setText(model.getId());
       holder.name.setText("Name: " + model.getName());



       check = model.getRate();
       if(check == null)
       {
           holder.rate.setVisibility(View.INVISIBLE);
       }
       if(check != null)
       {
           holder.rate.setVisibility(View.VISIBLE);
           holder.rate.setText("Daily rate: " + model.getRate());
       }
       Glide.with(holder.imageView.getContext()).load(model.getPhotoUrl()).into(holder.imageView);

//       holder.cardView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent i = new Intent(context, SelectEmployee.class);
//               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//               context.startActivity(i);
//
//
//           }
//       });

       holder.btnRate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent i = new Intent(context, SelectEmployee.class);
               i.putExtra("ID", model.getId());
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(i);

           }
       });

//       holder.btnPresent.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//               ID = model.getId();
//               name = model.getName();
//               count();
//
//               if(getCount != null)
//               {
//                   newCount = prevCount + 1;
//                   finalCount = Integer.toString(newCount);
//
//                   DatabaseReference countAttendance = FirebaseDatabase.getInstance().getReference("Attendance_Count");
//                   countAttendance.child(ID).setValue(finalCount);
//               }
//               if(getCount == null)
//               {
//                   DatabaseReference countAttendance = FirebaseDatabase.getInstance().getReference("Attendance_Count");
//                   countAttendance.child(ID).setValue(finalCount);
//               }
//
//               Date TodayChildName = new Date();
//               SimpleDateFormat format2 = new SimpleDateFormat("MMM d yyyy (EEEE)");
//               final String childName = format2.format(TodayChildName);
//
//               final String currentTime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
//
//               DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Employees/" + ID);
//               getRef.addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(@NonNull DataSnapshot snapshot) {
//                       if(snapshot.exists()){
//                           final String id = snapshot.child("id").getValue().toString().trim();
//                           final String name = snapshot.child("name").getValue().toString().trim();
//                           String photoUrl = snapshot.child("photoUrl").getValue().toString().trim();
//
//                           final DatabaseReference uploadRef = FirebaseDatabase.getInstance().getReference("Employee_Attendance");
//                           ScanActivityGetResult scanActivityGetResult = new ScanActivityGetResult(id, name, photoUrl, currentTime);
//                           uploadRef.child(childName).push().setValue(scanActivityGetResult)
//                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                       @Override
//                                       public void onComplete(@NonNull Task<Void> task) {
//                                           if(task.isSuccessful())
//                                           {
//                                               Toast.makeText(context, "'" + name + "'" + " has time ", Toast.LENGTH_SHORT).show();
//                                           }
//                                           else
//                                           {
//                                               Toast.makeText(context,"Employee has not been registered", Toast.LENGTH_SHORT).show();
//                                           }
//                                       }
//                                   });
//
//
//
//
//                       }
//                   }
//
//                   @Override
//                   public void onCancelled(@NonNull DatabaseError error) {
//
//                   }
//               });
//
//
//           }
//       });



    }

    @NonNull
    @Override
    public show_data onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fomat_vie_employees, parent, false);
        return new show_data(view);

    }

    class show_data extends RecyclerView.ViewHolder
    {

        CircleImageView imageView;
        CardView cardView;
        TextView id, name, rate;
        ImageButton btnRate, btnPresent;

        public show_data(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.employeePhoto);
            id = itemView.findViewById(R.id.employeeID);
            name = itemView.findViewById(R.id.Name);
            rate = itemView.findViewById(R.id.rate);
            btnRate = itemView.findViewById(R.id.btnRate);
            btnPresent = itemView.findViewById(R.id.btnPresent);
            cardView = itemView.findViewById(R.id.cardViewEmploy);

        }
    }

    public void count(){


        final DatabaseReference countAttendance = FirebaseDatabase.getInstance().getReference("Attendance_Count");
        countAttendance.child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(context, ID, Toast.LENGTH_SHORT).show();



                if(snapshot.exists())
                {
                    finalCount = Integer.toString(0);
                    prevCount = 0;
                    newCount = 0;
                    getCount = snapshot.getValue().toString();
                    Toast.makeText(context, getCount, Toast.LENGTH_SHORT).show();
                    prevCount = Integer.parseInt(getCount);
                }
                else
                {
                    finalCount = Integer.toString(1);
                    prevCount = 0;
                    newCount = 0;
                    getCount = null;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
