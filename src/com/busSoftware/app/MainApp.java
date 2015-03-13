//Package:
package com.busSoftware.app;

//Imported Code Libraries:
import java.util.List;
import java.util.Scanner;

//Main App Code:
public class MainApp {

    //Sets Up User Interface Options:
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Model model = Model.getInstance();
        int opt;
        
        // Do/While Loop For Possible Options, As Long As 5 Is Not Entered It Will Continue To Run:
        do{
            System.out.println("TOUR BUS COMPANY APP");
            System.out.println("---------------------");
            System.out.println("-1 Create A New Bus.");
            System.out.println("-2 Delete Existing Bus.");
            System.out.println("-3 Edit Existing Buses.");
            System.out.println("-4 View All Buses.");
            System.out.println();
            System.out.println("-5 Create A New Garage.");
            System.out.println("-6 Delete Existing Garage.");
            System.out.println("-7 Edit Existing Garages.");
            System.out.println("-8 View All Garages.");
            System.out.println("-9 Exit Tour Bus Company.");
            System.out.println();
        
            System.out.print("-Enter Option:");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);
        
            //If Options Is CLicked Then Break:
            System.out.println("-You Chose Option: " + opt);
            switch (opt) {
                //To Create A New Bus:
                case 1: {
                    System.out.println("-Creating A New Bus.");
                    createBus(keyboard,model);
                    break;
                }
                //To Delete A Existing Bus:
                case 2: {
                    System.out.println("-Deleting A Bus.");
                    deleteBus(keyboard,model);
                    break;
                }
                //To Update A Existing Bus: 
                case 3: {
                    System.out.println("-Updating All Buses.");
                    editBus(keyboard, model);
                    break;
                }
                 //To View All Buses: 
                case 4: {
                    System.out.println("-Viewing All Buses.");
                    viewBuses(model);
                    break;
                }
                //To Create A New Garage:
                case 5: {
                    System.out.println("-Creating A New Garage.");
                    createGarage(keyboard,model);
                    break;
                }
                //To Delete A Existing Garage:
                case 6: {
                    System.out.println("-Deleting A Garage.");
                    deleteGarage(keyboard,model);
                    break;
                }
                //To Update A Existing Garage: 
                case 7: {
                    System.out.println("-Updating All Garages.");
                    editGarage(keyboard, model);
                    break;
                }
                //To View All Garages:
                case 8: {
                    System.out.println("-Viewing All Garages.");
                    viewGarages(model);
                    break;
                }
            }
        }
        //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 9);
        System.out.println("-Exiting App.");
    }
    
    //Code For Telling If Bus Was Created Or Not USing A Boolean In The Model Class:
    private static void createBus(Scanner keyboard, Model model) {
        Bus b = readBus(keyboard);
        if (model.addBus(b)) {
            System.out.println("-New Bus Added To Database.");
        }
        else {
            System.out.println("-New Bus Was'nt Added To Database.");
        }
        System.out.println();
    }
    
    //Delete Methode:
    private static void deleteBus(Scanner keyboard, Model model) {
        System.out.println("-Enter The ID Of The Bus You Want To Delete:");
            int busID = Integer.parseInt(keyboard.nextLine());
            Bus b;
                    
            b = model.findBusByBusID(busID);
            if (b != null) {
                if (model.removeBus(b)) {
                    System.out.println("-Bus Deleted.\n");
                }
                else {
                    System.out.println("-Bus Not Deleted.\n");
                }
            }
            else {
                System.out.println("-Bus Not Found.\n");
            }          
    }
    
    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editBus(Scanner kb, Model m) {
        System.out.print("-Enter The Bus ID You Want To Edit: ");
        int busID = Integer.parseInt(kb.nextLine());
        Bus b;
        
        b = m.findBusByBusID(busID);
        if (b != null) {
            editBusDetails(kb, b);
            if (m. updateBus(b)) {
                System.out.println("-Bus Updated.\n");       
            }
            else {
                System.out.println("-Bus Not Updated.\n");
            }
        }
        else {
            System.out.println("-Bus Not Found.\n");
        }
    }

    //Code For Viewing All Buses:
    private static void viewBuses(Model model) {
        List<Bus> buses = model.getBuses();
        System.out.println();
        if (buses.isEmpty()) {
            System.out.println("-There Are No Buses In The Database.");
        }
        else {
            System.out.printf("%5s %30s %20s %20s %20s %25s %25s %25s %25s\n", 
                              "-Bus ID-", 
                              "-Registration Number-", 
                              "-Bus Make-", 
                              "-Bus Model-", 
                              "-Bus Seats-", 
                              "-Bus Engine Size-", 
                              "-Purchase Date-", 
                              "-Service Due Date-",
                              "-Garage Name-");
            for (Bus pr : buses) {
                System.out.printf("%7d %30s %20s %20s %20d %25s %25s %25s %25s\n",
                pr.getBusID(),
                pr.getRegistrationNo(),
                pr.getBusMake(),
                pr.getBusModel(),
                pr.getBusSeats(),
                pr.getBusEngineSize(),
                pr.getPurchaseDate(),
                pr.getDueServiceDate(),
                pr.getGarageID());
            }
        }
        System.out.println();
    }
    
    //Code For Creating A New Bus (Reads Input From Keyboard And Stores Into ReadBus Object:
    private static Bus readBus(Scanner keyb) {
        String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
        int busSeats, garageID;
        String line;
        
        registrationNo = getString(keyb, "-Enter Registration Number: ");
        busMake = getString(keyb, "-Enter Bus Make: ");
        busModel = getString(keyb, "-Enter Bus Model: ");
        line = getString(keyb, "-Enter Amount Of Bus Seats: ");
        busSeats = Integer.parseInt(line);
        busEngineSize = getString(keyb, "-Enter Bus Engine Size: ");
        purchaseDate = getString(keyb, "-Enter Bus Purchase Date (yyyy-mm-dd): ");
        dueServiceDate = getString(keyb, "-Enter Bus Service Due Date (yyyy-mm-dd): ");
        line = getString(keyb, "Enter Garage ID (Enter -1 For No Garage): ");
        garageID = Integer.parseInt(line);
        
        Bus b =
            new Bus(registrationNo, busMake, busModel, busSeats, busEngineSize, purchaseDate, dueServiceDate, garageID);
        
        return b;
    }
    
    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editBusDetails(Scanner keyb, Bus b) {
        String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
        int busSeats, garageID;
        String line1, line2;
        
        registrationNo = getString(keyb, "-Enter Registration Number [" + b.getRegistrationNo() + "]: ");
        busMake = getString(keyb, "-Enter Bus Make [" + b.getBusMake() + "]: ");
        busModel = getString(keyb, "-Enter Bus Model [" + b.getBusModel() + "]: ");
        line1 = getString(keyb, "-Enter Number Of Bus Seats [" + b.getBusSeats() + "]: ");
        busEngineSize = getString(keyb, "-Enter Bus Engine Size [" + b.getBusEngineSize() + "]: ");
        purchaseDate = getString(keyb, "-Enter Bus Purchase Date [" + b.getPurchaseDate() + "]: ");
        dueServiceDate = getString(keyb, "-Enter Bus Service Due Date [" + b.getDueServiceDate() + "]: ");
        line2 = getString(keyb, "Enter Garage ID (Enter -1 For No Garage)[" + b.getGarageID() + "]: ");
        
        if (registrationNo.length() != 0) {
            b.setRegistrationNo(registrationNo);
        }
        if (busMake.length() != 0) {
            b.setBusMake(busMake);
        }
        if (busModel.length() != 0) {
            b.setBusModel(busModel);
        }
        if (line1.length() != 0) {
            busSeats = Integer.parseInt(line1);
            b.setBusSeats(busSeats);
        }
        if (busEngineSize.length() != 0) {
            b.setBusEngineSize(busEngineSize);
        }
        if (purchaseDate.length() != 0) {
            b.setPurchaseDate(purchaseDate);
        }
        if (dueServiceDate.length() != 0) {
            b.setDueServiceDate(dueServiceDate);
        }
        if (line2.length() != 0) {
            garageID = Integer.parseInt(line2);
            b.setGarageID(garageID);
        }
    }
    
    //Code For Telling If Garage Was Created Or Not USing A Boolean In The Model Class:
    private static void createGarage(Scanner keyboard, Model model) {
        Garage g = readGarage(keyboard);
        if (model.addGarage(g)) {
            System.out.println("-New Garage Added To Database.");
        }
        else {
            System.out.println("-New Garage Was'nt Added To Database.");
        }
        System.out.println();
    }
    
    //Delete Methode:
    private static void deleteGarage(Scanner keyboard, Model model) {
        System.out.println("-Enter The ID Of The Garage You Want To Delete:");
            int garageID = Integer.parseInt(keyboard.nextLine());
            Garage g;
                    
            g = model.findGarageByGarageID(garageID);
            if (g != null) {
                if (model.removeGarage(g)) {
                    System.out.println("-Garage Deleted.\n");
                }
                else {
                    System.out.println("-Garage Not Deleted.\n");
                }
            }
            else {
                System.out.println("-Garage Not Found.\n");
            }          
    }
    
    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editGarage(Scanner kb, Model m) {
        System.out.print("-Enter The Garage ID You Want To Edit: ");
        int garageID = Integer.parseInt(kb.nextLine());
        Garage g;
        
        g = m.findGarageByGarageID(garageID);
        if (g != null) {
            editGarageDetails(kb, g);
            if (m. updateGarage(g)) {
                System.out.println("-Garage Updated.\n");       
            }
            else {
                System.out.println("-Garage Not Updated.\n");
            }
        }
        else {
            System.out.println("-Garage Not Found.\n");
        }
    }

    //Code For Viewing All Garagees:
    private static void viewGarages(Model model) {
        List<Garage> garages = model.getGarages();
        System.out.println();
        if (garages.isEmpty()) {
            System.out.println("-There Are No Garages In The Database.");
        }
        else {
            System.out.printf("%5s %20s %40s %30s %20s\n", 
                              "-Garage ID-", 
                              "-Garage Name-", 
                              "-Garage Address-", 
                              "-Garage Phone Number-", 
                              "-Manger Name-");
            for (Garage pr : garages) {
                System.out.printf("%10d %20s %40s %30d %20s\n",
                pr.getGarageID(),
                pr.getGarageName(),
                pr.getGarageAddress(),
                pr.getGaragePhoneNo(),
                pr.getManagerName());
            }
        }
        System.out.println();
    }
    
    //Code For Creating A New Garage (Reads Input From Keyboard And Stores Into ReadGarage Object:
    private static Garage readGarage(Scanner keyb) {
        String garageName, garageAddress, managerName;
        int garagePhoneNo;
        String line;
        
        garageName = getString(keyb, "-Enter Garage Name: ");
        garageAddress = getString(keyb, "-Enter Garage Address: ");
        line = getString(keyb, "-Enter Amount Of Garage Phone Number: ");
        garagePhoneNo = Integer.parseInt(line);
        managerName = getString(keyb, "-Enter Manager Name: ");
        
        Garage g =
            new Garage(garageName, garageAddress, garagePhoneNo, managerName);
        
        return g;
    }
    
    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editGarageDetails(Scanner keyb, Garage g) {
        String garageName, garageAddress, managerName;
        int garagePhoneNo;
        String line1;
        
        garageName = getString(keyb, "-Enter Garage Name [" + g.getGarageName() + "]: ");
        garageAddress = getString(keyb, "-Enter Garage Address [" + g.getGarageAddress() + "]: ");
        line1 = getString(keyb, "-Enter Garage Phone Number [" + g.getGaragePhoneNo() + "]: ");
        managerName = getString(keyb, "-Enter Manager Name [" + g.getManagerName() + "]: ");
        
        if (garageName.length() != 0) {
            g.setGarageName(garageName);
        }
        if (garageAddress.length() != 0) {
            g.setGarageAddress(garageAddress);
        }
        if (line1.length() != 0) {
            garagePhoneNo = Integer.parseInt(line1);
            g.setGaragePhoneNo(garagePhoneNo);
        }
        if (managerName.length() != 0) {
            g.setManagerName(managerName);
        }  
    }
    
    //GetString Methode:
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }
}
    
