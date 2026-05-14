package org.example.models;

public class DriverModel {

    private int driverId;

    private String driverName;

    public DriverModel(
            int driverId,
            String driverName
    ) {

        this.driverId = driverId;
        this.driverName = driverName;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }
}