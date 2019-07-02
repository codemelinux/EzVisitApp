package com.codemelinux.ezvisit;


public class VisitorClass {
    private String name, mobileNo, icNo, vehNo, unitNo, time, date,photoUrl;


    public VisitorClass () {

    }

    public VisitorClass (String name, String mobileNo, String icNo, String vehNo, String unitNo, String time, String date, String photoUrl) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.icNo = icNo;
        this.vehNo = vehNo;
        this.unitNo = unitNo;
        this.time = time;
        this.date= date;
        this.photoUrl = photoUrl;



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

    public String getTime(){
        return time;
    }
    public void  setTime(String time){
        this.time = time;
    }

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
