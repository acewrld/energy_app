module com.csym025.vosassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires java.logging;
    requires activation;
    requires mail;


    opens com.csym025.vosassignment to javafx.fxml;
    exports com.csym025.vosassignment;

    exports com.csym025.vosassignment.entity;
    opens com.csym025.vosassignment.entity to javafx.fxml;

    exports com.csym025.vosassignment.controller;
    opens com.csym025.vosassignment.controller to javafx.fxml;
}