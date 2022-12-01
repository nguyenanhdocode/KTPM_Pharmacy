module com.nad.pharmacymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;
    requires java.sql;

    opens com.nad.pharmacymanagement to javafx.fxml;
    exports com.nad.pharmacymanagement;
    exports com.nad.pojo;
}
