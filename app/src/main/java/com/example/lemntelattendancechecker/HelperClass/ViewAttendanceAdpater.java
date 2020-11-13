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

public class ViewAttendanceAdpater  extends FirebaseRecyclerAdapter<ScanActivityGetResult, ViewAttendanceAdpater.show_data> {

    public ViewAttendanceAdpater(@NonNull FirebaseRecyclerOptions<ScanActivityGetResult> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewAttendanceAdpater.show_data holder, final int position, @NonNull ScanActivityGetResult model)
    {
        holder.id.setText(model.getId());
        holder.name.setText("Name: " + model.getName());
        holder.time.setText("Time: " + model.getTime());
        Glide.with(holder.imageView.getContext()).load(model.getPhotoUrl()).into(holder.imageView);

    }

    @NonNull
    @Override
    public ViewAttendanceAdpater.show_data onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.format, parent, false);
        return new ViewAttendanceAdpater.show_data(view);

    }

    class show_data extends RecyclerView.ViewHolder
    {

        CircleImageView imageView;
        TextView id, name, date, time;

        public show_data(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.employeePhoto_Attendance);
            id = itemView.findViewById(R.id.employeeID_Attendance);
            name = itemView.findViewById(R.id.Name_Attendance);
            time = itemView.findViewById(R.id.Time_Attendance);

        }
    }
}
