package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserActivity extends AppCompatActivity {
    EditText etName, etphone, etID, etRm, Amount;
    //TextView tvNameError, tvIDError, tvPhoneError, tvAmount, tvroomnumber;
    Button btnbook;
    //private boolean isRegistrationClickable = false;
    FirebaseAuth Auth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        getSupportActionBar().hide();

        etName = findViewById(R.id.etName);
        etphone = findViewById(R.id.etphone);
        etID = findViewById(R.id.etID);
        etRm = findViewById(R.id.roomnumber);
        Amount = findViewById(R.id.amount);
        btnbook = findViewById(R.id.btn_book);

        Intent i=getIntent();
        String num=i.getStringExtra("room");
        String total=i.getStringExtra("amount");
        etRm.setText(num);
        Amount.setText(total);
        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                String phone=etphone.getText().toString();
                String room=etRm.getText().toString();
                String amount=Amount.getText().toString();
                Intent intent=new Intent(UserActivity.this,MpesaActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
                if(!name.isEmpty()&&!phone.isEmpty()&&!room.isEmpty()){
                    savedata(name,phone,room,amount);

                }
            }
        });
        databaseReference = firebaseDatabase.getReference("Hostel DB").child("Users").child(Auth.getUid());
        //databaseReference=firebaseDatabase.getReference().child("Room Details");

        getdata();
    }



    private void savedata(String name, String phone,String room,String amount) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Hostel DB").child("Booking Details");
        roomModel roomModel=new roomModel(name,phone,room,amount);
        reference.child(name).setValue(roomModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(UserActivity.this,"Bookimg successful",Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(UserActivity.this,"failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               // etName.setText(snapshot.getValue(String.class));
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                etName.setText((String) map.get("name"));
                etID.setText((String) map.get("id"));
                etphone.setText((String) map.get("phone"));
                //etRm.setText((String)map.get("room"));
                //Amount.setText((String)map.get("amount"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });



    }

   /* private void data() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();


                etRmNo.setText((String)map.get("room"));
                etAmount.setText((String)map.get("amount"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });
    }*/
}