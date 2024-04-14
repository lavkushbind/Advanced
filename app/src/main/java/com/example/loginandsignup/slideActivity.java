package com.example.loginandsignup;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.example.home.HomFragment;

import java.util.ArrayList;
import java.util.List;

public class slideActivity extends AppCompatActivity {

    private RecyclerView sliderRecyclerView;
    private lettyadapter sliderAdapter;

    private List<Integer> animationList;

    private String[] textArray ;
    private String[] textArray2 ;
    Button button;

    private LinearLayout indicatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
button= findViewById(R.id.textView66);
        sliderRecyclerView = findViewById(R.id.sliderRecyclerView);
        indicatorLayout = findViewById(R.id.indicatorContainer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new HomFragment());
                fragmentTransaction.commit();
            }
        });


        animationList = new ArrayList<>();
        animationList = new ArrayList<>();
        animationList.add(R.raw.anim5);
        animationList.add(R.raw.anim4);
        animationList.add(R.raw.anim3);
        animationList.add(R.raw.anim1);
        animationList.add(R.raw.anim2);
        animationList.add(R.raw.anim_f);

        textArray2 = new String[]{"steIn your activity where you need to access storagep1",
                "In your activity where you need to access storage",
                "In your activity where you need to access storage",
                "stIn your activity where you need to access storageep4",
                "In your activity where you need to access storageIn your activity where you need to access storage"};


        textArray = new String[]{"Step1", "Step2", "Step3", "Step4", "Step5"};
        sliderAdapter = new lettyadapter(this, animationList, textArray2,textArray);
        sliderRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sliderRecyclerView.setAdapter(sliderAdapter);

    }


}

