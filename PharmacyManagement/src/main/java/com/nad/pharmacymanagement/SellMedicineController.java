/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pharmacymanagement;

import com.nad.pojo.Medicine;
import com.nad.pojo.SellMedicine;
import com.nad.pojo.Unit;
import com.nad.pojo.User;
import com.nad.services.MedicineServices;
import com.nad.services.SellMedicineServices;
import com.nad.services.UnitServices;
import com.nad.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author thien thien
 */
public class SellMedicineController implements Initializable {
    @FXML
    private TableView<Medicine> tbMedicine;
    @FXML
    private TableView<SellMedicine> tbSellMedicine;
    @FXML
    private TextField txtIDMedicine;
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
    private Button btnLamMoi;
   
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableMedicine();
        this.loadComboBoxUnit();
        this.reloadTableMedicine(null);
        this.loadTableSellMedicine();
        this.btnLamMoi.setOnAction(e -> {
            try {
                refresh();
            } catch (SQLException ex) {
                Logger.getLogger(SellMedicineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    this.tbMedicine.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                
                this.txtIDMedicine.setText(Integer.toString(tbMedicine.getSelectionModel().getSelectedItem().getId()));
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
    }
     private void refresh() throws SQLException {
        this.reloadTableMedicine(null);
        this.txtIDMedicine.clear();
        this.txtBrandName.clear();
        this.txtChemicalName.clear();
        this.cbxUnit.setValue(null);
        this.txtUnitInStock.clear();
        this.txtUnitPrice.clear();
        this.txtAllowedUnitInStock.clear();
        this.txtProducingCountry.clear();
     }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private int getRowIndex(TableView<SellMedicine> table, SellMedicine sellMedicine) {
        for(int i = 0; i < table.getItems().size(); i++) {
            if (sellMedicine.getMedicineID().getId().equals( table.getItems().get(i).getMedicineID().getId())) {
                return i;
            }
        }
        return -1;
    }
    private void reloadTableMedicine(String kw) {
        MedicineServices s = new MedicineServices();
        try {
            this.tbMedicine.setItems(FXCollections.observableList(s.getListMedicine(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(MedicineManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadComboBoxUnit() {
        UnitServices s = new UnitServices();
        try {
            this.cbxUnit.setItems(FXCollections.observableArrayList(s.getListUnit()));
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
        
        TableColumn col5 = new TableColumn("Ton kho");
        col5.setCellValueFactory(new PropertyValueFactory("unitInStock"));
        
        TableColumn col6 = new TableColumn("Gia");
        col6.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        
        TableColumn col7 = new TableColumn("Cho phep toi da");
        col7.setCellValueFactory(new PropertyValueFactory("allowedUnitInStock"));
        
        TableColumn col8 = new TableColumn("Dat nuoc san xuat");
        col8.setCellValueFactory(new PropertyValueFactory("producingCountry"));
        
        TableColumn col9 = new TableColumn("");
        col9.setCellFactory(param -> new TableCell<Medicine, String>() {
            final Button btnChonThuoc = new Button("Chọn thuốc");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnChonThuoc.setOnAction(event -> {
                        
                        Medicine medicine = getTableView().getItems().get(getIndex());
                        
                        SellMedicine sellMedicine = new SellMedicine();
                        sellMedicine.setMedicineID(medicine);
                        sellMedicine.setUserID(user);
                        sellMedicine.setQuantity(1);

                        int rowIndex = getRowIndex(tbSellMedicine, sellMedicine);
                        
                        if (rowIndex == -1){
                            tbSellMedicine.getItems().add(sellMedicine);
                        }
                        else {
                            SellMedicine currentRow = tbSellMedicine.getItems().get(rowIndex);
                            currentRow.setQuantity(currentRow.getQuantity()+1);
                            tbSellMedicine.refresh();
                        }
                    });
                    setGraphic(btnChonThuoc);
                    setText(null);
                }
            }
        });
        
        this.tbMedicine.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
    }
    
    private void loadTableSellMedicine() { 
        TableColumn col1 = new TableColumn("Tên thuoc");
        col1.setCellValueFactory(new PropertyValueFactory("medicineID"));

//        TableColumn col2 = new TableColumn("Ten thuoc");
//        col2.setCellValueFactory(new PropertyValueFactory("brandName"));

        TableColumn col3 = new TableColumn("So luong");
        col3.setCellValueFactory(new PropertyValueFactory("quantity"));
        
        
        TableColumn col4 = new TableColumn("");
        col4.setCellFactory(param -> new TableCell<SellMedicine, String>() {
            final Button btnXoa = new Button("Xóa");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnXoa.setOnAction(event -> {
                        SellMedicine sellMedicine = getTableView().getItems().get(getIndex());
                        getTableView().getItems().remove(sellMedicine);
                    });
                    setGraphic(btnXoa);
                    setText(null);
                }
            }
        });
        
        this.tbSellMedicine.getColumns().addAll(col1, col3, col4);
    }
    
     public void SellMedicineHander(ActionEvent evt) throws SQLException {
        Alert a = Utils.getBox("Ban co chac muon mua thuoc?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = a.showAndWait();
        
        if(result.get() == ButtonType.OK) {
            ArrayList<SellMedicine> dsSellMedicines = new ArrayList(tbSellMedicine.getItems());
        
            SellMedicineServices s = new SellMedicineServices();

            switch(s.Sell(dsSellMedicines)) {
                
                case -2:
                    Utils.getBox("Ban chua chon thuoc nao!", Alert.AlertType.WARNING).show();
                    break;
                case 1:
                    Utils.getBox("Ban thuoc thanh cong!", Alert.AlertType.INFORMATION).show();
                    tbSellMedicine.getItems().clear();
                    break;
                default:
                    Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                    break;
            }
        }
    }
}
