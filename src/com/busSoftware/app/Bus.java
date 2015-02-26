//Package:
package com.busSoftware.app;

//Declaring The Varibles:
public class Bus {
    private int busID;
    private String registrationNo;
    private String busMake;
    private String busModel;
    private int busSeats;
    private String busEngineSize;
    private String purchaseDate;
    private String dueServiceDate;
    private int garageID;
    
    //.This Is Calling The Bus Constructor (When ID Is Present):
    public Bus(int bid, String rn, String bmk, String bml, int bs, String bes, String pd, String dsd, int gid) {
        this.busID = bid;
        this.registrationNo = rn;
        this.busMake = bmk;
        this.busModel = bml;
        this.busSeats = bs;
        this.busEngineSize = bes;
        this.purchaseDate = pd;
        this.dueServiceDate = dsd;
        this.garageID = gid;    
    }
    
    //(When ID Is Not Present):
    public Bus(String rn, String bmk, String bml, int bs, String bes, String pd, String dsd, int gid){
        this(-1, rn, bmk, bml, bs, bes, pd, dsd, gid);
    }
    
   //For busID:
    public int getBusID(){
        return busID;
    }
    public void setBusID(int busID){
        this.busID = busID;
    }
    
    //For registrationNo:
    public String getRegistrationNo(){
        return registrationNo;
    }
    public void setRegistrationNo(String registrationNo){
        this.registrationNo = registrationNo;
    }
    
    //For busMake:
    public String getBusMake(){
        return busMake;
    }
    public void setBusMake(String busMake){
        this.busMake = busMake;
    }
    
    //For busModel:
    public String getBusModel(){
        return busModel;
    }
    public void setBusModel(String busModel){
        this.busModel = busModel;
    }
    
     //For busSeats:
    public int getBusSeats(){
        return busSeats;
    }
    public void setBusSeats(int busSeats){
        this.busSeats = busSeats;
    }
    
    //For busEngineSize:
    public String getBusEngineSize(){
        return busEngineSize;
    }
    public void setBusEngineSize(String busEngineSize){
        this.busEngineSize = busEngineSize;
    }
    
    //For purchaseDate:
    public String getPurchaseDate(){
        return purchaseDate;
    }
    public void setPurchaseDate(String purchaseDate){
        this.purchaseDate = purchaseDate;
    }
    
    //For dueServiceDate:
    public String getDueServiceDate(){
        return dueServiceDate;
    }
    public void setDueServiceDate(String dueServiceDate){
        this.dueServiceDate = dueServiceDate;
    }
    
    //For garageID:
    public int getGarageID(){
        return garageID;
    }
    public void setGarageID(int garageID){
        this.garageID = garageID;
    }

   
}
