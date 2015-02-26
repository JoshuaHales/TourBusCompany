//Package:
package com.busSoftware.app;

//Declaring The Varibles:
public class Garage {
    private int garageID;
    private String garageName;
    private String garageAddress;
    private int garagePhoneNo;
    private String managerName;
    
    //.This Is Calling The Bus Constructor (When ID Is Present):
    public Garage(int gid, String gn, String ga, int gpn, String mn) {
        this.garageID = gid;
        this.garageName = gn;
        this.garageAddress = ga;
        this.garagePhoneNo = gpn;
        this.managerName = mn; 
    }
    
    //(When ID Is Not Present):
    public Garage(String gn, String ga, int gpn, String mn){
        this(-1, gn, ga, gpn, mn);
    }
    
   //For garageID:
    public int getGarageID(){
        return garageID;
    }
    public void setGarageID(int garageID){
        this.garageID = garageID;
    }
    
    //For garageName:
    public String getGarageName(){
        return garageName;
    }
    public void setGarageName(String garageName){
        this.garageName = garageName;
    }
    
    //For garageAddress:
    public String getGarageAddress(){
        return garageAddress;
    }
    public void setGarageAddress(String garageAddress){
        this.garageAddress = garageAddress;
    }
    
    //For garagePhoneNo:
    public int getGaragePhoneNo(){
        return garagePhoneNo;
    }
    public void setGaragePhoneNo(int garagePhoneNo){
        this.garagePhoneNo = garagePhoneNo;
    }
    
     //For managerName:
    public String getManagerName(){
        return managerName;
    }
    public void setManagerName(String managerName){
        this.managerName = managerName;
    }

}
