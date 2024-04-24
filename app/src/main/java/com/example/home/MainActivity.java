package com.example.home;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        if (isConnected()) {
            setupUI();
        } else {
            showNoInternetDialog();
        }
    }

    private void setupUI() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new HomFragment());
        fragmentTransaction.commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    case R.id.search:
                        fragmentTransaction.replace(R.id.container, new YourSearchActivity());
                        break;
                    case R.id.profile:
                        fragmentTransaction.replace(R.id.container, new ProfileFragment());
                        break;
                    case R.id.classs:
                        fragmentTransaction.replace(R.id.container, new ClasFragment());
                        break;
                }
                fragmentTransaction.commit();
                return true;
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isConnected()) {
                            setupUI();
                        } else {
                            showNoInternetDialog();
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}


