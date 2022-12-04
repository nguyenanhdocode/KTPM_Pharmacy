/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pojo.Medicine;
import com.nad.utils.JdbcUtils;
import com.nad.utils.Utils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

/**
 *
 * @author thien thien
 */
public class MedicineServices {
    public List<Medicine> getListMedicine(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `medicines` "
                    + "WHERE (BrandName like concat('%',?,'%')) OR (ChemicalName like concat('%',?,'%'))");
            if (kw == null)
                kw = "";
            
            stm.setString(1, kw);
            stm.setString(2, kw);

           
            ResultSet rs = stm.executeQuery();
            
            List<Medicine> listMedicine = new ArrayList<>();
            while (rs.next()) {                
                
                Integer id = rs.getInt("ID");
                String brandName = rs.getString("BrandName") ;
                String chemicalName = rs.getString("ChemicalName");
                Integer unitId = rs.getInt("UnitID");
                Integer unitInStock = rs.getInt("UnitInStock");
                Float unitPrice = rs.getFloat("UnitPrice") ;
                Integer allowedUnitInStock = rs.getInt("AllowedUnitInStock");
                String producingCountry = rs.getString("ProducingCountry");
                
                
                
                
                listMedicine.add(new Medicine(id, brandName, chemicalName, unitId, unitInStock, unitPrice, allowedUnitInStock, producingCountry));
            }
            
            return listMedicine;
        }
    }
    
    public boolean addMedicine(Medicine medicine) throws SQLException {
        if(medicine.getId()!= null && checkMedicineExist(medicine.getBrandName()) == false ){
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);

                PreparedStatement stm = conn.prepareStatement("INSERT INTO `medicines` "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                stm.setInt(1, medicine.getId());
                stm.setString(2, medicine.getBrandName());
                stm.setString(3, medicine.getChemicalName());
                stm.setInt(4, medicine.getUnitId());
                stm.setInt(5, medicine.getUnitInStock());
                stm.setFloat(6, medicine.getUnitPrice());
                stm.setInt(7, medicine.getAllowedUnitInStock());
                stm.setString(8, medicine.getProducingCountry());
                

                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        return false;
    }
    public boolean delMedicine(Integer maMedicine) throws SQLException {
        if(maMedicine != null ) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("DELETE FROM `medicines` WHERE ID = ?");

                stm.setInt(1, maMedicine);
                
                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        else {
            return false;
        }
    }
    
    public boolean editMedicine(Medicine medicine) throws SQLException {
        if(medicine.getId() != 0) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("UPDATE `medicines` "
                                + "SET `BrandName` = ?, "
                                + "`ChemicalName` = ?, "
                                + "`UnitID` = ?, "
                                + "`UnitInStock` = ?, "
                                + "`UnitPrice` = ?, "
                                + "`AllowedUnitInStock` = ?, "
                                + "`ProducingCountry` = ?, "
                                + "WHERE ID = ?");

                stm.setString(1, medicine.getBrandName());
                stm.setString(2, medicine.getChemicalName());
                stm.setInt(3, medicine.getUnitId());
                stm.setInt(4, medicine.getUnitInStock());
                stm.setFloat(5, medicine.getUnitPrice());
                stm.setInt(6, medicine.getAllowedUnitInStock());
                stm.setString(7, medicine.getProducingCountry());
                stm.setInt(8, medicine.getId());
                
                
                
                stm.executeUpdate();
                conn.commit();

                return true;
             }
        }
        return false;
    }
    
    public static boolean checkMedicineExist(String nameMedicine) throws SQLException {
        if (nameMedicine.isBlank() == false) {
            try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT BrandName FROM medicines WHERE BrandName = ?");

                stm.setString(1, nameMedicine);
                ResultSet rs = stm.executeQuery();

                if (rs.isBeforeFirst()) {
                    Utils.getBox("Trùng tên thuốc!", Alert.AlertType.INFORMATION).show();
                    return true;
                }

                return false;
            }
        }
        return false;
    }
}
