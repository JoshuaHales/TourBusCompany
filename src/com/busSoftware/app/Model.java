//Package:
package com.busSoftware.app;

//Imported Code Libraries:
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Main Model Code:
public class Model {
    
    //Stores Instance Of Single Model Class:
    private static Model instance = null;
    
    //Public Methode To Gain Acces To Instnace:
    public static Model getInstance() {
        //If Instance Is Null Then Create Instance In The Class Mode, Then Return Instance Value:
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    //Creates A List For All buses:
    List<Bus> buses;
    //Instance Of Bus Table Gateway:
    BusTableGateway gateway;
    
    //Connection To Database Reference To Instance For BusTableGateway,Surronding The Block With A Try And Catch:
    private Model(){
        //Passing A Parameter To the BusTableGateway:
        try {
            Connection conn = DataBaseConnection.getInstance();
            this.gateway = new BusTableGateway(conn);
            
            this.buses = this.gateway.getBuses();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Add Bus List Code:
    public boolean addBus(Bus b) {
        boolean result = false;
        try {
            int busID = this.gateway.insertBus(b.getRegistrationNo(), b.getBusMake(), b.getBusModel(), b.getBusSeats(), b.getBusEngineSize(), b.getPurchaseDate(), b.getDueServiceDate());
            if (busID != -1) {
                b.setBusID(busID);
                this.buses.add(b);
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    //Code Delete Bus:
    public boolean removeBus(Bus b) {
        boolean removed = false;
        
        try {
            removed = this.gateway.deleteBus(b.getBusID());
            if (removed) {
                removed = this.buses.remove(b);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }
    
    //Get Bus List Code:
    public List<Bus> getBuses() {
        try {
            this.buses = this.gateway.getBuses();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.buses;
    }

    //Find Bus List Code:
    Bus findBusByBusID(int busID) {
        Bus b = null;
        int i = 0;
        boolean found = false;
        while (i < this.buses.size() && !found) {
            b = this.buses.get(i);
            if (b.getBusID() == busID) {
                found = true;
            }
            else {
                i++;
            }
        }
        if (!found) {
            b = null;
        }
        return b;
    }

    //Update Bus List Code:
    boolean updateBus(Bus b) {
        boolean updated = false;
        
        try {
            updated = this.gateway.updateBus(b);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
}
