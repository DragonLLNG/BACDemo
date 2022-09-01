package com.example.bacdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        RadioGroup gender = findViewById(R.id.radioGroup);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            String genderSelect;
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                EditText weightInput = findViewById(R.id.weightInput);
                String weight = weightInput.getText().toString();
                if(checked==R.id.female){
                   genderSelect="(Female)";
                    findViewById(R.id.setWeight).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            EditText weightGender = findViewById(R.id.weightGender);
                            weightGender.setText(weight);
                            weightInput.setText("");

                        }
                    });
                }
                else if (checked==R.id.male){
                    genderSelect="(Male)";
                    findViewById(R.id.setWeight).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            EditText weightGender = findViewById(R.id.weightGender);
                            weightGender.setText(weight);
                            weightInput.setText("");

                        }
                    });
                }
            }
        });






    }
}