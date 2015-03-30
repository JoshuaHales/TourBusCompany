//Package:
package com.busSoftware.app;

//Imported Code Libraries:
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
    //Creates A List For All garages:
    List<Garage> garages;
    //Creates A List For All services:
    List<Service> services;
    //Creates A List For All assignments:
    List<Assignment> assignments;
    //Creates A List For All drivers:
    List<Driver> drivers;
    
    //Instance Of Bus Table Gateway:
    BusTableGateway busGateway;
    //Instance Of Garage Table Gateway:
    GarageTableGateway garageGateway;
    //Instance Of Service Table Gateway:
    ServiceTableGateway serviceGateway;
    //Instance Of Assignment Table Gateway:
    AssignmentTableGateway assignmentGateway;
    //Instance Of Driver Table Gateway:
    DriverTableGateway driverGateway;
    
    //Connection To Database Reference To Instance For Each Gateway,Surronding The Block With A Try And Catch:
    private Model(){
        //Passing A Parameter To the Table Gateways:
        try {
            Connection conn = DataBaseConnection.getInstance();
            this.busGateway = new BusTableGateway(conn);
            this.garageGateway = new GarageTableGateway(conn);
            this.serviceGateway = new ServiceTableGateway(conn);
            this.assignmentGateway = new AssignmentTableGateway(conn);
            this.driverGateway = new DriverTableGateway(conn);
            
            this.buses = this.busGateway.getBuses();
            this.garages = this.garageGateway.getGarage();
            this.services = this.serviceGateway.getService();
            this.assignments = this.assignmentGateway.getAssignments();
            this.drivers = this.driverGateway.getDriver();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*------------------------------------------------------------------------*/
    /*-------------------------------BUS CODE---------------------------------*/
    /*------------------------------------------------------------------------*/
    //Add Bus List Code:
    public boolean addBus(Bus b) {
        boolean result = false;
        try {
            int busID = this.busGateway.insertBus(b.getRegistrationNo(), b.getBusMake(), b.getBusModel(), b.getBusSeats(), b.getBusEngineSize(), b.getPurchaseDate(), b.getDueServiceDate(), b.getGarageID(), b.getServiceID(),  b.getAssignmentsID());
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
    
    //Video point 17,42mins
    /*//Get Bus List Code:
    public List<Bus> getBusByGarageID(int garageID) {
        List<Bus> list = new ArrayList<Bus>();
        for (Bus b : this.buses) {
            if (b.getGarageID() = garageID) {
                list.add(b);
            }
        }
        return list;
    }*/

    //Find Bus List Code:
    Bus findBusByBusID(int busID) {
        Bus b = null;
        int i = 0;
        boolean test = true;
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
    
    /*------------------------------------------------------------------------*/
    /*-------------------------------GARAGE CODE------------------------------*/
    /*------------------------------------------------------------------------*/
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
    
    
    /*------------------------------------------------------------------------*/
    /*-------------------------------SERVICE CODE-----------------------------*/
    /*------------------------------------------------------------------------*/
    //Add Service List Code:
    public boolean addService(Service s) {
        boolean result = false;
        try {
            int serviceID = this.serviceGateway.insertService(s.getServiceDate(), s.getJobsDone(), s.getMechanicName());
            if (serviceID != -1) {
                s.setServiceID(serviceID);
                this.services.add(s);
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    //Code Delete Service:
    public boolean removeService(Service s) {
        boolean removed = false;
        
        try {
            removed = this.serviceGateway.deleteService(s.getServiceID());
            if (removed) {
                removed = this.services.remove(s);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }
    
    //Get Service List Code:
    public List<Service> getServices() {
        try {
            this.services = this.serviceGateway.getService();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.services;
    }

    //Find Service List Code:
    Service findServiceByServiceID(int serviceID) {
        Service s = null;
        int i = 0;
        boolean found = false;
        while (i < this.services.size() && !found) {
            s = this.services.get(i);
            if (s.getServiceID() == serviceID) {
                found = true;
            }
            else {
                i++;
            }
        }
        if (!found) {
            s = null;
        }
        return s;
    }

    //Update Service List Code:
    boolean updateService(Service s) {
        boolean updated = false;
        
        try {
            updated = this.serviceGateway.updateService(s);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
    
    /*------------------------------------------------------------------------*/
    /*-------------------------------ASSIGNMENTS CODE-------------------------*/
    /*------------------------------------------------------------------------*/
    //Add Bus List Code:
    public boolean addAssignment(Assignment a) {
        boolean result = false;
        try {
            int assignmentsID = this.assignmentGateway.insertAssignment(a.getBusID(), a.getDriverID(), a.getDescription(), a.getAssignmentsDate());
            if (assignmentsID != -1) {
                a.setAssignmentsID(assignmentsID);
                this.assignments.add(a);
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    //Code Delete Bus:
    public boolean removeAssignment(Assignment a) {
        boolean removed = false;
        
        try {
            removed = this.assignmentGateway.deleteAssignment(a.getAssignmentsID());
            if (removed) {
                removed = this.assignments.remove(a);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }
    
    //Get Bus List Code:
    public List<Assignment> getAssignments() {
        try {
            this.assignments = this.assignmentGateway.getAssignments();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.assignments;
    }

    //Find Bus List Code:
    Assignment findAssignmentByAssignmentsID(int assignmentsID) {
        Assignment a = null;
        int i = 0;
        boolean found = false;
        while (i < this.assignments.size() && !found) {
            a = this.assignments.get(i);
            if (a.getAssignmentsID() == assignmentsID) {
                found = true;
            }
            else {
                i++;
            }
        }
        if (!found) {
            a = null;
        }
        return a;
    }

    //Update Bus List Code:
    boolean updateAssignment(Assignment a) {
        boolean updated = false;
        
        try {
            updated = this.assignmentGateway.updateAssignment(a);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }
    
    /*------------------------------------------------------------------------*/
    /*-------------------------------DRIVER CODE------------------------------*/
    /*------------------------------------------------------------------------*/
    //Add Driver List Code:
    public boolean addDriver(Driver d) {
        boolean result = false;
        try {
            int driverID = this.driverGateway.insertDriver(d.getFName(), d.getLName(), d.getAssignmentsID());
            if (driverID != -1) {
                d.setDriverID(driverID);
                this.drivers.add(d);
                result = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    //Code Delete Driver:
    public boolean removeDriver(Driver d) {
        boolean removed = false;
        
        try {
            removed = this.driverGateway.deleteDriver(d.getDriverID());
            if (removed) {
                removed = this.drivers.remove(d);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return removed;
    }
    
    //Get Driver List Code:
    public List<Driver> getDrivers() {
        try {
            this.drivers = this.driverGateway.getDriver();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.drivers;
    }

    //Find Driver List Code:
    Driver findDriverByDriverID(int driverID) {
        Driver d = null;
        int i = 0;
        boolean found = false;
        while (i < this.drivers.size() && !found) {
            d = this.drivers.get(i);
            if (d.getDriverID() == driverID) {
                found = true;
            }
            else {
                i++;
            }
        }
        if (!found) {
            d = null;
        }
        return d;
    }

    //Update Driver List Code:
    boolean updateDriver(Driver d) {
        boolean updated = false;
        
        try {
            updated = this.driverGateway.updateDriver(d);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return updated;
    }

}

