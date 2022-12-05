package com.example.autismdiagnosis;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autismdiagnosis.ml.ModelFmri;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class FMRI extends AppCompatActivity {

    private Button select;
    private TextView csvText;
    private TextView resultfmri;
    Button predict;
    float values[] = new float[6670];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmri);

        csvText = findViewById(R.id.csvtext);
        select = findViewById(R.id.button);
        predict = findViewById(R.id.predict);
        resultfmri=findViewById(R.id.resultfmri);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imp step
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        //choosing csv file
                        Intent intent = new Intent();
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select CSV File "), 101);
                    } else {
                        //getting permission from user
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        startActivity(intent);
                    }
                } else {
                    // for below android 11
                    Intent intent = new Intent();
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_AUTO_LAUNCH_SINGLE_CHOICE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    ActivityCompat.requestPermissions(FMRI.this, new String[]{WRITE_EXTERNAL_STORAGE}, 102);
                }
            }
        });
        predict.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                try {
                    ModelFmri model = ModelFmri.newInstance(getApplicationContext());

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 6670}, DataType.FLOAT32);
                    //inputFeature0.loadBuffer(byteBuffer);
                    inputFeature0.loadArray(values);

                    // Runs model inference and gets result.
                    ModelFmri.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();
                    if (outputFeature0.getFloatArray()[0]<0.5){
                        resultfmri.setText("Autistic");

                    }
                    else {
                        resultfmri.setText("Normal");
                    }

                } catch (IOException e) {
                    // TODO Handle the exception
                }

            }

        });
    }


    Uri fileuri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101 && data!=null){
            fileuri=data.getData();
            try {
                //csvText.setText(readCSVFile(fileuri.getPath().split(":")[1]));
                String path = fileuri.getPath().split(":")[1];
                readCSVFile(path);
                //csvText.setText(values[0]+"");
                csvText.setText(path);


            } catch (Exception e) {
                e.printStackTrace();
                csvText.setText(e.toString());

            }
        }
    }


    // this method is used for getting file path from uri
    public String getFilePathFromUri(Uri uri){
        String[] filename1;
        String fn;
        String filepath=uri.getPath();
        String filePath1[]=filepath.split(":");
        filename1 =filepath.split("/");
        fn=filename1[filename1.length-1];
        return Environment.getExternalStorageDirectory().getPath()+"/"+filePath1[1];
    }

    //reading file data

    public String readCSVFile(String path) throws IOException {
        String filedata = null;
        File file = new File(path);


        // code that might throw an exception
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] splited = line.split(",");

            for (int i = 0; i < splited.length; i++) {
                values[i] = Float.parseFloat(splited[i]);

            }


            //filedata=splited.length+"";
            //filedata=splited[10];
            //filedata=line;

        }

        return filedata;
    }



}

