package com.codemelinux.ezvisit;

public class VisitClassModel {

    private String  name, mobileNo, icNo, ndate ,photoUrl, ntimeEnter, exitTime;


    public VisitClassModel() {

    }

    public VisitClassModel( String name, String mobileNo, String icNo, String ndate, String ntimeEnter, String exitTime) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.icNo = icNo;
        this.ntimeEnter = ntimeEnter;
        this.ndate= ndate;
        this.exitTime = exitTime;
        this.photoUrl = photoUrl;



    }


    public  String getExitTime(){
        return exitTime;
    }

    public void setExitTime(String exitTime){
        this.exitTime = exitTime;
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


    public String getTimeEnter(){
        return ntimeEnter;
    }
    public void  setTimeEnter(String timeEnter){
        this.ntimeEnter = timeEnter;
    }

    public String getNDate(){
        return ndate;
    }
    public void  setDate(String ndate){
        this.ndate = ndate;
    }

    public String getPhotoUrl(){
        return photoUrl;
    }
    public void  setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
}
