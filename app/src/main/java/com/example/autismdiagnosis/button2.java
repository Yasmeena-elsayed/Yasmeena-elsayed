package com.example.autismdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.autismdiagnosis.ml.Model3;
import com.example.autismdiagnosis.ml.Questionaire;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class button2 extends AppCompatActivity {
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4,radioButton5,radioButton6,radioButton7,radioButton8,radioButton9,radioButton10;
    RadioGroup radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5,radioGroup6,radioGroup7,radioGroup8,radioGroup9,radioGroup10;
    Button button3;

    ByteBuffer byteBuffer;

    float arr[]=new float[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button2);

        button3=(Button)findViewById(R.id.button3) ;





        radioGroup1 = findViewById(R.id.radiogroup1);
        radioGroup2 = findViewById(R.id.radiogroup2);
        radioGroup3 = findViewById(R.id.radiogroup3);
        radioGroup4 = findViewById(R.id.radiogroup4);
        radioGroup5 = findViewById(R.id.radiogroup5);
        radioGroup6 = findViewById(R.id.radiogroup6);
        radioGroup7 = findViewById(R.id.radiogroup7);
        radioGroup8 = findViewById(R.id.radiogroup8);
        radioGroup9 = findViewById(R.id.radiogroup9);
        radioGroup10 = findViewById(R.id.radiogroup10);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId1 = radioGroup1.getCheckedRadioButtonId();
                int selectedId2 = radioGroup2.getCheckedRadioButtonId();
                int selectedId3 = radioGroup3.getCheckedRadioButtonId();
                int selectedId4 = radioGroup4.getCheckedRadioButtonId();
                int selectedId5 = radioGroup5.getCheckedRadioButtonId();
                int selectedId6 = radioGroup6.getCheckedRadioButtonId();
                int selectedId7 = radioGroup7.getCheckedRadioButtonId();
                int selectedId8 = radioGroup8.getCheckedRadioButtonId();
                int selectedId9 = radioGroup9.getCheckedRadioButtonId();
                int selectedId10 = radioGroup10.getCheckedRadioButtonId();


                // find the radiobutton by returned id
                radioButton1 = (RadioButton) findViewById(selectedId1);
                radioButton2 = (RadioButton) findViewById(selectedId2);
                radioButton3 = (RadioButton) findViewById(selectedId3);
                radioButton4 = (RadioButton) findViewById(selectedId4);
                radioButton5 = (RadioButton) findViewById(selectedId5);
                radioButton6 = (RadioButton) findViewById(selectedId6);
                radioButton7 = (RadioButton) findViewById(selectedId7);
                radioButton8 = (RadioButton) findViewById(selectedId8);
                radioButton9 = (RadioButton) findViewById(selectedId9);
                radioButton10 = (RadioButton) findViewById(selectedId10);



                if (radioButton1.getText().equals("Sometimes")||radioButton1.getText().equals("Rarly")||radioButton1.getText().equals("Never")){
                    arr[0]=1;
                   // Toast.makeText(button2.this,"yyyyyy", Toast.LENGTH_SHORT).show();

                }
                else{
                    arr[0]=0;
                }
                if (radioButton2.getText().equals("Sometimes")||radioButton2.getText().equals("Rarly")||radioButton2.getText().equals("Never")){
                    arr[1]=1;
                }
                else{
                    arr[1]=0;
                }
                if (radioButton3.getText().equals("Sometimes")||radioButton3.getText().equals("Rarly")||radioButton3.getText().equals("Never")){
                    arr[2]=1;
                }
                else{
                    arr[2]=0;
                }
                if (radioButton4.getText().equals("Sometimes")||radioButton4.getText().equals("Rarly")||radioButton4.getText().equals("Never")){
                    arr[3]=1;
                }
                else{
                    arr[3]=0;
                }
                if (radioButton5.getText().equals("Sometimes")||radioButton5.getText().equals("Rarly")||radioButton5.getText().equals("Never")){
                    arr[4]=1;
                }
                else{
                    arr[4]=0;
                }
                if (radioButton6.getText().equals("Sometimes")||radioButton6.getText().equals("Rarly")||radioButton6.getText().equals("Never")){
                    arr[5]=1;
                }
                else{
                    arr[5]=0;
                }
                if (radioButton7.getText().equals("Sometimes")||radioButton7.getText().equals("Rarly")||radioButton7.getText().equals("Never")){
                    arr[6]=1;
                }
                else{
                    arr[6]=0;
                }
                if (radioButton8.getText().equals("Sometimes")||radioButton8.getText().equals("Rarly")||radioButton8.getText().equals("Never")){
                    arr[7]=1;
                }
                else{
                    arr[7]=0;
                }
                if (radioButton9.getText().equals("Sometimes")||radioButton9.getText().equals("Rarly")||radioButton9.getText().equals("Never")){
                    arr[8]=1;
                }
                else{
                    arr[8]=0;
                }
                if (radioButton10.getText().equals("Always")||radioButton10.getText().equals("Usually")||radioButton10.getText().equals("Sometimes")){
                    arr[9]=1;
                }
                else{
                    arr[9]=0;
                }

                //Toast.makeText(button2.this,arr[0]+""+arr[1]+arr[2]+arr[3]+arr[4]+arr[5]+arr[6]+arr[7]+arr[8]+arr[9]+"",Toast.LENGTH_SHORT).show();

                byteBuffer= ByteBuffer.allocate(10*4);
                for (int i = 0; i<10; i++) {
                        byteBuffer.putFloat(arr[i]);
                     }


               // Toast.makeText(button2.this,byteBuffer.get(1)+""+byteBuffer.get(2)+byteBuffer.get(3)+byteBuffer.get(4)+byteBuffer.get(5)+byteBuffer.get(6)+byteBuffer.get(7)+byteBuffer.get(8)+byteBuffer.get(9),Toast.LENGTH_SHORT).show();

                try {
                    Questionaire model = Questionaire.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 10}, DataType.FLOAT32);
                    //inputFeature0.loadBuffer(byteBuffer);
                    inputFeature0.loadArray(arr);


                    // Runs model inference and gets result.
                    Questionaire.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();



                    Toast.makeText(button2.this,"Autistic by " + (outputFeature0.getFloatArray()[0]*100) + " %",Toast.LENGTH_SHORT).show();

                  /*
                    if (outputFeature0.getFloatArray()[0]<0.5){
                    Toast.makeText(button2.this,"Autistic",Toast.LENGTH_SHORT).show();}
                    else{
                        Toast.makeText(button2.this,"Normal",Toast.LENGTH_SHORT).show();
                    }

                   */

                } catch (IOException e) {
                    // TODO Handle the exception
                }


            }


        });




    }






}