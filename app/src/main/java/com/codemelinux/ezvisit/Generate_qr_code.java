package com.codemelinux.ezvisit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;


public class Generate_qr_code extends Fragment {

    TextInputEditText name, mobileNo, icNo, vehNo, unitNo, time, date;
    Button gen_btn;
    ImageView qr_image;
    String text2Qr;
    String photoUrl;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference mDatabase;
    private QRGEncoder qrgEncoder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.generate_qr_code, container,false);


       // getActivity().setTitle("Visitor Request");
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Visitor Request");

        ///////////////// TextInput Edit Text ////////////////
        name = (TextInputEditText) view.findViewById(R.id.name);
        mobileNo = (TextInputEditText) view.findViewById(R.id.mobileNo);
        icNo = (TextInputEditText) view.findViewById(R.id.icNo);
        vehNo = (TextInputEditText) view.findViewById(R.id.vehNo);
        unitNo = (TextInputEditText) view.findViewById(R.id.unitNo);
        time = (TextInputEditText) view.findViewById(R.id.time);
        date = (TextInputEditText) view.findViewById(R.id.date);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        gen_btn = (Button) view.findViewById(R.id.gen_btn);
        qr_image = (ImageView) view.findViewById(R.id.qr_image);

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line1 = "";
                String line2 = "";
                String line3 = "";
                String line4 = "";
                String line5 = "";
                String line6 = "";
                String line7 = "";

                if (name.getText().toString() != null) {
                    line1 = "Name       : " + name.getText().toString().trim();
                }
                if (mobileNo.getText().toString() != null) {
                    line2 = "Mobile No  : " + mobileNo.getText().toString().trim();
                }
                if (icNo.getText().toString() != null) {
                    line3 = "IC No      : " + icNo.getText().toString().trim();
                }
                if (vehNo.getText().toString() != null) {
                    line4 = "VEH No     : " + vehNo.getText().toString().trim();
                }
                if (unitNo.getText().toString() != null) {
                    line5 = "Unit No    : " + unitNo.getText().toString().trim();
                }
                if (time.getText().toString() != null) {
                    line6 = "Time       : " + time.getText().toString().trim();
                }
                if (date.getText().toString() != null) {
                    line7 = "Date       : " + date.getText().toString().trim();
                }

                text2Qr = line1 + "\n" + line2 +  "\n" + line3 +  "\n" + line4 +  "\n" + line5 +  "\n" + line6 +  "\n" + line7;


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
                        //qr_image.setImageBitmap(bitmapResult);
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

                user = auth.getCurrentUser();
                final String userID = user.getUid();

                // retrive image
                File imagePath = new File(getActivity().getCacheDir(), "images");
                File newFile = new File(imagePath, "image.png");
                Uri contentUri = FileProvider.getUriForFile(getActivity(), "com.codemelinux.ezvisit.fileprovider", newFile);

                // Write a message to the storage
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                // writing to database
                mDatabase = FirebaseDatabase.getInstance().getReference();


                final StorageReference refe = storageReference.child("images/users/"+ userID + "/" + UUID.randomUUID().toString()).child(contentUri.getLastPathSegment());
                final UploadTask uploadTask = refe.putFile(contentUri);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                photoUrl = uri.toString();
                                //Map userInfo = new HashMap();
                                VisitorClass visitorClass = new VisitorClass(name.getText().toString(), mobileNo.getText().toString(), icNo.getText().toString(), vehNo.getText().toString(),unitNo.getText().toString(),time.getText().toString(),date.getText().toString(), photoUrl);
                                mDatabase.child("users/"+ userID + "/" + "Visitors").push().setValue(visitorClass);
                            }
                        });
                    }
                });


                ///// Sending data to new fragment /////
                Bundle bundle = new Bundle();
                bundle.putString("text2Qr",text2Qr);

                Fragment fragment = new Generated_code();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);




                /// user input transfer
                fragment.setArguments(bundle);

                fragmentTransaction.commit();




            }



        });





        return view;
    }



}
