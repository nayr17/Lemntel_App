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
import com.example.lemntelattendancechecker.R;
import com.example.lemntelattendancechecker.SelectEmployee;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends FirebaseRecyclerAdapter<Model, Adapter.show_data>
{
    Context context;
    String check;


    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull show_data holder, final int position, @NonNull Model model)
    {
       holder.id.setText(model.getId());
       holder.name.setText("Name: " + model.getName());

       check = model.getRate();
       if(check == null)
       {
           holder.rate.setVisibility(View.INVISIBLE);
           holder.btnRate.setVisibility(View.VISIBLE);
       }
       if(check != null)
       {
           holder.rate.setVisibility(View.VISIBLE);
           holder.btnRate.setVisibility(View.INVISIBLE);
           holder.rate.setText("Daily Rate: " + model.getRate());
       }
       Glide.with(holder.imageView.getContext()).load(model.getPhotoUrl()).into(holder.imageView);

       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(context, SelectEmployee.class);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(i);


           }
       });

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
        ImageButton btnRate;

        public show_data(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.employeePhoto);
            id = itemView.findViewById(R.id.employeeID);
            name = itemView.findViewById(R.id.Name);
            rate = itemView.findViewById(R.id.rate);
            btnRate = itemView.findViewById(R.id.btnRate);
            cardView = itemView.findViewById(R.id.cardViewEmploy);

        }
    }


}
