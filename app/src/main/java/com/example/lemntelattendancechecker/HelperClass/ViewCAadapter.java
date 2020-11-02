package com.example.lemntelattendancechecker.HelperClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lemntelattendancechecker.AdjustBalance;
import com.example.lemntelattendancechecker.R;
import com.example.lemntelattendancechecker.ViewCashAdvance;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class ViewCAadapter  extends FirebaseRecyclerAdapter<CAbalanceHelper, ViewCAadapter.show_data>
{

    Context context;

    public ViewCAadapter(@NonNull FirebaseRecyclerOptions<CAbalanceHelper> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final show_data holder, int position, @NonNull final CAbalanceHelper model) {



        holder.id.setText(model.getId());
        holder.name.setText("Name: " + model.getName());
        holder.balance.setText("Balance: " + model.getBalance());
        Glide.with(holder.imageView.getContext()).load(model.getPhotoUrl()).into(holder.imageView);

        holder.editBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdjustBalance.class);
                String getID = model.getId();
                i.putExtra("id", getID);

                String getBalance = model.getBalance();
                i.putExtra("balance", getBalance);

//                Toast.makeText(context.context, model.getId(), Toast.LENGTH_SHORT).show();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // to avoid error
                context.startActivity(i);


            }
        });


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
        CardView editBalance;

        public show_data(@NonNull final View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.employeePhoto_CA);
            id = itemView.findViewById(R.id.employeeID_CA);
            name = itemView.findViewById(R.id.Name_CA);
            balance = itemView.findViewById(R.id.balance_CA);
            editBalance = itemView.findViewById(R.id.cardView_balance);

//            editBalance.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
////                    Intent i = new Intent(id.getContext(), AdjustBalance.class);
////                    i.getStringExtra("id", id.getText().toString());
////                    itemView.getContext().startActivity(i);
//
//
//                }
//            });



        }
    }

}
