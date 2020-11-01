package com.example.lemntelattendancechecker.HelperClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lemntelattendancechecker.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewCAadapter  extends FirebaseRecyclerAdapter<CAbalanceHelper, ViewCAadapter.show_data>
{
    public ViewCAadapter(@NonNull FirebaseRecyclerOptions<CAbalanceHelper> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull show_data holder, int position, @NonNull CAbalanceHelper model) {

        holder.id.setText(model.getId());
        holder.name.setText("Name: " + model.getName());
        holder.balance.setText("Balance: " + model.getBalance());
        Glide.with(holder.imageView.getContext()).load(model.getPhotoUrl()).into(holder.imageView);

    }

    @NonNull
    @Override
    public ViewCAadapter.show_data onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ca_balance_format, parent, false);
        return new ViewCAadapter.show_data(view);

    }

    class show_data extends RecyclerView.ViewHolder
    {

        CircleImageView imageView;
        TextView id, name, balance;

        public show_data(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.employeePhoto_CA);
            id = itemView.findViewById(R.id.employeeID_CA);
            name = itemView.findViewById(R.id.Name_CA);
            balance = itemView.findViewById(R.id.balance_CA);

        }
    }
}
