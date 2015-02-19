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
            System.out.println("-3 Edit Existing Buses..");
            System.out.println("-4 View All Buses.");
            System.out.println("-5 Exit Tour Bus Company.");
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
                case 4: {
                    System.out.println("-Viewing All Buses.");
                    viewBuses(model);
                    break;
                }
            }
        }
        //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 5);
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

    //Code For Viewing All Buses:
    private static void viewBuses(Model model) {
        List<Bus> buses = model.getBuses();
        System.out.println();
        if (buses.isEmpty()) {
            System.out.println("-There Are No Buses In The Database.");
        }
        else {
            System.out.printf("%5s %30s %20s %20s %20s %25s %25s %25s\n", 
                              "-Bus ID-", 
                              "-Registration Number-", 
                              "-Bus Make-", 
                              "-Bus Model-", 
                              "-Bus Seats-", 
                              "-Bus Engine Size-", 
                              "-Purchase Date-", 
                              "-Service Due Date-");
            for (Bus pr : buses) {
                System.out.printf("%7d %30s %20s %20s %20d %25s %25s %25s\n",
                pr.getBusID(),
                pr.getRegistrationNo(),
                pr.getBusMake(),
                pr.getBusModel(),
                pr.getBusSeats(),
                pr.getBusEngineSize(),
                pr.getPurchaseDate(),
                pr.getDueServiceDate());
            }
        }
        System.out.println();
    }
    
    //Code For Creating A New Bus (Reads Input From Keyboard And Stores Into ReadBus Object:
    private static Bus readBus(Scanner keyb) {
        String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
        int busSeats;
        String line;
        
        registrationNo = getString(keyb, "-Enter Registration Number: ");
        busMake = getString(keyb, "-Enter Bus Make: ");
        busModel = getString(keyb, "-Enter Bus Model: ");
        line = getString(keyb, "-Enter Amount Of Bus Seats: ");
        busSeats = Integer.parseInt(line);
        busEngineSize = getString(keyb, "-Enter Bus Engine Size: ");
        purchaseDate = getString(keyb, "-Enter Bus Purchase Date (yyyy-mm-dd): ");
        dueServiceDate = getString(keyb, "-Enter Bus Service Due Date (yyyy-mm-dd): ");
        
        Bus b =
            new Bus(registrationNo, busMake, busModel, busSeats, busEngineSize, purchaseDate, dueServiceDate);
        
        return b;
    }
    
    //GetString Methode:
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
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
        System.out.print("-Enter The Bus ID You Want To Edit.");
        int busID = Integer.parseInt(kb.nextLine());
        Bus b;
        
        b = m.findBusByBusID(busID);
        if (b != null) {
            editBusDetails(kb, b);
            if (m. updateBus(b)) {
                System.out.println("-Bus Updated.\n");       
            }
            else {
                System.out.println("-Programmer Not Updated.\n");
            }
        }
        else {
            System.out.println("-Bus Not Found.\n");
        }
    }

    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editBusDetails(Scanner keyb, Bus b) {
        String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
        int busSeats;
        String line1;
        
        registrationNo = getString(keyb, "-Enter Registration Number [" + b.getRegistrationNo() + "]: ");
        busMake = getString(keyb, "-Enter Bus Make [" + b.getBusMake() + "]: ");
        busModel = getString(keyb, "-Enter Bus Model [" + b.getBusModel() + "]: ");
        line1 = getString(keyb, "-Enter Number Of Bus Seats [" + b.getBusSeats() + "]: ");
        busEngineSize = getString(keyb, "-Enter Bus Engine Size [" + b.getBusEngineSize() + "]: ");
        purchaseDate = getString(keyb, "-Enter Bus Purchase Date [" + b.getPurchaseDate() + "]: ");
        dueServiceDate = getString(keyb, "-Enter Bus Service Due Date [" + b.getDueServiceDate() + "]: ");
        
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
    }
}
    
