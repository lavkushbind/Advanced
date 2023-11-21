package com.example.loginandsignup;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blank_learn.dark.R;
import com.example.home.MainActivity;
import com.example.home.MainActivity2;
import com.example.home.MainActivity3;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class login extends AppCompatActivity {
    EditText passsbtn, emaillbtn;
    Button loginbtn, Creatbtn;
    ImageView imageView;
    TextView textView;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("log");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        imageView=findViewById(R.id.profilimg);
        textView=findViewById(R.id.textView);
        passsbtn = findViewById(R.id.pasbtn);
        emaillbtn = findViewById(R.id.emaillbnt);
        Creatbtn = findViewById(R.id.Creattbtn);
        loginbtn = findViewById(R.id.loginnbtn);
        if(currentUser!= null)
        {
            Intent intent= new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String email, pas;
             email=emaillbtn.getText().toString();
             pas=passsbtn.getText().toString();
             auth.signInWithEmailAndPassword(email, pas)
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful())
                     {
                         HashMap<String, String> userMap= new HashMap<>();
                         userMap.put("pass",pas);
                         userMap.put("email",email);
                         root.push().setValue(userMap);
                         startActivity(new Intent(login.this, MainActivity.class));
                     }
                     else{
                       //  Toast.makeText(login.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                     }
                 }
             });
            }
        });
        Creatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, signup.class));
              //  Toast.makeText(login.this, "login", Toast.LENGTH_SHORT).show();
            }
        });
    }

}