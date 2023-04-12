package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroundFloorRooms extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<SenderModel> list;
    DatabaseReference databaseReference;
    ViewAdapter adapter;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_floor_rooms);
        Auth=FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.recycleview);
        databaseReference= FirebaseDatabase.getInstance().getReference("Hostel DB").child("Room Details").child("ground");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ViewAdapter(this,list,new ViewAdapter.ItemClickListener(){
            @Override
            public void onClickListener(SenderModel senderModel) {
                Intent intent=new Intent(getApplicationContext(),UserActivity.class);
                intent.putExtra("room",senderModel.getRoom());
                intent.putExtra("amount",senderModel.getAmount());
                startActivity(intent);
            }

        });
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    SenderModel user=dataSnapshot.getValue(SenderModel.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}