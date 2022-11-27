module java.clothshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires mysql.connector.java;


    opens main to javafx.fxml;
    exports main;
    exports Controller;
    opens Controller to javafx.fxml;
}