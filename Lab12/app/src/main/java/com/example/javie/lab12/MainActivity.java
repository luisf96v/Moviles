package com.example.javie.lab12;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    Button btnCamara, btnLlamar, btnCorreo;
    ImageView imageView;
    private Uri mImageCaptureUri;
    static final int CAM_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamara = (Button)findViewById(R.id.btnCamara);
        btnLlamar = (Button)findViewById(R.id.btnLlamar);
        btnCorreo = (Button)findViewById(R.id.btnCorreo);

        imageView = (ImageView)findViewById(R.id.imgView);
        imageView.setImageURI(Uri.parse("file://mnt/sdcard/d2.jpg"));

        btnCamara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cameraIntent =
                        new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        mImageCaptureUri);

                if (cameraIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(cameraIntent,CAM_REQUEST);
                }

            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText b = (EditText) findViewById(R.id.txtNumero);
                String numero = b.getText().toString();
                if(numero.length() == 8) {
                    dialContactPhone(numero);
                }
                else{
                    Toast.makeText(getBaseContext(), "Marque un numero valido!" , Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCorreo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sendEmail();
            }
        });
    }
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private File setFile(String path){

        File folder = new File("lab12");

        if(!folder.exists())
        {
            folder.mkdir();
        }

        File image_file = new File(folder,path);
        return image_file;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

        }


    }


    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
