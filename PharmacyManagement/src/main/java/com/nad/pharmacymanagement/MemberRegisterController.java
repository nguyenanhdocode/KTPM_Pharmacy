/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nad.pharmacymanagement;

import com.nad.pojo.Member;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import com.nad.services.UserServices;
import com.nad.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author nguye
 */
public class MemberRegisterController implements Initializable {

    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtAddress;
    @FXML
    private AnchorPane anchorPanel;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void registerHandler(ActionEvent event) throws SQLException {
        Window owner = txtFirstName.getScene().getWindow();
        if (Utils.isNullOrEmpty(txtLastName.getText())){
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi", "Vui lòng nhập Họ.");
            return;
        }
        
        if (Utils.isNullOrEmpty(txtFirstName.getText())){
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi", "Vui lòng nhập Tên.");
            return;
        }
        
        if (Utils.isNullOrEmpty(txtPhone.getText())){
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi", "Vui lòng nhập Số điện thoại.");
            return;
        }
        
        if (!txtPhone.getText().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi", "Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            return;
        }
        
        if (Utils.isNullOrEmpty(txtAddress.getText())){
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi", "Vui lòng nhập Địa chỉ");
            return;
        }
        Member member = new Member(0, txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), txtAddress.getText(), 0);
        if (UserServices.MemberRegister(member) > 0) {
            Utils.showAlert(Alert.AlertType.CONFIRMATION, owner, "Thông báo", "Đăng ký thành viên thành công.");
        }
        else {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi", "Có lỗi trong quá trình đăng ký.");
        }
    }
}
