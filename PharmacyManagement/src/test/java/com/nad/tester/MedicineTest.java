/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.tester;

import com.nad.pojo.Medicine;
import com.nad.services.MedicineServices;
import com.nad.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class MedicineTest {
    private static Connection conn;
    private static MedicineServices s;
    
    @BeforeAll
    public static void beforeAll() {
        s = new MedicineServices();
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
    @Order(1)
    public void testAddMedicine() throws SQLException {
        Medicine medicine = new Medicine();
        medicine.setId(0);
        medicine.setBrandName("nguyenanhdo");
        medicine.setChemicalName("jjjjka");
        medicine.setUnitId(1);
        medicine.setUnitInStock(8);
        medicine.setUnitPrice(Float.parseFloat("8"));
        medicine.setAllowedUnitInStock(10);
        medicine.setProducingCountry("vn");
        
        Assertions.assertTrue(s.addMedicine(medicine));
        
//        Medicine newMedicine = MedicineServices.getMedicineById(medicine.getId());
//        Assertions.assertNotNull(newMedicine);

    }
    @Test
    @Order(2)
    public void testDelMedicine() throws SQLException {
        int maMedicine = 29;
        
        Assertions.assertTrue(s.delMedicine(maMedicine));
    }
    @Test
    @Order(3)
    public void testDelMedicineErr() throws SQLException {
        
        Assertions.assertFalse(s.delMedicine(30));
    }
    @Test
    @Order(4)
    public void testEditMedicine() throws SQLException {
        Medicine medicine = new Medicine();
        medicine.setId(27);
        medicine.setBrandName("cam999");
        medicine.setChemicalName("jjjjka");
        medicine.setUnitId(2);
        medicine.setUnitInStock(8);
        medicine.setUnitPrice(8);
        medicine.setAllowedUnitInStock(10);
        medicine.setProducingCountry("vn");
        
        Assertions.assertTrue(s.editMedicine(medicine));
        
        Medicine newMedicine = MedicineServices.getMedicineById(medicine.getId());
        Assertions.assertNotNull(newMedicine);
        Assertions.assertEquals(medicine.getId(), newMedicine.getId());
        Assertions.assertEquals(medicine.getUnitId(), newMedicine.getUnitId());
    }
    
    @Test
    @Order(5)
    public void testEditMedicineError01() throws SQLException {
        Medicine medicine = new Medicine();
        medicine.setId(100);
        medicine.setBrandName("cam999");
        medicine.setChemicalName("jjjjka");
        medicine.setUnitId(2);
        medicine.setUnitInStock(8);
        medicine.setUnitPrice(8);
        medicine.setAllowedUnitInStock(10);
        medicine.setProducingCountry("vn");
        
        Assertions.assertFalse(s.editMedicine(medicine));
        
        //Medicine newMedicine = MedicineServices.getMedicineById(medicine.getId());
//        Assertions.assertNotNull(newMedicine);
//        Assertions.assertEquals(medicine.getId(), newMedicine.getId());
//        Assertions.assertEquals(medicine.getUnitId(), newMedicine.getUnitId());
    }
}
