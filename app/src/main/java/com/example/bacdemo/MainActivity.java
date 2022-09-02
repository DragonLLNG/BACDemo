package com.example.bacdemo;

import static com.example.bacdemo.R.color.green;
import static com.example.bacdemo.R.color.orange;
import static com.example.bacdemo.R.color.red;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
    TextView weightGender, status, numberDrinks, bac, drink, percentage;
    RadioGroup gender, drinkSize;
    SeekBar alcohol;
    Resources res;
    Drawable corner;
    String genderReturn = "";

    double bacLevel = 0.0;
    double genderRate = 0.0;

    String weight="";

    final double BAC_INDEX = 5.14;

    ArrayList<Integer> drinkList = new ArrayList<Integer>();
    ArrayList<Double> alcoholPercentage = new ArrayList<Double>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        gender = findViewById(R.id.radioGroup);

        setInput = findViewById(R.id.setWeight);
        setInput.setOnClickListener(new View.OnClickListener() {
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

                /*

                if((weight.isEmpty())||(gender.getCheckedRadioButtonId()==-1)){
                    Toast toast2 = Toast.makeText(MainActivity.this,"Set weight and gender first.",Toast.LENGTH_LONG);
                    toast2.setGravity(Gravity.CENTER, 0, 0);
                    toast2.show();
                }

                 */

                weightGender = findViewById(R.id.weightGender);
                weightGender.setText(weight + "lbs".concat(genderReturn));

                weightIn.getText().clear();
                weightIn.setHint("");

                drink = findViewById(R.id.drinkOut);
                drink.setText("0");
                bac = findViewById(R.id.bacOUT);
                drinkList.clear();
                bac.setText("0.0000");

                alcoholPercentage.clear();

                alcohol = findViewById(R.id.seekBar2);
                alcohol.setProgress(0);

                status = findViewById(R.id.statusLvl);
                status.setText("You're Safe");
                status.setBackgroundColor(res.getColor(green));



            }
        });


        drinkSize = findViewById(R.id.radioGroup2);
        percentage = findViewById(R.id.percent);

        //Alcohol seekbar
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

        @Override

            public void onClick(View view) {

            if ((weight.isEmpty()) || (gender.getCheckedRadioButtonId() == -1)) {
                Toast toast2 = Toast.makeText(MainActivity.this, "Set weight and gender first.", Toast.LENGTH_SHORT);
                toast2.setGravity(Gravity.CENTER, 0, 0);
                toast2.show();
            }
            else {
                //addDrink.setEnabled(true);
                int numDrink = drinkSize.getCheckedRadioButtonId();
                if (numDrink == R.id.oneOz) {
                    drinkList.add(1);
                } else if (numDrink == R.id.fiveOz) {
                    drinkList.add(5);
                } else {
                    drinkList.add(12);
                }


                alcoholPercentage.add((double) alcohol.getProgress());

                numberDrinks = findViewById(R.id.drinkOut);
                numberDrinks.setText(String.valueOf(drinkList.size()));


                int checked = gender.getCheckedRadioButtonId();
                double consume = 0.0;

                if (checked == R.id.female) {
                    genderRate = 0.66;
                } else {
                    genderRate = 0.73;
                }

                //BAC Calculation
                for (int i = 0; i < drinkList.size(); i++) {
                    consume += drinkList.get(i) * alcoholPercentage.get(i) / 100;
                }
                bacLevel = consume * BAC_INDEX / (Double.parseDouble(weight) * genderRate);


                bac = findViewById(R.id.bacOUT);
                bac.setText(String.format("%.3f", bacLevel));

                status = findViewById(R.id.statusLvl);



                res = getResources();
                int color1 = res.getColor(red);
                int color2 = res.getColor(orange);
                int color3 = res.getColor(green);


                if (bacLevel >= 0.25) {
                    addDrink.setEnabled(false);
                    status.setText("Over the limit!");
                    status.setBackgroundColor(color1);
                    Toast toast = Toast.makeText(MainActivity.this, "No more drinks for you.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else if (bacLevel > 0.2) {
                    status.setText("Over the limit!");
                    status.setBackgroundColor(color1);
                } else if (bacLevel > 0.08) {
                    status.setText("Be careful.");
                    status.setBackgroundColor(color2);
                } else {
                    status.setText("You're safe");
                    status.setBackgroundColor(color3);
                }
            }


            }


        });

    /*
    Drawable mDrawable = ContextCompat.getDrawable(mActivity, R.drawable.rounded_corner);
    mDrawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(mActivity, R.color.honeycombish_blue), PorterDuff.Mode.SRC_IN));
    incident_icon.setBackground(mDrawable);
     */


    reset = findViewById(R.id.reset);
    reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TextView numberDrinks = findViewById(R.id.drinkOut);
            drinkList.clear();
            alcoholPercentage.clear();
            numberDrinks.setText("0");
            //TextView bac = findViewById(R.id.bacOUT);
            bac.setText("0.0000");
            addDrink.setEnabled(true);
            weightGender.setText("");
            alcohol.setProgress(0);
            drinkSize.check(R.id.oneOz);
            gender.check(R.id.female);
            status = findViewById(R.id.statusLvl);
            status.setText("You're Safe");
            status.setBackgroundColor(res.getColor(green));
            weightIn.setHint("Enter weight");

        }
    });

    }
}