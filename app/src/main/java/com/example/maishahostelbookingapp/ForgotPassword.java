package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button btnreset;
    TextInputLayout inpemail;
    FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        inpemail=findViewById(R.id.edt_email);
        btnreset=findViewById(R.id.btn_reset);
        Auth=FirebaseAuth.getInstance();
        //hide actionbar
        getSupportActionBar().hide();

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textemail=inpemail.getEditText().getText().toString();
                if(TextUtils.isEmpty(textemail)){
                    inpemail.setError("Email required");
                    inpemail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()){
                    inpemail.setError("enter a valid email");
                    inpemail.requestFocus();

                }else{
                    resetpassword(textemail);
                }
            }
        });
    }

    private void resetpassword(String textemail) {
        Auth=FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);
        // progressDialog.setMessage("verifying..");
        //progressDialog.show();
        Auth.sendPasswordResetEmail(textemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Please check your inbox for password reset link",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ForgotPassword.this,LoginPage.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(ForgotPassword.this,"Something went wrong,try again",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}