package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        String username = "sa";
        String password = "123456";

        String url =
                "jdbc:sqlserver://localhost:1433;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";

        try {

            Connection conn =
                    DriverManager.getConnection(url, username, password);

            Statement stmt = conn.createStatement();

            // Create Database
            stmt.executeUpdate(
                    "CREATE DATABASE FarmRestaurantDB");

            // Use Database
            stmt.executeUpdate(
                    "USE FarmRestaurantDB");

            // Farm Table
            stmt.executeUpdate("""
            CREATE TABLE Farm (
                FarmID INT PRIMARY KEY,
                FarmName VARCHAR(100),
                City VARCHAR(100),
                Region VARCHAR(150)
            )
            """);

            // CropType Table
            stmt.executeUpdate("""
            CREATE TABLE CropType (
                CropTypeID INT PRIMARY KEY,
                CropName VARCHAR(100)
            )
            """);

            // Restaurant Table
            stmt.executeUpdate("""
            CREATE TABLE Restaurant (
                RestaurantID INT PRIMARY KEY,
                RestaurantName VARCHAR(100),
                City VARCHAR(100),
                Street VARCHAR(150),
                PreferredDeliveryWindow VARCHAR(100)
            )
            """);

            // Driver Table
            stmt.executeUpdate("""
            CREATE TABLE Driver (
                DriverID INT PRIMARY KEY,
                DriverName VARCHAR(100)
            )
            """);

            // DeliveryTrip Table
            stmt.executeUpdate("""
            CREATE TABLE DeliveryTrip (
                TripID INT PRIMARY KEY,
                Route VARCHAR(255),
                TotalDistance DECIMAL(8,2),
                Status VARCHAR(50),
                TripDate DATE,
                DriverID INT,

                FOREIGN KEY (DriverID)
                REFERENCES Driver(DriverID)
            )
            """);

            // HarvestBatch Table
            stmt.executeUpdate("""
            CREATE TABLE HarvestBatch (
                BatchID INT PRIMARY KEY,
                HarvestDate DATE,
                AvailableQuantity INT,
                FreshnessWindow INT,
                FarmID INT,
                CropTypeID INT,

                FOREIGN KEY (FarmID)
                REFERENCES Farm(FarmID),

                FOREIGN KEY (CropTypeID)
                REFERENCES CropType(CropTypeID)
            )
            """);

            // Orders Table
            stmt.executeUpdate("""
            CREATE TABLE Orders (
                OrderID INT PRIMARY KEY,
                OrderDate DATE,
                TotalAmount DECIMAL(10,2),
                RestaurantID INT,
                TripID INT,

                FOREIGN KEY (RestaurantID)
                REFERENCES Restaurant(RestaurantID),

                FOREIGN KEY (TripID)
                REFERENCES DeliveryTrip(TripID)
            )
            """);

            // FarmCropType Table
            stmt.executeUpdate("""
            CREATE TABLE FarmCropType (
                FarmID INT,
                CropTypeID INT,

                PRIMARY KEY (FarmID, CropTypeID),

                FOREIGN KEY (FarmID)
                REFERENCES Farm(FarmID),

                FOREIGN KEY (CropTypeID)
                REFERENCES CropType(CropTypeID)
            )
            """);

            // OrderBatch Table
            stmt.executeUpdate("""
            CREATE TABLE OrderBatch (
                OrderID INT,
                BatchID INT,
                QuantityOrdered INT,
                PriceAtOrder DECIMAL(10,2),

                PRIMARY KEY (OrderID, BatchID),

                FOREIGN KEY (OrderID)
                REFERENCES Orders(OrderID),

                FOREIGN KEY (BatchID)
                REFERENCES HarvestBatch(BatchID)
            )
            """);

            System.out.println("Database and tables created successfully!");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}