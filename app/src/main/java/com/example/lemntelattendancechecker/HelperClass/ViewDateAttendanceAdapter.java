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

import com.example.lemntelattendancechecker.AdjustBalance;
import com.example.lemntelattendancechecker.R;
import com.example.lemntelattendancechecker.ViewAttendaceFilterDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ViewDateAttendanceAdapter extends RecyclerView.Adapter {

    List<DateAttendanceHelper> getData;
    Context context;

    public ViewDateAttendanceAdapter(List<DateAttendanceHelper> getData, Context context) {
        this.getData = getData;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.format_date,parent,false);
        viewHolderClass viewHolderClass = new viewHolderClass(view);

        return  viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        viewHolderClass viewHolderClass = (viewHolderClass)holder;

        final DateAttendanceHelper dateAttendanceHelper = getData.get(position);
        viewHolderClass.id.setText(dateAttendanceHelper.getId());

        ((viewHolderClass) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ViewAttendaceFilterDate.class);
                String getDate = dateAttendanceHelper.getId();
//                Toast.makeText(context, getID, Toast.LENGTH_SHORT).show();
                i.putExtra("dateSelected", getDate);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // to avoid error
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return getData.size();
    }

    public class viewHolderClass extends RecyclerView.ViewHolder
    {
        TextView id;
        CardView cardView;

        public viewHolderClass(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_date);
            cardView = itemView.findViewById(R.id.cardView_Datefilter);
        }
    }


}
