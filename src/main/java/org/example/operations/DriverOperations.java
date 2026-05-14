package org.example.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.DriverModel;

public class DriverOperations
{
    private Connection conn;

    public DriverOperations() {
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

    // Insert new driver

    public void InsertDriver(String DriverName) {
        String query = "INSERT INTO dbo.Driver (DriverName) VALUES (?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, DriverName);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Driver inserted successfully: " + DriverName);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete driver by ID
    public void DeleteDriver(int DriverID) {
        String query = "DELETE FROM dbo.Driver WHERE DriverID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, DriverID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Driver with ID " + DriverID + " deleted successfully.");
            } else {
                System.out.println("No driver found with ID: " + DriverID);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update driver name by ID
    public void UpdateDriverName(int DriverID, String DriverName) {
        String query = "UPDATE dbo.Driver SET DriverName = ? WHERE DriverID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, DriverName);
            stmt.setInt(2, DriverID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Driver ID " + DriverID + " updated to: " + DriverName);
            } else {
                System.out.println("No driver found with ID: " + DriverID);
            }

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Select driver by name
    public void SelectDriverByName(String DriverName) {
        String query = "SELECT DriverID, DriverName FROM dbo.Driver WHERE DriverName = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, DriverName);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Drivers with name: " + DriverName);
            boolean found = false;

            while (rs.next()) {
                found = true;
                int id     = rs.getInt("DriverID");
                String name = rs.getString("DriverName");

                System.out.println(id + " | " + name);
            }

            if (!found) {
                System.out.println("No driver found with name: " + DriverName);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Get drivers with their delivery trips
    public void GetDriverTrips() {
        String query = """
                SELECT
                    d.DriverID,
                    d.DriverName,
                    t.TripID,
                    t.Route,
                    t.TotalDistance,
                    t.Status,
                    t.TripDate
                FROM dbo.Driver d
                JOIN dbo.DeliveryTrip t
                    ON d.DriverID = t.DriverID
                ORDER BY d.DriverID, t.TripID;
                """;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Drivers with their Delivery Trips:");
            System.out.println("--------------------------------------------------");

            while (rs.next()) {
                int    driverID   = rs.getInt("DriverID");
                String driverName = rs.getString("DriverName");
                int    tripID     = rs.getInt("TripID");
                String route      = rs.getString("Route");
                double distance   = rs.getDouble("TotalDistance");
                String status     = rs.getString("Status");
                String tripDate   = rs.getString("TripDate");

                System.out.println(
                        "Driver: [" + driverID + "] " + driverName +
                                " | Trip: " + tripID +
                                " | Route: " + route +
                                " | Distance: " + distance + " km" +
                                " | Status: " + status +
                                " | Date: " + tripDate
                );
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<DriverModel> getDriversByName(String name) {

        ObservableList<DriverModel> drivers =
                FXCollections.observableArrayList();

        String query =
                "SELECT DriverID, DriverName " +
                        "FROM dbo.Driver " +
                        "WHERE DriverName = ?";

        try {

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            stmt.setString(1, name);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                drivers.add(
                        new DriverModel(
                                rs.getInt("DriverID"),
                                rs.getString("DriverName")
                        )
                );
            }

            rs.close();

            stmt.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return drivers;
    }
}