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
                return """
                ========================================
                TOP CROP REPORT
                ========================================
                Crop Name    : %s
                Total Orders : %d
                ========================================
                """.formatted(
                        rs.getString("CropName"),
                        rs.getInt("TotalOrders")
                );
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

                sb.append(
                        "========================================\n"
                );

                sb.append(
                        "Inactive Farm : "
                ).append(
                        rs.getString("FarmName")
                ).append("\n");

                sb.append(
                        "========================================\n\n"
                );
            }
            if (sb.isEmpty()) {

                sb.append("No inactive farms found.");
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
                return """
                ========================================
                TOP DRIVER REPORT
                ========================================
                Driver Name : %s
                Trips Count : %d
                ========================================
                """.formatted(
                        rs.getString("DriverName"),
                        rs.getInt("Trips")
                );
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

                sb.append(
                        "========================================\n"
                );

                sb.append(
                        "Restaurant : "
                ).append(
                        rs.getString("RestaurantName")
                ).append("\n");

                sb.append(
                        "Status     : No Orders Last Month\n"
                );

                sb.append(
                        "========================================\n\n"
                );
            }
            if (sb.isEmpty()) {

                sb.append("All restaurants have orders.");
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

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            ResultSet rs =
                    stmt.executeQuery();

            StringBuilder sb =
                    new StringBuilder();

            while (rs.next()) {

                sb.append(
                        "========================================\n"
                );

                sb.append(
                        "Restaurant : "
                ).append(
                        rs.getString("RestaurantName")
                ).append("\n");

                sb.append(
                        "Batch ID   : "
                ).append(
                        rs.getInt("BatchID")
                ).append("\n");

                sb.append(
                        "Crop       : "
                ).append(
                        rs.getString("CropName")
                ).append("\n");

                sb.append(
                        "========================================\n\n"
                );
            }

            if (sb.isEmpty()) {

                sb.append(
                        "No data found."
                );
            }

            rs.close();

            stmt.close();

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

            while (rs.next()) {

                result.append(
                        "========================================\n"
                );

                result.append(
                        "Farm ID    : "
                ).append(
                        rs.getInt("FarmID")
                ).append("\n");

                result.append(
                        "Farm Name  : "
                ).append(
                        rs.getString("FarmName")
                ).append("\n");

                result.append(
                        "Revenue    : "
                ).append(
                        rs.getDouble("TotalRevenue")
                ).append("\n");

                result.append(
                        "========================================\n\n"
                );
            }

            rs.close();
            stmt.close();

            return result.toString();

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

}
