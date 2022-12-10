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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
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
    private TextField txtKeywords;
    @FXML
    private ComboBox cbxLoaiTimKiem;
    @FXML
    private TableView<SellMedicine> tbChuaThanhToan;
    @FXML
    private TableView<SellMedicine> tbDaThanhToan;
    @FXML
    private Button btnLamMoi;
   
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableMedicine();
        this.loadComboBoxUnit();
        this.reloadTableMedicine(null,null);
        this.loadTableSellMedicine();
        this.bangChuaThanhToan();
        this.loadColumnsChuaThanhToan();
        
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
    txtKeywords.setPromptText("Tìm kiếm");
    this.txtKeywords.textProperty().addListener((evt) ->{
        this.reloadTableMedicine(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
    });

    this.cbxLoaiTimKiem.getItems().addAll("Theo ten thuoc", "Theo hoat chat", "Theo quoc gia san xuat");
    this.cbxLoaiTimKiem.getSelectionModel().select(0);
    this.cbxLoaiTimKiem.valueProperty().addListener((evt) ->{
        this.reloadTableMedicine(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
    });
    
    this.txtIDMedicine.setEditable(false);
        this.txtBrandName.setEditable(false);
        this.txtChemicalName.setEditable(false);
        this.cbxUnit.setEditable(false);
        this.txtUnitInStock.setEditable(false);
        this.txtUnitPrice.setEditable(false);
        this.txtAllowedUnitInStock.setEditable(false);
        this.txtProducingCountry.setEditable(false);
    }
    
     private void refresh() throws SQLException {
        this.reloadTableMedicine(null, null);
        this.txtIDMedicine.clear();
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
    private void reloadTableMedicine(String kw, Integer loaiTimKiem) {
        MedicineServices s = new MedicineServices();
        try {
            this.tbMedicine.setItems(FXCollections.observableList(s.getListMedicine(kw,loaiTimKiem)));
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
        TableColumn col1 = new TableColumn("Mã thuốc");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setMinWidth(50);
        

        TableColumn col2 = new TableColumn("Tên thuốc");
        col2.setCellValueFactory(new PropertyValueFactory("brandName"));
        col2.setMinWidth(200);

        TableColumn col3 = new TableColumn("Tên hoạt chất");
        col3.setCellValueFactory(new PropertyValueFactory("chemicalName"));
        col3.setMinWidth(200);
        
        TableColumn col4 = new TableColumn("Giá");
        col4.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        col4.setMinWidth(100);
        
        TableColumn col5 = new TableColumn("");
        col5.setCellFactory(param -> new TableCell<Medicine, String>() {
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
        
        this.tbMedicine.getColumns().addAll(col1, col2, col3, col4, col5);
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
     private void bangChuaThanhToan() {
        SellMedicineServices s = new SellMedicineServices();
        try {
            this.tbChuaThanhToan.setItems(FXCollections.observableList(s.getListSellMedicine()));
        } catch (SQLException ex) {
            Logger.getLogger(SellMedicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     private void loadColumnsChuaThanhToan() {
        TableColumn col1 = new TableColumn("Ma thuoc");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn col2 = new TableColumn("Ten thuoc");
        col2.setCellValueFactory(new PropertyValueFactory("brandName"));
        
        TableColumn col3 = new TableColumn("Ma user");
        col3.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn col4 = new TableColumn("Ten user");
        col4.setCellValueFactory(new PropertyValueFactory("userName"));
        
        TableColumn col5 = new TableColumn("Ngay mua");
        col5.setCellValueFactory(new PropertyValueFactory("date"));
        
        TableColumn col6 = new TableColumn("So luong");
        col6.setCellValueFactory(new PropertyValueFactory("quantity"));
        
        TableColumn col7 = new TableColumn("Gia");
        col6.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        
        TableColumn col8 = new TableColumn("");
        col8.setCellFactory(param -> new TableCell<SellMedicine, String>() {
            final Button btnThanhToan = new Button("Thanh toan");
            
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } 
                else {
                    btnThanhToan.setOnAction(event -> {
                        SellMedicine sellMedicine = getTableView().getItems().get(getIndex());
                        SellMedicineServices s = new SellMedicineServices();
                        
//                        try {
//                            sellMedicine.setDate(Timestamp.valueOf(LocalDateTime.now()));
//                            if(s.thanhToan(sellMedicine) == true) {
//                                Utils.getBox("Thanh toan thanh cong!", Alert.AlertType.INFORMATION).show();
//                                tbDaThanhToan.getItems().add(sellMedicine);
//                                getTableView().getItems().remove(sellMedicine);
//                            }
//                            else {
//                                Utils.getBox("Thanh toan that bai!", Alert.AlertType.WARNING).show();
//                            }
//                        }
//                        catch(Exception e) {
//                            Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
//                        }
                    });
                    setGraphic(btnThanhToan);
                    setText(null);
                }
            }
        });

        this.tbChuaThanhToan.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);
    }
    
}
