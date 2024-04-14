package com.example.loginandsignup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.blank_learn.dark.R;
import java.util.List;

public class lettyadapter extends RecyclerView.Adapter<lettyadapter.SliderViewHolder> {

    private final List<Integer> animationList;
    private final Context context;
    private String[] textArray;
    private String[] textArray2;


    private int selectedIndex = 0;

    public lettyadapter(Context context, List<Integer> animationList, String[] textArray2,String[] textArray) {
        this.context = context;
        this.animationList = animationList;
        this.textArray2 = textArray2;

        this.textArray = textArray;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_letty, parent, false);
        return new SliderViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Integer animationResId = animationList.get(position);
        holder.animationView.setAnimation(animationResId);
        holder.animationView.playAnimation();
        holder.textView.setText(textArray2[position]);
        holder.textView2.setText(textArray[position]);

    }


    @Override
    public int getItemCount() {
        return Math.min(animationList.size(), textArray != null ? textArray.length : 0);
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        LottieAnimationView animationView;
        TextView textView;
        TextView textView2;


        ImageView indicatorView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            animationView = itemView.findViewById(R.id.animationView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);



        }
    }
}
