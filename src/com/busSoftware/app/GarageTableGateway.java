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
public class GarageTableGateway {
    private Connection mConnection;
    
    //Code For Linking For Creating The Tables That Get The Info From phpmyadmin To Print Out Onto The Screen:
    //Static Finale Constant variables:
    private static final String TABLE_NAME = "garages";
    private static final String COLUMN_GARAGE_ID = "garageID";
    private static final String COLUMN_GARAGE_NAME = "garageName";
    private static final String COLUMN_GARAGE_ADDRESS = "garageAddress";
    private static final String COLUMN_GARAGE_PHONE_NO = "garagePhoneNo";
    private static final String COLUMN_MANAGER_NAME = "managerName";
    
    public GarageTableGateway( Connection connection) {
        mConnection = connection;
    }
    
    //Insert Code:
    public int insertGarage(String gn, String ga, int gpn, String mn) throws SQLException {
        // The SQL Query To Execute
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        //If -1 Is Returned Something Has Gone Wrong:
        int id = -1;
        
        // The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_GARAGE_NAME + ", " +
                COLUMN_GARAGE_ADDRESS + ", " +
                COLUMN_GARAGE_PHONE_NO + ", " +
                COLUMN_MANAGER_NAME + 
                ") VALUES (?, ?, ?, ?)";
        
        //Create A PreparedStatement Object To Execute The Query And Insert The Values Into The Query:
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, gn);
        stmt.setString(2, ga);
        stmt.setInt(3, gpn);
        stmt.setString(4, mn);
        
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
    public boolean deleteGarage(int id) throws SQLException {
        // The SQL Query To Execute:
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        
        //The Required SQL DELETE Statement With Place Holder For The BusID Of The Row To Be Remove From The Database:
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_GARAGE_ID + " = ?";
        
        //Create A PreparedStatement Object To Execute The Wuery And Insert The BusID Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        //Return The True If One And Only One ROw Was Deleted From The Database:
        return (numRowsAffected == 1);
    }
    
    //Code For Retieving All Buses For Table In The Database:
    public List<Garage> getGarage() throws SQLException {
        //The SQL To Execute:
        String query;
        //The Java.sql.Statement Object Used To Execute The SQL Query:
        Statement stmt;
        //Thhe Java.sql.Result Representing The Result Of SQL Query:
        ResultSet rs;
        //The Java.sql.List Containing The Bus Objects The Result Of The Query BusID Of The Bus:
        List<Garage> garages;
        
        String garageName, garageAddress, managerName;
        int garagePhoneNo, garageID;
        
        //A Bus Object Created From A Row In The Result Of The Query:
        Garage g;
        
        //Execute An SQL SELECT Statement To Get A Java.util.ResultSet Representing The Results Of The SELECT Statement:
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        garages = new ArrayList<Garage>();
        while (rs.next()) {
            garageID = rs.getInt(COLUMN_GARAGE_ID);
            garageName = rs.getString(COLUMN_GARAGE_NAME);
            garageAddress = rs.getString(COLUMN_GARAGE_ADDRESS);
            garagePhoneNo = rs.getInt(COLUMN_GARAGE_PHONE_NO);
            managerName = rs.getString(COLUMN_MANAGER_NAME);
  
            g = new Garage(garageID, garageName, garageAddress, garagePhoneNo, managerName);
            garages.add(g);
        }
        
        return garages;              
    }

    //Code For Updating A Bus:
    boolean updateGarage(Garage g) throws SQLException {
        //The SQL Query To Execute:
       String query;
       //The Java.sql.PreparedStatment Object Used To Execute The SQL Query:
       PreparedStatement stmt;
       int numRowsAffected;
       
       //The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
       query = "UPDATE " + TABLE_NAME + " SET " +
            COLUMN_GARAGE_NAME      + " = ?, " +
            COLUMN_GARAGE_ADDRESS   + " = ?, " +
            COLUMN_GARAGE_PHONE_NO  + " = ?, " +
            COLUMN_MANAGER_NAME     + " = ? " +
            " WHERE " + COLUMN_GARAGE_ID + " = ?";
       
       //Create A PreparedStatement Object To Execute Te Query And Insert The New Values Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, g.getGarageName());
        stmt.setString(2, g.getGarageAddress());
        stmt.setInt(3, g.getGaragePhoneNo());
        stmt.setString(4, g.getManagerName());
        stmt.setInt(5, g.getGarageID());
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
}

