package com.example.maishahostelbookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VAdapter extends RecyclerView.Adapter<VAdapter.MyViewHolder> {
    Context context;
    ArrayList<roomModel> list;

    public VAdapter(Context context,ArrayList<roomModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewrooms, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        roomModel roomModel = list.get(position);
        holder.name.setText(roomModel.getName());
        holder.phone.setText(roomModel.getPhone());
        holder.roomno.setText(roomModel.getRoom());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone,roomno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomno = itemView.findViewById(R.id.roomvalue);
            phone = itemView.findViewById(R.id.phonevalue);
            name = itemView.findViewById(R.id.namevalue);

        }
    }
}
