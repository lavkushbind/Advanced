package com.example.loginandsignup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.example.home.MainActivity;

public class NewFlash extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flash);

        ImageView splashLogo = findViewById(R.id.splash_logo);
        TextView splashText = findViewById(R.id.splash_text);

        // Load and apply animations
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_fade_in);
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        splashLogo.setVisibility(View.VISIBLE);
        splashText.setVisibility(View.VISIBLE);

        splashLogo.startAnimation(logoAnimation);
        splashText.startAnimation(textAnimation);

        // Delay the transition to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity
                Intent intent = new Intent(NewFlash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
