package org.example.controllers;

public class DashboardController {

    public void openFarmScreen() {
        SceneManager.switchScene(
                "farm.fxml",
                "Farm Operations"
        );
    }

    public void openDriverScreen() {
        SceneManager.switchScene(
                "driver.fxml",
                "Driver Operations"
        );
    }

    public void openHarvestScreen() {

        SceneManager.switchScene(
                "harvest.fxml",
                "Harvest Operations"
        );
    }

    public void openRestaurantScreen() {

        SceneManager.switchScene(
                "restaurant.fxml",
                "Restaurant Operations"
        );
    }

    public void openInquiryScreen() {

        SceneManager.switchScene(
                "inquiry.fxml",
                "Inquiry Reports"
        );
    }
}