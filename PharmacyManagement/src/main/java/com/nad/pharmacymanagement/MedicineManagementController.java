/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pharmacymanagement;

import com.nad.pojo.Medicine;
import com.nad.pojo.Unit;
import com.nad.services.MedicineServices;
import com.nad.services.UnitServices;
import com.nad.utils.Utils;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

/**
 *
 * @author thien thien
 */
public class MedicineManagementController implements Initializable {

    @FXML
    private TableView<Medicine> tbMedicine;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtBrandName;
    @FXML
    private TextField txtChemicalName;
    @FXML
    private ComboBox<Unit> cbxUnit;
    @FXML
    private TextField txtUnitInStock;
    @FXML
    private TextField txtUnitPrice;
    @FXML
    private TextField txtAllowedUnitInStock;
    @FXML
    private TextField txtProducingCountry;
    @FXML
    private TextField txtKeywords;
    @FXML
    private ComboBox cbxLoaiTimKiem;
  
    @FXML
    private Button btnLamMoi;
    @FXML
    private Button submitButton;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.refresh();
        } catch (SQLException ex) {
            Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loadComboBoxUnit();
        this.btnLamMoi.setOnAction(e -> {
            try {
                refresh();
            } catch (SQLException ex) {
                Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.loadTableMedicine();
        try {
            this.reloadTableMedicine(null,null);
            
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        } catch (SQLException ex) {
            Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.tbMedicine.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                this.txtId.setText(Integer.toString(tbMedicine.getSelectionModel().getSelectedItem().getId()));
                this.txtBrandName.setText(tbMedicine.getSelectionModel().getSelectedItem().getBrandName());
                this.txtChemicalName.setText(tbMedicine.getSelectionModel().getSelectedItem().getChemicalName());
                int maUnit = tbMedicine.getSelectionModel().getSelectedItem().getUnitId();
                try {
                    Unit unit = UnitServices.getUnitById(maUnit);
                    this.cbxUnit.setValue(unit);
                }
                catch (SQLException e){
                }
                this.txtUnitInStock.setText(Integer.toString(tbMedicine.getSelectionModel().getSelectedItem().getUnitInStock()));
                this.txtUnitPrice.setText(Float.toString(tbMedicine.getSelectionModel().getSelectedItem().getUnitPrice()));
                this.txtAllowedUnitInStock.setText(Integer.toString(tbMedicine.getSelectionModel().getSelectedItem().getAllowedUnitInStock()));
                this.txtProducingCountry.setText(tbMedicine.getSelectionModel().getSelectedItem().getProducingCountry());
               
            }
        });
        this.txtId.setEditable(false);
        this.txtUnitInStock.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtUnitInStock.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        this.txtUnitPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtUnitPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        this.txtAllowedUnitInStock.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtAllowedUnitInStock.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        txtKeywords.setPromptText("Tìm kiếm");
        this.txtKeywords.textProperty().addListener((evt) ->{
            try {
                this.reloadTableMedicine(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
            } catch (SQLException ex) {
                Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.cbxLoaiTimKiem.getItems().addAll("Theo ten thuoc", "Theo hoat chat", "Theo quoc gia san xuat");
        this.cbxLoaiTimKiem.getSelectionModel().select(0);
        this.cbxLoaiTimKiem.valueProperty().addListener((evt) ->{
            try {
                this.reloadTableMedicine(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
            } catch (SQLException ex) {
                Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    private Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private Float parseFloatOrNull(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private void refresh() throws SQLException {
        this.reloadTableMedicine(null, null);
        this.txtId.clear();
        this.txtBrandName.clear();
        this.txtChemicalName.clear();
        this.cbxUnit.setValue(null);
        this.txtUnitInStock.clear();
        this.txtUnitPrice.clear();
        this.txtAllowedUnitInStock.clear();
        this.txtProducingCountry.clear();
        this.cbxLoaiTimKiem.getSelectionModel().select(0);
        this.txtKeywords.clear();

    }
    private void loadComboBoxUnit() {
        UnitServices s = new UnitServices();
        try {
            this.cbxUnit.setItems(FXCollections.observableArrayList(s.getListUnit()));
        } catch (SQLException ex) {
            Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
     
        }
    }
    private void reloadTableMedicine(String kw,Integer loaiTimKiem) throws SQLException {
        MedicineServices s = new MedicineServices();
        try {
            this.tbMedicine.setItems(FXCollections.observableList(s.getListMedicine(kw,loaiTimKiem)));
        } catch (SQLException ex) {
            Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadTableMedicine() {
        TableColumn col1 = new TableColumn("Ma thuoc");
        col1.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn col2 = new TableColumn("Ten thuoc");
        col2.setCellValueFactory(new PropertyValueFactory("brandName"));

        TableColumn col3 = new TableColumn("Ten hoat chat");
        col3.setCellValueFactory(new PropertyValueFactory("chemicalName"));
        
        TableColumn col4 = new TableColumn("Ma don vi");
        col4.setCellValueFactory(new PropertyValueFactory("unitId"));
        
        TableColumn col5 = new TableColumn("So luong nhap");
        col5.setCellValueFactory(new PropertyValueFactory("unitInStock"));
        
        TableColumn col6 = new TableColumn("Gia");
        col6.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        
        TableColumn col7 = new TableColumn("Cho phep toi da");
        col7.setCellValueFactory(new PropertyValueFactory("allowedUnitInStock"));
        
        TableColumn col8 = new TableColumn("Dat nuoc san xuat");
        col8.setCellValueFactory(new PropertyValueFactory("producingCountry"));
        
        TableColumn col9 = new TableColumn("");
        col9.setCellFactory(param -> new TableCell<Medicine, String>() {
            final Button btnXoa = new Button("Xóa");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnXoa.setOnAction(event -> {
                        Alert a = Utils.getBox("Ban co chac muon xoa?", Alert.AlertType.CONFIRMATION);
                        Optional<ButtonType> result = a.showAndWait();
                        if(result.get() == ButtonType.OK) {
                            Medicine medicine = getTableView().getItems().get(getIndex());
                        
                            try {
                                MedicineServices s = new MedicineServices();
                               

                                if(s.delMedicine(medicine.getId()) == true) {
                                    Utils.getBox("Xoa thanh cong!", Alert.AlertType.INFORMATION).show();
                                }
                            }
                            catch (Exception e) {
                                Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                            }

                            getTableView().getItems().remove(medicine);
                           
                        }
                    });
                    setGraphic(btnXoa);
                    setText(null);
                }
            }
        });
        
        this.tbMedicine.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
    }
    
    
    public void addMedicineHandler(ActionEvent evt) throws SQLException {
        
        Medicine medicine = new Medicine();
        medicine.setId(0);
        medicine.setBrandName(txtBrandName.getText());
        medicine.setChemicalName(txtChemicalName.getText());
        medicine.setAllowedUnitInStock(parseIntOrNull(txtAllowedUnitInStock.getText()));
        medicine.setProducingCountry(txtProducingCountry.getText());


        Window owner = submitButton.getScene().getWindow();
        if (txtBrandName.getText().isEmpty()) {
        Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!", "Chưa nhập tên thuốc");
            return;
        }
        if (txtChemicalName.getText().isEmpty()) {
        Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!", "Chưa nhập hoạt chất");
            return;
        }

        if (cbxUnit.getValue() == null) {
        Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!", "Chưa nhập đơn vị tính");
            return;
        }else{
            medicine.setUnitId(cbxUnit.getSelectionModel().getSelectedItem().getId());
        } 
        
        boolean check = false;
        if (!txtAllowedUnitInStock.getText().isEmpty()) {
            boolean flag = true;
            check = true;
            if (parseIntOrNull(txtAllowedUnitInStock.getText()) == 0) {
                Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Phải nhập số lượng nhập tối đa khác 0");
                flag = false;
                return;
            }
            if(flag = true)
                
                medicine.setAllowedUnitInStock(parseIntOrNull(txtAllowedUnitInStock.getText()));
        }else{
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Chưa nhập số lượng tối đa");
        }
        
        if (!txtUnitInStock.getText().isEmpty() && check == true) {
            boolean flag = true;
            if (parseIntOrNull(txtUnitInStock.getText()) == 0) {
                Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Phải nhập số lượng khác 0");
                flag = false;
                return;
            }
            if (parseIntOrNull(txtUnitInStock.getText()) > parseIntOrNull(txtAllowedUnitInStock.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Số lượng thuốc nhập vào phải nhỏ hơn số lượng cho phép");
                flag=false;
                return;
            }
            
            if(flag = true)
                medicine.setUnitInStock(parseIntOrNull(txtUnitInStock.getText()));
            
        }else{
            if(check == false)
                Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Chưa nhập số lượng tối đa");
            else
                Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Chưa nhập số lượng nhập vào kho");
        }
        
        
        if (!txtUnitPrice.getText().isEmpty()) {
            boolean flag = true;
            if (parseIntOrNull(txtUnitPrice.getText()) == 0) {
                Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Phải nhập giá khác 0");
                flag = false;
                return;
            }
            if(flag = true)
                medicine.setUnitPrice(parseFloatOrNull(txtUnitPrice.getText()));
        }else{
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Chưa nhập giá");
        }
        
 
               
        if (txtProducingCountry.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Chưa nhập quốc gia sản xuất");
            return;
        }
           
        MedicineServices s = new MedicineServices();       
        if(s.addMedicine(medicine) == true) {
            Utils.getBox("Them thuoc thanh cong!", Alert.AlertType.INFORMATION).show();
            refresh();
        }
        else {
            Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
        }
    }
    
    
    public void editMedicineHandler(ActionEvent evt) throws SQLException {
        if(this.txtBrandName.getText().isBlank() == false) {
            
        
            Medicine medicine = new Medicine();

            medicine.setId(parseIntOrNull(txtId.getText()));
            medicine.setBrandName(txtBrandName.getText());
            medicine.setChemicalName(txtChemicalName.getText());
            medicine.setUnitId(1);
            if(cbxUnit.getValue() != null)
                medicine.setUnitId(cbxUnit.getSelectionModel().getSelectedItem().getId());
            medicine.setUnitInStock(parseIntOrNull(txtUnitInStock.getText()));
            medicine.setUnitPrice(parseFloatOrNull(txtUnitPrice.getText()));
            medicine.setAllowedUnitInStock(parseIntOrNull(txtAllowedUnitInStock.getText()));
            medicine.setProducingCountry(txtProducingCountry.getText());
           


            MedicineServices s = new MedicineServices();

            if(s.editMedicine(medicine) == true) {
                refresh();
                Utils.getBox("Sua thanh cong!", Alert.AlertType.INFORMATION).show();
            }
            else {
                Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
            }
        }
        
        else {
            Utils.getBox("Ban chua nhap ten thuoc!", Alert.AlertType.WARNING).show();
        }
    }
    
}
