package com.example.javie.lab12v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by javie on 11/15/2017.
 */

public class Tab2Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";

    private Button btnLlamar;
    private EditText txtNumero;

    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab2_fragment,container,false);

        btnLlamar = (Button) view.findViewById(R.id.btnLlamar);
        txtNumero = (EditText) view.findViewById(R.id.txtNumero);


        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numero = txtNumero.getText().toString();
                if(numero.length() == 8) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numero, null)));
                }
                else{
                    Toast.makeText(getActivity(), "Marque un numero valido!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;

    }

}