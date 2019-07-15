package com.codemelinux.ezvisit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {


    private EditText userMail,userPassword;
    private Button btnLogin;
    private ProgressBar loginProgress;
    //private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageView loginPhoto;
    //FirebaseUser user;
    private String UserId;

    UserLocalStore userLocalStore;


    public static  final  String COLLECTION_NAME_KEY = "users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.loginBtn);
        loginProgress = findViewById(R.id.login_progress);
       // mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //HomeActivity = new Intent(this,com.codemelinux.aws.blogapp.Activities.Home.class);
        loginPhoto = findViewById(R.id.login_photo);
        userLocalStore = new UserLocalStore(this);




        loginProgress.setVisibility(View.INVISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.VISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if (mail.isEmpty() || password.isEmpty()) {
                    showMessage("Please Verify All Field");
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else
                {
                    signIn();

                }

            }
        });


    }
    /*
    private void signIn(String mail, String password) {


        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    updateUI();

                }
                else {
                    showMessage(task.getException().getMessage());
                    btnLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }


            }
        });



    } */



    private void signIn() {


        if (!userMail.getText().toString().equals("") && !userPassword.getText().toString().equals(""))
        {

            DocumentReference docRef = db.collection(COLLECTION_NAME_KEY).document(userMail.getText().toString());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists())
                    {
                        Users users = documentSnapshot.toObject(Users.class);

                        if (users.getPassword().equals(userPassword.getText().toString()))
                        {

                            Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
                            loginProgress.setVisibility(View.INVISIBLE);
                            btnLogin.setVisibility(View.VISIBLE);

                            //// store user email and password
                            Users registeredData = new Users(userMail.getText().toString(), userPassword.getText().toString());

                            //Users user = new Users(null, null);
                            userLocalStore.storeUserData(registeredData);
                            userLocalStore.setUserLoggedIn(true);

                            updateUI();
                        }

                        else
                        {
                            Toast.makeText(LoginActivity.this, "Passsword Mismatching", Toast.LENGTH_SHORT).show();
                            btnLogin.setVisibility(View.VISIBLE);
                            loginProgress.setVisibility(View.INVISIBLE);
                        }

                    }

                    else
                    {

                        Toast.makeText(getApplicationContext(), "Check your Email ", Toast.LENGTH_SHORT).show();
                        btnLogin.setVisibility(View.VISIBLE);
                        loginProgress.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }

        else

        {
            Toast.makeText(LoginActivity.this, "Email or Password Cannot be Empty", Toast.LENGTH_SHORT).show();
        }



    }

    private void updateUI() {

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    public boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void logUserIn(Users returnedUser) {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();


        if(authenticate() == true) {
            //user is already connected  so we need to redirect him to home page
            updateUI();

        }

        /*
        if(user != null){

            final String userID = user.getUid();
            db.collection(COLLECTION_NAME_KEY).document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String user_name = documentSnapshot.getString("email");
                    String password  = documentSnapshot.getString("password");


                    userMail.setText(user_name);
                    userPassword.setText(password);
                }
            });

            updateUI();
        }*/



    }
}
