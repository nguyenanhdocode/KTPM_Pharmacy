/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pojo.SellMedicine;
import com.nad.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author thien thien
 */
public class SellMedicineServices {
    public int Sell(ArrayList<SellMedicine> dsSellMedicines) throws SQLException {
        
        if(dsSellMedicines.isEmpty()) {
            return -2;
        }
        else {
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
    }
}
