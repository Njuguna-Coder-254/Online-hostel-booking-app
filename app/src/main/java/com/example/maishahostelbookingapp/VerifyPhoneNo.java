package com.example.maishahostelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity {
    Button button;
    String verificationCodeBysystem;
    EditText etOtp;
    FirebaseAuth Auth;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);
        getSupportActionBar().hide();
        Auth=FirebaseAuth.getInstance();
        button=findViewById(R.id.btnVerify);
        etOtp=findViewById(R.id.otp);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        String phone=getIntent().getStringExtra("phone");
        String name=getIntent().getStringExtra("name");
        String id=getIntent().getStringExtra("id");
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");

        sendVerificationToUser(phone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=etOtp.getText().toString();
                if(code.isEmpty()||code.length()<6){
                    etOtp.setError("wrong OTP...");
                    etOtp.requestFocus();
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                //verifyCode(code);
                registerUser(name,id,phone,email,password);
            }
        });
    }

    private void sendVerificationToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+254"+ phone,
                60,
                TimeUnit.SECONDS, this,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBysystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String phone=getIntent().getStringExtra("phone");
            String name=getIntent().getStringExtra("name");
            String id=getIntent().getStringExtra("id");
            String email=getIntent().getStringExtra("email");
            String password=getIntent().getStringExtra("password");

            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar.setVisibility(View.VISIBLE);
                //verifyCode(code);
                registerUser(name,id,phone,email,password);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNo.this, "failed to verify", Toast.LENGTH_SHORT).show();
        }
    };

    private void registerUser(String name, String id, String phone, String email, String password) {
        Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference=firebaseDatabase.getReference("Hostel DB").child("Users");
                    String uid=task.getResult().getUser().getUid();
                    UserData userData=new UserData(uid,name,id,phone,email,0);
                    databaseReference.child(uid).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(VerifyPhoneNo.this, "Phone number verified and account created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(VerifyPhoneNo.this,LoginPage.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(VerifyPhoneNo.this, "phone number verification failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }
    /*private void verifyCode(String codeByUser){
      PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBysystem,codeByUser);
      signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
    Auth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
         if(task.isSuccessful()){
             Intent intent=new Intent(VerifyPhoneNo.this,LoginPage.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent);
         }else{
             Toast.makeText(VerifyPhoneNo.this, "failed", Toast.LENGTH_SHORT).show();
         }
        }
    });*/

    //}

}