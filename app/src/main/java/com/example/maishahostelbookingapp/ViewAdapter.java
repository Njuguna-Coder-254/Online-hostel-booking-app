package com.example.maishahostelbookingapp;

import android.annotation.SuppressLint;

import android.content.Context;

import android.graphics.Color;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<SenderModel>list;
    public ItemClickListener clickListener;

    public ViewAdapter(Context context, ArrayList<SenderModel> list,ItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.userview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SenderModel senderModel = list.get(position);
        holder.room.setText(senderModel.getRoom());
        holder.capacity.setText(senderModel.getCapacity());
        holder.amount.setText(senderModel.getAmount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClickListener(list.get(position));
                holder.itemView.setBackgroundColor(Color.YELLOW);
                holder.itemView.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView room,capacity,amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            room=itemView.findViewById(R.id.roomvalue);
            amount=itemView.findViewById(R.id.amountvalue);
            capacity=itemView.findViewById(R.id.capacityvalue);

        }
    }
    public  interface ItemClickListener{
          public void onClickListener(SenderModel senderModel);

    }
}
