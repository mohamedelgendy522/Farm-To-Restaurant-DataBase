package org.example;

import java.sql.*;

class RestaurantOperations {

    private Connection conn;

    public RestaurantOperations() {
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

    // Insert new restaurant
    public void InsertRestaurant(String RestaurantName, String City, String Street, String PreferredDeliveryTime) {

        String query =
                "INSERT INTO dbo.Restaurant " +
                        "(RestaurantName, City, Street, PreferredDeliveryWindow) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, RestaurantName);
            stmt.setString(2, City);
            stmt.setString(3, Street);
            stmt.setString(4, PreferredDeliveryTime);

            int rows = stmt.executeUpdate();

            System.out.println(rows + " restaurant inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete restaurant by ID
    public void DeleteRestaurant(int RestaurantID) {

        String query =
                "DELETE FROM dbo.Restaurant " +
                        "WHERE RestaurantID = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, RestaurantID);

            int rows = stmt.executeUpdate();

            System.out.println(rows + " restaurant deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update restaurant Name
    public void UpdateRestaurantName(int RestaurantID, String RestaurantName) {

        String query =
                "UPDATE dbo.Restaurant " +
                        "SET RestaurantName = ? " +
                        "WHERE RestaurantID = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, RestaurantName);
            stmt.setInt(2, RestaurantID);

            int rows = stmt.executeUpdate();

            System.out.println(rows + " restaurant updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Select restaurants by city
    public void SelectRestaurantsByCity(String City) {

        String query =
                "SELECT * FROM dbo.Restaurant " +
                        "WHERE City = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, City);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getInt("RestaurantID") + " | " +
                                rs.getString("RestaurantName") + " | " +
                                rs.getString("City") + " | " +
                                rs.getString("Street") + " | " +
                                rs.getString("PreferredDeliveryWindow")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get restaurants with their orders
    public void GetRestaurantOrders() {

        String query =
                "SELECT r.RestaurantName, o.OrderID, o.OrderDate, o.TotalAmount " +
                        "FROM dbo.Restaurant r " +
                        "JOIN dbo.Orders o " +
                        "ON r.RestaurantID = o.RestaurantID";

        try {

            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getString("RestaurantName") + " | " +
                                rs.getInt("OrderID") + " | " +
                                rs.getString("OrderDate") + " | " +
                                rs.getDouble("TotalAmount")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}