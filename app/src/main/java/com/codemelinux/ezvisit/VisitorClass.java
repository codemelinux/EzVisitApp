package com.codemelinux.ezvisit;


public class VisitorClass {
    private String email, name, mobileNo, icNo, vehNo, unitNo, date,photoUrl, timeEnter;


    public VisitorClass () {

    }

    public VisitorClass (String email, String name, String mobileNo, String icNo, String vehNo, String unitNo, String date,  String photoUrl) {
        this.email = email;
        this.name = name;
        this.mobileNo = mobileNo;
        this.icNo = icNo;
        this.vehNo = vehNo;
        this.unitNo = unitNo;
        //this.timeEnter = timeEnter;
        this.date= date;
        this.photoUrl = photoUrl;



    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIcNo(){
        return icNo;
    }
    public void  setIcNo(String icNo){
        this.icNo = icNo;
    }

    public String getVehNo(){
        return vehNo;
    }
    public void  setVehNo(String vehNo){
        this.vehNo = vehNo;
    }

    public String getUnitNo(){
        return unitNo;
    }
    public void  setUnitNo(String unitNo){
        this.icNo = unitNo;
    }

   /* public String getTimeEnter(){
        return timeEnter;
    }
    public void  setTimeEnter(String timeEnter){
        this.timeEnter = timeEnter;
    }*/

    public String getDate(){
        return date;
    }
    public void  setDate(String date){
        this.date = date;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }
    public void  setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
