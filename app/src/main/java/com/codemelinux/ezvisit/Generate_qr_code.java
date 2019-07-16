package com.codemelinux.ezvisit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;


import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class Generate_qr_code extends Fragment {

    TextInputEditText name, mobileNo, icNo, vehNo, unitNo, time, date;
    Button gen_btn;
    ImageView qr_image;
    String text2Qr, user;
    String photoUrl, line1, line2, line3,line4,line5,line6,line7;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db;
    private QRGEncoder qrgEncoder;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    UserLocalStore userLocalStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        View view = inflater.inflate(R.layout.generate_qr_code, container,false);


        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Visitor Request");

        ///////////////// TextInput Edit Text ////////////////
        name = (TextInputEditText) view.findViewById(R.id.name);
        mobileNo = (TextInputEditText) view.findViewById(R.id.mobileNo);
        icNo = (TextInputEditText) view.findViewById(R.id.icNo);
        vehNo = (TextInputEditText) view.findViewById(R.id.vehNo);
        date =  view.findViewById(R.id.date);
        db = FirebaseFirestore.getInstance();
        gen_btn = (Button) view.findViewById(R.id.gen_btn);
        storageReference = FirebaseStorage.getInstance().getReference();
        qr_image = (ImageView) view.findViewById(R.id.qr_image);
        userLocalStore = new UserLocalStore(getActivity());

        TimeAndDateModule();

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.length()==0){
                    name.setError("Enter Name");
                }else if (mobileNo.length()==0){
                    mobileNo.setError("Enter Moblie Number");
                }else if (icNo.length()==0){
                    icNo.setError("Enter IC Number");
                }else if (vehNo.length()==0){
                    vehNo.setError("Enter Vehicle Number");
                }else if (date.length()==0){
                    date.setError("Enter Date");
                } else {

                    line1 = "Name       : " + name.getText().toString().trim();
                    line2 = "Mobile No  : " + mobileNo.getText().toString().trim();
                    line3 = "IC No      : " + icNo.getText().toString().trim();
                    line4 = "VEH No     : " + vehNo.getText().toString().trim();
                    line7 = "Date       : " + date.getText().toString().trim();

                    // getting stored user
                    user = userLocalStore.getLoggedInUser().getEmail();

                    DocumentReference docRef = db.collection("users").document(user);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {

                                    String unitNo = (String) document.get("unitNo");
                                    text2Qr = line1 + "\n" + line2 +  "\n" + line3 +  "\n" + line4 +  "\n" + unitNo +  "\n" + line7;

                                    // Generating QR code
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

                                            //saving image to local cache
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


                                    ///// Sending data to new fragment /////
                                    Bundle bundle = new Bundle();
                                    bundle.putString("text2Qr",text2Qr);

                                    Fragment fragment = new Generated_code();
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    //fragmentTransaction.addToBackStack(null);

                                    /// user input transfer
                                    fragment.setArguments(bundle);

                                    StoreDataInDatabase();

                                    fragmentTransaction.commit();


                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });



                }


            }



        });


        return view;
    }


    public void TimeAndDateModule(){


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;

                String mDate = year + "-" + month + "-" + day;
                date.setText(mDate);

            }
        };

    }


    public void StoreDataInDatabase(){

        // getting loggedin user
        user = userLocalStore.getLoggedInUser().getEmail();

        // retrive image stored in cache
        File imagePath = new File(getActivity().getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(getActivity(), "com.codemelinux.ezvisit.fileprovider", newFile);



        // storing qr image to firebase storage
        final StorageReference refe = storageReference.child("images/users/"+ user + "/" + UUID.randomUUID().toString()).child(contentUri.getLastPathSegment());
        final UploadTask uploadTask = refe.putFile(contentUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Map userInfo = new HashMap();
                        final String user = userLocalStore.getLoggedInUser().getEmail();
                        photoUrl = uri.toString();

                        DocumentReference docRef = db.collection("users").document(user);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        String unitNo = (String) document.get("unitNo");
                                        // calling visitors class
                                        VisitorClass visitorClass = new VisitorClass(user,name.getText().toString(), mobileNo.getText().toString(), icNo.getText().toString(), vehNo.getText().toString(),unitNo,date.getText().toString(), photoUrl);
                                        // passing visitor class to firestore db
                                        db.collection("users").document(user + "/" + "visits/" + UUID.randomUUID().toString()).set(visitorClass);

                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });


                    }
                });
            }
        });

    }



}
