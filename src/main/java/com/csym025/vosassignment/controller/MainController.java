package com.csym025.vosassignment.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button createCustomer;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button supplierLogout;

    @FXML
    private Button viewCustomers;

    @FXML
    void createCustomer(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/csym025/vosassignment/create-customer.fxml"));
            mainBorderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supplierLogout(MouseEvent event) {
        try {
            Stage stage1 = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage1.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csym025/vosassignment/login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("VISUAL OBJECT SOFTWARE");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void viewCustomers(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/csym025/vosassignment/view-customers.fxml"));
            mainBorderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void init() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/csym025/vosassignment/view-customers.fxml"));
            mainBorderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/csym025/vosassignment/create-customer.fxml"));
            mainBorderPane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}