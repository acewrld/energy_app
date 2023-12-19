package com.csym025.vosassignment.controller;

import com.csym025.vosassignment.entity.Bill;
import com.csym025.vosassignment.entity.Customer;
import com.csym025.vosassignment.entity.Supplier;
import com.csym025.vosassignment.util.Utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import com.csym025.vosassignment.entity.Bill;
import com.csym025.vosassignment.entity.Customer;
import com.csym025.vosassignment.entity.Supplier;
import com.csym025.vosassignment.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class GenerateGasBill {

    @FXML
    private TextField closingRead;

    @FXML
    private Text customerEmail;

    @FXML
    private Text customerMeterNo;

    @FXML
    private Text customerMobileNo;

    @FXML
    private Text customerName;

    @FXML
    private AnchorPane customersPane;

    @FXML
    private Text kwh;

    @FXML
    private TextField openingRead;

    @FXML
    private Text period;

    @FXML
    private Text ratePerUnit;

    @FXML
    private Button sentByEmail;

    @FXML
    private Text supplierName;

    @FXML
    private Text total;

    private Customer customer;

    private Bill bill;

    @FXML
    void generateInvoice(MouseEvent event) {
        payBill();

        Document document = new Document();
        LocalDate localDate = LocalDate.now();
        String month = localDate.getMonth().name();

        try {
            File pdfBillName = new File("target/", customer.getFullName()+"-"+month+"-bill"+".pdf");
            pdfBillName.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(pdfBillName);
            PdfWriter.getInstance(document, fileOutputStream);

            document.open();

            Font font = new Font();
            font.setStyle(Font.BOLD);
            font.setSize(8);

            Paragraph p = new Paragraph();
            p.add("########## "+month+" BILL SUMMARY ##########");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            System.out.println("");

            document.add(new Paragraph("Customer FullName:     "+ customer.getFullName()));
            document.add(new Paragraph("Customer Email:           "+ customer.getEmail()));
            document.add(new Paragraph("Customer Mobile No:    "+ customer.getMobileNo()));
            document.add(new Paragraph("Customer Postal Code: "+ customer.getPostCode()));
            document.add(new Paragraph("Customer Meter No:      "+ customer.getMeterNo()));
            document.add(new Paragraph("Bill Period:                     "+ bill.getPeriod()));
            document.add(new Paragraph("Bill Generation Date:     "+ localDate));
            System.out.println("");
            System.out.println("");

            Paragraph p1 = new Paragraph();
            p1.add("########## "+month+" BILL DETAILS ##########");
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            System.out.println("");
            System.out.println("");

            document.add(new Paragraph("No of Units Consumed       Rate Per Unit       Opening Read       Closing Read       Total"));
            document.add(new Paragraph(     bill.getKwh()+"                                          "+bill.getRatePerUit()+"                       "+bill.getOpenRead()+"                           "+bill.getCloseRead()+"                       "+bill.getTotal()));

            document.close();

            System.out.println("Done");

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    private void payBill() {
        ArrayList<Bill> bills = Utils.loadBills("target/bills.dat");
        bills.add(bill);
        Utils.createBills(bills, "target/bills.dat");
    }

    @FXML
    void sentByEmail(MouseEvent event) {
    }

    public void initData(Customer c) {
        customer = c;
        customerName.setText(customerName.getText()+" "+c.getFullName());
        customerEmail.setText(customerEmail.getText()+" "+c.getEmail());
        customerMobileNo.setText(customerMobileNo.getText()+" "+c.getMobileNo());
        customerMeterNo.setText(customerMeterNo.getText()+" "+c.getMeterNo());
        openingRead.setText("0");
        closingRead.setText("0");
        ratePerUnit.setText(ratePerUnit.getText()+" £0.20");

        openingRead.textProperty().addListener((observable, oldValue, newValue) -> {
            bill = calculateBill(newValue, "openingRead");
        });
    }
    public Bill calculateBill(String newValue, String field) {
        System.out.println(newValue+"  "+field);
        int opening = 0;
        int closing = 0;
        if(field.equals("openingRead")) {
            closing = Integer.parseInt(closingRead.getText());
            opening = Integer.parseInt(newValue);
        }
        else {
            closing = Integer.parseInt(newValue);
            opening = Integer.parseInt(closingRead.getText());
        }

        int read = 0;
        double totalBill = 0;
        LocalDate localDate = LocalDate.now();
        String firstDate = localDate.withDayOfMonth(1).toString();
        String lastDate = localDate.withDayOfMonth(localDate.lengthOfMonth()).toString();
        period.setText(firstDate+" - "+lastDate);

        if(opening > closing) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("opening read should be less than or equal to closing read");
            alert.show();
            openingRead.setText("0");
            closingRead.setText("0");
        } else {
            read = closing - opening;
            kwh.setText(""+read);
            totalBill = read * 0.20;
            total.setText("£"+totalBill);
        }

        return new Bill("Gas", customer.getId(), customer.getFullName(), firstDate+" - "+lastDate, opening, closing, 0.20, read, totalBill);
    }


}
