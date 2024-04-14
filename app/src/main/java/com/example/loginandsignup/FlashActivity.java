package com.example.loginandsignup;

//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.WindowManager;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.blank_learn.dark.R;
//import com.example.home.MainActivity;
//
//
//public class FlashActivity extends AppCompatActivity {
// @Override
//protected void onCreate(Bundle savedInstanceState) {
//     super.onCreate(savedInstanceState);
//     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//             WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//     setContentView(R.layout.activity_flash);
//
//     new Handler().postDelayed(new Runnable() {
//         @Override
//         public void run() {
//             Intent i = new Intent(FlashActivity.this, login.class);
////             Intent i = new Intent(FlashActivity.this, signup.class);
//
//             // on below line we are
//             // starting a new activity.
//             startActivity(i);
//
//             // on the below line we are finishing
//             // our current activity.
//             finish();
//         }
//     }, 500);
//
// }}

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.blank_learn.dark.R;
import com.example.home.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_flash);

        // Load data from Firebase and move to MainActivity
        loadDataAndMoveToMain();
    }

    private void loadDataAndMoveToMain() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(FlashActivity.this, login.class);
                        startActivity(i);
                        finish();
                    }
                }, 500); // Delay time in milliseconds
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors if needed
                // For example, show an error message or retry loading data
            }
        });
    }
}
