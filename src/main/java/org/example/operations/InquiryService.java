package org.example.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InquiryService {

    private Connection conn;

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
    public String GetTopCropType() {

        String query = """
        SELECT TOP 1
               ct.CropName,
               COUNT(DISTINCT o.OrderID) AS TotalOrders
        FROM CropType ct
        JOIN HarvestBatch hb ON ct.CropTypeID = hb.CropTypeID
        JOIN OrderBatch oi ON hb.BatchID = oi.BatchID
        JOIN Orders o ON oi.OrderID = o.OrderID
        GROUP BY ct.CropName
        ORDER BY TotalOrders DESC;
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Top Crop: " + rs.getString("CropName")
                        + "\nTotal Orders: " + rs.getInt("TotalOrders");
            }

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }

        return "No data found";
    }

    // Get farms with no sold batches last month
    public String GetInactiveFarmsLastMonth() {

        String query = """
        SELECT f.FarmName
        FROM Farm f
        WHERE f.FarmID NOT IN (
            SELECT DISTINCT hb.FarmID
            FROM HarvestBatch hb
            JOIN OrderBatch ob ON hb.BatchID = ob.BatchID
            JOIN Orders o ON ob.OrderID = o.OrderID
            WHERE o.OrderDate >= DATEADD(MONTH, -1, GETDATE())
        )
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append(rs.getString("FarmName")).append("\n");
            }

            return sb.toString();

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    // Get driver with highest completed deliveries
    public String GetTopDriverLastMonth() {

        String query = """
        SELECT TOP 1 d.DriverName,
               COUNT(dt.TripID) AS Trips
        FROM Driver d
        JOIN DeliveryTrip dt ON d.DriverID = dt.DriverID
        WHERE dt.TripDate >= DATEADD(MONTH, -1, GETDATE())
        GROUP BY d.DriverName
        ORDER BY Trips DESC
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("DriverName")
                        + " - Trips: "
                        + rs.getInt("Trips");
            }

        } catch (SQLException e) {
            return e.getMessage();
        }

        return "No data";
    }

    // Get restaurants without orders last month
    public String GetRestaurantsWithoutOrders() {

        String query = """
        SELECT r.RestaurantName
        FROM Restaurant r
        WHERE r.RestaurantID NOT IN (
            SELECT DISTINCT o.RestaurantID
            FROM Orders o
            WHERE o.OrderDate >= DATEADD(MONTH, -1, GETDATE())
        )
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append(rs.getString("RestaurantName")).append("\n");
            }

            return sb.toString();

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    // Get delivered batches for each restaurant
    public String GetRestaurantDeliveredBatches() {

        String query = """
        SELECT r.RestaurantName, hb.BatchID, ct.CropName
        FROM Restaurant r
        JOIN Orders o ON r.RestaurantID = o.RestaurantID
        JOIN OrderBatch ob ON o.OrderID = ob.OrderID
        JOIN HarvestBatch hb ON ob.BatchID = hb.BatchID
        JOIN CropType ct ON hb.CropTypeID = ct.CropTypeID
        ORDER BY r.RestaurantName
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append(rs.getString("RestaurantName"))
                        .append(" | Batch: ")
                        .append(rs.getInt("BatchID"))
                        .append(" | Crop: ")
                        .append(rs.getString("CropName"))
                        .append("\n");
            }

            return sb.toString();

        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    // Get total revenue for each farm
    public String GetFarmRevenue() {

        String query = """
        SELECT 
            f.FarmID,
            f.FarmName,
            COALESCE(SUM(oi.QuantityOrdered * oi.PriceAtOrder), 0) AS TotalRevenue
        FROM dbo.Farm f
        LEFT JOIN dbo.HarvestBatch hb
            ON f.FarmID = hb.FarmID
        LEFT JOIN dbo.OrderBatch oi
            ON hb.BatchID = oi.BatchID
        LEFT JOIN dbo.Orders o
            ON oi.OrderID = o.OrderID
        LEFT JOIN dbo.DeliveryTrip dt
            ON o.TripID = dt.TripID
            AND dt.Status = 'Completed'
        GROUP BY f.FarmID, f.FarmName
        ORDER BY TotalRevenue DESC;
    """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder result = new StringBuilder();
            result.append("Farm Revenue Report:\n\n");

            while (rs.next()) {
                int farmId = rs.getInt("FarmID");
                String farmName = rs.getString("FarmName");
                double revenue = rs.getDouble("TotalRevenue");

                result.append(farmId)
                        .append(" - ")
                        .append(farmName)
                        .append(" | Revenue: ")
                        .append(revenue)
                        .append("\n");
            }

            rs.close();
            stmt.close();

            return result.toString();

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

}
