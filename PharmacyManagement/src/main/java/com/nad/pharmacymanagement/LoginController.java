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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author thien thien
 */
public class LoginController implements Initializable{
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassWord;
    @FXML
    private Button btnDangNhap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public void dangNhapHandler(ActionEvent evt) throws IOException, SQLException {
        Window owner = btnDangNhap.getScene().getWindow();

        User user = new User();
        user.setUserName(txtUserName.getText());
        user.setPassWord(txtPassWord.getText());


        if (txtUserName.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Chưa nhập UserName");
            return;
        }

        if (txtPassWord.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Chưa nhập mật khẩu");
            return;
        }

        UserServices us = new UserServices();
        User newUser = us.dangNhap(user);

        if(newUser != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLMenu.fxml"));
            Parent root = fxmlLoader.load();

            MenuController sceneMenu = fxmlLoader.getController();
            
          

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();
        }

        else {
            Utils.getBox("Thong tin dang nhap khong hop le!", Alert.AlertType.WARNING).show();
        }
    }
    public void dangKyHandler(ActionEvent evt) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLRegister.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Đăng ký");
        stage.show();
    }
    
}
