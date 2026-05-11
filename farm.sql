
USE FarmRestaurantDB;
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
    FreshnessWindow INT NOT NULL,
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

/* FARM */
/* INSERT */
INSERT INTO dbo.Farm (FarmName, City, Region) VALUES (?, ?, ?);
/* SELECT BY CITY*/
SELECT FarmID, FarmName, City, Region FROM dbo.Farm WHERE City = ?
/* UPDATE BY NAME */
UPDATE dbo.Farm SET FarmName = ? WHERE FarmID = ?;
/* DELETE BY ID*/
DELETE FROM dbo.Farm WHERE FarmID = ?;
/* GetFarmHarvestBatches */
SELECT
    f.FarmID,
    f.FarmName,
    hb.BatchID,
    hb.HarvestDate,
    hb.AvailableQuantity,
    hb.FreshnessWindow,
    ct.CropName
FROM dbo.Farm f
         JOIN dbo.HarvestBatch hb
              ON f.FarmID = hb.FarmID
         JOIN dbo.CropType ct
              ON hb.CropTypeID = ct.CropTypeID
ORDER BY f.FarmID, hb.BatchID;


/* RESTAURANT */
/* INSERT */
INSERT INTO dbo.Restaurant (RestaurantName, City, Street, PreferredDeliveryWindow) VALUES (?, ?, ?, ?);
/* SELECT BY CITY */
SELECT RestaurantID, RestaurantName, City, Street, PreferredDeliveryWindow FROM dbo.Restaurant WHERE City = ?;
/* SELECT BY ID */
SELECT RestaurantID, RestaurantName, City, Street, PreferredDeliveryWindow FROM dbo.Restaurant WHERE RestaurantID = ?;
/* UPDATE BY ID */
UPDATE dbo.Restaurant SET RestaurantName = ?, City = ?, Street = ?, PreferredDeliveryWindow = ? WHERE RestaurantID = ?;
/* DELETE BY ID */
DELETE FROM dbo.Restaurant WHERE RestaurantID = ?;
/* GetRestaurantOrders */
SELECT r.RestaurantID,
       r.RestaurantName,
       o.OrderID,
       o.OrderDate,
       o.TotalAmount,
       dt.TripID
FROM dbo.Restaurant r
         JOIN dbo.Orders o ON r.RestaurantID = o.RestaurantID
         JOIN dbo.DeliveryTrip dt ON o.TripID = dt.TripID
WHERE r.RestaurantID = ?
ORDER BY o.OrderID;


/* DRIVER */
/* INSERT */
INSERT INTO dbo.Driver (DriverName) VALUES (?);
/* SELECT BY NAME */
SELECT DriverID, DriverName FROM dbo.Driver WHERE DriverName = ?;
/* UPDATE BY ID */
UPDATE dbo.Driver SET DriverName = ? WHERE DriverID = ?;
/* DELETE BY ID */
DELETE FROM dbo.Driver WHERE DriverID = ?;
/* GetDriverTrips */
SELECT d.DriverID,
       d.DriverName,
       dt.TripID,
       dt.Route,
       dt.TotalDistance,
       dt.Status,
       dt.TripDate
FROM dbo.Driver d
         JOIN dbo.DeliveryTrip dt ON d.DriverID = dt.DriverID
WHERE d.DriverID = ?
ORDER BY dt.TripDate;




/* HARVEST BATCH */
/* INSERT */
INSERT INTO dbo.HarvestBatch (HarvestDate, AvailableQuantity, FreshnessWindow, FarmID, CropTypeID) VALUES (?, ?, ?, ?, ?);
/* SELECT BY ID */
SELECT BatchID, HarvestDate, AvailableQuantity, FreshnessWindow, FarmID, CropTypeID FROM dbo.HarvestBatch WHERE BatchID = ?;
/* SELECT BY FARM */
SELECT BatchID, HarvestDate, AvailableQuantity, FreshnessWindow, FarmID, CropTypeID FROM dbo.HarvestBatch WHERE FarmID = ?;
/* UPDATE BY ID */
UPDATE dbo.HarvestBatch SET HarvestDate = ?, AvailableQuantity = ?, FreshnessWindow = ?, FarmID = ?, CropTypeID = ? WHERE BatchID = ?;
/* DELETE BY ID */
DELETE FROM dbo.HarvestBatch WHERE BatchID = ?;
/* GetHarvestBatchAvailability */
SELECT hb.BatchID,
       hb.HarvestDate,
       hb.AvailableQuantity,
       hb.FreshnessWindow,
       f.FarmName,
       ct.CropName
FROM dbo.HarvestBatch hb
         JOIN dbo.Farm f ON hb.FarmID = f.FarmID
         JOIN dbo.CropType ct ON hb.CropTypeID = ct.CropTypeID
WHERE hb.BatchID = ?
ORDER BY hb.BatchID;


/* INQUIRY SERVICE */
/* GetTopCropType */
SELECT TOP 1
    ct.CropName,
    COUNT(DISTINCT o.OrderID) AS TotalOrders
FROM dbo.CropType ct
JOIN dbo.HarvestBatch hb
    ON ct.CropTypeID = hb.CropTypeID
JOIN dbo.OrderBatch oi
    ON hb.BatchID = oi.BatchID
JOIN dbo.Orders o
    ON oi.OrderID = o.OrderID
GROUP BY ct.CropName
ORDER BY TotalOrders DESC;


/* GetInactiveFarmsLastMonth */
SELECT f.FarmID, f.FarmName
FROM dbo.Farm f
WHERE NOT EXISTS (
    SELECT 1
    FROM dbo.HarvestBatch hb
    JOIN dbo.OrderBatch oi
        ON hb.BatchID = oi.BatchID
    JOIN dbo.Orders o
        ON oi.OrderID = o.OrderID
    WHERE hb.FarmID = f.FarmID
      AND o.OrderDate >= DATEADD(MONTH, -1, GETDATE())
);


/* GetTopDriverLastMonth */
SELECT TOP 1
       d.DriverID,
       d.DriverName,
       COUNT(t.TripID) AS CompletedTrips
FROM dbo.Driver d
JOIN dbo.DeliveryTrip t
    ON d.DriverID = t.DriverID
WHERE t.Status = 'Completed'
  AND t.TripDate >= DATEADD(MONTH, -1, GETDATE())
GROUP BY d.DriverID, d.DriverName
ORDER BY CompletedTrips DESC;


/* GetRestaurantsWithoutOrders */
SELECT r.RestaurantID, r.RestaurantName
FROM dbo.Restaurant r
WHERE NOT EXISTS (
    SELECT 1
    FROM dbo.Orders o
    WHERE o.RestaurantID = r.RestaurantID
      AND o.OrderDate >= DATEADD(MONTH, -1, GETDATE())
);


/* GetRestaurantDeliveredBatches */
SELECT
    r.RestaurantID,
    r.RestaurantName,
    hb.BatchID,
    ct.CropName,
    o.OrderID
FROM dbo.Restaurant r
JOIN dbo.Orders o
    ON r.RestaurantID = o.RestaurantID
JOIN dbo.OrderBatch oi
    ON o.OrderID = oi.OrderID
JOIN dbo.HarvestBatch hb
    ON oi.BatchID = hb.BatchID
JOIN dbo.CropType ct
    ON hb.CropTypeID = ct.CropTypeID
JOIN dbo.DeliveryTrip dt
    ON o.TripID = dt.TripID
WHERE dt.Status = 'Completed'
ORDER BY r.RestaurantID, o.OrderID;


/* GetFarmRevenue */
SELECT
    f.FarmID,
    f.FarmName,
    SUM(oi.QuantityOrdered * oi.PriceAtOrder) AS TotalRevenue
FROM dbo.Farm f
JOIN dbo.HarvestBatch hb
    ON f.FarmID = hb.FarmID
JOIN dbo.OrderBatch oi
    ON hb.BatchID = oi.BatchID
JOIN dbo.Orders o
    ON oi.OrderID = o.OrderID
JOIN dbo.DeliveryTrip dt
    ON o.TripID = dt.TripID
WHERE dt.Status = 'Completed'
GROUP BY f.FarmID, f.FarmName
ORDER BY TotalRevenue DESC;
