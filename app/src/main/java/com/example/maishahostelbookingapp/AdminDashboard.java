package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth Auth;
    CardView cardView1,cardView2,cardView3,cardView4,cardView5,card6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Auth=FirebaseAuth.getInstance();

        cardView1=findViewById(R.id.addrooms);
        cardView2=findViewById(R.id.viewrooms);
        cardView3=findViewById(R.id.firstfloor);
        cardView4=findViewById(R.id.secondfloor);
        cardView5=findViewById(R.id.thirdfloor);
        card6=findViewById(R.id.logout);
        //implementation of cardViews
        cardView1.setOnClickListener(this::onClick);
        cardView2.setOnClickListener(this::onClick);
        cardView3.setOnClickListener(this::onClick);
        cardView4.setOnClickListener(this::onClick);
        cardView5.setOnClickListener(this::onClick);
        card6.setOnClickListener(this::onClick);

        getSupportActionBar().
                setTitle("Welcome Admin...");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addrooms:
                Intent intent=new Intent(AdminDashboard.this,RoomDetails.class);
                startActivity(intent);
                break;
            case R.id.firstfloor:
             intent=new Intent(AdminDashboard.this,firstfloor.class);
            startActivity(intent);
            break;
            case R.id.secondfloor:
                intent=new Intent(AdminDashboard.this,secondfloor.class);
                startActivity(intent);
                break;
            case R.id.thirdfloor:
                intent=new Intent(AdminDashboard.this,thirdfloor.class);
                startActivity(intent);
                break;
            case R.id.viewrooms:
                intent=new Intent(AdminDashboard.this,BookedRooms.class);
                startActivity(intent);
                break;
            case R.id.logout:
                intent=new Intent(AdminDashboard.this,GenReport.class);
                startActivity(intent);
                finish();
                break;

        }


    }
    //creating ActionBar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate items
        getMenuInflater().inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //when any menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id= item.getItemId();
        if(id==R.id.home){
            Intent intent=new Intent(AdminDashboard.this,AdminDashboard.class);
            //clear stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //finish();
        }
        if(id==R.id.logout){

            Auth.signOut();
            Toast.makeText(AdminDashboard.this,"You are logged out",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(AdminDashboard.this,LoginPage.class);
            //clear stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //finish();

        }



        return super.onOptionsItemSelected(item);
    }


}