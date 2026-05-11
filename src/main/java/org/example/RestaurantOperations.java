package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
class RestaurantOperations
{
    private Connection conn;

    public RestaurantOperations() {
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

    // Insert new restaurant
    public void InsertRestaurant(String RestaurantName, String City, String Street, String PreferredDeliveryTime){
        String query =
                "INSERT INTO Restaurant " +
                        "(RestaurantName, City, Street, PreferredDeliveryWindow) " +
                        "VALUES ('" + RestaurantName + "', '" +
                        City + "', '" +
                        Street + "', '" +
                        PreferredDeliveryTime + "')";

        try {

            Statement stmt = conn.createStatement();

            int rows = stmt.executeUpdate(query);

            System.out.println(rows + " restaurant inserted.");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete restaurant by IDz
    public void DeleteRestaurant(int RestaurantID){
        String query =
                "DELETE FROM Restaurant " +
                        "WHERE RestaurantID = " + RestaurantID;

        try {

            Statement stmt = conn.createStatement();

            int rows = stmt.executeUpdate(query);

            System.out.println(rows + " restaurant deleted.");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update restaurant information
    public void UpdateRestaurant(int RestaurantID, String RestaurantName, String City, String Street){
        String query =
                "UPDATE Restaurant " +
                        "SET RestaurantName = '" + RestaurantName + "', " +
                        "City = '" + City + "', " +
                        "Street = '" + Street + "' " +
                        "WHERE RestaurantID = " + RestaurantID;

        try {

            Statement stmt = conn.createStatement();

            int rows = stmt.executeUpdate(query);

            System.out.println(rows + " restaurant updated.");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Select restaurants by city
    public void SelectRestaurantsByCity(String City){
        String query =
                "SELECT * FROM Restaurant " +
                        "WHERE City = '" + City + "'";

        try {

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                System.out.println(
                        rs.getInt("RestaurantID") + " | " +
                                rs.getString("RestaurantName") + " | " +
                                rs.getString("City") + " | " +
                                rs.getString("Street")
                );
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Simple JOIN query:
    // Get restaurants with their orders
    public void GetRestaurantOrders(){
        String query =
                "SELECT Restaurant.RestaurantName, " +
                        "Orders.OrderID, Orders.TotalAmount " +
                        "FROM Restaurant " +
                        "JOIN Orders " +
                        "ON Restaurant.RestaurantID = Orders.RestaurantID";

        try {

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                System.out.println(
                        rs.getString("RestaurantName") + " | " +
                                rs.getInt("OrderID") + " | " +
                                rs.getDouble("TotalAmount")
                );
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
