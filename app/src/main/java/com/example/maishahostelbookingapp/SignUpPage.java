package com.example.maishahostelbookingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage extends AppCompatActivity {
    EditText etName, etEmail, etPass, etID, etPhone;

    TextView tvNameError, tvEmailError, tvPasswordError, textView, tvIdError, tvPhoneError;
    CardView card1, card2, card3, card4, btnRegister;
    private boolean isAtleast8 = false, hasNumber = false, hasUpperCase = false, hasSymbol = false, isRegistrationClickable = false;
    FirebaseAuth Auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final String TAG = "SignUpPage";
    private CheckBox mBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Auth = FirebaseAuth.getInstance();


        //hide actionbar
        getSupportActionBar().hide();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etID = findViewById(R.id.etID);
        etPhone = findViewById(R.id.etPhoneNo);

        mBox = (CheckBox) findViewById(R.id.checkBox1);
        String checkBoxText = "I agree to all the <a href='http://www.redbus.in/mob/mTerms.aspx' > Terms and Conditions</a>";
        mBox.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if(mBox.isChecked()){
                    isRegistrationClickable = true;
                    btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.teal_200)));
                }
                else{
                    isRegistrationClickable = false;
                    btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
                }
            }
        });
        tvNameError = findViewById(R.id.tvNameError);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        textView = findViewById(R.id.txt_signin);
        tvPhoneError = findViewById(R.id.tvPhoneError);
        tvIdError = findViewById(R.id.tvIDError);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        btnRegister = findViewById(R.id.bntRegister);
        mBox.setText(Html.fromHtml(checkBoxText));
        mBox.setMovementMethod(LinkMovementMethod.getInstance());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String id = etID.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();

                if (name.length() > 0 && id.length() > 0 && phone.length() > 0 && email.length() > 0 && password.length() > 0&& mBox.isChecked()) {
                    if (isRegistrationClickable) {

                        //registerUser(name, id, phone, email, password);
                        //registerUser(name,id,phone,email,password);
                        Intent intent = new Intent(SignUpPage.this, VerifyPhoneNo.class);
                        intent.putExtra("phone", phone);
                        intent.putExtra("name", name);
                        intent.putExtra("id", id);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);


                    }


                } else {
                    if (name.length() == 0) {
                        tvNameError.setVisibility(View.VISIBLE);
                    }
                    if (id.length() == 0) {
                        tvIdError.setVisibility(View.VISIBLE);
                    }
                    if (phone.length() == 0) {
                        tvPhoneError.setVisibility(View.VISIBLE);
                    }
                    if (email.length() == 0) {
                        tvEmailError.setVisibility(View.VISIBLE);
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        tvEmailError.setVisibility(View.VISIBLE);
                    }
                    if (password.length() == 0) {
                        tvPasswordError.setVisibility(View.VISIBLE);
                    }
                }

            }

        });

        inputChange();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });


    }

    /*private void registerUser(String name, String id, String phone, String email, String password) {
        Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    databaseReference=firebaseDatabase.getReference("Hostel DB");
                    String uid=task.getResult().getUser().getUid();
                    UserData userData=new UserData(uid,name,id,phone,email,0);
                    databaseReference.child(uid).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpPage.this, "Phone number verified and account created successfully", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SignUpPage.this,LoginPage.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignUpPage.this, "phone number verification failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    */
//}

    private void checkEmptyFields(String name ,String id,String phone,String email,String password){
        if(name.length()>0 && tvNameError.getVisibility()==View.VISIBLE){
            tvNameError.setVisibility(View.GONE);
        }
        if(id.length()>0 && tvIdError.getVisibility()==View.VISIBLE){
            tvIdError.setVisibility(View.GONE);
        }
        if(phone.length()>0 && tvPhoneError.getVisibility()==View.VISIBLE){
            tvPhoneError.setVisibility(View.GONE);
        }
        if(email.length()>0 && tvEmailError.getVisibility()==View.VISIBLE){
            tvEmailError.setVisibility(View.GONE);

        }

        if(password.length()>0 && tvPasswordError.getVisibility()==View.VISIBLE){
            tvPasswordError.setVisibility(View.GONE);
        }




    }




    @SuppressLint("ResourceType")
    private void passwordCheck(){
        String name= etName.getText().toString();
        String email=etEmail.getText().toString();
        String id=etID.getText().toString();
        String phone=etPhone.getText().toString();
        String password=etPass.getText().toString();


        checkEmptyFields(name,id,phone,email,password);
//for atleast 8 characters
        if(password.length()>=8 && password.length()<=16){
            isAtleast8=true;
            card1.setCardBackgroundColor(Color.parseColor(getString(R.color.teal_200)));
        }else{
            isAtleast8=false;
            card1.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));

        }
        //for uppercase
        if(password.matches("(.*[A-Z].*)")){
            hasUpperCase=true;
            card2.setCardBackgroundColor(Color.parseColor(getString(R.color.teal_200)));
        }else{
            hasUpperCase=false;
            card2.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));

        }
        //for number
        if(password.matches("(.*[0-9].*)")){
            hasNumber=true;
            card3.setCardBackgroundColor(Color.parseColor(getString(R.color.teal_200)));
        }else{
            hasNumber=false;
            card3.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        //for symbol
        if(password.matches("^(?=.*[_@.()]).*$")){
            hasSymbol=true;
            card4.setCardBackgroundColor(Color.parseColor(getString(R.color.teal_200)));
        }else{
            hasSymbol=false;
            card4.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));
        }
        checkData(email);
    }



    @SuppressLint("ResourceType")
    private void checkData(String email){
        if(isAtleast8 && hasUpperCase && hasNumber && hasSymbol  && email.length()>0 && mBox.isChecked()){
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError("invalid email");
            }else {
                isRegistrationClickable = true;
                btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.teal_200)));
            }
        }else{
            isRegistrationClickable=false;
            btnRegister.setCardBackgroundColor(Color.parseColor(getString(R.color.colorDefault)));


        }

    }
    private void inputChange(){
        mBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }




}