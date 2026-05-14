package org.example.models;

public class DriverTripModel {

    private int driverId;

    private String driverName;

    private int tripId;

    private String route;

    private double distance;

    private String status;

    public DriverTripModel(
            int driverId,
            String driverName,
            int tripId,
            String route,
            double distance,
            String status
    ) {

        this.driverId = driverId;
        this.driverName = driverName;
        this.tripId = tripId;
        this.route = route;
        this.distance = distance;
        this.status = status;
    }

    public int getDriverId() {
        return driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public int getTripId() {
        return tripId;
    }

    public String getRoute() {
        return route;
    }

    public double getDistance() {
        return distance;
    }

    public String getStatus() {
        return status;
    }
}