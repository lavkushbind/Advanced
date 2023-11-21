package com.example.payment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blank_learn.dark.databinding.ActivityPaymentBinding;
import com.example.dark.clasmodel;
import com.example.home.MainActivity;
import com.example.home.appmodel;
import com.example.home.post2Activity;
import com.example.loginandsignup.login;
import com.example.notification.NotificationModel;
import com.example.profile.ProActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
ActivityPaymentBinding binding;
Intent intent;
Context context;
FirebaseAuth auth;
    String randomKey;
String stand;
    String postid;
    String price;
    String name;

    FirebaseStorage storage;
    FirebaseDatabase database;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         binding = ActivityPaymentBinding.inflate(getLayoutInflater());
         setContentView(binding.getRoot());
         auth = FirebaseAuth.getInstance();
         storage=FirebaseStorage.getInstance();
         database=FirebaseDatabase.getInstance();
         intent = getIntent();


         stand= intent.getStringExtra("stand");

       //  Bundle bundle= getIntent().getExtras();
        binding. textView31.setText(getIntent().getStringExtra("price"));
         price= intent.getStringExtra("price");
         postid = intent.getStringExtra("postid");
         name = intent.getStringExtra("postedBy");
         database.getReference().child("App").child("pay").addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 String link = snapshot.getValue(String.class);

                 // loading that data into rImage
                 // variable which is ImageView
                 Picasso.get().load(link).into(binding.imageView3);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

binding.Pydone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(PaymentActivity.this, MainActivity.class));

        String update= binding.payd.getText().toString();
        binding.payd.setText("");
        database.getReference().child("New payments").child(auth.getUid()).child("name").setValue(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });

        Toast.makeText(PaymentActivity.this, "done", Toast.LENGTH_SHORT).show();
        //Toast.makeText(context this, "done", Toast.LENGTH_SHORT).show();
    randomKey=FirebaseDatabase.getInstance().getReference().push().getKey();
    NotificationModel notificationModel = new NotificationModel();
        intent.putExtra("price",price);
        intent.putExtra("postedBy",name);
        intent.putExtra("postid",postid);
        notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
        notificationModel.setNotificationAt(new Date().getTime());
        notificationModel.setType("payment send");
        FirebaseDatabase.getInstance().getReference()
                .child("notification")
                .child(FirebaseAuth.getInstance().getUid())
                .push()
                .setValue(notificationModel);

        clasmodel clasmodel = new clasmodel();
        clasmodel.setType("buy");
       // clasmodel.setPostpic("");
        clasmodel.setPosttitle("");
        clasmodel.setLink(stand);
        clasmodel.setClasat(new Date().getTime());
        FirebaseDatabase.getInstance().getReference()
                .child("AAclass")
                .child(FirebaseAuth.getInstance().getUid())
                .push()
                .setValue(clasmodel);

        FirebaseDatabase.getInstance().getReference()
                .child("Group")
                .child(stand)
                .child("member")
                .child(FirebaseAuth.getInstance().getUid())
                .push()
                .setValue(randomKey)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {



                    }
                });


    }
});
     }
}