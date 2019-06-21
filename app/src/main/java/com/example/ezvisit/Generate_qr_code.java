package com.example.ezvisit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Generate_qr_code extends Fragment {

    TextInputEditText name, mobileNo, icNo, vehNo, unitNo, time, date;
    Button gen_btn;
    ImageView qr_image;
    String text2Qr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.generate_qr_code, container,false);

        ///////////////// TextInput Edit Text ////////////////
        name = (TextInputEditText) view.findViewById(R.id.name);
        mobileNo = (TextInputEditText) view.findViewById(R.id.mobileNo);
        icNo = (TextInputEditText) view.findViewById(R.id.icNo);
        vehNo = (TextInputEditText) view.findViewById(R.id.vehNo);
        unitNo = (TextInputEditText) view.findViewById(R.id.unitNo);
        time = (TextInputEditText) view.findViewById(R.id.time);
        date = (TextInputEditText) view.findViewById(R.id.date);

        gen_btn = (Button) view.findViewById(R.id.gen_btn);
        qr_image = (ImageView) view.findViewById(R.id.qr_image);

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line1 = "Name         : " + name.getText() .toString().trim();
                String line2 = "Mobile No  : " + mobileNo.getText().toString().trim();
                String line3 = "IC No          : " + icNo.getText().toString().trim();
                String line4 = "VEH No      : " + vehNo.getText().toString().trim();
                String line5 = "Unit No       : " + unitNo.getText().toString().trim();
                String line6 = "Time           : " + time.getText().toString().trim();
                String line7 = "Date            : " + date.getText().toString().trim();

                text2Qr = line1 + "\n" + line2 +  "\n" + line3 +  "\n" + line4 +  "\n" + line5 +  "\n" + line6 +  "\n" + line7;
                ///// Sending data to new fragment /////
                Bundle bundle = new Bundle();
                bundle.putString("text2Qr",text2Qr);

                Fragment fragment = new Generated_code();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                fragment.setArguments(bundle);

                fragmentTransaction.commit();


            }



        });





        return view;
    }



}
