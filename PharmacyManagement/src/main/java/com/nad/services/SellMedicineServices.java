/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pojo.Medicine;
import com.nad.pojo.SellMedicine;
import com.nad.pojo.Stat;
import com.nad.pojo.User;
import com.nad.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thien thien
 */
public class SellMedicineServices {
    public int Sell(ArrayList<SellMedicine> dsSellMedicines) throws SQLException {
        
        if(dsSellMedicines.isEmpty()) {
            return -2;
        }
       
        int tongSoLuong = 0;

        for(SellMedicine sellMedicine: dsSellMedicines) {
            tongSoLuong += sellMedicine.getQuantity();
        }

        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm;

            for(SellMedicine sellMedicine: dsSellMedicines) {
                stm = conn.prepareStatement(
                    "INSERT INTO `sell_medicines`(`MedicineID`, `UserID`, `Date`, `Quantity`) "
                    + "VALUES (?, ?, ?, ?)");

                stm.setInt(1, sellMedicine.getMedicineID().getId());
                stm.setInt(2, 1);
                stm.setInt(4, sellMedicine.getQuantity());
                if(sellMedicine.getDate() != null)
                    stm.setTimestamp(3, sellMedicine.getDate());
                else 
                    stm.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

                stm.executeUpdate();
            }

            conn.commit();

            return 1;
        }
    }
    public Stat sumSellByYear(Integer nam) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Stat stat = null;
            
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT SUM(`Quantity`) AS 'tong_so_luong', "
                            + "EXTRACT(YEAR FROM `Date`) AS 'year' "
                            + "FROM `sell_medicines` "
                            + "WHERE EXTRACT(YEAR FROM `Date`) = ? ");
            
            stm.setInt(1, nam);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                stat = new Stat();
                stat.setTongSoLuong(rs.getInt("tong_so_luong"));
                stat.setNam(rs.getInt("year"));
            }
            return stat;
        }
    }
    public List<SellMedicine> getListSellMedicine() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * "
                    + "FROM `sell_medicines` "
                    + "WHERE `Date` IS NULL ");
            
            ResultSet rs = stm.executeQuery();
            
            List<SellMedicine> listSellMedicine = new ArrayList<>();
            while (rs.next()) {  
                Medicine medicineID = MedicineServices.getMedicineById(rs.getInt("ID"));
                User userID = UserServices.getUserById(rs.getInt("ID"));
                Integer quantity = rs.getInt("Quantity");
                Timestamp date = rs.getTimestamp("Date");
                Integer unitPrice = rs.getInt("UnitPrice");
                
                listSellMedicine.add(new SellMedicine(medicineID, userID, date, unitPrice, quantity));
            }
            
            return listSellMedicine;
        }
    }
    public boolean thanhToan(SellMedicine sellMedicine) throws SQLException {
        
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);

            PreparedStatement stm = conn.prepareStatement(
                    "UPDATE `sell_medicines` "
                            + "SET `UnitPrice`= ? "
                            + "WHERE `MedicineID` = ?"
                            + "AND `UserID` = ?"
                            );


            stm.setFloat(1, sellMedicine.getUnitPrice());
            stm.setInt(2, sellMedicine.getMedicineID().getId());
            stm.setInt(3, sellMedicine.getUserID().getId());
            

            stm.executeUpdate();
            conn.commit();

            return true;
        }
    }
    
    public int getUnitInStock(int medicineID) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement statement = conn.prepareCall("SELECT m.UnitInStock FROM pharmacydb.medicines m WHERE m.ID = ?");
            statement.setInt(1, medicineID);
            
            ResultSet result = statement.executeQuery();
            
            while (result.next()) {
                return result.getInt("UnitInStock");
            }
            return -1;
        }
    }
    
    public boolean checkValidUnitInStock(int medicineID, int quantity) throws SQLException {
        return quantity <= getUnitInStock(medicineID);
    }
}
