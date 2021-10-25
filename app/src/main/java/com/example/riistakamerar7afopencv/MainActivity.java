package com.example.riistakamerar7afopencv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_CODE = 1001;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    Button OtaKuva;
    ImageView NaytaKuva;
    private static final int PERMISSION_CODE = 1000;
    String kamera = "01";

    Uri image_uri;


    private static String TAG = "Main Activity";

    static {

        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "Opencv latautu onnistuneesti");
        } else {

            Log.d(TAG, "Opencv lataus ei onnistunut ");

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OtaKuva = findViewById(R.id.Ota_Kuva);
        NaytaKuva = findViewById(R.id.Kuva_Naytto);

        OtaKuva.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Kuvaa();
            }
        });


    }

    private void Kuvaa() {
        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        int a = 0;
        // Ensure that there's a camera activity to handle the intent
       // if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create the File where the photo should go
            // createImageFile(); HEITTÄÄ poikkeuksen ei toimi tuollaisenaan
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception b) {
                // Error occurred while creating the File
                Toast.makeText(this, "Jotain meni pieleen", Toast.LENGTH_SHORT).show();


            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com/example/riistakamerar7afopencv.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
       // }

    }





    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(new Date());
        String imageFileName = kamera +"_"+ timeStamp +"";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }






}
