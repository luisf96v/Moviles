package com.example.javie.lab12v2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by javie on 11/15/2017.
 */

public class Tab1Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";

    private Button btnCamara;
    ImageView imageView;
    private Uri mImageCaptureUri;
    static final int CAM_REQUEST = 1;
    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        btnCamara = (Button) view.findViewById(R.id.btnCamara);
        imageView = (ImageView)view.findViewById(R.id.imgView);


        btnCamara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cameraIntent =
                        new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        mImageCaptureUri);

                if (cameraIntent.resolveActivity(getActivity().getPackageManager())!=null){
                     startActivityForResult(cameraIntent,CAM_REQUEST);
                }

            }
        });



        return view;

    }
    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }

    }

}