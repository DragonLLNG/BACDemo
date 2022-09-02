package com.example.bacdemo;

import static com.example.bacdemo.R.color.green;
import static com.example.bacdemo.R.color.orange;
import static com.example.bacdemo.R.color.red;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
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

    Button addDrink, reset, setInput;
    EditText weightIn;
    TextView weightGender, status;
    RadioGroup gender, drinkSize;
    SeekBar alcohol;

    double bacLevel = 0.0;
    double genderRate = 0.0;

    String weight="";

    final double BAC_INDEX = 5.14;

    ArrayList<Integer> drinkList = new ArrayList<Integer>();
    ArrayList<Double> alcoholPercentage = new ArrayList<Double>();
    double w = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        int color3 = res.getColor(green);

        gender = findViewById(R.id.radioGroup);

        setInput = findViewById(R.id.setWeight);
        setInput.setOnClickListener(new View.OnClickListener() {
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
                weight = weightIn.getText().toString();


                weightGender = findViewById(R.id.weightGender);
                weightGender.setText(weight + "lbs".concat(genderReturn));

                weightIn.getText().clear();
                weightIn.setHint("");

                TextView drink = findViewById(R.id.drinkOut);
                drink.setText("0");

                TextView bac = findViewById(R.id.bacOUT);
                drinkList.clear();
                bac.setText("0.0000");

                alcoholPercentage.clear();

                alcohol = findViewById(R.id.seekBar2);
                alcohol.setProgress(0);

                status = findViewById(R.id.statusLvl);
                status.setText("You're Safe");
                status.setBackgroundColor(color3);





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

    //if(setInput.isClickable()){


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


            //BAC Calculation
            int checked = gender.getCheckedRadioButtonId();
            double consume=0.0;

            if(checked==R.id.female){
                genderRate = 0.66;
            }
            else {
                genderRate = 0.73;
            }
                for (int i=0; i<drinkList.size();i++) {
                    consume += drinkList.get(i) * alcoholPercentage.get(i) / 100;
                }
            bacLevel = consume*BAC_INDEX/(Double.parseDouble(weight) * genderRate);


            TextView bac = findViewById(R.id.bacOUT);
            bac.setText(String.format("%.3f",bacLevel));

            TextView status = findViewById(R.id.statusLvl);


            Resources res = getResources();
            int color1 = res.getColor(red);
            int color2 = res.getColor(orange);
            int color3 = res.getColor(green);

            if (bacLevel >=0.25){
                addDrink.setEnabled(false);
                status.setText("Over the limit!");
                status.setBackgroundColor(color1);
                Toast toast = Toast.makeText(MainActivity.this,"No more drinks for you.",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
            else if(bacLevel > 0.2){
                status.setText("Over the limit!");
                status.setBackgroundColor(color1);
            }
            else if (bacLevel > 0.08){
                status.setText("Be careful.");
                status.setBackgroundColor(color2);
            }
            else {
                status.setText("You're safe");
                status.setBackgroundColor(color3);
            }

            }

    });

    //}
    /*
    else{
        Toast toast2 = Toast.makeText(MainActivity.this,"Set weight and gender first.",Toast.LENGTH_LONG);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
    }

     */

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
            weightGender.setText("");
            alcohol.setProgress(0);
            drinkSize.check(R.id.oneOz);
            status = findViewById(R.id.statusLvl);
            status.setText("You're Safe");
            status.setBackgroundColor(color3);
            weightIn.setHint("Enter weight");

        }
    });

    }


}