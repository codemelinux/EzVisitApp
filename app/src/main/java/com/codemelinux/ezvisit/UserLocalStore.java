package com.codemelinux.ezvisit;

import android.content.Context;
import android.content.SharedPreferences;


public class UserLocalStore {

    public static final String SP_NAME = "userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(Users user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("email", user.email);
        userLocalDatabaseEditor.putString("password", user.password);
        userLocalDatabaseEditor.commit();
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("loggedIn", false)==true){
            return true;
        }else {
            return false;
        }
    }

    public Users getLoggedInUser() {

        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");


        Users user = new Users(email, password);
        return user;
    }
}
