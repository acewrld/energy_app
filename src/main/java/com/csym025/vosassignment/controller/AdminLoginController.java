package com.csym025.vosassignment.controller;

import com.csym025.vosassignment.entity.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AdminLoginController {
    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private PasswordField password;

    @FXML
    private TextField supplierName;
    @FXML
    public void adminLogin(MouseEvent event) {
        String sName = supplierName.getText();
        String sPassword = password.getText();

        if(sName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("username field shouldn't be null");
            alert.show();

            return;
        }

        if(sPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("password field shouldn't be null");
            alert.show();

            return;
        }

        if(sName.equals("admin") && sPassword.equals("123")) {
            Supplier session = Supplier.getSupplierObject();
            session.setSupplierName("admin");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Either username or password is incorrect");
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();

            return;
        }

        try {
            System.out.println("supplier is Logged is");
            Stage stage1 = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage1.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csym025/vosassignment/main.fxml"));
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
}
