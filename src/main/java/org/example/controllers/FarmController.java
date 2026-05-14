package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.operations.FarmOperations;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.models.FarmModel;

public class FarmController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField regionField;

    @FXML
    private TextField farmIdField;

    @FXML
    private TableView<FarmModel> farmTable;

    @FXML
    private TableColumn<FarmModel, Integer> idColumn;

    @FXML
    private TableColumn<FarmModel, String> nameColumn;

    @FXML
    private TableColumn<FarmModel, String> cityColumn;

    @FXML
    private TableColumn<FarmModel, String> regionColumn;

    FarmOperations farm = new FarmOperations();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("farmId")
        );

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("farmName")
        );

        cityColumn.setCellValueFactory(
                new PropertyValueFactory<>("city")
        );

        regionColumn.setCellValueFactory(
                new PropertyValueFactory<>("region")
        );
    }


    @FXML
    public void insertFarm() {

        farm.InsertFarm(
                nameField.getText(),
                cityField.getText(),
                regionField.getText()
        );

    }

    @FXML
    public void deleteFarm() {

        int id = Integer.parseInt(farmIdField.getText());

        farm.DeleteFarm(id);


    }

    @FXML
    public void updateFarm() {

        int id = Integer.parseInt(farmIdField.getText());

        farm.UpdateFarmName(
                id,
                nameField.getText()
        );


    }

    @FXML
    public void selectFarmByCity() {

        ObservableList<FarmModel> farms =

                farm.getFarmsByCity(
                        cityField.getText()
                );

        farmTable.setItems(farms);
    }

    @FXML
    public void getHarvestBatches() {

        GetFarmHarvestBatches();

    }
}