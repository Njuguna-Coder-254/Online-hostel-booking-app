package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firstfloor extends AppCompatActivity {
    EditText etroom, etcapacity, etamaount;
    //TextView tvNameError, tvIDError, tvPhoneError, tvAmount, tvroomnumber;
    Button btnsubmit,btnupdate;
    //private boolean isRegistrationClickable = false;
    FirebaseAuth Auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        Auth = FirebaseAuth.getInstance();


        getSupportActionBar().hide();

        etroom = findViewById(R.id.etroomnumber);
        etcapacity = findViewById(R.id.etcapacity);
        etamaount = findViewById(R.id.etAmount);

        btnsubmit = findViewById(R.id.btn_submit);
        btnupdate=findViewById(R.id.btn_update);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String room = etroom.getText().toString();
                String capacity = etcapacity.getText().toString();
                String amount = etamaount.getText().toString();

                if(room.isEmpty() && capacity.isEmpty() && amount.isEmpty()){
                    Toast.makeText(firstfloor.this,"all fields required",Toast.LENGTH_SHORT).show();
                }else{
                    insertDetails(room,capacity,amount);
                }
            }
        });
    }

    private void insertDetails(String room, String capacity, String amount) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Hostel DB").child("Room Details").child("firstfloor");
        SenderModel senderModel = new SenderModel(room, capacity, amount);
        reference.child(room).setValue(senderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(firstfloor.this, "successful", Toast.LENGTH_SHORT).show();
                    //firebaseUser.sendEmailVerification();
                    Intent intent = new Intent(firstfloor.this, AdminDashboard.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(firstfloor.this, "failed,try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}