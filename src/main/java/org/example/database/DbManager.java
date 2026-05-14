package org.example.database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager
{
    private final String url =
            "jdbc:sqlserver://localhost:1433;databaseName=FarmToRestaurantDB;encrypt=true;trustServerCertificate=true";

    public Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection(url);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
