package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
                            +"databaseName=FarmRestaurantDB;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true;";

            conn = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new farm
    public void InsertFarm(String FarmName , String City , String Region){}

    // Delete farm by ID
    public void DeleteFarm(int farmID){}

    // Update farm information
    public void UpdateFarmName(int farmID,String farmName){}

    // Select farms by city
    public void SelectFarmBycity(String City){}

    // Simple JOIN query:
    // Get farms with their harvest batches
    public void GetFarmHarvestBatches(){}
}
