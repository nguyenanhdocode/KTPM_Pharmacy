module com.nad.pharmacymanagement {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nad.pharmacymanagement to javafx.fxml;
    exports com.nad.pharmacymanagement;
}
