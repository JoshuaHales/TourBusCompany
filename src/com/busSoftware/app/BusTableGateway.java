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

// Main BusTableGateway Class Code:
public class BusTableGateway {
    private Connection mConnection;
    
    //Code For Linking For Creating The Tables That Get The Info From phpmyadmin To Print Out Onto The Screen:
    //Static Finale Constant variables:
    private static final String TABLE_NAME = "Buses";
    private static final String COLUMN_BUS_ID = "busID";
    private static final String COLUMN_REGISTRATION_NO = "registrationNo";
    private static final String COLUMN_BUS_MAKE = "busMake";
    private static final String COLUMN_BUS_MODEL = "busModel";
    private static final String COLUMN_BUS_SEATS = "busSeats";
    private static final String COLUMN_BUS_ENGINE_SIZE = "busEngineSize";
    private static final String COLUMN_PURCHASE_DATE = "purchaseDate";
    private static final String COLUMN_DUE_SERVICE_DATE = "dueServiceDate";
    private static final String COLUMN_GARAGE_ID = "garageID";
    private static final String COLUMN_SERVICE_ID = "serviceID";
    
    public BusTableGateway( Connection connection) {
        mConnection = connection;
    }
    
    //Insert Code:
    public int insertBus(String rn, String bmk, String bml, int bs, String bes, String pd, String dsd, int gid, int sid) throws SQLException {
        // The SQL Query To Execute
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        //If -1 Is Returned Something Has Gone Wrong:
        int id = -1;
        
        // The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_REGISTRATION_NO + ", " +
                COLUMN_BUS_MAKE + ", " +
                COLUMN_BUS_MODEL + ", " +
                COLUMN_BUS_SEATS + ", " +
                COLUMN_BUS_ENGINE_SIZE + ", " +
                COLUMN_PURCHASE_DATE + ", " +
                COLUMN_DUE_SERVICE_DATE + ", " +
                COLUMN_GARAGE_ID + ", " +
                COLUMN_SERVICE_ID +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        //Code To Get Date Values To Work:
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date purchaseDate;
        try {
            purchaseDate = format.parse(pd);
        } 
        catch (ParseException ex) {
            purchaseDate = new Date();
        }
        Date serviceDate;
        try {
            serviceDate = format.parse(dsd);
        } 
        catch (ParseException ex) {
            serviceDate = new Date();
        }
        
        //Create A PreparedStatement Object To Execute The Query And Insert The Values Into The Query:
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, rn);
        stmt.setString(2, bmk);
        stmt.setString(3, bml);
        stmt.setInt(4, bs);
        stmt.setString(5, bes);
        stmt.setDate(6, new java.sql.Date(purchaseDate.getTime()));
        stmt.setDate(7, new java.sql.Date(serviceDate.getTime()));
        if (gid == -1) {
            stmt.setNull(8, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(8, gid);
        }
        
        if (sid == -1) {
            stmt.setNull(9, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(9, sid);
        }
        
        //Rxecute The Query And Make Sure That One And Only One Row Was Inserted Into The Database:
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            //If One Row Is Inserted, Retrieve The BusID Assigned To That Row:
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            id = keys.getInt(1);
        }
        
        //Return The BusID Assigned To The Row In The Database: 
        return id;
    }
    
    //Delete Code:
    public boolean deleteBus(int id) throws SQLException {
        // The SQL Query To Execute:
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        
        //The Required SQL DELETE Statement With Place Holder For The BusID Of The Row To Be Remove From The Database:
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_BUS_ID + " = ?";
        
        //Create A PreparedStatement Object To Execute The Wuery And Insert The BusID Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        //Return The True If One And Only One ROw Was Deleted From The Database:
        return (numRowsAffected == 1);
    }
    
    //Code For Retieving All Buses For Table In The Database:
    public List<Bus> getBuses() throws SQLException {
        //The SQL To Execute:
        String query;
        //The Java.sql.Statement Object Used To Execute The SQL Query:
        Statement stmt;
        //Thhe Java.sql.Result Representing The Result Of SQL Query:
        ResultSet rs;
        //The Java.sql.List Containing The Bus Objects The Result Of The Query BusID Of The Bus:
        List<Bus> buses;
        
        String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
        int busID, busSeats, garageID, serviceID;
        
        //A Bus Object Created From A Row In The Result Of The Query:
        Bus b;
        
        //Execute An SQL SELECT Statement To Get A Java.util.ResultSet Representing The Results Of The SELECT Statement:
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        buses = new ArrayList<Bus>();
        while (rs.next()) {
            busID = rs.getInt(COLUMN_BUS_ID);
            registrationNo = rs.getString(COLUMN_REGISTRATION_NO);
            busMake = rs.getString(COLUMN_BUS_MAKE);
            busModel = rs.getString(COLUMN_BUS_MODEL);
            busSeats = rs.getInt(COLUMN_BUS_SEATS);
            busEngineSize = rs.getString(COLUMN_BUS_ENGINE_SIZE);
            purchaseDate = rs.getString(COLUMN_PURCHASE_DATE);
            dueServiceDate = rs.getString(COLUMN_DUE_SERVICE_DATE);
            garageID = rs.getInt(COLUMN_GARAGE_ID);
              if (rs.wasNull()) {
                garageID = -1;
            }
              
            serviceID = rs.getInt(COLUMN_SERVICE_ID);
              if (rs.wasNull()) {
                serviceID = -1;
            }

            b = new Bus(busID, registrationNo, busMake, busModel, busSeats, busEngineSize, purchaseDate, dueServiceDate, garageID, serviceID);
            buses.add(b);
        }
        
        return buses;              
    }

    //Code For Updating A Bus:
    boolean updateBus(Bus b) throws SQLException {
        //The SQL Query To Execute:
       String query;
       //The Java.sql.PreparedStatment Object Used To Execute The SQL Query:
       PreparedStatement stmt;
       int numRowsAffected;
       int gid;
       int sid;
       
       //The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
       query = "UPDATE " + TABLE_NAME + " SET " +
            COLUMN_REGISTRATION_NO  + " = ?, " +
            COLUMN_BUS_MAKE         + " = ?, " +
            COLUMN_BUS_MODEL        + " = ?, " +
            COLUMN_BUS_SEATS        + " = ?, " +
            COLUMN_BUS_ENGINE_SIZE  + " = ?, " +
            COLUMN_PURCHASE_DATE    + " = ?, " +
            COLUMN_DUE_SERVICE_DATE + " = ?, " +
            COLUMN_GARAGE_ID + " = ?, " +
            COLUMN_SERVICE_ID + " = ? " +
            " WHERE " + COLUMN_BUS_ID + " = ?";
       
       //Create A PreparedStatement Object To Execute Te Query And Insert The New Values Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, b.getRegistrationNo());
        stmt.setString(2, b.getBusMake());
        stmt.setString(3, b.getBusModel());
        stmt.setInt(4, b.getBusSeats());
        stmt.setString(5, b.getBusEngineSize());
        stmt.setString(6, b.getPurchaseDate());
        stmt.setString(7, b.getDueServiceDate());
        gid = b.getGarageID();
        if (gid == -1) {
            stmt.setNull(8, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(8, gid);
        }
        
        sid = b.getServiceID();
        if (sid == -1) {
            stmt.setNull(9, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(9, sid);
        }
        
        stmt.setInt(10, b.getBusID());
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
}

