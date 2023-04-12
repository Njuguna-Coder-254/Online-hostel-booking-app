package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    TextView txtview;
    private TextInputLayout inpemail,inppass;
    private static final String TAG="LoginPage";
    FirebaseAuth Auth;
    Button btnlogin,btnforgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        inpemail=findViewById(R.id.edt_email);
        inppass=findViewById(R.id.edt_pass);
        btnlogin=findViewById(R.id.btn_login);
        btnforgotpass=findViewById(R.id.btn_forgot_pass);
        Auth=FirebaseAuth.getInstance();

        //hide actionbar
        getSupportActionBar().hide();

        btnforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginPage.this,"You can now reset your password",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginPage.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
        //login user
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textemail=inpemail.getEditText().getText().toString();
                String textpassword=inppass.getEditText().getText().toString();
                if(TextUtils.isEmpty(textemail)){
                    inpemail.setError("field required");
                    inpemail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textemail).matches()){
                    inpemail.setError("enter a valid email");
                    inpemail.requestFocus();

                }else if(TextUtils.isEmpty(textpassword)){
                    inppass.setError("field required!");
                    inppass.requestFocus();
                }else{
                    loginUser(textemail,textpassword);
                }
            }
        });

        txtview=findViewById(R.id.txtsignup);
        txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginPage.this,SignUpPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
    }

    private void loginUser(String email, String password) {
        Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid=task.getResult().getUser().getUid();
                    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference("Hostel DB").child("Users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int usertype =snapshot.getValue(Integer.class);
                            if(usertype==0) {
                                Toast.makeText(LoginPage.this, "you are now logged in as user", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginPage.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }
                            if(usertype==1){
                                Toast.makeText(LoginPage.this, "you are now logged in as admin", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginPage.this, AdminDashboard.class);
                                startActivity(intent);
                                finish();

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });






                }else{
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        inpemail.setError("user with this email does not exist");
                        inpemail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        inppass.setError("Invalid credentials");
                        inppass.requestFocus();
                    }catch(Exception e){
                        Log.e(TAG,e.getMessage());
                    }
                    Toast.makeText(LoginPage.this,"Login failed.Try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}