package com.example.home;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blank_learn.dark.R;
import com.example.home.HomFragment;
import com.example.loginandsignup.lettyadapter;

import java.util.ArrayList;
import java.util.List;
public class student_slider extends AppCompatActivity {
    private RecyclerView sliderRecyclerView;
    private lettyadapter sliderAdapter;
    private List<Integer> animationList;
    private String[] textArray ;
    private String[] textArray2 ;
    TextView button;
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
        animationList.add(R.raw.anim8);
        animationList.add(R.raw.anim10);
        animationList.add(R.raw.anim2);
        animationList.add(R.raw.anim3);
        animationList.add(R.raw.anim_f);
        animationList.add(R.raw.anim6);
        textArray= new String[]{
                "1. Choosing Your Course:",
                "2. Seeing Course Details:",
                "3. Enrolling in a Course:",
                "4. You'll be added to the group automatically:",
                "5. Accessing Class Links and Asking Doubts:",
                "6. Personalized Guidance with teacher:",
                "7. Taking the Course Test:"};

        textArray2 = new String[]{

                "Browse the available courses.\n" +
                        "You can filter courses by category, subject, teacher, or skill level.\n" +
                        "Use the search bar to find specific courses.\n" +
                        "Read course descriptions to understand the learning objectives and topics covered",
                "Once you find an interesting course, tap on it to view details.\n" +
                        "The details page will typically include:\n" +
                        "A more detailed course description.\n" +
                        "The teacher's profile and qualifications.\n" +
                        "The course schedule (including days, times, and duration).\n" +
                        "Any prerequisites or required materials.",
                "If you're ready to begin learning, click the \"Enroll Now\" button.\n" +
                        "You might need to create an account or log in to your existing BlankLearn account.\n" +
                        "Follow the on-screen instructions to complete the enrollment process. \n" +
                        "This may involve selecting a payment method (if applicable) and confirming your enrollment.",
                "Once enrolled, you'll be placed in a small group with other students taking the same course.\n" +
                        "This group setting fosters interaction and allows for personalized attention from the teacher.",
                "You'll receive a link to join the live video class within the app or through email.\n" +
                        "The app will provide a dedicated space for you to interact with your classmates and ask questions directly to the teacher during class or through a chat function."
            ,    "BlankLearn offers 24/7 support through the app or website in case you encounter any technical difficulties.\n" +
                "You might have access to additional resources like study materials or practice exercises provided by the teacher.",
                "Demonstrate your acquired knowledge by taking a final test at the end of the course."
        };
        sliderAdapter = new lettyadapter(this, animationList, textArray2,textArray);
        sliderRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sliderRecyclerView.setAdapter(sliderAdapter);
        sliderRecyclerView.setItemAnimator(new CustomItemAnimator());
    }
    public class CustomItemAnimator extends DefaultItemAnimator {
        @Override
        public boolean animateAdd(RecyclerView.ViewHolder holder) {
            holder.itemView.setAlpha(0f);
            holder.itemView.setTranslationY(100);
            holder.itemView.animate()
                    .alpha(1f)
                    .translationY(0)
                    .setDuration(getAddDuration())
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            dispatchAddFinished(holder);
                        }
                    })
                    .start();
            return false;
        }
    }

}