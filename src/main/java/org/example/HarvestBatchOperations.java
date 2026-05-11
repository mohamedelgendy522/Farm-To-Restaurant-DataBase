package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
class HarvestBatchOperations
{
    private Connection conn;

    public HarvestBatchOperations() {
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

    // Insert new harvest batch
    public void InsertHarvestBatch(int FarmID, String HarvestDate, int AvailableQuantity, int FreshnessWindow,int croptypeID){

        String query =
                "INSERT INTO dbo.HarvestBatch(FarmID, HarvestDate, AvailableQuantity, FreshnessWindow,croptypeID) VALUES (?,?,?,?,?)";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)
        ){
             stmt.setInt(1,FarmID);
             stmt.setString(2,HarvestDate);
             stmt.setInt(3,AvailableQuantity);
             stmt.setInt(4,FreshnessWindow);
             stmt.setInt(5,croptypeID);
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete batch by ID
    public void DeleteHarvestBatch(int BatchID){}

    // Update batch information
    public void UpdateHarvestBatch(int BatchID, int Quantity){}

    // Select available batches only
    public void SelectAvailableBatches(){}

    // Simple JOIN query:
    // Get harvest batches with crop types
    public void GetBatchCropTypes(){}
}
