package org.example.models;

public class HarvestModel {

    private int batchId;

    private int quantity;

    private String harvestDate;

    private String cropName;

    public HarvestModel(
            int batchId,
            int quantity,
            String harvestDate,
            String cropName
    ) {

        this.batchId = batchId;
        this.quantity = quantity;
        this.harvestDate = harvestDate;
        this.cropName = cropName;
    }

    public int getBatchId() {
        return batchId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getHarvestDate() {
        return harvestDate;
    }
    public String getCropName() {
        return cropName;
    }
}