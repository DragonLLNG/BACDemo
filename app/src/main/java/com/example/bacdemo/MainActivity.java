package com.example.bacdemo;

import static com.example.bacdemo.R.color.orange;
import static com.example.bacdemo.R.color.red;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addDrink, reset;
    EditText weightIn;
    RadioGroup gender, drinkSize;
    SeekBar alcohol;

    double bacLevel = 0.0;
    double rate = 0.0;

    ArrayList<Integer> drinkList = new ArrayList<Integer>();
    ArrayList<Double> alcoholPercentage = new ArrayList<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gender = findViewById(R.id.radioGroup);

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


                weightIn = findViewById(R.id.weightInput);
                String weight = weightIn.getText().toString()+"lbs";

                TextView weightGender = findViewById(R.id.weightGender);
                weightGender.setText(weight.concat(genderReturn));
                weightIn.getText().clear();
                weightIn.setHint("");

                TextView drink = findViewById(R.id.drinkOut);
                drink.setText("0");

                TextView bac = findViewById(R.id.bacOUT);
                bac.setText("0.0000");

                SeekBar alcohol = findViewById(R.id.seekBar2);
                alcohol.setProgress(0);

                TextView status = findViewById(R.id.statusLvl);
                status.setText("You are Safe");


            }
        });

        drinkSize = findViewById(R.id.radioGroup2);

        TextView percentage = findViewById(R.id.percent);

        alcohol = findViewById(R.id.seekBar2);
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



    addDrink = findViewById(R.id.addDrink);

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

            alcoholPercentage.add((double) alcohol.getProgress());

            TextView numberDrinks = findViewById(R.id.drinkOut);
            numberDrinks.setText(String.valueOf(drinkList.size()));



            //Double weightNum = Double.parseDouble(weight);

            int checked = gender.getCheckedRadioButtonId();

            if(checked==R.id.female){
                rate = 0.66;
            }
            else {
                rate = 0.73;
            }
            double a=0.0;
                for (int i=0; i<alcoholPercentage.size();i++) {
                    a += drinkList.get(i) * alcoholPercentage.get(i) / 100;
                }
            bacLevel = a*5.14/(150* rate);


            TextView bac = findViewById(R.id.bacOUT);
            bac.setText(String.format("%.3f",bacLevel));

            TextView status = findViewById(R.id.statusLvl);

            if (bacLevel >=0.25){
                addDrink.setEnabled(false);
                Toast toast = Toast.makeText(MainActivity.this,"No more drinks for you.",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

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

    reset = findViewById(R.id.reset);
    reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView numberDrinks = findViewById(R.id.drinkOut);
            drinkList.clear();
            alcoholPercentage.clear();
            numberDrinks.setText("0");
            TextView bac = findViewById(R.id.bacOUT);
            bac.setText("0.0000");
            addDrink.setEnabled(true);

        }
    });

    }




}