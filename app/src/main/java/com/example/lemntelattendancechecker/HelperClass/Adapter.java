package com.example.lemntelattendancechecker.HelperClass;

import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lemntelattendancechecker.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends FirebaseRecyclerAdapter<Model, Adapter.show_data>
{


    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull show_data holder, final int position, @NonNull Model model)
    {
       holder.id.setText(model.getId());
       holder.name.setText("Name: " + model.getName());
       Glide.with(holder.imageView.getContext()).load(model.getPhotoUrl()).into(holder.imageView);

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
        TextView id, name;

        public show_data(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.employeePhoto);
            id = itemView.findViewById(R.id.employeeID);
            name = itemView.findViewById(R.id.Name);

        }
    }


}
