package com.example.maishahostelbookingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=3000;
    Animation top_anim,bottom_anim;
    ImageView img;
    TextView tv_appname,tv_subappname,tv_copyright;
    @RequiresApi(api = Build.VERSION_CODES.P)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
        setContentView(R.layout.activity_main);
        top_anim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
//hide actionbar

        getSupportActionBar().hide();

        img = findViewById(R.id.image);
        tv_appname = findViewById(R.id.tv_appname);
        tv_subappname = findViewById(R.id.tv_subappname);
        tv_copyright = findViewById(R.id.tv_copyright);

        img.setAnimation(top_anim);
        tv_appname.setAnimation(bottom_anim);
        tv_subappname.setAnimation(bottom_anim);
        tv_copyright.setAnimation(bottom_anim);

        new Handler().postDelayed((Runnable) () -> {
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN);

    }
}