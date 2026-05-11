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
   public void DeleteHarvestBatch(int BatchID){

        String query =
                "delete HarvestBatch where BatchID = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)
        ){
            stmt.setInt(1,BatchID);

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Update batch information
    public void UpdateHarvestBatch(int BatchID, int AvailableQuantity){
        String query =
                "update HarvestBatch set AvailableQuantity = ? where BatchID = ?";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)
        ){
            stmt.setInt(1,AvailableQuantity);
            stmt.setInt(2,BatchID);

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Select available batches only
    public void SelectAvailableBatches(){
        String query =
                "select * from HarvestBatch where AvailableQuantity > 0";

        try (
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ){

            while (rs.next()) {

                System.out.println(
                        "Batch ID: " + rs.getInt("BatchID") +
                        ", Quantity: " + rs.getInt("AvailableQuantity")
                );

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Simple JOIN query:
    // Get harvest batches with crop types
    public void GetBatchCropTypes() {

        String query =
                "SELECT h.BatchID, h.AvailableQuantity, h.HarvestDate, c.CropName " +
                "FROM HarvestBatch h , CropType c " +
                "WHERE h.CropTypeID = c.CropTypeID";

        try (
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                System.out.println(
                        "Batch ID: " + rs.getInt("BatchID") +
                        ", CropName: " + rs.getString("CropName")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
