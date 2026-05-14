package org.example.controllers;

import javafx.fxml.FXML;
import org.example.models.DriverTripModel;
import javafx.scene.control.TextField;
import org.example.operations.DriverOperations;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class DriverController {

    @FXML
    private TextField driverNameField;

    @FXML
    private TextField driverIdField;

    @FXML
    private TableView<DriverTripModel> tripTable;

    @FXML
    private TableColumn<DriverTripModel, Integer> driverIdColumn;

    @FXML
    private TableColumn<DriverTripModel, String> driverNameColumn;

    @FXML
    private TableColumn<DriverTripModel, Integer> tripIdColumn;

    @FXML
    private TableColumn<DriverTripModel, String> routeColumn;

    @FXML
    private TableColumn<DriverTripModel, Double> distanceColumn;

    @FXML
    private TableColumn<DriverTripModel, String> statusColumn;

    DriverOperations driver =
            new DriverOperations();

    @FXML
    public void initialize() {

        driverIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("driverId")
        );

        driverNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("driverName")
        );

        tripIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("tripId")
        );

        routeColumn.setCellValueFactory(
                new PropertyValueFactory<>("route")
        );

        distanceColumn.setCellValueFactory(
                new PropertyValueFactory<>("distance")
        );

        statusColumn.setCellValueFactory(
                new PropertyValueFactory<>("status")
        );
    }


    @FXML
    public void insertDriver() {

        driver.InsertDriver(
                driverNameField.getText()
        );

    }

    @FXML
    public void deleteDriver() {

        int id =
                Integer.parseInt(driverIdField.getText());

        driver.DeleteDriver(id);


    }

    @FXML
    public void updateDriver() {

        int id =
                Integer.parseInt(driverIdField.getText());

        driver.UpdateDriverName(
                id,
                driverNameField.getText()
        );

    }

    @FXML
    public void selectDriver() {

        ObservableList<DriverTripModel> trips =

                driver.getDriversByName(
                        driverNameField.getText()
                );

        tripTable.setItems(trips);
    }

    @FXML
    public void getTrips() {

        ObservableList<DriverTripModel> trips =

                driver.getDriverTrips();

        tripTable.setItems(trips);
    }
}