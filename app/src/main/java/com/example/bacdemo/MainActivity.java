package com.example.bacdemo;

import static com.example.bacdemo.R.color.orange;
import static com.example.bacdemo.R.color.red;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int size=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ArrayList<String> genderChoice = new ArrayList<String>();

        RadioGroup gender = findViewById(R.id.radioGroup);

        findViewById(R.id.setWeight).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                String genderReturn="";
                int checked = gender.getCheckedRadioButtonId();
                if(checked==R.id.female){
                    genderReturn=" (Female)";
                }
                else if (checked==R.id.male){
                   genderReturn=" (Male)";
                }

                EditText weightInput = findViewById(R.id.weightInput);
                String weight = weightInput.getText().toString()+"lbs";

                TextView weightGender = findViewById(R.id.weightGender);
                weightGender.setText(weight.concat(genderReturn));
                weightInput.setText("");

                TextView drink = findViewById(R.id.drinkOut);
                drink.setText("");

                TextView bac = findViewById(R.id.bacOUT);
                bac.setText("");

                TextView status = findViewById(R.id.statusLvl);
                status.setText("You are Safe");


            }
        });

        RadioGroup drinkSize = findViewById(R.id.radioGroup2);

        TextView percentage = findViewById(R.id.percent);
        SeekBar alcohol = findViewById(R.id.seekBar2);
        alcohol.setMax(30);
        alcohol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percentage.setText(i+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    ArrayList<Integer> drinkList = new ArrayList<Integer>();
    ArrayList<Double> AlcoholPercentage = new ArrayList<Double>();

    Button addDrink = findViewById(R.id.addDrink);

    addDrink.setOnClickListener(new View.OnClickListener() {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view) {
            int numDrink = drinkSize.getCheckedRadioButtonId();
            if(numDrink == R.id.oneOz){
                drinkList.add(1);
            }
            else if(numDrink == R.id.fiveOz){
                drinkList.add(5);
            }
            else {
                drinkList.add(12);
            }

            AlcoholPercentage.add((double) alcohol.getProgress());

            TextView numberDrinks = findViewById(R.id.drinkOut);
            numberDrinks.setText(String.valueOf(drinkList.size()));


            //Double weightNum = Double.parseDouble(weight);

            double bacLevel = 0.0;

            int checked = gender.getCheckedRadioButtonId();
            if(checked==R.id.female){
                for(int i:drinkList){
                    for (double j:AlcoholPercentage){
                        bacLevel = (i*j/100*5.14)/(50* 0.66);
                    }
                }
            }
            else if (checked==R.id.male){
                for(Integer i:drinkList){
                    for (Double j:AlcoholPercentage){
                        bacLevel += (i*j/100*5.14)/(60* 0.73);
                    }
                }
            }
            TextView bac = findViewById(R.id.bacOUT);
            bac.setText(String.format("%.4f",bacLevel));

            TextView status = findViewById(R.id.statusLvl);

            if (bacLevel >=0.25){
                addDrink.setEnabled(false);

            }
            else if(bacLevel > 0.2){
                status.setText("Over the limit!");
                status.setBackgroundColor(R.color.red);
            }
            else if (bacLevel > 0.08){
                status.setText("Be careful.");
                status.setBackgroundColor(R.color.orange);
            }
            else {
                status.setText("You're safe");
                status.setBackgroundColor(R.color.green);
            }

            }



    });

    Button reset = findViewById(R.id.reset);
    reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView numberDrinks = findViewById(R.id.drinkOut);
            numberDrinks.setText("0");

        }
    });

    }




}