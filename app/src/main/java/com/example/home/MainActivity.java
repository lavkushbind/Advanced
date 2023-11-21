package com.example.home;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.blank_learn.dark.R;
import com.example.dark.ClasFragment;
import com.example.dark.aboutFragment;
import com.example.notification.Notification2Fragment;
import com.example.payment.PostFragment;
import com.example.profile.ProfileFragment;
import com.example.profile.YourSearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,new HomFragment());
        fragmentTransaction.commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
             switch (item.getItemId()) {
                 case R.id.home:
                     fragmentTransaction.replace(R.id.container, new HomFragment());

                     break;  
                 case R.id.notificationid:
                         fragmentTransaction.replace(R.id.container, new Notification2Fragment());
                         break;
                 case R.id.about:
                             fragmentTransaction.replace(R.id.container, new ClasFragment());
                             break;
                  case R.id.profile:
                                 fragmentTransaction.replace(R.id.container, new ProfileFragment());
                                 break;
                 case R.id.post:
                     fragmentTransaction.replace(R.id.container, new PostFragment());
                     break;
                         }
                         fragmentTransaction.commit();
                         return true;
         }
     });
    }
}
