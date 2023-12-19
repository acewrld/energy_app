package com.csym025.vosassignment.controller;

import com.csym025.vosassignment.entity.Customer;
import com.csym025.vosassignment.entity.Supplier;
import com.csym025.vosassignment.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewCustomers implements Initializable {

    @FXML
    private TableColumn<Customer, String> actions;

    @FXML
    private TableColumn<Customer, String> customerEmail;

    @FXML
    private TableColumn<Customer, Integer> customerId;

    @FXML
    private TableColumn<Customer, String> customerMeterNo;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private AnchorPane customersPane;

    @FXML
    private TableColumn<Customer, String> customerMobileNo;

    @FXML
    private TextField search;

    @FXML
    private Text supplierName;

    @FXML
    private TableView<Customer> customersTable;

    ObservableList<Customer> customersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String msg = Supplier.getSupplierObject().getSupplierName();
        supplierName.setText(supplierName.getText()+" "+msg);

        ArrayList<Customer> customers = Utils.loadCustomers();
        if (!customers.isEmpty()) {
            for (int i = 0; i < customers.size(); i++) {
                Customer cust = customers.get(i);
                customersList.add(new Customer(cust.getId(), cust.getFullName(), cust.getEmail(), cust.getMobileNo(), cust.getPostCode(), cust.getMeterNo()));
            }

            customerId.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            customerEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            customerMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
            customerMeterNo.setCellValueFactory(new PropertyValueFactory<>("meterNo"));

            customersTable.setItems(customersList);

            Callback<TableColumn<Customer, String>, TableCell<Customer, String>> cellFoctory =
                    (TableColumn<Customer, String> param) -> {
                        // make cell containing buttons
                        final TableCell<Customer, String> cell = new TableCell<>() {
                            private final Button electricityBillBtn = new Button("Create Electricity Bill");
                            private final Button gasBillBtn = new Button("Create Gas Bill");
                            private final Button billHistoryBtn = new Button("Bill History");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                //that cell created only on non-empty rows
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);

                                } else {
                                    setGraphic(new HBox(3, electricityBillBtn, gasBillBtn, billHistoryBtn));

                                    electricityBillBtn.setOnMouseClicked((MouseEvent event) -> {
                                        Customer customer = getTableView().getItems().get(getIndex());
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csym025/vosassignment/generate-electricity-bill.fxml"));
                                            Scene scene = new Scene(loader.load());
                                            Stage stage = new Stage();
                                            stage.setScene(scene);
                                            stage.initStyle(StageStyle.UTILITY);
                                            GenerateElectricityBill electricityBill = loader.getController();
                                            electricityBill.initData(customer);
                                            stage.show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                                    gasBillBtn.setOnMouseClicked((MouseEvent event) -> {
                                        Customer customer = getTableView().getItems().get(getIndex());
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csym025/vosassignment/generate-gas-bill.fxml"));
                                            Scene scene = new Scene(loader.load());
                                            Stage stage = new Stage();
                                            stage.setScene(scene);
                                            stage.initStyle(StageStyle.UTILITY);
                                            GenerateGasBill gasBill = loader.getController();
                                            gasBill.initData(customer);
                                            stage.show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });

                                    billHistoryBtn.setOnMouseClicked((MouseEvent event) -> {
                                        Customer customer = getTableView().getItems().get(getIndex());
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csym025/vosassignment/bill-history.fxml"));
                                            Scene scene = new Scene(loader.load());
                                            Stage stage = new Stage();
                                            stage.setScene(scene);
                                            stage.initStyle(StageStyle.UTILITY);
                                            BillHistory billHistory = loader.getController();
                                            billHistory.initData(customer);
                                            stage.show();
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                }
                            }
                        };
                        return cell;
                    };

            actions.setCellFactory(cellFoctory);


            FilteredList<Customer> filteredList = new FilteredList<>(customersList, b -> true);
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(p -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKey = newValue.toLowerCase();
                    if(p.getFullName().toLowerCase().indexOf(searchKey) > -1) return true;
                    else if(p.getEmail().toLowerCase().indexOf(searchKey) > -1) return true;
                    else if(p.getMobileNo().toLowerCase().indexOf(searchKey) > -1) return true;
                    else if(p.getMeterNo().toLowerCase().indexOf(searchKey) > -1) return true;

                    else return false;
                });
            });
            SortedList<Customer> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(customersTable.comparatorProperty());
            customersTable.setItems(sortedList);
        }
    }
}