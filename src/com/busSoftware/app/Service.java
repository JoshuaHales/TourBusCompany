//Package:
package com.busSoftware.app;

//Declaring The Varibles:
public class Service {
    private int serviceID;
    private String serviceDate;
    private String jobsDone;
    private String mechanicName;
    
    //.This Is Calling The Bus Constructor (When ID Is Present):
    public Service(int sid, String sd, String jd, String mn) {
        this.serviceID = sid;
        this.serviceDate = sd;
        this.jobsDone = jd;
        this.mechanicName = mn;
    }
    
    //(When ID Is Not Present):
    public Service(String sd, String jd, String mn){
        this(-1, sd, jd, mn);
    }
    
   //For serviceID:
    public int getServiceID(){
        return serviceID;
    }
    public void setServiceID(int serviceID){
        this.serviceID = serviceID;
    }
    
    //For serviceDate:
    public String getServiceDate(){
        return serviceDate;
    }
    public void setServiceDate(String serviceDate){
        this.serviceDate = serviceDate;
    }
    
    //For jobsDone:
    public String getJobsDone(){
        return jobsDone;
    }
    public void setJobsDone(String jobsDone){
        this.jobsDone = jobsDone;
    }
    
    //For mechanicName:
    public String getMechanicName(){
        return mechanicName;
    }
    public void setMechanicName(String mechanicName){
        this.mechanicName = mechanicName;
    }

}
