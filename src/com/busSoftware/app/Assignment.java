//Package:
package com.busSoftware.app;

//Declaring The Varibles:
public class Assignment {
    private int assignmentsID;
    private int busID;
    private int driverID;
    private String description;
    private String assignmentsDate;
    
    //.This Is Calling The Bus Constructor (When ID Is Present):
    public Assignment(int aid, int bid, int did, String d, String ad) {
        this.assignmentsID = aid;
        this.busID = bid;
        this.driverID = did;
        this.description = d;
        this.assignmentsDate = ad;
    }
    
    //(When ID Is Not Present):
    public Assignment(int bid,int did, String d, String ad){
        this(-1, bid, did, d, ad);
    }
    
   //For busID:
    public int getBusID(){
        return busID;
    }
    public void setBusID(int busID){
        this.busID = busID;
    }
    
    //For assignmentsID:
    public int getAssignmentsID(){
        return assignmentsID;
    }
    public void setAssignmentsID(int assignmentsID){
        this.assignmentsID = assignmentsID;
    }
    
    //For driverID:
    public int getDriverID(){
        return driverID;
    }
    public void setDriverID(int driverID){
        this.driverID = driverID;
    }
    
    //For description:
    public String getDescription(){
        return description;
    }
    public void setDesciption(String description){
        this.description = description;
    }
    
    //For assignmentsDate:
    public String getAssignmentsDate(){
        return assignmentsDate;
    }
    public void setAssignmentsDate(String assignmentsDate){
        this.assignmentsDate = assignmentsDate;
    }
}
