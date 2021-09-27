package com.example.riistakamerar7afopencv;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_CODE = 1001;
    Button OtaKuva ;
    ImageView NaytaKuva ;
    private static final int PERMISSION_CODE =1000 ;


    Uri image_uri;


    private static String TAG="Main Activity";
    static {

        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "Opencv latautu onnistuneesti");
        }

        else{

            Log.d(TAG, "Opencv lataus ei onnistunut ");

        }


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OtaKuva=findViewById(R.id.Ota_Kuva);
        NaytaKuva=findViewById(R.id.Kuva_Naytto);

        OtaKuva.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

              if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M) {

                  if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                      String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                      requestPermissions(permission, PERMISSION_CODE);


                  } else {
                      Kuvaa();

                  }
              }
              else {

                      Kuvaa();

              }
              }


              });



        }



    private void Kuvaa(){
    String kamera="1";

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date now = new Date();
        String stringDate = sdfDate.format(now);

        ContentValues arvot = new ContentValues();
        arvot.put(MediaStore.Images.Media.DATE_TAKEN, stringDate);
        arvot.put(MediaStore.Images.Media.TITLE,kamera);

        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,arvot);
        Intent kameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        kameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(kameraIntent,IMAGE_CAPTURE_CODE);
        NaytaKuva.setImageURI(image_uri);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {

        NaytaKuva.setImageURI(image_uri);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Metodi kutsutaan mikäli käyttäjä hyväksyy tai ei hyväksy pääsyä

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case PERMISSION_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission oli myönnetty popup teksti
                    Kuvaa();


                } else {
                    //lupaa ei ollut myönnetty popup teksti
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();


                }
            }


        }

    }
}