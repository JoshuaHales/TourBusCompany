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

// Main DriverTableGateway Class Code:
public class DriverTableGateway {
    private Connection mConnection;
    
    //Code For Linking For Creating The Tables That Get The Info From phpmyadmin To Print Out Onto The Screen:
    //Static Finale Constant variables:
    private static final String TABLE_NAME = "drivers";
    private static final String COLUMN_DRIVER_ID = "driverID";
    private static final String COLUMN_F_NAME = "fName";
    private static final String COLUMN_L_Name = "lName";
    private static final String COLUMN_ASSIGNMENTS_ID = "assignmentsID";
    
    public DriverTableGateway( Connection connection) {
        mConnection = connection;
    }
    
    //Insert Code:
    public int insertDriver(String fn, String ln, int aid) throws SQLException {
        // The SQL Query To Execute
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        //If -1 Is Returned Something Has Gone Wrong:
        int id = -1;
        
        // The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_F_NAME + ", " +
                COLUMN_L_Name + ", " +
                COLUMN_ASSIGNMENTS_ID + 
                ") VALUES (?, ?, ?)";
        
        //Create A PreparedStatement Object To Execute The Query And Insert The Values Into The Query:
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, fn);
        stmt.setString(2, ln);
        stmt.setInt(3, aid);
        
        //Rxecute The Query And Make Sure That One And Only One Row Was Inserted Into The Database:
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            //If One Row Is Inserted, Retrieve The DriverID Assigned To That Row:
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            id = keys.getInt(1);
        }
        
        //Return The DriverID Assigned To The Row In The Database: 
        return id;
    }
    
    //Delete Code:
    public boolean deleteDriver(int id) throws SQLException {
        // The SQL Query To Execute:
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        
        //The Required SQL DELETE Statement With Place Holder For The DriverID Of The Row To Be Remove From The Database:
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_DRIVER_ID + " = ?";
        
        //Create A PreparedStatement Object To Execute The Wuery And Insert The DriverID Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        //Return The True If One And Only One Row Was Deleted From The Database:
        return (numRowsAffected == 1);
    }
    
    //Code For Retieving All Drivers For Table In The Database:
    public List<Driver> getDriver() throws SQLException {
        //The SQL To Execute:
        String query;
        //The Java.sql.Statement Object Used To Execute The SQL Query:
        Statement stmt;
        //Thhe Java.sql.Result Representing The Result Of SQL Query:
        ResultSet rs;
        //The Java.sql.List Containing The Driver Objects The Result Of The Query DriverID Of The Driver:
        List<Driver> drivers;
        
        String fName, lName;
        int driverID, assignmentsID;
        
        //A Driver Object Created From A Row In The Result Of The Query:
        Driver d;
        
        //Execute An SQL SELECT Statement To Get A Java.util.ResultSet Representing The Results Of The SELECT Statement:
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        drivers = new ArrayList<Driver>();
        while (rs.next()) {
            driverID = rs.getInt(COLUMN_DRIVER_ID);
            fName = rs.getString(COLUMN_F_NAME);
            lName = rs.getString(COLUMN_L_Name);
            assignmentsID = rs.getInt(COLUMN_ASSIGNMENTS_ID);
  
            d = new Driver(driverID, fName, lName, assignmentsID);
            drivers.add(d);
        }
        
        return drivers;              
    }

    //Code For Updating A Driver:
    boolean updateDriver(Driver d) throws SQLException {
        //The SQL Query To Execute:
       String query;
       //The Java.sql.PreparedStatment Object Used To Execute The SQL Query:
       PreparedStatement stmt;
       int numRowsAffected;
       int aid;
       
       //The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
       query = "UPDATE " + TABLE_NAME + " SET " +
            COLUMN_F_NAME      + " = ?, " +
            COLUMN_L_Name   + " = ?, " +
            COLUMN_ASSIGNMENTS_ID     + " = ? " +
            " WHERE " + COLUMN_DRIVER_ID + " = ?";
       
       //Create A PreparedStatement Object To Execute Te Query And Insert The New Values Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, d.getFName());
        stmt.setString(2, d.getLName());
        aid = d.getAssignmentsID();
        if (aid == -1) {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(3, aid);
        } 
        stmt.setInt(4, d.getDriverID());
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
}

