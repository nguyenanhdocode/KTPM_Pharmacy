/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pharmacymanagement.MedicineManagementController;
import com.nad.pojo.Medicine;
import com.nad.pojo.SellMedicine;
import com.nad.utils.JdbcUtils;
import com.nad.utils.Utils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author thien thien
 */
public class MedicineServices {
    public List<Medicine> getListMedicine(String kw, Integer loaiTimKiem) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm;
            String sql = "SELECT * FROM `medicines` ";
            // OR  OR 
            if (kw == null)
                kw = "";

            if(loaiTimKiem != null) {
                switch(loaiTimKiem) {
                    case 0:
                        sql += "WHERE BrandName like concat('%',?,'%')";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addListMedicine(stm);
                    case 1:
                        sql += "WHERE ChemicalName like concat('%',?,'%')";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addListMedicine(stm);
                    case 2:
                        sql += "WHERE ProducingCountry like concat('%',?,'%')";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addListMedicine(stm);
                    default:
                        break;
                }
            }

            stm = conn.prepareStatement(sql);
            
            return addListMedicine(stm);
        }
    }
    private List<Medicine> addListMedicine(PreparedStatement stm) throws SQLException {
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
        if( maMedicine != null ) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("DELETE FROM `medicines` WHERE ID = ?");

                stm.setInt(1, maMedicine);
                
                
                int rowAffected = stm.executeUpdate();
                conn.commit();

                return rowAffected != 0;
            }
        }
        return false;
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
                                + "`ProducingCountry` = ? "
                                + "WHERE `ID` = ?");

                stm.setString(1, medicine.getBrandName());
                stm.setString(2, medicine.getChemicalName());
                stm.setInt(3, medicine.getUnitId());
                stm.setInt(4, medicine.getUnitInStock());
                stm.setFloat(5, medicine.getUnitPrice());
                stm.setInt(6, medicine.getAllowedUnitInStock());
                stm.setString(7, medicine.getProducingCountry());
                stm.setInt(8, medicine.getId());
                
                
                
                int affectedRow = stm.executeUpdate();
                conn.commit();
                return affectedRow != 0;
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
                    //Utils.getBox("Trùng tên thuốc!", Alert.AlertType.INFORMATION).show();
                    return true;
                }

                return false;
            }
        }
        return false;
    }
    public static boolean checkMedicineExistById(Integer maThuoc) throws SQLException {
        if (maThuoc != null) {
            try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT ID FROM medicines WHERE ID = ?");

                stm.setInt(1, maThuoc);
                ResultSet rs = stm.executeQuery();

                if (rs.isBeforeFirst()) {
                    Utils.getBox("Trùng mã thuốc!", Alert.AlertType.INFORMATION).show();
                    return true;
                }

                return false;
            }
        }
        return false;
    }
    public static Medicine getMedicineById(int maThuoc) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `medicines` WHERE ID = ?");
            stm.setInt(1, maThuoc);
            ResultSet rs = stm.executeQuery();

            Medicine medicine = null;
            while (rs.next()) {
                medicine = new Medicine();

                medicine.setId(rs.getInt("ID"));
                medicine.setBrandName(rs.getString("BrandName"));
                medicine.setChemicalName(rs.getString("ChemicalName"));
                medicine.setUnitId(rs.getInt("UnitID"));
                medicine.setUnitInStock(rs.getInt("UnitInStock"));
                medicine.setUnitPrice(rs.getFloat("UnitPrice"));
                medicine.setAllowedUnitInStock(rs.getInt("AllowedUnitInStock"));
                medicine.setProducingCountry(rs.getString("ProducingCountry"));
                
                break;
            }

            return medicine;
        }
    }
}
