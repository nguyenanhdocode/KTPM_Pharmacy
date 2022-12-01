/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pharmacymanagement;

import com.nad.pojo.User;
import com.nad.services.UserServices;
import com.nad.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 *
 * @author thien thien
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassWord;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private RadioButton rdbNam;
    @FXML
    private RadioButton rdbNu;
    @FXML
    private TextField txtAddress;    
    @FXML
    private PasswordField txtNhapLaiMatKhau;
    @FXML
    private Button submitButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void register(ActionEvent event) throws SQLException, IOException {
        Window owner = submitButton.getScene().getWindow();

        User user = new User();
        
        user.setId(Integer.parseInt(txtID.getText()));
        user.setUserName(txtUserName.getText());
        user.setPassWord(txtPassWord.getText());
        user.setFirstName(txtFirstName.getText());
        user.setLastName(txtLastName.getText());
        user.setGender(chooseGender());
        user.setAddress(txtAddress.getText());
        
        
   
        UserServices u = new UserServices();
        
        
        if (txtUserName.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Nhập tên UserName");
            return;
        }

        if (txtFirstName.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Nhập tên của bạn");
            return;
        }
        
        
        if (txtPassWord.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Nhập mật khẩu");
            return;
        }
          
        if (txtPassWord.getText().equals(txtNhapLaiMatKhau.getText()) ) {
            u.dangKy(user);
            Utils.showAlert(Alert.AlertType.CONFIRMATION, owner, "Đăng ký thành công!",
            "Xin chào " + txtUserName.getText());
            return;
        }else
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Mật khẩu không khớp");
           

    }
    private String chooseGender(){
        if(rdbNam.isSelected()){
            return rdbNam.getText();
        }else
            return rdbNu.getText();
    }
}
