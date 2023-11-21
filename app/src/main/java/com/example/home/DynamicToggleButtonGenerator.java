package com.example.home;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.blank_learn.dark.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DynamicToggleButtonGenerator {

    private Context context;
    private LinearLayout container;
    private List<ToggleButton> toggleButtons;

    public DynamicToggleButtonGenerator(Context context, LinearLayout container) {
        this.context = context;
        this.container = container;
        this.toggleButtons = new ArrayList<>();
    }

    public void createToggleButtons(List<String> buttonNames) {
        for (String buttonName : buttonNames) {
            ToggleButton toggleButton = createToggleButton(buttonName);
            toggleButtons.add(toggleButton);
            container.addView(toggleButton);
        }
    }

    private ToggleButton createToggleButton(String buttonName) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ToggleButton toggleButton = (ToggleButton) inflater.inflate(R.layout.toggle_button_item, container, false);
        toggleButton.setText(buttonName);
        toggleButton.setBackgroundColor(getRandomColor());

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle toggle button state change if needed
            }
        });

        return toggleButton;
    }

    private int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
