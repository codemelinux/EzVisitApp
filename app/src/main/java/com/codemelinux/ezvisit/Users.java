package com.codemelinux.ezvisit;

public class Users {


   String email;
   String password;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users() {

    }


    public String getEmail() {
        return email;
    }

    public void setName(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





}
