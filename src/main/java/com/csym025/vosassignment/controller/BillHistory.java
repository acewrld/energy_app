package com.csym025.vosassignment.controller;

import com.csym025.vosassignment.entity.Bill;
import com.csym025.vosassignment.entity.Customer;

import com.csym025.vosassignment.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BillHistory {

    @FXML
    private AnchorPane billHistoryPane;

    @FXML
    private Text closeRead;

    @FXML
    private Text customerEmail;

    @FXML
    private Text customerMeterNo;

    @FXML
    private Text customerMobileNo;

    @FXML
    private Text customerName;

    @FXML
    private Text grandTotal;

    @FXML
    private Text kwh;

    @FXML
    private Text openRead;

    @FXML
    private Text period;

    @FXML
    private Text rpu;

    @FXML
    private Text supplierName;

    @FXML
    private Text total;

    @FXML
    private Text billType;

    public void initData(Customer customer) {
        ArrayList<Bill> custBills = Utils.loadBillByCustId(customer.getId(), "target/bills.dat");
        customerName.setText(customerName.getText()+" "+customer.getFullName());
        customerEmail.setText(customerEmail.getText()+" "+customer.getEmail());
        customerMobileNo.setText(customerMobileNo.getText()+" "+customer.getMobileNo());
        customerMeterNo.setText(customerMeterNo.getText()+" "+customer.getMeterNo());
        StringBuilder periods = new StringBuilder();
        StringBuilder totals = new StringBuilder();
        StringBuilder rpus = new StringBuilder();
        StringBuilder kwhs = new StringBuilder();
        StringBuilder openReads = new StringBuilder();
        StringBuilder closeReads = new StringBuilder();
        StringBuilder billTypes = new StringBuilder();

        double gTotal = 0.0;
        for(int i = 0; i < custBills.size(); i++) {
            periods.append(custBills.get(i).getPeriod()).append("\n");
            totals.append(custBills.get(i).getTotal()).append("\n");
            rpus.append(custBills.get(i).getRatePerUit()).append("\n");
            kwhs.append(custBills.get(i).getKwh()).append("\n");
            openReads.append(custBills.get(i).getOpenRead()).append("\n");
            closeReads.append(custBills.get(i).getCloseRead()).append("\n");
            billTypes.append(custBills.get(i).getType()).append("\n");

            gTotal += custBills.get(i).getTotal();
        }
        period.setText(periods.toString());
        total.setText(totals.toString());
        rpu.setText(rpus.toString());
        kwh.setText(kwhs.toString());
        openRead.setText(openReads.toString());
        closeRead.setText(closeReads.toString());
        billType.setText(billTypes.toString());
        grandTotal.setText("£"+gTotal);
    }
}

