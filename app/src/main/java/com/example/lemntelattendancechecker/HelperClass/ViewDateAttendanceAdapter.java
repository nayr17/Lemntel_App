package com.example.lemntelattendancechecker.HelperClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lemntelattendancechecker.R;

import java.util.List;

public class ViewDateAttendanceAdapter extends RecyclerView.Adapter {

    List<DateAttendanceHelper> getData;

    public ViewDateAttendanceAdapter(List<DateAttendanceHelper> getData) {
        this.getData = getData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.format_date,parent,false);
        viewHolderClass viewHolderClass = new viewHolderClass(view);

        return  viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        viewHolderClass viewHolderClass = (viewHolderClass)holder;

        DateAttendanceHelper dateAttendanceHelper = getData.get(position);
        viewHolderClass.id.setText(dateAttendanceHelper.getId());



    }

    @Override
    public int getItemCount() {
        return getData.size();
    }

    public class viewHolderClass extends RecyclerView.ViewHolder
    {
        TextView id;

        public viewHolderClass(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_date);
        }
    }


}
