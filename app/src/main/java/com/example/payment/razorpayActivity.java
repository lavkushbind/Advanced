package com.example.payment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//import com.blank_learn.dark.Manifest;
import com.blank_learn.dark.R;
import com.bumptech.glide.Glide;
import com.example.dark.clasmodel;
import com.example.loginandsignup.Users;
import com.example.notification.NotificationModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.firebase.database.ServerValue;
import com.squareup.picasso.Picasso;

public class razorpayActivity extends AppCompatActivity implements PaymentResultListener {
    private FusedLocationProviderClient fusedLocationClient;
    FirebaseAuth auth;
    String randomKey;
    String stand;
    String postid;
    String price;
    String name;
    String country;
    FirebaseStorage storage;
    FirebaseDatabase database;
    private TextView amountEdt;
    private Button payBtn;
    TextView textView;


    String uri;
    String topic;
    String email;
    String phone_num;
    Intent intent;
    String postpic;
    ImageView imageView;
    String currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getUserLocation();
        intent = getIntent();
        postid = intent.getStringExtra("Postid");
        topic = intent.getStringExtra("Topic");
        price= intent.getStringExtra("Price");
        postpic= intent.getStringExtra("postpic");

        imageView = findViewById(R.id.imageView13);
        textView = findViewById(R.id.textView6);
        amountEdt = findViewById(R.id.idEdtAmount);
        amountEdt.setText(price) ;

        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        payBtn = findViewById(R.id.idBtnPay);
        if (postpic != null && !postpic.isEmpty()) {
            Picasso.get().load(postpic).into(imageView);
        }else {
        }

        textView.setText(topic);



        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                     phone_num = user.getPhone(); // Assuming your Users class has a method getName() to retrieve the name
                     email = user.getEmail();
                }
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String samount = price.toString();


                int amount = Math.round(Float.parseFloat(samount) * 100);

                Checkout checkout = new Checkout();


                checkout.setKeyID("rzp_live_6vd9RApruseTAi");

                checkout.setImage(R.drawable.lop);

                JSONObject object = new JSONObject();
                try {
                    object.put("name", topic);

                    object.put("description", "");

                    object.put("theme.color", "#0A2FF8" );

                    object.put("currency", currency);

                    object.put("amount", amount);

                    object.put("prefill.contact", phone_num);

                    object.put("prefill.email", email);

                    checkout.open(razorpayActivity.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(razorpayActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null && !addresses.isEmpty()) {
                                    String country = addresses.get(0).getCountryCode();
                                    setCurrencyBasedOnCountry(country);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation();
            }
        }
    }

    private void setCurrencyBasedOnCountry(String country) {
        switch (country) {
//            case "IN":
//                currency = "USD";
//                break;
            case "US":
                currency = "USD";
                break;
            default:
                currency = "USD";
                break;
        }
    }

//    private void initiatePayment() {
//        String samount = String.valueOf(price);
//        int amount = Math.round(Float.parseFloat(samount) * 100);
//        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_live_6vd9RApruseTAi");
//        checkout.setImage(R.drawable.lop);
//
//        JSONObject object = new JSONObject();
//        try {
//            object.put("name", topic);
//            object.put("description", "");
//            object.put("theme.color", "#021785");
//            object.put("currency", currency);
//            object.put("amount", amount);
//            object.put("prefill.contact", phone_num);
//            object.put("prefill.email", email);
//            checkout.open(razorpayActivity.this, object);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onPaymentSuccess(String s) {

        database.getReference().child("New payments").child("name").child(auth.getUid())
                .setValue(ServerValue.TIMESTAMP)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        clasmodel clasmodel = new clasmodel();
                        clasmodel.setType("buy");
                        // clasmodel.setPostpic("");
                        clasmodel.setPosttitle(postid);
                        clasmodel.setLink(postid);
                        clasmodel.setPostpic(postpic);
                        clasmodel.setPosttitle(topic);
                        clasmodel.setClasat(new Date().getTime());

                        FirebaseDatabase.getInstance().getReference()
                                .child("Classes")
                                .child(FirebaseAuth.getInstance().getUid())
                                .push()
                                .setValue(clasmodel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(razorpayActivity.this, " class done", Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Group")
                                                .child(postid)
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
                });

        //Toast.makeText(context this, "done", Toast.LENGTH_SHORT).show();
        randomKey = FirebaseDatabase.getInstance().getReference().push().getKey();
        NotificationModel notificationModel = new NotificationModel();
        intent.putExtra("price", price);
        intent.putExtra("postedBy", name);
        intent.putExtra("postid", postid);
        notificationModel.setNotificationBy(FirebaseAuth.getInstance().getUid());
        notificationModel.setNotificationAt(new Date().getTime());
        notificationModel.setType("buy");

        FirebaseDatabase.getInstance().getReference()
                .child("notification")
                .child(FirebaseAuth.getInstance().getUid())
                .push()
                .setValue(notificationModel);
//            notificationModel.setNotificationBy(name);
//            notificationModel.setNotificationAt(new Date().getTime());
//            notificationModel.setType("sell");
//
//            FirebaseDatabase.getInstance().getReference()
//                    .child("notification")
//                    .child(name)
//                    .push()
//                    .setValue(notificationModel);

//       }
    }



    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed ", Toast.LENGTH_SHORT).show();
    }
}
