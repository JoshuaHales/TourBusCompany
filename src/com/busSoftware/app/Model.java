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
    List<Garage> garages;
    //Instance Of Bus Table Gateway:
    BusTableGateway busGateway;
    GarageTableGateway garageGateway;
    
    //Connection To Database Reference To Instance For BusTableGateway,Surronding The Block With A Try And Catch:
    private Model(){
        //Passing A Parameter To the BusTableGateway:
        try {
            Connection conn = DataBaseConnection.getInstance();
            this.busGateway = new BusTableGateway(conn);
            this.garageGateway = new GarageTableGateway(conn);
            
            this.buses = this.busGateway.getBuses();
            this.garages = this.garageGateway.getGarage();
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
            int busID = this.busGateway.insertBus(b.getRegistrationNo(), b.getBusMake(), b.getBusModel(), b.getBusSeats(), b.getBusEngineSize(), b.getPurchaseDate(), b.getDueServiceDate(), b.getGarageID());
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
            removed = this.busGateway.deleteBus(b.getBusID());
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
            this.buses = this.busGateway.getBuses();
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
            updated = this.busGateway.updateBus(b);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
    
    //Code Add Garage:
    public boolean addGarage(Garage g) {
        boolean result = false;
        try {
            int garageID = this.garageGateway.insertGarage(g.getGarageName(), g.getGarageAddress(), g.getGaragePhoneNo(), g.getManagerName());
            if (garageID != -1) {
                g.setGarageID(garageID);
                this.garages.add(g);
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    //Code Delete Garage:
    public boolean removeGarage(Garage g) {
        boolean removed = false;
        
        try {
            removed = this.garageGateway.deleteGarage(g.getGarageID());
            if (removed) {
                removed = this.garages.remove(g);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }

    public List<Garage> getGarages() {
        try {
            this.garages = this.garageGateway.getGarage();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.garages;
    }
 

    Garage findGarageByGarageID(int garageID) {
        Garage g = null;
        int i = 0;
        boolean found = false;
        while (i < this.garages.size() && !found) {
            g = this.garages.get(i);
            if (g.getGarageID() == garageID) {
                found = true;
            }
            else {
                i++;
            }
        }
        if (!found) {
            g = null;
        }
        return g;
    }

    //Update Bus List Code:
    boolean updateGarage(Garage g) {
        boolean updated = false;
        
        try {
            updated = this.garageGateway.updateGarage(g);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
}
