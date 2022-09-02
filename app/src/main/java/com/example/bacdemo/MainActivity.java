package com.example.bacdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
        /*gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                if(checked==R.id.female){
                    genderChoice.add("(Female)");
                }
                else if (checked==R.id.male){
                    genderChoice.add("(Male)");
                }
            }
        });

         */
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
        drinkSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                if (checked == R.id.oneOz) {
                    getSize(1);
                } else if (checked == R.id.fiveOz) {
                    getSize(5);
                }
                else if (checked == R.id.twelveOz){
                    getSize(12);

                }
            }
        });

    }
    private void getSize (int drinkSelect){
        size = drinkSelect;
    }
}