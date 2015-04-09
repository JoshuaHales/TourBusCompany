//Package:
package com.busSoftware.app;

//Imported Code Libraries:
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

// Main ServiceTableGateway Class Code:
public class ServiceTableGateway {
    private Connection mConnection;
    
    //Code For Linking For Creating The Tables That Get The Info From phpmyadmin To Print Out Onto The Screen:
    //Static Finale Constant variables:
    private static final String TABLE_NAME = "services";
    private static final String COLUMN_SERVICE_ID = "serviceID";
    private static final String COLUMN_SERVICE_DATE = "serviceDate";
    private static final String COLUMN_JOBS_DONE = "jobsDone";
    private static final String COLUMN_MECHANIC_NAME = "mechanicName";
    
    public ServiceTableGateway( Connection connection) {
        mConnection = connection;
    }
    
    //Insert Code:
    public int insertService(String sd, String jd, String mn) throws SQLException {
        // The SQL Query To Execute
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        //If -1 Is Returned Something Has Gone Wrong:
        int id = -1;
        
        // The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_SERVICE_DATE + ", " +
                COLUMN_JOBS_DONE + ", " +
                COLUMN_MECHANIC_NAME +
                ") VALUES (?, ?, ?)";
        
        //Code To Get Date Values To Work:
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date serviceDate;
        try {
            serviceDate = format.parse(sd);
        } 
        catch (ParseException ex) {
            serviceDate = new Date();
        }
        
        //Create A PreparedStatement Object To Execute The Query And Insert The Values Into The Query:
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, sd);
        stmt.setString(2, jd);
        stmt.setString(3, mn);
        
        //Rxecute The Query And Make Sure That One And Only One Row Was Inserted Into The Database:
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            //If One Row Is Inserted, Retrieve The ServiceID Assigned To That Row:
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            id = keys.getInt(1);
        }
        
        //Return The ServiceID Assigned To The Row In The Database: 
        return id;
    }
    
    //Delete Code:
    public boolean deleteService(int id) throws SQLException {
        // The SQL Query To Execute:
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        
        //The Required SQL DELETE Statement With Place Holder For The ServiceID Of The Row To Be Remove From The Database:
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_SERVICE_ID + " = ?";
        
        //Create A PreparedStatement Object To Execute The Wuery And Insert The ServiceID Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        //Return The True If One And Only One ROw Was Deleted From The Database:
        return (numRowsAffected == 1);
    }
    
    //Code For Retieving All Buses For Table In The Database:
    public List<Service> getService() throws SQLException {
        //The SQL To Execute:
        String query;
        //The Java.sql.Statement Object Used To Execute The SQL Query:
        Statement stmt;
        //Thhe Java.sql.Result Representing The Result Of SQL Query:
        ResultSet rs;
        //The Java.sql.List Containing The Bus Objects The Result Of The Query BusID Of The Bus:
        List<Service> services;
        
        String serviceDate, jobsDone, mechanicName;
        int serviceID;
        
        //A Service Object Created From A Row In The Result Of The Query:
        Service s;
        
        //Execute An SQL SELECT Statement To Get A Java.util.ResultSet Representing The Results Of The SELECT Statement:
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        services = new ArrayList<Service>();
        while (rs.next()) {
            serviceID = rs.getInt(COLUMN_SERVICE_ID);
            serviceDate = rs.getString(COLUMN_SERVICE_DATE);
            jobsDone = rs.getString(COLUMN_JOBS_DONE);
            mechanicName = rs.getString(COLUMN_MECHANIC_NAME);
  
            s = new Service(serviceID, serviceDate, jobsDone, mechanicName);
            services.add(s);
        }
        
        return services;              
    }

    //Code For Updating A Service:
    boolean updateService(Service s) throws SQLException {
        //The SQL Query To Execute:
       String query;
       //The Java.sql.PreparedStatment Object Used To Execute The SQL Query:
       PreparedStatement stmt;
       int numRowsAffected;
       
       //The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
       query = "UPDATE " + TABLE_NAME + " SET " +
            COLUMN_SERVICE_DATE  + " = ?, " +
            COLUMN_JOBS_DONE         + " = ?, " +
            COLUMN_MECHANIC_NAME + " = ? " +
            " WHERE " + COLUMN_SERVICE_ID + " = ?";
       
       //Create A PreparedStatement Object To Execute Te Query And Insert The New Values Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, s.getServiceDate());
        stmt.setString(2, s.getJobsDone());
        stmt.setString(3, s.getMechanicName());
        stmt.setInt(4, s.getServiceID());
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
}

