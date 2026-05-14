package org.example.operations;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.example.models.HarvestModel;
public class HarvestBatchOperations


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
    public void InsertHarvestBatch(int FarmID, String HarvestDate, int AvailableQuantity, int FreshnessWindow, int CropTypeID){

        String query =
                "INSERT INTO dbo.HarvestBatch(FarmID, HarvestDate, AvailableQuantity, FreshnessWindow, CropTypeID) VALUES (?,?,?,?,?)";

        try (
                PreparedStatement stmt = conn.prepareStatement(query)
        ){
             stmt.setInt(1,FarmID);
             stmt.setString(2,HarvestDate);
             stmt.setInt(3,AvailableQuantity);
             stmt.setInt(4,FreshnessWindow);
             stmt.setInt(5,CropTypeID);
            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete batch by ID
   public void DeleteHarvestBatch(int BatchID){

        String query =
                "DELETE FROM dbo.HarvestBatch WHERE BatchID = ?";

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
                "UPDATE dbo.HarvestBatch SET AvailableQuantity = ? WHERE BatchID = ?";

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


    // Simple JOIN query:
    // Get harvest batches with crop types
    public ObservableList<HarvestModel> getBatchCropTypes() {

        ObservableList<HarvestModel> batches =
                FXCollections.observableArrayList();

        String query =
                "SELECT h.BatchID, " +
                        "h.AvailableQuantity, " +
                        "h.HarvestDate, " +
                        "c.CropName " +
                        "FROM dbo.HarvestBatch h, dbo.CropType c " +
                        "WHERE h.CropTypeID = c.CropTypeID";

        try {

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                batches.add(
                        new HarvestModel(
                                rs.getInt("BatchID"),
                                rs.getInt("AvailableQuantity"),
                                rs.getString("HarvestDate"),
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

    // Select available batches only
    public ObservableList<HarvestModel> getAvailableBatches() {

        ObservableList<HarvestModel> batches =
                FXCollections.observableArrayList();

        String query =
                "SELECT BatchID, AvailableQuantity, HarvestDate " +
                        "FROM dbo.HarvestBatch " +
                        "WHERE AvailableQuantity > 0";

        try {

            PreparedStatement stmt =
                    conn.prepareStatement(query);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                batches.add(
                        new HarvestModel(
                                rs.getInt("BatchID"),
                                rs.getInt("AvailableQuantity"),
                                rs.getString("HarvestDate"),
                                ""
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


}
