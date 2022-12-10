/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.tester;

import com.nad.pojo.Member;
import com.nad.services.MedicineServices;
import com.nad.services.UserServices;
import com.nad.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
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
 * @author nguye
 */
public class MemberRegisterTest {
    private static Connection conn;
    private static UserServices service;
    
    @BeforeAll
    public static void beforeAll() {
        service = new UserServices();
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(MemberRegisterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(MemberRegisterTest.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    public void testReigister() throws SQLException {
        Member member = new Member();
        
        member.setFirstName("Nguyen");
        member.setLastName("Do");
        member.setPhone("0398796231");
        member.setAddress("Go Vap");
        
        Assertions.assertTrue(UserServices.MemberRegister(member) == 1);
    }
    
    @Test
    public void testCheckInvalid01() {
        Member member = new Member();
        
        member.setFirstName("");
        member.setLastName("");
        member.setPhone("0398796231");
        member.setAddress("Go Vap");
        
        Assertions.assertTrue(UserServices.CheckInValid(member) != 0);
    }
    
    @Test
    public void testCheckInvalid02() {
        Member member = new Member();
        
        member.setFirstName("Nguyen");
        member.setLastName("Do");
        member.setPhone("0398796231343");
        member.setAddress("Go Vap");
        
        Assertions.assertTrue(UserServices.CheckInValid(member) != 0);
    }
    
    @Test
    public void testCheckInvalid03() {
        Member member = new Member();
        
        member.setFirstName("Nguyen");
        member.setLastName("Do");
        member.setPhone("0398796231343");
        member.setAddress("");
        
        Assertions.assertTrue(UserServices.CheckInValid(member) != 0);
    }
}
