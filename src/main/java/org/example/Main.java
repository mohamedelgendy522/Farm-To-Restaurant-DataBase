package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);

       while (true) {
           System.out.println("=== Farm-To-Restaurant Menu ===");
           System.out.println("1. Farm Operations");
           System.out.println("2. Driver Operations");
           System.out.println("3. Harvest Batch Operations");
           System.out.println("4. Restaurant Operations");
           System.out.println("5. Inquiry Service (reports)");
           System.out.println("0. Exit");
           System.out.print("Choose an option: ");

           String choice = sc.nextLine().trim();

           switch (choice) {
               case "1" -> {
                   FarmOperations fo = new FarmOperations();
                   System.out.println("=== Farm ===");

                   while (true) {
                       System.out.println("1. Insert Farm");
                       System.out.println("2. Delete Farm by ID");
                       System.out.println("3. Update Farm Name");
                       System.out.println("4. Select Farms by City");
                       System.out.println("5. Get Farm Harvest Batches");
                       System.out.println("0. Back");
                       System.out.print("Choose a sub-option: ");

                       String sub = sc.nextLine().trim();

                       switch (sub) {
                           case "1" -> {
                               System.out.print("Farm name: ");
                               String name = sc.nextLine().trim();
                               System.out.print("City: ");
                               String city = sc.nextLine().trim();
                               System.out.print("Region: ");
                               String region = sc.nextLine().trim();

                               fo.InsertFarm(name, city, region);
                               System.out.println("InsertFarm called.");
                           }
                           case "2" -> {
                               System.out.print("Farm ID (int): ");
                               String idStr = sc.nextLine().trim();
                               int id = Integer.parseInt(idStr);
                               fo.DeleteFarm(id);
                               System.out.println("DeleteFarm called.");
                           }
                           case "3" -> {
                               System.out.print("Farm ID (int): ");
                               String idStr2 = sc.nextLine().trim();
                               System.out.print("New Farm Name: ");
                               String newName = sc.nextLine().trim();
                               int id2 = Integer.parseInt(idStr2);
                               fo.UpdateFarmName(id2, newName);
                               System.out.println("UpdateFarmName called.");
                           }
                           case "4" -> {
                               System.out.print("City: ");
                               String city2 = sc.nextLine().trim();
                               fo.SelectFarmBycity(city2);
                               System.out.println("SelectFarmBycity called.");
                           }
                           case "5" -> {
                               fo.GetFarmHarvestBatches();
                               System.out.println("GetFarmHarvestBatches called.");
                           }
                           case "0" -> System.out.println("Back to main menu.");
                           default -> System.out.println("Invalid sub-option.");
                       }

                       if (sub.equals("0")) break;
                   }
               }
               case "2" -> {
                   DriverOperations dr = new DriverOperations();
                   System.out.println("=== Driver Operations ===");

                   while (true) {
                       System.out.println("1. Insert Driver");
                       System.out.println("2. Delete Driver by ID");
                       System.out.println("3. Update Driver Name");
                       System.out.println("4. Select Driver by Name");
                       System.out.println("5. Get Driver Trips");
                       System.out.println("0. Back");
                       System.out.print("Choose a sub-option: ");

                       String sub = sc.nextLine().trim();

                       switch (sub) {
                           case "1" -> {
                               System.out.print("Driver name: ");
                               String name = sc.nextLine().trim();
                               dr.InsertDriver(name);
                               System.out.println("InsertDriver called.");
                           }
                           case "2" -> {
                               System.out.print("Driver ID (int): ");
                               String idStr = sc.nextLine().trim();
                               int id = Integer.parseInt(idStr);
                               dr.DeleteDriver(id);
                               System.out.println("DeleteDriver called.");
                           }
                           case "3" -> {
                               System.out.print("Driver ID (int): ");
                               String idStr2 = sc.nextLine().trim();
                               System.out.print("New Driver Name: ");
                               String newName = sc.nextLine().trim();
                               int id2 = Integer.parseInt(idStr2);
                               dr.UpdateDriverName(id2, newName);
                               System.out.println("UpdateDriverName called.");
                           }
                           case "4" -> {
                               System.out.print("Driver name: ");
                               String name2 = sc.nextLine().trim();
                               dr.SelectDriverByName(name2);
                               System.out.println("SelectDriverByName called.");
                           }
                           case "5" -> {
                               dr.GetDriverTrips();
                               System.out.println("GetDriverTrips called.");
                           }
                           case "0" -> System.out.println("Back to main menu.");
                           default -> System.out.println("Invalid sub-option.");
                       }

                       if (sub.equals("0")) break;
                   }
               }
               case "3" -> {
                   HarvestBatchOperations hb = new HarvestBatchOperations();
                   System.out.println("=== Harvest Batch Operations ===");

                   while (true) {
                       System.out.println("1. Insert Harvest Batch");
                       System.out.println("2. Delete Batch by ID");
                       System.out.println("3. Update Batch Quantity");
                       System.out.println("4. Select Available Batches");
                       System.out.println("5. Get Batch Crop Types");
                       System.out.println("0. Back");
                       System.out.print("Choose a sub-option: ");

                       String sub = sc.nextLine().trim();

                       switch (sub) {
                           case "1" -> {
                               System.out.print("Farm ID (int): ");
                               int farmId = Integer.parseInt(sc.nextLine().trim());
                               System.out.print("Harvest Date (YYYY-MM-DD): ");
                               String date = sc.nextLine().trim();
                               System.out.print("Quantity (int): ");
                               int qty = Integer.parseInt(sc.nextLine().trim());
                                System.out.print("Freshness Window (int): ");
                               int window = Integer.parseInt(sc.nextLine().trim());
                               System.out.print("CropType ID (int): ");
                               int CropTypeID = Integer.parseInt(sc.nextLine().trim());

                               hb.InsertHarvestBatch(farmId, date, qty, window,CropTypeID);
                               System.out.println("InsertHarvestBatch called.");
                           }
                           case "2" -> {
                               System.out.print("Batch ID (int): ");
                               String idStr = sc.nextLine().trim();
                               int id = Integer.parseInt(idStr);
                               hb.DeleteHarvestBatch(id);
                               System.out.println("DeleteHarvestBatch called.");
                           }
                           case "3" -> {
                               System.out.print("Batch ID (int): ");
                               String idStr2 = sc.nextLine().trim();
                               System.out.print("New Quantity (int): ");
                               String qtyStr = sc.nextLine().trim();
                               int id2 = Integer.parseInt(idStr2);
                               int newQty = Integer.parseInt(qtyStr);
                               hb.UpdateHarvestBatch(id2, newQty);
                               System.out.println("UpdateHarvestBatch called.");
                           }
                           case "4" -> {
                               hb.SelectAvailableBatches();
                               System.out.println("SelectAvailableBatches called.");
                           }
                           case "5" -> {
                               hb.GetBatchCropTypes();
                               System.out.println("GetBatchCropTypes called.");
                           }
                           case "0" -> System.out.println("Back to main menu.");
                           default -> System.out.println("Invalid sub-option.");
                       }

                       if (sub.equals("0")) break;
                   }
               }
               case "4" -> {
                   RestaurantOperations ro = new RestaurantOperations();
                   System.out.println("=== Restaurant Operations ===");

                   while (true) {
                       System.out.println("1. Insert Restaurant");
                       System.out.println("2. Delete Restaurant by ID");
                       System.out.println("3. Update Restaurant");
                       System.out.println("4. Select Restaurants by City");
                       System.out.println("5. Get Restaurant Orders");
                       System.out.println("0. Back");
                       System.out.print("Choose a sub-option: ");

                       String sub = sc.nextLine().trim();

                       switch (sub) {
                           case "1" -> {
                               System.out.print("Restaurant Name: ");
                               String name = sc.nextLine().trim();
                               System.out.print("City: ");
                               String city = sc.nextLine().trim();
                               System.out.print("Street: ");
                               String street = sc.nextLine().trim();
                               System.out.print("Preferred Delivery Window: ");
                               String window = sc.nextLine().trim();

                               ro.InsertRestaurant(name, city, street, window);
                               System.out.println("InsertRestaurant called.");
                           }
                           case "2" -> {
                               System.out.print("Restaurant ID (int): ");
                               String idStr = sc.nextLine().trim();
                               int id = Integer.parseInt(idStr);
                               ro.DeleteRestaurant(id);
                               System.out.println("DeleteRestaurant called.");
                           }
                           case "3" -> {
                               System.out.print("Restaurant ID (int): ");
                               String idStr2 = sc.nextLine().trim();
                               System.out.print("New Restaurant Name: ");
                               String newName = sc.nextLine().trim();
                               int id2 = Integer.parseInt(idStr2);
                               ro.UpdateRestaurantName(id2, newName);
                               System.out.println("UpdateRestaurant called.");
                           }
                           case "4" -> {
                               System.out.print("City: ");
                               String city2 = sc.nextLine().trim();
                               ro.SelectRestaurantsByCity(city2);
                               System.out.println("SelectRestaurantsByCity called.");
                           }
                           case "5" -> {
                               ro.GetRestaurantOrders();
                               System.out.println("GetRestaurantOrders called.");
                           }
                           case "0" -> System.out.println("Back to main menu.");
                           default -> System.out.println("Invalid sub-option.");
                       }

                       if (sub.equals("0")) break;
                   }
               }
               case "5" -> {
                   InquiryService is = new InquiryService();

                   System.out.println("=== Inquiry Service ===");

                   while (true) {
                       System.out.println("1. Get Top Crop Type");
                       System.out.println("2. Get Inactive Farms Last Month");
                       System.out.println("3. Get Top Driver Last Month");
                       System.out.println("4. Get Restaurants Without Orders Last Month");
                       System.out.println("5. Get Restaurant Delivered Batches");
                       System.out.println("6. Get Farm Revenue");
                       System.out.println("0. Back");
                       System.out.print("Choose a sub-option: ");

                       String sub = sc.nextLine().trim();

                       switch (sub) {
                           case "1" -> {
                               is.GetTopCropType();
                               System.out.println("GetTopCropType called.");
                           }
                           case "2" -> {
                               is.GetInactiveFarmsLastMonth();
                               System.out.println("GetInactiveFarmsLastMonth called.");
                           }
                           case "3" -> {
                               is.GetTopDriverLastMonth();
                               System.out.println("GetTopDriverLastMonth called.");
                           }
                           case "4" -> {
                               is.GetRestaurantsWithoutOrders();
                               System.out.println("GetRestaurantsWithoutOrders called.");
                           }
                           case "5" -> {
                               is.GetRestaurantDeliveredBatches();
                               System.out.println("GetRestaurantDeliveredBatches called.");
                           }
                           case "6" -> {
                               is.GetFarmRevenue();
                               System.out.println("GetFarmRevenue called.");
                           }
                           case "0" -> System.out.println("Back to main menu.");
                           default -> System.out.println("Invalid sub-option.");
                       }
                       if (sub.equals("0")) break;
                   }
               }
               case "0" -> {
                   System.out.println("Exit.");
                   sc.close();
                   return;
               }
               default -> System.out.println("Invalid choice, try again.");
           }

           System.out.println();
       }
    }
}
