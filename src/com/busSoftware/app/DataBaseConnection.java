//Package:
package com.busSoftware.app;

//Imported Code Libraries:
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Code For Connecting To Database:
public class DataBaseConnection {

    private static Connection sConnection;
    
    //This Methode Returns A Connection Object (Singleton Pattern:
    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        String host, db, user, password;
        
        //Own Database:
        //host = "daneel";
        //db = "N00133834";
        //user = "N00133834";
        //password = "N00133834";
        host = "localhost"; 
        db = "n00133834"; 
        user = "root"; 
        password = ""; 
        
        
        //If Connection Is Null Or Not Found Then Creates A Class Not Found Exception, Else Creates A Connection To The Database:
        if (sConnection == null || sConnection.isClosed()) {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver");
            //Giving A Refernce To User And Password:
            sConnection = DriverManager.getConnection(url, user, password);
        }
        return sConnection;
    } 
}
