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
    public void InsertRestaurant(String RestaurantName, String City, String Street, String PreferredDeliveryTime){}

    // Delete restaurant by ID
    public void DeleteRestaurant(int RestaurantID){}

    // Update restaurant information
    public void UpdateRestaurant(int RestaurantID, String RestaurantName, String City, String Street){}

    // Select restaurants by city
    public void SelectRestaurantsByCity(String City){}

    // Simple JOIN query:
    // Get restaurants with their orders
    public void GetRestaurantOrders(){}
}