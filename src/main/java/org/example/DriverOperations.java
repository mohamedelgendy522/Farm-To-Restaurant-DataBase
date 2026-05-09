package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
class DriverOperations
{
    // Insert new driver
    public void InsertDriver(String DriverName){}

    // Delete driver by ID
    public void DeleteDriver(int DriverID){}

    // Update driver information
    public void UpdateDriverName(int DriverID, String DriverName){}

    // Select driver by name
    public void SelectDriverByName(String DriverName){}

    // Simple JOIN query:
    // Get drivers with their delivery trips
    public void GetDriverTrips(){}
}