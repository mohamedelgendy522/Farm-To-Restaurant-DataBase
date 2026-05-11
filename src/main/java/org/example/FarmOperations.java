package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class FarmOperations
{
    private Connection conn;

    public FarmOperations() {
        try {
            String user = "sa";
            String pass = "123456";
            String url =
                    "jdbc:sqlserver://localhost:1433;"
                            + "databaseName=FarmRestaurantDB;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true;";

            conn = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new farm
    public void InsertFarm(String FarmName, String City, String Region) {
        String query = "INSERT INTO dbo.Farm (FarmName, City, Region) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, FarmName);
            stmt.setString(2, City);
            stmt.setString(3, Region);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Farm inserted successfully: " + FarmName);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete farm by ID
    public void DeleteFarm(int farmID) {
        String query = "DELETE FROM dbo.Farm WHERE FarmID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, farmID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Farm with ID " + farmID + " deleted successfully.");
            } else {
                System.out.println("No farm found with ID: " + farmID);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update farm name by ID
    public void UpdateFarmName(int farmID, String farmName) {
        String query = "UPDATE dbo.Farm SET FarmName = ? WHERE FarmID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, farmName);
            stmt.setInt(2, farmID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Farm ID " + farmID + " updated to: " + farmName);
            } else {
                System.out.println("No farm found with ID: " + farmID);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Select farms by city
    public void SelectFarmBycity(String City) {
        String query = "SELECT FarmID, FarmName, City, Region FROM dbo.Farm WHERE City = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, City);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Farms in city: " + City);
            boolean found = false;

            while (rs.next()) {
                found = true;
                int id       = rs.getInt("FarmID");
                String name  = rs.getString("FarmName");
                String city  = rs.getString("City");
                String region= rs.getString("Region");

                System.out.println(id + " | " + name + " | " + city + " | " + region);
            }

            if (!found) {
                System.out.println("No farms found in city: " + City);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get farms with their harvest batches
    public void GetFarmHarvestBatches() {
        String query = """
                SELECT
                    f.FarmID,
                    f.FarmName,
                    hb.BatchID,
                    hb.HarvestDate,
                    hb.AvailableQuantity,
                    hb.FreshnessWindow,
                    ct.CropName
                FROM dbo.Farm f
                JOIN dbo.HarvestBatch hb
                    ON f.FarmID = hb.FarmID
                JOIN dbo.CropType ct
                    ON hb.CropTypeID = ct.CropTypeID
                ORDER BY f.FarmID, hb.BatchID;
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Farms with their Harvest Batches:");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                int    farmId    = rs.getInt("FarmID");
                String farmName  = rs.getString("FarmName");
                int    batchId   = rs.getInt("BatchID");
                String harvestDate = rs.getString("HarvestDate");
                int    qty       = rs.getInt("AvailableQuantity");
                int freshness    = rs.getInt("FreshnessWindow");
                String cropName  = rs.getString("CropName");

                System.out.println(
                        "Farm: [" + farmId + "] " + farmName +
                                " | Batch: " + batchId +
                                " | Crop: " + cropName +
                                " | Harvested: " + harvestDate +
                                " | Qty: " + qty +
                                        " | Freshness: " + freshness + " days"
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}