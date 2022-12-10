/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.tester;

import com.nad.pojo.User;
import com.nad.services.UserServices;
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

/**
 *
 * @author thien thien
 */
public class RegisterTest {
    private static Connection conn;
    private static UserServices s;
    
    @BeforeAll
    public static void beforeAll() {
        s = new UserServices();
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserTest.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    @Order(1)
    public void testRegister() throws SQLException {
        User user = new User();
        
        user.setId(0);
        user.setUserName("nguyenanhdo2");
        user.setPassWord("c4ca4238a0b923820dcc509a6f75849b");
        user.setFirstName("hai");
        user.setLastName("Phan");
        user.setGender("Nữ");
        user.setAddress("hcm");
        
        
        Assertions.assertTrue(s.dangKy(user));
        
//        User newUser = UserServices.getUserByUserName(user.getUserName());
//        Assertions.assertNotNull(newUser);
//        Assertions.assertNotEquals(user.getUserName(), newUser.getUserName());
    }  
    @Test
    @Order(2)
    public void testRegisterErr() throws SQLException {
        User user = new User();
        
        user.setId(0);
        user.setUserName("haifsdfdfdf");
        user.setPassWord("1");
        user.setFirstName("hai");
        user.setLastName("Phan");
        user.setGender("Nữ");
        user.setAddress("hcm");
        
        
        Assertions.assertFalse(s.dangKy(user));
        
        User newUser = UserServices.getUserByUserName(user.getUserName());
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user.getUserName(), newUser.getUserName());
    } 
    
    @Test
    public void testCheckUserNameExist() throws SQLException {
        Assertions.assertTrue(UserServices.checkUsernameExist("nn"));
    }
}
