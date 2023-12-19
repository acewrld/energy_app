package com.csym025.vosassignment.controller;

import com.csym025.vosassignment.entity.Customer;
import com.csym025.vosassignment.entity.Supplier;
import com.csym025.vosassignment.util.CustomException;
import com.csym025.vosassignment.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateCustomer implements Initializable {

    @FXML
    private Button createNewCustomer;

    @FXML
    private AnchorPane customersPane;

    @FXML
    private TextField emailAddress;

    @FXML
    private TextField fullName;

    @FXML
    private TextField meterNo;

    @FXML
    private TextField mobileNo;

    @FXML
    private TextField postCode;

    @FXML
    private Text supplierName;

    @FXML
    void saveCustomer(MouseEvent event) throws CustomException {
        String name = fullName.getText();
        String email = emailAddress.getText();
        String mobile = mobileNo.getText();
        String postalCode = postCode.getText();
        String meterNum = meterNo.getText();

        if(name.isEmpty() || email.isEmpty() || mobile.isEmpty() || postalCode.isEmpty() || meterNum.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please fill in data in all fields");
            alert.showAndWait();
        } else if(Utils.authenticateEmailAddress(email) && Utils.authenticateMobileNo(mobile)) {
            Customer customer = new Customer(1, name, email, mobile, postalCode, meterNum);
            ArrayList<Customer> customers = Utils.loadCustomers();
            int custId = customers.size() > 0 ? customers.size() + 1 : 1;
            customer.setId(custId);
            customers.add(customer);
            Utils.createCustomers(customers);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("new customer has been created");
            alert.setTitle("user created");
            alert.setHeaderText(null);
            alert.showAndWait();
            System.out.println("new customer has been created");

            Stage oldStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            oldStage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/csym025/vosassignment/main.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.setTitle("VISUAL OBJECT SOFTWARE");
                MainController controller = loader.getController();
                controller.init();
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String msg = Supplier.getSupplierObject().getSupplierName();
        supplierName.setText(supplierName.getText()+" "+msg);
    }
}