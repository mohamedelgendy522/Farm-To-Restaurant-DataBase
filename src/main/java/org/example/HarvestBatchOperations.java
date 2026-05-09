package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
class HarvestBatchOperations
{
    // Insert new harvest batch
    public void InsertHarvestBatch(int FarmID, String HarvestDate, int Quantity, String PreferredDeliveryTime){}

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