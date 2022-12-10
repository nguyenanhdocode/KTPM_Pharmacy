/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.tester;

import com.nad.pojo.Medicine;
import com.nad.pojo.SellMedicine;
import com.nad.pojo.User;
import com.nad.services.SellMedicineServices;
import com.nad.services.UserServices;
import com.nad.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

/**
 *
 * @author thien thien
 */
public class SellMedicineTest {
     private static Connection conn;
    private static SellMedicineServices service;
    
    @BeforeAll
    public static void beforeAll() {
        service = new SellMedicineServices();
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(SellMedicineTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SellMedicineTest.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    public void sellTest() throws SQLException {
        User user = new User();
        user.setId(1);
        Medicine medicine = new Medicine();
        medicine.setId(1);
        
        SellMedicine m1 = new SellMedicine();
        m1.setDate(new Timestamp(System.currentTimeMillis()));
        m1.setMedicineID(medicine);
        m1.setQuantity(10);
        m1.setUserID(user);
        m1.setUnitPrice(100.0f);
        
        ArrayList<SellMedicine> sellMedicines = new ArrayList<>();
        sellMedicines.add(m1);
        
        Assertions.assertTrue(service.Sell(sellMedicines) == 1);
    }
    
    // Số thuốc trong khoảng số lượng trong kho
    @Test
    public void sellTestUnitInStock() throws SQLException {
       Assertions.assertTrue(service.checkValidUnitInStock(1, 2));
    }
    
    // Số thuốc vượt số lượng tồn kho
    @Test
    public void sellTestUnitInStockError() throws SQLException {
       Assertions.assertFalse(service.checkValidUnitInStock(1, 100));
    }
}
