package org.example.controllers;

public class DashboardController {

    public void openFarmScreen() {
        SceneManager.switchScene(
                "farm.fxml",
                "Farm"
        );
    }

    public void openDriverScreen() {
        SceneManager.switchScene(
                "driver.fxml",
                "Drivers"
        );
    }

    public void openHarvestScreen() {

        SceneManager.switchScene(
                "harvest.fxml",
                "Harvest Batches"
        );
    }

    public void openRestaurantScreen() {

        SceneManager.switchScene(
                "restaurant.fxml",
                "Restaurants"
        );
    }

    public void openInquiryScreen() {

        SceneManager.switchScene(
                "inquiry.fxml",
                "Reports"
        );
    }
}