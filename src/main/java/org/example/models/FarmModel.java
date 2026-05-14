package org.example.models;

public class FarmModel {

    private int farmId;

    private String farmName;

    private String city;

    private String region;

    private int batchId;

    private String harvestDate;

    private int quantity;

    private String cropName;

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

    public FarmModel(
            int farmId,
            String farmName,
            int batchId,
            String harvestDate,
            int quantity,
            String cropName
    ) {

        this.farmId = farmId;
        this.farmName = farmName;
        this.batchId = batchId;
        this.harvestDate = harvestDate;
        this.quantity = quantity;
        this.cropName = cropName;
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

    public int getBatchId() {
        return batchId;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCropName() {
        return cropName;
    }
}