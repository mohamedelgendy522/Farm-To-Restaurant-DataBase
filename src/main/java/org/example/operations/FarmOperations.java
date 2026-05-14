package org.example.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.FarmModel;

public class FarmOperations
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
    public String SelectFarmBycity(String City) {

        String query =
                "SELECT FarmID, FarmName, City, Region " +
                        "FROM dbo.Farm WHERE City = ?";

        StringBuilder result =
                new StringBuilder();

        try {

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            stmt.setString(1, City);

            ResultSet rs =
                    stmt.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                result.append(
                        rs.getInt("FarmID")
                ).append(" | ");

                result.append(
                        rs.getString("FarmName")
                ).append(" | ");

                result.append(
                        rs.getString("City")
                ).append(" | ");

                result.append(
                        rs.getString("Region")
                ).append("\n");
            }

            if (!found) {

                result.append(
                        "No farms found."
                );
            }

            rs.close();

            stmt.close();

        } catch (SQLException e) {

            result.append(
                    e.getMessage()
            );
        }

        return result.toString();
    }


    // Get farms with their harvest batches
    public ObservableList<FarmModel> GetFarmHarvestBatches() {

        ObservableList<FarmModel> batches =
                FXCollections.observableArrayList();

        String query = """
        SELECT
            f.FarmID,
            f.FarmName,
            hb.BatchID,
            hb.HarvestDate,
            hb.AvailableQuantity,
            ct.CropName
        FROM dbo.Farm f
        JOIN dbo.HarvestBatch hb
            ON f.FarmID = hb.FarmID
        JOIN dbo.CropType ct
            ON hb.CropTypeID = ct.CropTypeID
        ORDER BY f.FarmID, hb.BatchID;
        """;

        try {

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                batches.add(
                        new FarmModel(
                                rs.getInt("FarmID"),
                                rs.getString("FarmName"),
                                rs.getInt("BatchID"),
                                rs.getString("HarvestDate"),
                                rs.getInt("AvailableQuantity"),
                                rs.getString("CropName")
                        )
                );
            }

            rs.close();

            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return batches;
    }

    public ObservableList<FarmModel> getFarmsByCity(String city) {

        ObservableList<FarmModel> farms =
                FXCollections.observableArrayList();

        String query =
                "SELECT FarmID, FarmName, City, Region " +
                        "FROM dbo.Farm WHERE City = ?";

        try {

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            stmt.setString(1, city);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                farms.add(
                        new FarmModel(
                                rs.getInt("FarmID"),
                                rs.getString("FarmName"),
                                rs.getString("City"),
                                rs.getString("Region")
                        )
                );
            }

            rs.close();

            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return farms;
    }
}