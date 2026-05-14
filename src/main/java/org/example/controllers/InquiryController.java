package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.example.operations.InquiryService;

public class InquiryController {

    @FXML
    private TextArea outputArea;

    InquiryService inquiry =
            new InquiryService();

    @FXML
    public void topCrop() {

        String result = inquiry.GetTopCropType();

        outputArea.setText(result);
    }

    @FXML
    public void inactiveFarms() {

        String result = inquiry.GetInactiveFarmsLastMonth();

        outputArea.setText(result);
    }

    @FXML
    public void topDriver() {

        String result = inquiry.GetTopDriverLastMonth();

        outputArea.setText(result);
    }

    @FXML
    public void restaurantsWithoutOrders() {

        String result = inquiry.GetRestaurantsWithoutOrders();

        outputArea.setText(result);
    }

    @FXML
    public void restaurantDelivered() {

        String result = inquiry.GetRestaurantDeliveredBatches();

        outputArea.setText(result);
    }

    @FXML
    public void farmRevenue() {

        String result = inquiry.GetFarmRevenue();

        outputArea.setText(result);
    }
}