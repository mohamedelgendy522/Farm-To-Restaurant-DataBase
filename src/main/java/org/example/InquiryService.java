package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InquiryService {

    private Connection conn;

    // Constructor to initialize DB connection
    public InquiryService() {
        try {
            String user = "sa";
            String pass = "123456";
            String url =
                    "jdbc:sqlserver://localhost:1433;"
                            +"databaseName=FarmRestaurantDB;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true;";

            conn = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get crop type with maximum orders
    public void GetTopCropType() {

        String query = """
     SELECT TOP 1
     ct.CropName,
     COUNT(DISTINCT o.OrderID) AS TotalOrders
     FROM dbo.CropType ct
     JOIN dbo.HarvestBatch hb
     ON ct.CropTypeID = hb.CropTypeID
     JOIN dbo.OrderBatch oi
     ON hb.BatchID = oi.BatchID
     JOIN dbo.Orders o
     ON oi.OrderID = o.OrderID
     GROUP BY ct.CropName
     ORDER BY TotalOrders DESC;
     """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String cropName = rs.getString("CropName");
                int totalOrders = rs.getInt("TotalOrders");

                System.out.println("Top Crop Type: " + cropName);
                System.out.println("Total Orders: " + totalOrders);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get farms with no sold batches last month
    public void GetInactiveFarmsLastMonth() {

        String query = """
        SELECT f.FarmID, f.FarmName
        FROM dbo.Farm f
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.HarvestBatch hb
            JOIN dbo.OrderBatch oi
                ON hb.BatchID = oi.BatchID
            JOIN dbo.Orders o
                ON oi.OrderID = o.OrderID
            WHERE hb.FarmID = f.FarmID
              AND o.OrderDate >= DATEADD(MONTH, -1, GETDATE())
        );
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Inactive Farms Last Month:");

            while (rs.next()) {
                int farmId = rs.getInt("FarmID");
                String farmName = rs.getString("FarmName");

                System.out.println(farmId + " - " + farmName);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get driver with highest completed deliveries
    public void GetTopDriverLastMonth() {

        String query = """
        SELECT TOP 1
               d.DriverID,
               d.DriverName,
               COUNT(t.TripID) AS CompletedTrips
        FROM dbo.Driver d
        JOIN dbo.DeliveryTrip t
            ON d.DriverID = t.DriverID
        WHERE t.Status = 'Completed'
          AND t.TripDate >= DATEADD(MONTH, -1, GETDATE())
        GROUP BY d.DriverID, d.DriverName
        ORDER BY CompletedTrips DESC;
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Top Driver Last Month:");

            if (rs.next()) {
                int driverId = rs.getInt("DriverID");
                String driverName = rs.getString("DriverName");
                int trips = rs.getInt("CompletedTrips");

                System.out.println(driverId + " - " + driverName + " | Trips: " + trips);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get restaurants without orders last month
    public void GetRestaurantsWithoutOrders() {

        String query = """
        SELECT r.RestaurantID, r.RestaurantName
        FROM dbo.Restaurant r
        WHERE NOT EXISTS (
            SELECT 1
            FROM dbo.Orders o
            WHERE o.RestaurantID = r.RestaurantID
              AND o.OrderDate >= DATEADD(MONTH, -1, GETDATE())
        );
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Restaurants Without Orders Last Month:");

            while (rs.next()) {
                int id = rs.getInt("RestaurantID");
                String name = rs.getString("RestaurantName");

                System.out.println(id + " - " + name);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get delivered batches for each restaurant
    public void GetRestaurantDeliveredBatches() {

        String query = """
        SELECT 
            r.RestaurantID,
            r.RestaurantName,
            hb.BatchID,
            ct.CropName,
            o.OrderID
        FROM dbo.Restaurant r
        JOIN dbo.Orders o
            ON r.RestaurantID = o.RestaurantID
        JOIN dbo.OrderBatch oi
            ON o.OrderID = oi.OrderID
        JOIN dbo.HarvestBatch hb
            ON oi.BatchID = hb.BatchID
        JOIN dbo.CropType ct
            ON hb.CropTypeID = ct.CropTypeID
        JOIN dbo.DeliveryTrip dt
            ON o.TripID = dt.TripID
        WHERE dt.Status = 'Completed'
        ORDER BY r.RestaurantID, o.OrderID;
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Delivered Batches per Restaurant:");

            while (rs.next()) {
                int restaurantId = rs.getInt("RestaurantID");
                String restaurantName = rs.getString("RestaurantName");
                int batchId = rs.getInt("BatchID");
                String cropName = rs.getString("CropName");
                int orderId = rs.getInt("OrderID");

                System.out.println(
                        restaurantId + " - " + restaurantName +
                                " | Order: " + orderId +
                                " | Batch: " + batchId +
                                " | Crop: " + cropName
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get total revenue for each farm
    public void GetFarmRevenue() {

        String query = """
        SELECT 
            f.FarmID,
            f.FarmName,
            SUM(oi.QuantityOrdered * oi.PriceAtOrder) AS TotalRevenue
        FROM dbo.Farm f
        JOIN dbo.HarvestBatch hb
            ON f.FarmID = hb.FarmID
        JOIN dbo.OrderBatch oi
            ON hb.BatchID = oi.BatchID
        JOIN dbo.Orders o
            ON oi.OrderID = o.OrderID
        JOIN dbo.DeliveryTrip dt
            ON o.TripID = dt.TripID
        WHERE dt.Status = 'Completed'
        GROUP BY f.FarmID, f.FarmName
        ORDER BY TotalRevenue DESC;
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Farm Revenue Report:");

            while (rs.next()) {
                int farmId = rs.getInt("FarmID");
                String farmName = rs.getString("FarmName");
                double revenue = rs.getDouble("TotalRevenue");

                System.out.println(
                        farmId + " - " + farmName +
                                " | Revenue: " + revenue
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
