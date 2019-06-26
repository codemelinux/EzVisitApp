package com.codemelinux.ezvisit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;

public class Generated_code extends Fragment {
    String text2Qr;
    Button share_btn;
    ImageView qr_image;
    Intent shareIntent;
    private QRGEncoder qrgEncoder;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.generated_code, container,false);

        qr_image = (ImageView) view.findViewById(R.id.qr_image);

        Bundle bundle = getArguments();

        text2Qr = bundle.getString("text2Qr");

        if (text2Qr != null ){
            //calculating bitmap dimension
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            WindowManager manager = (WindowManager)getActivity().getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(text2Qr, null, QRGContents.Type.TEXT, smallerDimension);
            try {
                Bitmap bitmapResult = qrgEncoder.encodeAsBitmap();
                qr_image.setImageBitmap(bitmapResult);
                //btnSave.setVisibility(View.VISIBLE);

                //saving image
                File cachePath = new File(getActivity().getCacheDir(), "images");
                cachePath.mkdirs(); // don't forget to make the directory
                FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
                bitmapResult.compress(Bitmap.CompressFormat.PNG, 100, stream);
                stream.close();

            } catch (WriterException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }


        share_btn = (Button) view.findViewById(R.id.share_btn);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                File imagePath = new File(getActivity().getCacheDir(), "images");
                File newFile = new File(imagePath, "image.png");
                Uri contentUri = FileProvider.getUriForFile(getActivity(), "com.codemelinux.ezvisit.fileprovider", newFile);

                if (contentUri != null) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
                    shareIntent.setDataAndType(contentUri, getActivity().getContentResolver().getType(contentUri));
                    shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                    startActivity(Intent.createChooser(shareIntent, "Choose an app"));
                }

                /*
                if (text2Qr != null ){

                    //calculating bitmap dimension
                    WindowManager manager = (WindowManager)getActivity().getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(text2Qr, null, QRGContents.Type.TEXT, smallerDimension);
                    try {
                        Bitmap bitmapResult = qrgEncoder.encodeAsBitmap();
                        qr_image.setImageBitmap(bitmapResult);

                        // String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
                        //Uri uri = Uri.parse(path);
                        Uri imageUri = (Uri) Uri.parse(String.valueOf(QRGContents.ImageType.IMAGE_JPEG));


                        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EzVisit");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                        startActivity(Intent.createChooser(shareIntent, "Share Image"));

                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    //editText.setError("Enter some text");




                    /**
                    try{
                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr,   BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                       // Object i = qr_image.setImageBitmap(bitmap);


                       // String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Description", null);
                        //Uri uri = Uri.parse(path);
                        Uri imageUri = (Uri) Uri.parse((qr_image.setImageBitmap(bitmap)));

                        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("image/*");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "EzVisit");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                        startActivity(Intent.createChooser(shareIntent, "Share Image"));


                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }**/

            }


        });


        return view;
    }

}
