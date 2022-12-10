/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pharmacymanagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author thien thien
 */
public class MenuController implements Initializable {
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void quanLyThuocHandler(ActionEvent evt) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLMedicineManagement.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Quan Ly Thuoc");
        stage.show();
        
    }
    public void banThuocHandler(ActionEvent evt) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLSellMedicine.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Ban Thuoc");
        stage.show();
        
    }
    public void thongKeThuocHandler(ActionEvent evt) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLSellMedicineStat.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Thong ke Thuoc");
        stage.show();
        
    }
    
    public void registerMemberHandler(ActionEvent evt) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLMemberRegister.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Đăng ký thành viên");
        stage.show();
        
    }
}
