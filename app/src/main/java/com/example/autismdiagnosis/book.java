package com.example.autismdiagnosis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class book extends AppCompatActivity {
    Button Next;
    private Object View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        EditText nameEditText = (EditText) findViewById(R.id.names);
        String fullName = nameEditText.getText().toString();

       /* public void radioButtonhandler(View view) {

            // Decide what happens when a user clicks on a button 
        } */

        EditText ageEditText = (EditText) findViewById(R.id.age);
        String age = ageEditText.getText().toString();

        Button Next=(Button) findViewById(R.id.Next);

        Next.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent int10= new Intent(book.this,form2.class);
                startActivity(int10);
            }
        });
    }
}