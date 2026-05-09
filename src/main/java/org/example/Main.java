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
            stmt.executeUpdate("CREATE DATABASE FarmRestaurantDB");

            // Use Database
            stmt.executeUpdate("USE FarmRestaurantDB");

            // ── Schema ────────────────────────────────────────────────────────

            stmt.executeUpdate("""
            CREATE TABLE Farm (
                FarmID INT PRIMARY KEY,
                FarmName VARCHAR(100),
                City VARCHAR(100),
                Region VARCHAR(150)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE CropType (
                CropTypeID INT PRIMARY KEY,
                CropName VARCHAR(100)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE Restaurant (
                RestaurantID INT PRIMARY KEY,
                RestaurantName VARCHAR(100),
                City VARCHAR(100),
                Street VARCHAR(150),
                PreferredDeliveryWindow VARCHAR(100)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE Driver (
                DriverID INT PRIMARY KEY,
                DriverName VARCHAR(100)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE DeliveryTrip (
                TripID INT PRIMARY KEY,
                Route VARCHAR(255),
                TotalDistance DECIMAL(8,2),
                Status VARCHAR(50),
                TripDate DATE,
                DriverID INT,
                FOREIGN KEY (DriverID) REFERENCES Driver(DriverID)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE HarvestBatch (
                BatchID INT PRIMARY KEY,
                HarvestDate DATE,
                AvailableQuantity INT,
                FreshnessWindow INT,
                FarmID INT,
                CropTypeID INT,
                FOREIGN KEY (FarmID)      REFERENCES Farm(FarmID),
                FOREIGN KEY (CropTypeID)  REFERENCES CropType(CropTypeID)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE Orders (
                OrderID INT PRIMARY KEY,
                OrderDate DATE,
                TotalAmount DECIMAL(10,2),
                RestaurantID INT,
                TripID INT,
                FOREIGN KEY (RestaurantID) REFERENCES Restaurant(RestaurantID),
                FOREIGN KEY (TripID)       REFERENCES DeliveryTrip(TripID)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE FarmCropType (
                FarmID INT,
                CropTypeID INT,
                PRIMARY KEY (FarmID, CropTypeID),
                FOREIGN KEY (FarmID)     REFERENCES Farm(FarmID),
                FOREIGN KEY (CropTypeID) REFERENCES CropType(CropTypeID)
            )
            """);

            stmt.executeUpdate("""
            CREATE TABLE OrderBatch (
                OrderID INT,
                BatchID INT,
                QuantityOrdered INT,
                PriceAtOrder DECIMAL(10,2),
                PRIMARY KEY (OrderID, BatchID),
                FOREIGN KEY (OrderID)  REFERENCES Orders(OrderID),
                FOREIGN KEY (BatchID)  REFERENCES HarvestBatch(BatchID)
            )
            """);

            System.out.println("Schema created successfully!");

            // ── Seed Data ─────────────────────────────────────────────────────

            // Farm – varied cities, regions (urban / rural / coastal / highland)
            stmt.executeUpdate("""
            INSERT INTO Farm (FarmID, FarmName, City, Region) VALUES
            (1,  'Green Valley Farm',      'Springfield',   'Midwest Plains'),
            (2,  'Sunrise Organics',       'Portland',      'Pacific Northwest'),
            (3,  'Desert Bloom Ranch',     'Phoenix',       'Southwest Desert'),
            (4,  'Blue Ridge Growers',     'Asheville',     'Appalachian Highland'),
            (5,  'Coastal Harvest Co.',    'Charleston',    'Atlantic Coastal'),
            (6,  'Prairie Wind Farm',      'Amarillo',      'Southern Plains'),
            (7,  'Lake Shore Produce',     'Chicago',       'Great Lakes'),
            (8,  'Mountain Peak Farms',    'Denver',        'Rocky Mountain'),
            (9,  'Bayou Fresh Fields',     'New Orleans',   'Gulf South'),
            (10, 'Northern Lights Crops',  'Anchorage',     'Sub-Arctic North')
            """);

            // CropType – diverse crops
            stmt.executeUpdate("""
            INSERT INTO CropType (CropTypeID, CropName) VALUES
            (1,  'Tomato'),
            (2,  'Lettuce'),
            (3,  'Strawberry'),
            (4,  'Bell Pepper'),
            (5,  'Cucumber'),
            (6,  'Spinach'),
            (7,  'Carrot'),
            (8,  'Zucchini'),
            (9,  'Blueberry'),
            (10, 'Kale')
            """);

            // Restaurant – varied cuisines, delivery windows
            stmt.executeUpdate("""
            INSERT INTO Restaurant (RestaurantID, RestaurantName, City, Street, PreferredDeliveryWindow) VALUES
            (1,  'The Farm Table',          'Springfield',  '12 Oak Street',         '06:00-08:00'),
            (2,  'Verde Kitchen',           'Portland',     '88 Maple Ave',          '07:00-09:00'),
            (3,  'Casa del Campo',          'Phoenix',      '301 Cactus Blvd',       '05:00-07:00'),
            (4,  'Harvest & Hearth',        'Asheville',    '17 Ridgeline Rd',       '08:00-10:00'),
            (5,  'Seaside Bistro',          'Charleston',   '5 Harbor Walk',         '09:00-11:00'),
            (6,  'Prairie Plate',           'Amarillo',     '200 Windmill Way',      '06:30-08:30'),
            (7,  'Lakefront Grill',         'Chicago',      '900 Lakeview Dr',       '10:00-12:00'),
            (8,  'Summit Eatery',           'Denver',       '44 Alpine Pass',        '07:00-09:00'),
            (9,  'Bayou Bites',             'New Orleans',  '77 Creole Lane',        'Anytime'),
            (10, 'Aurora Dining',           'Anchorage',    '3 Tundra Terrace',      '11:00-13:00')
            """);

            // Driver – varied names
            stmt.executeUpdate("""
            INSERT INTO Driver (DriverID, DriverName) VALUES
            (1,  'James Carter'),
            (2,  'Maria Gonzalez'),
            (3,  'Liam Nguyen'),
            (4,  'Aisha Patel'),
            (5,  'Tom Brewski'),
            (6,  'Sara Kim'),
            (7,  'Omar Hassan'),
            (8,  'Elena Petrov'),
            (9,  'Carlos Rivera'),
            (10, 'Fiona McAllister')
            """);

            // DeliveryTrip – varied statuses: Completed, In Transit, Pending, Cancelled, Delayed
            stmt.executeUpdate("""
            INSERT INTO DeliveryTrip (TripID, Route, TotalDistance, Status, TripDate, DriverID) VALUES
            (1,  'Springfield -> Portland',       1450.50, 'Completed',  '2024-01-10', 1),
            (2,  'Phoenix -> Asheville',           850.00, 'Completed',  '2024-01-12', 2),
            (3,  'Charleston -> Amarillo',        1200.75, 'In Transit', '2024-03-05', 3),
            (4,  'Chicago -> Denver',              920.30, 'Pending',    '2024-04-18', 4),
            (5,  'New Orleans -> Anchorage',      3100.00, 'Cancelled',  '2024-02-20', 5),
            (6,  'Portland -> Chicago',           2100.60, 'Completed',  '2024-01-25', 6),
            (7,  'Denver -> Phoenix',              600.40, 'Delayed',    '2024-05-01', 7),
            (8,  'Asheville -> New Orleans',       700.20, 'Completed',  '2024-02-14', 8),
            (9,  'Amarillo -> Springfield',        550.90, 'In Transit', '2024-05-10', 9),
            (10, 'Anchorage -> Charleston',       3800.00, 'Pending',    '2024-06-01', 10)
            """);

            // HarvestBatch – varied freshness windows (1–14 days), quantities (small to bulk)
            stmt.executeUpdate("""
            INSERT INTO HarvestBatch (BatchID, HarvestDate, AvailableQuantity, FreshnessWindow, FarmID, CropTypeID) VALUES
            (1,  '2024-01-08',  500,  7,  1, 1),
            (2,  '2024-01-10',  300,  5,  2, 2),
            (3,  '2024-01-15', 1200,  3,  3, 3),
            (4,  '2024-02-01',  750, 10,  4, 4),
            (5,  '2024-02-10',   80,  2,  5, 5),
            (6,  '2024-03-05',  600, 14,  6, 6),
            (7,  '2024-03-20', 2000,  7,  7, 7),
            (8,  '2024-04-01',  150,  4,  8, 8),
            (9,  '2024-04-15',  900,  6,  9, 9),
            (10, '2024-05-01',  400,  1, 10, 10)
            """);

            // Orders – varied totals, dates spread across the year
            stmt.executeUpdate("""
            INSERT INTO Orders (OrderID, OrderDate, TotalAmount, RestaurantID, TripID) VALUES
            (1,  '2024-01-09',   250.00,  1, 1),
            (2,  '2024-01-11',   875.50,  2, 2),
            (3,  '2024-03-04',    99.99,  3, 3),
            (4,  '2024-04-17',  3200.00,  4, 4),
            (5,  '2024-02-18',   540.75,  5, 5),
            (6,  '2024-01-24',  1100.00,  6, 6),
            (7,  '2024-04-30',   420.30,  7, 7),
            (8,  '2024-02-13',   760.00,  8, 8),
            (9,  '2024-05-09',    55.00,  9, 9),
            (10, '2024-05-31',  6500.00, 10, 10)
            """);

            // FarmCropType – each farm linked to a different crop; two farms share a crop (edge case)
            stmt.executeUpdate("""
            INSERT INTO FarmCropType (FarmID, CropTypeID) VALUES
            (1,  1),
            (2,  2),
            (3,  3),
            (4,  4),
            (5,  5),
            (6,  6),
            (7,  7),
            (8,  8),
            (9,  9),
            (10, 10)
            """);

            // OrderBatch – varied quantities and unit prices; some orders use high volume, some minimal
            stmt.executeUpdate("""
            INSERT INTO OrderBatch (OrderID, BatchID, QuantityOrdered, PriceAtOrder) VALUES
            (1,   1,  50,   5.00),
            (2,   2,  30,   8.50),
            (3,   3, 400,   0.25),
            (4,   4,  75,  12.00),
            (5,   5,   5,  15.00),
            (6,   6, 100,   3.50),
            (7,   7, 800,   1.99),
            (8,   8,  20,  18.00),
            (9,   9, 200,   4.75),
            (10, 10,  40,   7.25)
            """);

            System.out.println("All 10 rows inserted per table successfully!");

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}