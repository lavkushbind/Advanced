package com.example.home;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.blank_learn.dark.databinding.ActivityPost2Binding;
import com.example.loginandsignup.Users;
import com.example.payment.PaymentActivity;
import com.example.payment.postmodel;
import com.example.profile.ProActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class post2Activity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    Intent intent;
    ExoPlayer exoPlayer;
    SimpleExoPlayer player;
    ActivityPost2Binding binding;
    PlayerView playerView;
    Context context;
    String paylink;
   String postid;
   String price;
   String stand;
   String name;
   String uri;
   String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPost2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent = getIntent();
        paylink= intent.getStringExtra("Link");
        stand= intent.getStringExtra("stand");
        price= intent.getStringExtra("price");
        postid = intent.getStringExtra("postid");
        name = intent.getStringExtra("postedBy");
        uri = intent.getStringExtra("video");
        topic = intent.getStringExtra("title");
        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        exoPlayer = new ExoPlayer.Builder(this).build();
        exoPlayer = new ExoPlayer.Builder(this ).build();
        binding.videoView.setPlayer(exoPlayer);
        MediaItem mediaItem= MediaItem.fromUri(uri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();
        if (mediaItem != null) {
            exoPlayer.stop();
        }

        database.getReference()
                .child("Users")
                .child(name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Users user= snapshot.getValue(Users.class);
//                            Picasso.get().load(user.getCoverpic())
//                                    //.placeholder(R.drawable.profileuser)
//                                    .into(binding.profilepic);
                            Picasso.get().load(user.getProfilepic())
                                    .placeholder(R.drawable.profileuser)
                                    .into(binding.profilepic);
                            binding.usernm.setText(user.getName());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        database.getReference().child("posts").child(postid).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
                  postmodel postmodel= snapshot.getValue(postmodel.class);
                  binding.NameID.setText(postmodel.getPostdescription());
                  binding.AboutID.setText(postmodel.getAbout());
                //  binding.AboutID.setText(postmodel.getAbout());
                  binding.DurationID.setText(postmodel.getDuration());
                  binding.LanguageID.setText(postmodel.getLanguage());
                 binding.PriceID.setText(postmodel.getPrice());
                 // binding.AboutID.setText(postmodel.getAbout());
                  binding.TimeID.setText(postmodel.getTime());

          }
          @Override
          public void onCancelled(@NonNull DatabaseError error) {
          }
      });





      binding.profilepic.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent= new Intent( post2Activity.this, ProActivity.class);
              intent.putExtra("name",name);

              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
          }
      });

//        binding.paybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                database.getReference().child("Payment").child(auth.getUid()).child("name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                    }
//                });
//            }
//        });


        binding.paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference().child("Payment").child(auth.getUid())
                        .child("name").child(postid).child(topic).setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                });

                database.getReference().child(auth.getUid()).child(postid).setValue(postid).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });

                Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paylink));
                startActivity(urlIntent);
//                Intent intent= new Intent( post2Activity.this, PaymentActivity.class);
//                intent.putExtra("price",price);
//                intent.putExtra("stand",stand);
//                intent.putExtra("postedBy",name);
//                intent.putExtra("postid",postid);
//
//
//               startActivity(intent);
            }
        });

    }

   @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(exoPlayer== null){
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (exoPlayer.isPlaying()){
            exoPlayer.stop();
        }
    }
}