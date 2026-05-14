package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import org.example.operations.RestaurantOperations;
import javafx.scene.control.TextArea;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.example.models.RestaurantModel;


public class RestaurantController {

    @FXML
    private TextField restaurantIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField windowField;

    @FXML
    private TableView<RestaurantModel> restaurantTable;

    @FXML
    private TableColumn<RestaurantModel, Integer> restaurantIdColumn;

    @FXML
    private TableColumn<RestaurantModel, String> restaurantNameColumn;

    @FXML
    private TableColumn<RestaurantModel, String> cityColumn;

    @FXML
    private TableColumn<RestaurantModel, String> streetColumn;

    @FXML
    private TextArea outputArea;


    RestaurantOperations restaurant =
            new RestaurantOperations();

    @FXML
    public void initialize() {

        restaurantIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("restaurantId")
        );

        restaurantNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("restaurantName")
        );

        cityColumn.setCellValueFactory(
                new PropertyValueFactory<>("city")
        );

        streetColumn.setCellValueFactory(
                new PropertyValueFactory<>("street")
        );

    }


    @FXML
    public void insertRestaurant() {

        restaurant.InsertRestaurant(
                nameField.getText(),
                cityField.getText(),
                streetField.getText(),
                windowField.getText()
        );

    }

    @FXML
    public void deleteRestaurant() {

        restaurant.DeleteRestaurant(
                Integer.parseInt(
                        restaurantIdField.getText()
                )
        );

    }

    @FXML
    public void updateRestaurant() {

        restaurant.UpdateRestaurantName(
                Integer.parseInt(
                        restaurantIdField.getText()
                ),
                nameField.getText()
        );

    }

    @FXML
    public void selectByCity() {

        ObservableList<RestaurantModel> restaurants =

                restaurant.getRestaurantsByCity(
                        cityField.getText()
                );

        restaurantTable.setItems(restaurants);
    }

    @FXML
    public void getOrders() {

        String result =
                restaurant.GetRestaurantOrders();

        outputArea.setText(result);

    }
}