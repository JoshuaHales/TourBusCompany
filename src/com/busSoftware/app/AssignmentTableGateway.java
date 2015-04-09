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

// Main AssignmentTableGateway Class Code:
public class AssignmentTableGateway {
    private Connection mConnection;
    
    //Code For Linking For Creating The Tables That Get The Info From phpmyadmin To Print Out Onto The Screen:
    //Static Finale Constant variables:
    private static final String TABLE_NAME = "assignments";
    private static final String COLUMN_ASSIGNMENTS_ID = "assignmentsID";
    private static final String COLUMN_BUS_ID = "busID";
    private static final String COLUMN_DRIVER_ID = "driverID";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_ASSIGNMENTS_DATE = "assignmentsDate";
    
    public AssignmentTableGateway( Connection connection) {
        mConnection = connection;
    }
    
    //Insert Code:
    public int insertAssignment(int bid, int did, String d, String ad) throws SQLException {
        // The SQL Query To Execute
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        //If -1 Is Returned Something Has Gone Wrong:
        int id = -1;
        
        // The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
        query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_BUS_ID + ", " +
                COLUMN_DRIVER_ID + ", " +
                COLUMN_DESCRIPTION + ", " +
                COLUMN_ASSIGNMENTS_DATE + 
                ") VALUES (?, ?, ?, ?)";
        
        //Code To Get Date Values To Work:
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date assignmentsDate;
        try {
            assignmentsDate = format.parse(ad);
        } 
        catch (ParseException ex) {
            assignmentsDate = new Date();
        }
        
        //Create A PreparedStatement Object To Execute The Query And Insert The Values Into The Query:
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        if (bid == -1) {
            stmt.setNull(1, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(1, bid);
        }
        
        if (did == -1) {
            stmt.setNull(2, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(2, did);
        }
        stmt.setString(3, d);
        stmt.setDate(4, new java.sql.Date(assignmentsDate.getTime()));
        
        //Rxecute The Query And Make Sure That One And Only One Row Was Inserted Into The Database:
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            //If One Row Is Inserted, Retrieve The BusID Assigned To That Row:
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            
            id = keys.getInt(1);
        }
        
        //Return The AssignmentID Assigned To The Row In The Database: 
        return id;
    }
    
    //Delete Code:
    public boolean deleteAssignment(int id) throws SQLException {
        // The SQL Query To Execute:
        String query;
        //The Java.sql.PreparedStatement Object Used To Execute The SQL Query:
        PreparedStatement stmt;
        int numRowsAffected;
        
        //The Required SQL DELETE Statement With Place Holder For The AssignmentID Of The Row To Be Remove From The Database:
        query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ASSIGNMENTS_ID + " = ?";
        
        //Create A PreparedStatement Object To Execute The Wuery And Insert The BusID Into The Query:
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        //Return The True If One And Only One ROw Was Deleted From The Database:
        return (numRowsAffected == 1);
    }
    
    //Code For Retieving All Assignments For Table In The Database:
    public List<Assignment> getAssignments() throws SQLException {
        //The SQL To Execute:
        String query;
        //The Java.sql.Statement Object Used To Execute The SQL Query:
        Statement stmt;
        //Thhe Java.sql.Result Representing The Result Of SQL Query:
        ResultSet rs;
        //The Java.sql.List Containing The Bus Objects The Result Of The Query BusID Of The Bus:
        List<Assignment> assignments;
        
        String assignmentsDate, description;
        int assignmentsID, busID, driverID;
        
        //A Assignment Object Created From A Row In The Result Of The Query:
        Assignment a;
        
        //Execute An SQL SELECT Statement To Get A Java.util.ResultSet Representing The Results Of The SELECT Statement:
        query = "SELECT * FROM " + TABLE_NAME;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);
        
        assignments = new ArrayList<Assignment>();
        while (rs.next()) {
            assignmentsID = rs.getInt(COLUMN_ASSIGNMENTS_ID);
            assignmentsDate = rs.getString(COLUMN_ASSIGNMENTS_DATE);
            description = rs.getString(COLUMN_DESCRIPTION);
            busID = rs.getInt(COLUMN_BUS_ID);
              if (rs.wasNull()) {
                busID = -1;
            }
              
            driverID = rs.getInt(COLUMN_DRIVER_ID);
              if (rs.wasNull()) {
                driverID = -1;
            }

            a = new Assignment(assignmentsID, busID, driverID, description, assignmentsDate);
            assignments.add(a);
        }
        
        return assignments;              
    }

    //Code For Updating A Assignment:
    boolean updateAssignment(Assignment a) throws SQLException {
        //The SQL Query To Execute:
       String query;
       //The Java.sql.PreparedStatment Object Used To Execute The SQL Query:
       PreparedStatement stmt;
       int numRowsAffected;
       int bid;
       int did;
       
       //The Required SQL INSERT Statement With Place Holders For The Values To Be Inserted Into The Database:
       query = "UPDATE " + TABLE_NAME + " SET " +
            COLUMN_BUS_ID           + " = ?, " +
            COLUMN_DRIVER_ID        + " = ?, " +
            COLUMN_DESCRIPTION        + " = ?, " +
            COLUMN_ASSIGNMENTS_DATE + " = ? " +
            " WHERE " + COLUMN_ASSIGNMENTS_ID + " = ?";
       
       //Create A PreparedStatement Object To Execute Te Query And Insert The New Values Into The Query:
        stmt = mConnection.prepareStatement(query);
        bid = a.getBusID();
        if (bid == -1) {
            stmt.setNull(1, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(1, bid);
        }
        
        did = a.getDriverID();
        if (did == -1) {
            stmt.setNull(2, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(2, did);
        }
        stmt.setString(3, a.getDescription());
        stmt.setString(4, a.getAssignmentsDate());
        stmt.setInt(5, a.getAssignmentsID());
        
        //Execute The Query:
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }
}

