package com.example.chat;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.squareup.picasso.Picasso;

public class FullScreenImageActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_URL = "extra_image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fullscreen_image);

        ImageView imageView = findViewById(R.id.fullScreenImageView);

        // Get the image URL from the intent
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        // Load the image using Picasso
        Picasso.get()
                .load(imageUrl)
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
