package com.example.autismdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1=(Button) findViewById(R.id.button1);
        Button button2=(Button) findViewById(R.id.button2);
        Button FMRI=(Button) findViewById(R.id.button5);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent int2= new Intent(MainActivity.this,button1.class);
                startActivity(int2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent int2= new Intent(MainActivity.this,button2.class);
                startActivity(int2);
            }
        });
        FMRI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent int2= new Intent(MainActivity.this,FMRI.class);
                startActivity(int2);
            }
        });


    }
}


