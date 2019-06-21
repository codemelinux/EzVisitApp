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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Generated_code extends Fragment {
    ImageView qr_image;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.generated_code, container,false);

        qr_image = (ImageView) view.findViewById(R.id.qr_image);

        Bundle bundle = getArguments();

        String text2Qr = bundle.getString("text2Qr");



        if (text2Qr != null ){
            try{
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr,   BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qr_image.setImageBitmap(bitmap);



            } catch (WriterException e) {
                e.printStackTrace();
            }

        }


        //gen_btn = (Button) view.findViewById(R.id.gen_btn);


        return view;
    }
}
