package com.example.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class photoview extends AppCompatActivity {

    public static final String EXTRA_IMAGE_URL = "extra_image_url";
    ImageView imageView;
    Intent intent;
  String name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        intent = getIntent();
//        auth= FirebaseAuth.getInstance();
        name = intent.getStringExtra("name");
         imageView = findViewById(R.id.photo);

        // Get the image URL from the intent
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        // Load the image using Picasso
        Picasso.get()
                .load(name)
                .into(imageView);

        // Set a click listener to close the activity when the image is clicked
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
