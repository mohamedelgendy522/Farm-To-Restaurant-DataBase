IF DB_ID(N'FarmRestaurantDB') IS NULL
BEGIN
    CREATE DATABASE FarmRestaurantDB;
END;
GO

USE FarmRestaurantDB;
GO


DROP TABLE IF EXISTS dbo.OrderBatch;
DROP TABLE IF EXISTS dbo.FarmCropType;
DROP TABLE IF EXISTS dbo.Orders;
DROP TABLE IF EXISTS dbo.HarvestBatch;
DROP TABLE IF EXISTS dbo.DeliveryTrip;
DROP TABLE IF EXISTS dbo.Restaurant;
DROP TABLE IF EXISTS dbo.Driver;
DROP TABLE IF EXISTS dbo.CropType;
DROP TABLE IF EXISTS dbo.Farm;
GO

CREATE TABLE dbo.Farm (
    FarmID INT IDENTITY(1,1) PRIMARY KEY,
    FarmName VARCHAR(100) NOT NULL,
    City VARCHAR(100) NOT NULL,
    Region VARCHAR(150) NOT NULL
);
GO

CREATE TABLE dbo.CropType (
    CropTypeID INT IDENTITY(1,1) PRIMARY KEY,
    CropName VARCHAR(100) NOT NULL
);
GO

CREATE TABLE dbo.Restaurant (
    RestaurantID INT IDENTITY(1,1) PRIMARY KEY,
    RestaurantName VARCHAR(100) NOT NULL,
    City VARCHAR(100) NOT NULL,
    Street VARCHAR(150) NOT NULL,
    PreferredDeliveryWindow VARCHAR(100) NOT NULL
);
GO

CREATE TABLE dbo.Driver (
    DriverID INT IDENTITY(1,1) PRIMARY KEY,
    DriverName VARCHAR(100) NOT NULL
);
GO

CREATE TABLE dbo.DeliveryTrip (
    TripID INT IDENTITY(1,1) PRIMARY KEY,
    Route VARCHAR(255) NOT NULL,
    TotalDistance DECIMAL(8,2) NOT NULL,
    Status VARCHAR(50) NOT NULL,
    TripDate DATE NOT NULL,
    DriverID INT NOT NULL,
    CONSTRAINT FK_DeliveryTrip_Driver
        FOREIGN KEY (DriverID) REFERENCES dbo.Driver(DriverID)
);
GO

CREATE TABLE dbo.HarvestBatch (
    BatchID INT IDENTITY(1,1) PRIMARY KEY,
    HarvestDate DATE NOT NULL,
    AvailableQuantity INT NOT NULL,
    FreshnessWindow VARCHAR(100) NOT NULL,
    FarmID INT NOT NULL,
    CropTypeID INT NOT NULL,
    CONSTRAINT FK_HarvestBatch_Farm
        FOREIGN KEY (FarmID) REFERENCES dbo.Farm(FarmID),
    CONSTRAINT FK_HarvestBatch_CropType
        FOREIGN KEY (CropTypeID) REFERENCES dbo.CropType(CropTypeID)
);
GO

CREATE TABLE dbo.Orders (
    OrderID INT IDENTITY(1,1) PRIMARY KEY,
    OrderDate DATE NOT NULL,
    TotalAmount DECIMAL(10,2) NOT NULL,
    RestaurantID INT NOT NULL,
    TripID INT NOT NULL,
    CONSTRAINT FK_Orders_Restaurant
        FOREIGN KEY (RestaurantID) REFERENCES dbo.Restaurant(RestaurantID),
    CONSTRAINT FK_Orders_DeliveryTrip
        FOREIGN KEY (TripID) REFERENCES dbo.DeliveryTrip(TripID)
);
GO

CREATE TABLE dbo.FarmCropType (
    FarmID INT NOT NULL,
    CropTypeID INT NOT NULL,
    CONSTRAINT PK_FarmCropType PRIMARY KEY (FarmID, CropTypeID),
    CONSTRAINT FK_FarmCropType_Farm
        FOREIGN KEY (FarmID) REFERENCES dbo.Farm(FarmID),
    CONSTRAINT FK_FarmCropType_CropType
        FOREIGN KEY (CropTypeID) REFERENCES dbo.CropType(CropTypeID)
);
GO

CREATE TABLE dbo.OrderBatch (
    OrderID INT NOT NULL,
    BatchID INT NOT NULL,
    QuantityOrdered INT NOT NULL,
    PriceAtOrder DECIMAL(10,2) NOT NULL,
    CONSTRAINT PK_OrderBatch PRIMARY KEY (OrderID, BatchID),
    CONSTRAINT FK_OrderBatch_Orders
        FOREIGN KEY (OrderID) REFERENCES dbo.Orders(OrderID),
    CONSTRAINT FK_OrderBatch_HarvestBatch
        FOREIGN KEY (BatchID) REFERENCES dbo.HarvestBatch(BatchID)
);
GO

-- Seed data
SET IDENTITY_INSERT dbo.Farm ON;
INSERT INTO dbo.Farm (FarmID, FarmName, City, Region) VALUES
(1,  'Green Valley Farm',      'Springfield',   'Midwest Plains'),
(2,  'Sunrise Organics',       'Portland',      'Pacific Northwest'),
(3,  'Desert Bloom Ranch',     'Phoenix',       'Southwest Desert'),
(4,  'Blue Ridge Growers',     'Asheville',     'Appalachian Highland'),
(5,  'Coastal Harvest Co.',    'Charleston',    'Atlantic Coastal'),
(6,  'Prairie Wind Farm',      'Amarillo',      'Southern Plains'),
(7,  'Lake Shore Produce',     'Chicago',       'Great Lakes'),
(8,  'Mountain Peak Farms',    'Denver',        'Rocky Mountain'),
(9,  'Bayou Fresh Fields',     'New Orleans',   'Gulf South'),
(10, 'Northern Lights Crops',  'Anchorage',     'Sub-Arctic North');
SET IDENTITY_INSERT dbo.Farm OFF;
GO

SET IDENTITY_INSERT dbo.CropType ON;
INSERT INTO dbo.CropType (CropTypeID, CropName) VALUES
(1,  'Tomato'),
(2,  'Lettuce'),
(3,  'Strawberry'),
(4,  'Bell Pepper'),
(5,  'Cucumber'),
(6,  'Spinach'),
(7,  'Carrot'),
(8,  'Zucchini'),
(9,  'Blueberry'),
(10, 'Kale');
SET IDENTITY_INSERT dbo.CropType OFF;
GO

SET IDENTITY_INSERT dbo.Restaurant ON;
INSERT INTO dbo.Restaurant (RestaurantID, RestaurantName, City, Street, PreferredDeliveryWindow) VALUES
(1,  'The Farm Table',          'Springfield',  '12 Oak Street',         '06:00-08:00'),
(2,  'Verde Kitchen',           'Portland',     '88 Maple Ave',          '07:00-09:00'),
(3,  'Casa del Campo',          'Phoenix',      '301 Cactus Blvd',       '05:00-07:00'),
(4,  'Harvest & Hearth',        'Asheville',    '17 Ridgeline Rd',       '08:00-10:00'),
(5,  'Seaside Bistro',          'Charleston',   '5 Harbor Walk',         '09:00-11:00'),
(6,  'Prairie Plate',           'Amarillo',     '200 Windmill Way',      '06:30-08:30'),
(7,  'Lakefront Grill',         'Chicago',      '900 Lakeview Dr',       '10:00-12:00'),
(8,  'Summit Eatery',           'Denver',       '44 Alpine Pass',        '07:00-09:00'),
(9,  'Bayou Bites',             'New Orleans',  '77 Creole Lane',        'Anytime'),
(10, 'Aurora Dining',           'Anchorage',    '3 Tundra Terrace',      '11:00-13:00');
SET IDENTITY_INSERT dbo.Restaurant OFF;
GO

SET IDENTITY_INSERT dbo.Driver ON;
INSERT INTO dbo.Driver (DriverID, DriverName) VALUES
(1,  'James Carter'),
(2,  'Maria Gonzalez'),
(3,  'Liam Nguyen'),
(4,  'Aisha Patel'),
(5,  'Tom Brewski'),
(6,  'Sara Kim'),
(7,  'Omar Hassan'),
(8,  'Elena Petrov'),
(9,  'Carlos Rivera'),
(10, 'Fiona McAllister');
SET IDENTITY_INSERT dbo.Driver OFF;
GO

SET IDENTITY_INSERT dbo.DeliveryTrip ON;
INSERT INTO dbo.DeliveryTrip (TripID, Route, TotalDistance, Status, TripDate, DriverID) VALUES
(1,  'Springfield -> Portland',       1450.50, 'Completed',  '2024-01-10', 1),
(2,  'Phoenix -> Asheville',           850.00,  'Completed',  '2024-01-12', 2),
(3,  'Charleston -> Amarillo',        1200.75,  'In Transit', '2024-03-05', 3),
(4,  'Chicago -> Denver',              920.30,  'Pending',    '2024-04-18', 4),
(5,  'New Orleans -> Anchorage',      3100.00,  'Cancelled',  '2024-02-20', 5),
(6,  'Portland -> Chicago',           2100.60,  'Completed',  '2024-01-25', 6),
(7,  'Denver -> Phoenix',              600.40,  'Delayed',    '2024-05-01', 7),
(8,  'Asheville -> New Orleans',       700.20,  'Completed',  '2024-02-14', 8),
(9,  'Amarillo -> Springfield',        550.90,  'In Transit', '2024-05-10', 9),
(10, 'Anchorage -> Charleston',       3800.00,  'Pending',    '2024-06-01', 10);
SET IDENTITY_INSERT dbo.DeliveryTrip OFF;
GO

SET IDENTITY_INSERT dbo.HarvestBatch ON;
INSERT INTO dbo.HarvestBatch (BatchID, HarvestDate, AvailableQuantity, FreshnessWindow, FarmID, CropTypeID) VALUES
(1,  '2024-01-08',  500,  7,  1, 1),
(2,  '2024-01-10',  300,  5,  2, 2),
(3,  '2024-01-15', 1200,  3,  3, 3),
(4,  '2024-02-01',  750, 10,  4, 4),
(5,  '2024-02-10',   80,  2,  5, 5),
(6,  '2024-03-05',  600, 14,  6, 6),
(7,  '2024-03-20', 2000,  7,  7, 7),
(8,  '2024-04-01',  150,  4,  8, 8),
(9,  '2024-04-15',  900,  6,  9, 9),
(10, '2024-05-01',  400,  1, 10, 10);
SET IDENTITY_INSERT dbo.HarvestBatch OFF;
GO

SET IDENTITY_INSERT dbo.Orders ON;
INSERT INTO dbo.Orders (OrderID, OrderDate, TotalAmount, RestaurantID, TripID) VALUES
(1,  '2024-01-09',   250.00,  1, 1),
(2,  '2024-01-11',   875.50,  2, 2),
(3,  '2024-03-04',    99.99,  3, 3),
(4,  '2024-04-17',  3200.00,  4, 4),
(5,  '2024-02-18',   540.75,  5, 5),
(6,  '2024-01-24',  1100.00,  6, 6),
(7,  '2024-04-30',   420.30,  7, 7),
(8,  '2024-02-13',   760.00,  8, 8),
(9,  '2024-05-09',    55.00,  9, 9),
(10, '2024-05-31',  6500.00, 10, 10);
SET IDENTITY_INSERT dbo.Orders OFF;
GO

INSERT INTO dbo.FarmCropType (FarmID, CropTypeID) VALUES
(1,  1),
(2,  2),
(3,  3),
(4,  4),
(5,  5),
(6,  6),
(7,  7),
(8,  8),
(9,  9),
(10, 10);
GO

INSERT INTO dbo.OrderBatch (OrderID, BatchID, QuantityOrdered, PriceAtOrder) VALUES
(1,   1,  50,   5.00),
(2,   2,  30,   8.50),
(3,   3, 400,   0.25),
(4,   4,  75,  12.00),
(5,   5,   5,  15.00),
(6,   6, 100,   3.50),
(7,   7, 800,   1.99),
(8,   8,  20,  18.00),
(9,   9, 200,   4.75),
(10, 10,  40,   7.25);
GO

