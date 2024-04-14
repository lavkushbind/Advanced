package com.example.payment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.bumptech.glide.Glide;
import com.example.dark.clasmodel;
import com.example.notification.NotificationModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import com.google.firebase.database.ServerValue;
import com.squareup.picasso.Picasso;

public class razorpayActivity extends AppCompatActivity implements PaymentResultListener {

    FirebaseAuth auth;
    String randomKey;
    String stand;
    String postid;
    String price;
    String name;
    FirebaseStorage storage;
    FirebaseDatabase database;
    private TextView amountEdt;
    private Button payBtn;
    TextView textView;


    String uri;
    String topic;
    Intent intent;
    String postpic;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        intent = getIntent();
        postid = intent.getStringExtra("Postid");
        topic = intent.getStringExtra("Topic");
        price= intent.getStringExtra("Price");
        postpic= intent.getStringExtra("postpic");
//        Toast.makeText(this, "Image URL: " + postpic, Toast.LENGTH_SHORT).show();

        imageView = findViewById(R.id.imageView13);
        textView = findViewById(R.id.textView6);
        amountEdt = findViewById(R.id.idEdtAmount);
        amountEdt.setText(price);

        auth = FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
        payBtn = findViewById(R.id.idBtnPay);
        if (postpic != null && !postpic.isEmpty()) {
            Picasso.get().load(postpic).into(imageView);
        }else {
//            Toast.makeText(this, "nulllllllllll", Toast.LENGTH_SHORT).show();
        }

        textView.setText(topic);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // amount that is entered by user.
                String samount = price.toString();

                // rounding off the amount.
                int amount = Math.round(Float.parseFloat(samount) * 100);

                // initialize Razorpay account.
                Checkout checkout = new Checkout();

                // set your id as below
                checkout.setKeyID("rzp_live_6vd9RApruseTAi");

                // set image
                checkout.setImage(R.drawable.lop);

                // initialize json object
                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", topic);

                    // put description
                    object.put("description", "");

                    // to set theme color
                    object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "INR");

                    // put amount
                    object.put("amount", amount);

                    // put mobile number
                    object.put("prefill.contact", "9284064503");

                    // put email
                    object.put("prefill.email", "chaitanyamunje@gmail.com");

                    // open razorpay to checkout activity
                    checkout.open(razorpayActivity.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

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
