package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.operations.DriverOperations;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.DriverModel;

public class DriverController {

    @FXML
    private TextField driverNameField;

    @FXML
    private TextField driverIdField;

    @FXML
    private TableView<DriverModel> driverTable;

    @FXML
    private TableColumn<DriverModel, Integer> driverIdColumn;

    @FXML
    private TableColumn<DriverModel, String> driverNameColumn;

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

        ObservableList<DriverModel> drivers =

                driver.getDriversByName(
                        driverNameField.getText()
                );

        driverTable.setItems(drivers);
    }

    @FXML
    public void getTrips() {

        driver.GetDriverTrips();

    }
}