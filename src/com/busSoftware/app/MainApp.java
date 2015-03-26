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
        String option = null;
        do {
            System.out.println("TOUR BUS COMPANY APP");
            System.out.println("---------------------");
            System.out.println();
            System.out.println("-1 BUS TABLE");
            System.out.println("-2 GARAGE TABLE");
            System.out.println("-3 SERVICE TABLE");
            System.out.println("-4 ASSIGNMENT TABLE");
            System.out.println("-5 DRIVER TABLE");
            System.out.println("-6 EXIT APP");
            System.out.println("-Choose A Table: ");
            option = keyboard.nextLine();
            if (option.equals("Bus") || option.equals("bus") || option.equals("b") || option.equals("1")) {
                doBusMenu(keyboard, model);
            }
            else if (option.equals("Garage") || option.equals("garage") || option.equals("g") || option.equals("2")) {
                doGarageMenu(keyboard, model);
            } 
            else if (option.equals("Garage") || option.equals("garage") || option.equals("g") || option.equals("3")) {
                doServiceMenu(keyboard, model);
            } 
            else if (option.equals("Assignment") || option.equals("assignment") || option.equals("a") || option.equals("4")) {
                doAssignmentMenu(keyboard, model);
            } 
            else if (option.equals("Assignment") || option.equals("assignment") || option.equals("a") || option.equals("5")) {
                doDriverMenu(keyboard, model);
            }
        } while (!(option.equals("Exit") || option.equals("exit") || option.equals("e") || option.equals("6")));
    }
    /*------------------------------------------------------------------------*/
    /*-------------------------------BUS CODE---------------------------------*/
    /*------------------------------------------------------------------------*/

    //Code For Telling If Bus Was Created Or Not USing A Boolean In The Model Class:
    private static void createBus(Scanner keyboard, Model model) {
        try {
            Bus b = readBus(keyboard);
            if (model.addBus(b)) {
                System.out.println("-New Bus Added To Database.");
            } else {
                System.out.println("-New Bus Was'nt Added To Database.");
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Delete Methode:
    private static void deleteBus(Scanner keyboard, Model model) {
        try {
            System.out.println("-Enter The ID Of The Bus You Want To Delete:");
            int busID = Integer.parseInt(keyboard.nextLine());
            Bus b;

            b = model.findBusByBusID(busID);
            if (b != null) {
                if (model.removeBus(b)) {
                    System.out.println("-Bus Deleted.\n");
                } else {
                    System.out.println("-Bus Not Deleted.\n");
                }
            } else {
                System.out.println("-Bus Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editBus(Scanner kb, Model m) {
        try {
            System.out.print("-Enter The Bus ID You Want To Edit: ");
            int busID = Integer.parseInt(kb.nextLine());
            Bus b;

            b = m.findBusByBusID(busID);
            if (b != null) {
                editBusDetails(kb, b);
                if (m.updateBus(b)) {
                    System.out.println("-Bus Updated.\n");
                } else {
                    System.out.println("-Bus Not Updated.\n");
                }
            } else {
                System.out.println("-Bus Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Code For Viewing All Buses:
    private static void viewBuses(Model model) {
        List<Bus> buses = model.getBuses();
        System.out.println();
        if (buses.isEmpty()) {
            System.out.println("-There Are No Buses In The Database.");
        } else {
            System.out.printf("%5s %30s %20s %20s %20s %25s %25s %25s %25s %25s %25s\n",
                    "-Bus ID-",
                    "-Registration Number-",
                    "-Bus Make-",
                    "-Bus Model-",
                    "-Bus Seats-",
                    "-Bus Engine Size-",
                    "-Purchase Date-",
                    "-Service Due Date-",
                    "-Garage Name-",
                    "-Services ID-",
                    "-Assignments ID-");
            for (Bus pr : buses) {
                System.out.printf("%7d %30s %20s %20s %20d %25s %25s %25s %25s %25d %25d\n",
                        pr.getBusID(),
                        pr.getRegistrationNo(),
                        pr.getBusMake(),
                        pr.getBusModel(),
                        pr.getBusSeats(),
                        pr.getBusEngineSize(),
                        pr.getPurchaseDate(),
                        pr.getDueServiceDate(),
                        pr.getGarageID(),
                        pr.getServiceID(),
                        pr.getAssignmentsID());
            }
        }
        System.out.println();
    }

    //Code For Creating A New Bus (Reads Input From Keyboard And Stores Into ReadBus Object:
    private static Bus readBus(Scanner keyb) {
        String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
        int busSeats, garageID, serviceID, assignmentsID;
        String line, line2, line3, line4;

        registrationNo = getString(keyb, "-Enter Registration Number: ");
        busMake = getString(keyb, "-Enter Bus Make: ");
        busModel = getString(keyb, "-Enter Bus Model: ");
        line = getString(keyb, "-Enter Amount Of Bus Seats: ");
        busSeats = Integer.parseInt(line);
        busEngineSize = getString(keyb, "-Enter Bus Engine Size: ");
        purchaseDate = getString(keyb, "-Enter Bus Purchase Date (yyyy-mm-dd): ");
        dueServiceDate = getString(keyb, "-Enter Bus Service Due Date (yyyy-mm-dd): ");
        line2 = getString(keyb, "Enter Garage ID (Enter -1 For No Garage): ");
        garageID = Integer.parseInt(line2);
        line3 = getString(keyb, "Enter Service ID (Enter -1 For No Service): ");
        serviceID = Integer.parseInt(line3);
        line4 = getString(keyb, "Enter Assignments ID (Enter -1 For No Assignments): ");
        assignmentsID = Integer.parseInt(line4);

        Bus b
                = new Bus(registrationNo, busMake, busModel, busSeats, busEngineSize, purchaseDate, dueServiceDate, garageID, serviceID, assignmentsID);

        return b;
    }

    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editBusDetails(Scanner keyb, Bus b) {
        try {
            String registrationNo, busMake, busModel, busEngineSize, purchaseDate, dueServiceDate;
            int busSeats, garageID, serviceID, assignmentsID;
            String line1, line2, line3, line4;

            registrationNo = getString(keyb, "-Enter Registration Number [" + b.getRegistrationNo() + "]: ");
            busMake = getString(keyb, "-Enter Bus Make [" + b.getBusMake() + "]: ");
            busModel = getString(keyb, "-Enter Bus Model [" + b.getBusModel() + "]: ");
            line1 = getString(keyb, "-Enter Number Of Bus Seats [" + b.getBusSeats() + "]: ");
            busEngineSize = getString(keyb, "-Enter Bus Engine Size [" + b.getBusEngineSize() + "]: ");
            purchaseDate = getString(keyb, "-Enter Bus Purchase Date [" + b.getPurchaseDate() + "]: ");
            dueServiceDate = getString(keyb, "-Enter Bus Service Due Date [" + b.getDueServiceDate() + "]: ");
            line2 = getString(keyb, "Enter Garage ID (Enter -1 For No Garage)[" + b.getGarageID() + "]: ");
            line3 = getString(keyb, "Enter Service ID (Enter -1 For No Service)[" + b.getServiceID() + "]: ");
            line4 = getString(keyb, "Enter Assignments ID (Enter -1 For No Assignments)[" + b.getAssignmentsID() + "]: ");

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
            if (line3.length() != 0) {
                serviceID = Integer.parseInt(line3);
                b.setServiceID(serviceID);
            }
            if (line4.length() != 0) {
                assignmentsID = Integer.parseInt(line4);
                b.setAssignmentsID(assignmentsID);
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    /*------------------------------------------------------------------------*/
    /*------------------------------GARAGE CODE-------------------------------*/
    /*------------------------------------------------------------------------*/
    //Code For Telling If Garage Was Created Or Not Using A Boolean In The Model Class:
    private static void createGarage(Scanner keyboard, Model model) {
        try {
            Garage g = readGarage(keyboard);
            if (model.addGarage(g)) {
                System.out.println("-New Garage Added To Database.");
            } else {
                System.out.println("-New Garage Was'nt Added To Database.");
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Delete Methode:
    private static void deleteGarage(Scanner keyboard, Model model) {
        try {
            System.out.println("-Enter The ID Of The Garage You Want To Delete:");
            int garageID = Integer.parseInt(keyboard.nextLine());
            Garage g;

            g = model.findGarageByGarageID(garageID);
            if (g != null) {
                if (model.removeGarage(g)) {
                    System.out.println("-Garage Deleted.\n");
                } else {
                    System.out.println("-Garage Not Deleted.\n");
                }
            } else {
                System.out.println("-Garage Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editGarage(Scanner kb, Model m) {
        try {
            System.out.print("-Enter The Garage ID You Want To Edit: ");
            int garageID = Integer.parseInt(kb.nextLine());
            Garage g;

            g = m.findGarageByGarageID(garageID);
            if (g != null) {
                editGarageDetails(kb, g);
                if (m.updateGarage(g)) {
                    System.out.println("-Garage Updated.\n");
                } else {
                    System.out.println("-Garage Not Updated.\n");
                }
            } else {
                System.out.println("-Garage Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Code For Viewing All Garagees:
    private static void viewGarages(Model model) {
        List<Garage> garages = model.getGarages();
        System.out.println();
        if (garages.isEmpty()) {
            System.out.println("-There Are No Garages In The Database.");
        } else {
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

        Garage g
                = new Garage(garageName, garageAddress, garagePhoneNo, managerName);

        return g;
    }

    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editGarageDetails(Scanner keyb, Garage g) {
        try {
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
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    /*------------------------------------------------------------------------*/
    /*------------------------------SERVICE CODE------------------------------*/
    /*------------------------------------------------------------------------*/
    //Code For Telling If Service Was Created Or Not Using A Boolean In The Model Class:
    private static void createService(Scanner keyboard, Model model) {
        try {
            Service s = readService(keyboard);
            if (model.addService(s)) {
                System.out.println("-New Service Added To Database.");
            } else {
                System.out.println("-New Service Was'nt Added To Database.");
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Delete Methode:
    private static void deleteService(Scanner keyboard, Model model) {
        try {
            System.out.println("-Enter The ID Of The Service You Want To Delete:");
            int serviceID = Integer.parseInt(keyboard.nextLine());
            Service s;

            s = model.findServiceByServiceID(serviceID);
            if (s != null) {
                if (model.removeService(s)) {
                    System.out.println("-Service Deleted.\n");
                } else {
                    System.out.println("-Service Not Deleted.\n");
                }
            } else {
                System.out.println("-Service Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editService(Scanner kb, Model m) {
        try {
            System.out.print("-Enter The Service ID You Want To Edit: ");
            int serviceID = Integer.parseInt(kb.nextLine());
            Service s;

            s = m.findServiceByServiceID(serviceID);
            if (s != null) {
                editServiceDetails(kb, s);
                if (m.updateService(s)) {
                    System.out.println("-Service Updated.\n");
                } else {
                    System.out.println("-Service Not Updated.\n");
                }
            } else {
                System.out.println("-Service Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Code For Viewing All Services:
    private static void viewServices(Model model) {
        List<Service> services = model.getServices();
        System.out.println();
        if (services.isEmpty()) {
            System.out.println("-There Are No Services In The Database.");
        } else {
            System.out.printf("%5s %30s %20s %20s\n",
                    "-Service ID-",
                    "-Service Date-",
                    "-Jobs Done-",
                    "-Mechanic's Name-");
            for (Service pr : services) {
                System.out.printf("%7d %30s %20s %20s\n",
                        pr.getServiceID(),
                        pr.getServiceDate(),
                        pr.getJobsDone(),
                        pr.getMechanicName());
            }
        }
        System.out.println();
    }

    //Code For Creating A New Service (Reads Input From Keyboard And Stores Into ReadBus Object:
    private static Service readService(Scanner keyb) {
        String serviceDate, jobsDone, mechanicName;

        serviceDate = getString(keyb, "-Enter Service Date(yyyy-mm-dd): ");
        jobsDone = getString(keyb, "-Enter Jobs Done: ");
        mechanicName = getString(keyb, "-Enter Mechanic's Name: ");

        Service s
                = new Service(serviceDate, jobsDone, mechanicName);

        return s;
    }

    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editServiceDetails(Scanner keyb, Service s) {
        try {
            String serviceDate, jobsDone, mechanicName;

            serviceDate = getString(keyb, "-Enter Service Date [" + s.getServiceDate() + "]: ");
            jobsDone = getString(keyb, "-Enter Jobs Done [" + s.getJobsDone() + "]: ");
            mechanicName = getString(keyb, "-Enter Mechanic's Name [" + s.getMechanicName() + "]: ");

            if (serviceDate.length() != 0) {
                s.setServiceDate(serviceDate);
            }
            if (jobsDone.length() != 0) {
                s.setJobsDone(jobsDone);
            }
            if (mechanicName.length() != 0) {
                s.setMechanicName(mechanicName);
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    /*------------------------------------------------------------------------*/
    /*------------------------------ASSIGNMENT CODE---------------------------*/
    /*------------------------------------------------------------------------*/
    //Code For Telling If Assignment Was Created Or Not Using A Boolean In The Model Class:
    private static void createAssignment(Scanner keyboard, Model model) {
        try {
            Assignment a = readAssignment(keyboard);
            if (model.addAssignment(a)) {
                System.out.println("-New Assignment Added To Database.");
            } else {
                System.out.println("-New Assignment Was'nt Added To Database.");
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Delete Methode:
    private static void deleteAssignment(Scanner keyboard, Model model) {
        try {
            System.out.println("-Enter The ID Of The Assignment You Want To Delete:");
            int assignmentsID = Integer.parseInt(keyboard.nextLine());
            Assignment a;

            a = model.findAssignmentByAssignmentsID(assignmentsID);
            if (a != null) {
                if (model.removeAssignment(a)) {
                    System.out.println("-Assignment Deleted.\n");
                } else {
                    System.out.println("-Assignment Not Deleted.\n");
                }
            } else {
                System.out.println("-Assignment Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editAssignment(Scanner kb, Model m) {
        try {
            System.out.print("-Enter The Assignment ID You Want To Edit: ");
            int assignmentsID = Integer.parseInt(kb.nextLine());
            Assignment a;

            a = m.findAssignmentByAssignmentsID(assignmentsID);
            if (a != null) {
                editAssignmentDetails(kb, a);
                if (m.updateAssignment(a)) {
                    System.out.println("-Assignment Updated.\n");
                } else {
                    System.out.println("-Assignment Not Updated.\n");
                }
            } else {
                System.out.println("-Service Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Code For Viewing All Assignments:
    private static void viewAssignments(Model model) {
        List<Assignment> assignments = model.getAssignments();
        System.out.println();
        if (assignments.isEmpty()) {
            System.out.println("-There Are No Assignment In The Database.");
        } else {
            System.out.printf("%5s %30s %20s %20s %20s\n",
                    "-Assignment ID-",
                    "-Bus ID-",
                    "-Driver ID-",
                    "-Description-",
                    "-Assignment Date-");
            for (Assignment pr : assignments) {
                System.out.printf("%7d %30d %20d %20s %20s\n",
                        pr.getAssignmentsID(),
                        pr.getBusID(),
                        pr.getDriverID(),
                        pr.getDescription(),
                        pr.getAssignmentsDate());
            }
        }
        System.out.println();
    }

    //Code For Creating A New Assignment (Reads Input From Keyboard And Stores Into ReadAssignment Object:
    private static Assignment readAssignment(Scanner keyb) {
        String description, assignmentsDate;
        int busID, driverID;
        String line1, line2;

        line1 = getString(keyb, "-Enter The bus ID: ");
        busID = Integer.parseInt(line1);
        line2 = getString(keyb, "-Enter The Driver ID: ");
        driverID = Integer.parseInt(line2);
        description = getString(keyb, "-Enter Assignment Description): ");
        assignmentsDate = getString(keyb, "-Enter Assignment Date(yyyy-mm-dd): ");

        Assignment a
                = new Assignment(busID, driverID, description, assignmentsDate);

        return a;
    }

    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editAssignmentDetails(Scanner keyb, Assignment a) {
        try {
            String description, assignmentsDate;
            int busID, driverID;
            String line1, line2;

            line1 = getString(keyb, "-Enter Bus ID [" + a.getBusID() + "]: ");
            line2 = getString(keyb, "-Enter Garage Driver ID [" + a.getDriverID() + "]: ");
            description = getString(keyb, "-Enter Job Desciption [" + a.getDescription() + "]: ");
            assignmentsDate = getString(keyb, "-Enter Assignment Date [" + a.getAssignmentsDate() + "]: ");

            if (assignmentsDate.length() != 0) {
                a.setAssignmentsDate(assignmentsDate);
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    /*------------------------------------------------------------------------*/
    /*-------------------------------DRIVER CODE------------------------------*/
    /*------------------------------------------------------------------------*/
    //Code For Telling If Driver Was Created Or Not Using A Boolean In The Model Class:
    private static void createDriver(Scanner keyboard, Model model) {
        try {
            Driver d = readDriver(keyboard);
            if (model.addDriver(d)) {
                System.out.println("-New Driver Added To Database.");
            } else {
                System.out.println("-New Driver Was'nt Added To Database.");
            }
            System.out.println();
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Delete Methode:
    private static void deleteDriver(Scanner keyboard, Model model) {
        try {
            System.out.println("-Enter The ID Of The Driver You Want To Delete:");
            int driverID = Integer.parseInt(keyboard.nextLine());
            Driver d;

            d = model.findDriverByDriverID(driverID);
            if (d != null) {
                if (model.removeDriver(d)) {
                    System.out.println("-Driver Deleted.\n");
                } else {
                    System.out.println("-Driver Not Deleted.\n");
                }
            } else {
                System.out.println("-Driver Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Edit Code If/Else If Table Is Updated Or Not:
    private static void editDriver(Scanner kb, Model m) {
        try {
            System.out.print("-Enter The Driver ID You Want To Edit: ");
            int driverID = Integer.parseInt(kb.nextLine());
            Driver d;

            d = m.findDriverByDriverID(driverID);
            if (d != null) {
                editDriverDetails(kb, d);
                if (m.updateDriver(d)) {
                    System.out.println("-Driver Updated.\n");
                } else {
                    System.out.println("-Driver Not Updated.\n");
                }
            } else {
                System.out.println("-Driver Not Found.\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }
    }

    //Code For Viewing All Drivers:
    private static void viewDrivers(Model model) {
        List<Driver> drivers = model.getDrivers();
        System.out.println();
        if (drivers.isEmpty()) {
            System.out.println("-There Are No Drivers In The Database.");
        } else {
            System.out.printf("%5s %30s %20s %20s\n",
                    "-Driver ID-",
                    "-First Name-",
                    "-Last Name-",
                    "-Assignments ID-");
            for (Driver pr : drivers) {
                System.out.printf("%7d %30s %20s %20d\n",
                        pr.getDriverID(),
                        pr.getFName(),
                        pr.getLName(),
                        pr.getAssignmentsID());
            }
        }
        System.out.println();
    }

    //Code For Creating A New Driver (Reads Input From Keyboard And Stores Into ReadDriver Object:
    private static Driver readDriver(Scanner keyb) {
        String fName, lName;
        int assignmentsID;
        String line1;

        fName = getString(keyb, "-Enter First Name: ");
        lName = getString(keyb, "-Enter Last Name: ");
        line1 = getString(keyb, "-Enter AssignmentsID: ");
        assignmentsID = Integer.parseInt(line1);

        Driver d
                = new Driver(fName, lName, assignmentsID);

        return d;
    }

    //Edit code (This Code Gets String From Keyboard And Places Current Info And Placement Reads New Value):
    private static void editDriverDetails(Scanner keyb, Driver d) {
        try {
            String fName, lName;
            int assignmentsID;
            String line1;

            fName = getString(keyb, "-Enter First Name [" + d.getFName() + "]: ");
            lName = getString(keyb, "-Enter Last Name [" + d.getLName() + "]: ");
            line1 = getString(keyb, "-Enter Assignments ID [" + d.getAssignmentsID() + "]: ");

            if (fName.length() != 0) {
                d.setFName(fName);
            }
            if (lName.length() != 0) {
                d.setLName(lName);
            }
            if (line1.length() != 0) {
                assignmentsID = Integer.parseInt(line1);
                d.setAssignmentsID(assignmentsID);
            }
        } catch (NumberFormatException e) {
            System.out.println("-Incorrect Data Type Or Null Input.");
            System.out.println("Number Format Exception: " + e.getMessage() + ".\n");
        }

    }

    //GetString Methode:
    private static String getString(Scanner keyboard, String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private static void doBusMenu(Scanner keyboard, Model model) {
        int opt;

        // Do/While Loop For Possible Options, As Long As 5 Is Not Entered It Will Continue To Run:
        do {
            System.out.println();
            System.out.println("-BUS TABLE");
            System.out.println("----------");
            System.out.println();
            System.out.println("-1 Create A New Bus.");
            System.out.println("-2 Delete Existing Bus.");
            System.out.println("-3 Edit Existing Bus.");
            System.out.println("-4 View All Buses.");
            System.out.println("-5 Back To Tables.");

            System.out.print("-Enter Option:");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            //If Options Is CLicked Then Break:
            System.out.println("-You Chose Option: " + opt);
            switch (opt) {
                //To Create A New Bus:
                case 1: {
                    System.out.println("-Creating A New Bus.");
                    createBus(keyboard, model);
                    break;
                }
                //To Delete A Existing Bus:
                case 2: {
                    System.out.println("-Deleting A Bus.");
                    deleteBus(keyboard, model);
                    break;
                }
                //To Update A Existing Bus: 
                case 3: {
                    System.out.println("-Updating A Bus.");
                    editBus(keyboard, model);
                    break;
                }
                //To View All Buses: 
                case 4: {
                    System.out.println("-Viewing All Buses.");
                    viewBuses(model);
                    break;
                }
            }
        } //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 5);
    }

    private static void doGarageMenu(Scanner keyboard, Model model) {
        int opt;

        // Do/While Loop For Possible Options, As Long As 5 Is Not Entered It Will Continue To Run:
        do {
            System.out.println();
            System.out.println("-GARAGE TABLE");
            System.out.println("----------");
            System.out.println();
            System.out.println("-1 Create A New Garage.");
            System.out.println("-2 Delete Existing Garage.");
            System.out.println("-3 Edit Existing Garage.");
            System.out.println("-4 View All Garages.");
            System.out.println("-5 Back To Tables.");

            System.out.print("-Enter Option:");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            //If Options Is CLicked Then Break:
            System.out.println("-You Chose Option: " + opt);
            switch (opt) {
                //To Create A New Garage:
                case 1: {
                    System.out.println("-Creating A New Garage.");
                    createGarage(keyboard, model);
                    break;
                }
                //To Delete A Existing Garage:
                case 2: {
                    System.out.println("-Deleting A Garage.");
                    deleteGarage(keyboard, model);
                    break;
                }
                //To Update A Existing Garage: 
                case 3: {
                    System.out.println("-Updating A Garage.");
                    editGarage(keyboard, model);
                    break;
                }
                //To View All Garages: 
                case 4: {
                    System.out.println("-Viewing All Garage.");
                    viewGarages(model);
                    break;
                }
            }
        } //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 5);
    }

    private static void doServiceMenu(Scanner keyboard, Model model) {
        int opt;

        // Do/While Loop For Possible Options, As Long As 5 Is Not Entered It Will Continue To Run:
        do {
            System.out.println();
            System.out.println("-SERIVCE TABLE");
            System.out.println("----------");
            System.out.println();
            System.out.println("-1 Create A New Service.");
            System.out.println("-2 Delete Existing Service.");
            System.out.println("-3 Edit Existing Service.");
            System.out.println("-4 View All Services.");
            System.out.println("-5 Back To Tables.");

            System.out.print("-Enter Option:");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            //If Options Is CLicked Then Break:
            System.out.println("-You Chose Option: " + opt);
            switch (opt) {
                //To Create A New Service:
                case 1: {
                    System.out.println("-Creating A New Service.");
                    createService(keyboard, model);
                    break;
                }
                //To Delete A Existing Service:
                case 2: {
                    System.out.println("-Deleting A Service.");
                    deleteService(keyboard, model);
                    break;
                }
                //To Update A Existing Service: 
                case 3: {
                    System.out.println("-Updating A Service.");
                    editService(keyboard, model);
                    break;
                }
                //To View All Services: 
                case 4: {
                    System.out.println("-Viewing All Services.");
                    viewServices(model);
                    break;
                }
            }
        } //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 5);
    }

    private static void doAssignmentMenu(Scanner keyboard, Model model) {
        int opt;

        // Do/While Loop For Possible Options, As Long As 5 Is Not Entered It Will Continue To Run:
        do {
            System.out.println();
            System.out.println("-ASSIGNMENT TABLE");
            System.out.println("----------");
            System.out.println();
            System.out.println("-1 Create A New Assignment.");
            System.out.println("-2 Delete Existing Assignment.");
            System.out.println("-3 Edit Existing Assignment.");
            System.out.println("-4 View All Assignments.");
            System.out.println("-5 Back To Tables.");

            System.out.print("-Enter Option:");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            //If Options Is CLicked Then Break:
            System.out.println("-You Chose Option: " + opt);
            switch (opt) {
                //To Create A New Service:
                case 1: {
                    System.out.println("-Creating A New Assignment.");
                    createAssignment(keyboard, model);
                    break;
                }
                //To Delete A Existing Service:
                case 2: {
                    System.out.println("-Deleting A Assignment.");
                    deleteAssignment(keyboard, model);
                    break;
                }
                //To Update A Existing Service: 
                case 3: {
                    System.out.println("-Updating A Assignment.");
                    editAssignment(keyboard, model);
                    break;
                }
                //To View All Services: 
                case 4: {
                    System.out.println("-Viewing All Assignments.");
                    viewAssignments(model);
                    break;
                }
            }
        } //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 5);
    }

    private static void doDriverMenu(Scanner keyboard, Model model) {
        int opt;

        // Do/While Loop For Possible Options, As Long As 5 Is Not Entered It Will Continue To Run:
        do {
            System.out.println();
            System.out.println("-DRIVER TABLE");
            System.out.println("----------");
            System.out.println();
            System.out.println("-1 Create A New Driver.");
            System.out.println("-2 Delete Existing Driver.");
            System.out.println("-3 Edit Existing Driver.");
            System.out.println("-4 View All Drivers.");
            System.out.println("-5 Back To Tables.");

            System.out.print("-Enter Option:");
            String line = keyboard.nextLine();
            opt = Integer.parseInt(line);

            //If Options Is CLicked Then Break:
            System.out.println("-You Chose Option: " + opt);
            switch (opt) {
                //To Create A New Driver:
                case 1: {
                    System.out.println("-Creating A New Assignment.");
                    createDriver(keyboard, model);
                    break;
                }
                //To Delete A Existing Driver:
                case 2: {
                    System.out.println("-Deleting A Assignment.");
                    deleteDriver(keyboard, model);
                    break;
                }
                //To Update A Existing Driver: 
                case 3: {
                    System.out.println("-Updating A Assignment.");
                    editDriver(keyboard, model);
                    break;
                }
                //To View All Drivers: 
                case 4: {
                    System.out.println("-Viewing All Assignments.");
                    viewDrivers(model);
                    break;
                }
            }
        } //Once Not Equals To 5 Programes Runs Else Stops:
        while (opt != 5);
    }
}
