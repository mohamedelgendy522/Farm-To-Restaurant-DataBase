package org.example.models;

public class FarmModel {

    private int farmId;

    private String farmName;

    private String city;

    private String region;

    public FarmModel(
            int farmId,
            String farmName,
            String city,
            String region
    ) {

        this.farmId = farmId;
        this.farmName = farmName;
        this.city = city;
        this.region = region;
    }

    public int getFarmId() {
        return farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }
}