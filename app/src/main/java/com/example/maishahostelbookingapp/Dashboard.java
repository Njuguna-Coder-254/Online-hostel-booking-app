package com.example.maishahostelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    CardView cardView1,cardView2,cardView3,cardView4;
    FirebaseAuth Auth;


    //Toolbar toolbar;
    private ViewPager2 viewPager2;
    private List<Image> imageList;
    private ImageAdapter adapter;
    private Handler sliderHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Auth = FirebaseAuth.getInstance();

        viewPager2 = findViewById(R.id.viewPager2);
        imageList = new ArrayList<>();
        imageList.add(new Image(R.drawable.imge11));
        imageList.add(new Image(R.drawable.imge12));
        imageList.add(new Image(R.drawable.imge13));
        imageList.add(new Image(R.drawable.imge14));
        imageList.add(new Image(R.drawable.imge15));
        imageList.add(new Image(R.drawable.onebed));


        adapter = new ImageAdapter(imageList, viewPager2);
        viewPager2.setAdapter(adapter);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);

        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.14f);
            }
        });
        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000);
            }
        });


        //toolbar=findViewById(R.id.toolbar);

        cardView1 = findViewById(R.id.cardone);
        cardView2 = findViewById(R.id.cardtwo);
        cardView3 = findViewById(R.id.cardthree);
        cardView4 = findViewById(R.id.cardfour);


        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);

        getSupportActionBar().
                setTitle("Welcome");
    }

    private  Runnable sliderRunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable,2000);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.cardone:
                intent=new Intent(Dashboard.this,GroundFloorRooms.class);
                startActivity(intent);
                break;
            case R.id.cardtwo:
                intent=new Intent(Dashboard.this,FirstfloorRooms.class);
                startActivity(intent);
                break;
            case R.id.cardthree:
                intent=new Intent(Dashboard.this,SecondFloorRooms.class);
                startActivity(intent);
                break;
            case R.id.cardfour:
                intent=new Intent(Dashboard.this,ThirdFloorRooms.class);
                startActivity(intent);
                break;
            default:




        }

    }
    //creating ActionBar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate items
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //when any menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id= item.getItemId();
        if(id==R.id.location){
            Intent intent=new Intent(Dashboard.this,GoogleMapAPI.class);
            //clear stack
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //finish();
        }
        if(id==R.id.logout){

            Auth.signOut();
            Toast.makeText(Dashboard.this,"You are logged out",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Dashboard.this,LoginPage.class);
            //clear stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }



        return super.onOptionsItemSelected(item);
    }

}