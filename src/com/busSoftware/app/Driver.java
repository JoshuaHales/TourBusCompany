//Package:
package com.busSoftware.app;

//Declaring The Varibles:
public class Driver implements Comparable<Driver>{
    private int driverID;
    private String fName;
    private String lName;
    private int assignmentsID;
    
    //This Is Calling The Driver Constructor (When ID Is Present):
    public Driver(int did, String fn, String ln, int aid) {
        this.driverID = did;
        this.fName = fn;
        this.lName = ln;
        this.assignmentsID = aid;
    }
    
    //(When ID Is Not Present):
    public Driver(String fn, String ln, int did){
        this(-1, fn, ln, did);
    }
    
   //For driverID:
    public int getDriverID(){
        return driverID;
    }
    public void setDriverID(int driverID){
        this.driverID = driverID;
    }
    
    //For fName:
    public String getFName(){
        return fName;
    }
    public void setFName(String fName){
        this.fName = fName;
    }
    
    //For lName:
    public String getLName(){
        return lName;
    }
    public void setLName(String lName){
        this.lName = lName;
    }
    
    //For assignmentsID:
    public int getAssignmentsID(){
        return assignmentsID;
    }
    public void setAssignmentsID(int assignmentsID){
        this.assignmentsID = assignmentsID;
    }
    
    //Code To Order By First Name:
    @Override
    public int compareTo(Driver that) {
        String myFName = this.getFName();
        String yourFName = that.getFName();
        
        return myFName.compareTo(yourFName);
    }
}
