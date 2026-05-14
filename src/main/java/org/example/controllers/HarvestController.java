package org.example.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import org.example.operations.HarvestBatchOperations;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import org.example.models.HarvestModel;

public class HarvestController {

    @FXML
    private TextField batchIdField;

    @FXML
    private TextField farmIdField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField freshnessField;

    @FXML
    private TextField cropTypeField;

    @FXML
    private TableView<HarvestModel> harvestTable;

    @FXML
    private TableColumn<HarvestModel, Integer> batchIdColumn;

    @FXML
    private TableColumn<HarvestModel, Integer> quantityColumn;

    @FXML
    private TableColumn<HarvestModel, String> dateColumn;

    @FXML
    private TableColumn<HarvestModel, String> cropColumn;

    HarvestBatchOperations harvest =
            new HarvestBatchOperations();

    @FXML
    public void initialize() {

        batchIdColumn.setCellValueFactory(
                new PropertyValueFactory<>("batchId")
        );

        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("harvestDate")
        );

        cropColumn.setCellValueFactory(
                new PropertyValueFactory<>("cropName")
        );
    }

    @FXML
    public void insertBatch() {

        harvest.InsertHarvestBatch(
                Integer.parseInt(farmIdField.getText()),
                dateField.getText(),
                Integer.parseInt(quantityField.getText()),
                Integer.parseInt(freshnessField.getText()),
                Integer.parseInt(cropTypeField.getText())
        );


    }

    @FXML
    public void deleteBatch() {

        harvest.DeleteHarvestBatch(
                Integer.parseInt(batchIdField.getText())
        );


    }

    @FXML
    public void updateBatch() {

        harvest.UpdateHarvestBatch(
                Integer.parseInt(batchIdField.getText()),
                Integer.parseInt(quantityField.getText())
        );


    }

    @FXML
    public void availableBatches() {

        ObservableList<HarvestModel> batches =

                harvest.getAvailableBatches();

        harvestTable.setItems(batches);
    }

    @FXML
    public void batchCropTypes() {

        ObservableList<HarvestModel> batches =

                harvest.getBatchCropTypes();

        harvestTable.setItems(batches);
    }
}