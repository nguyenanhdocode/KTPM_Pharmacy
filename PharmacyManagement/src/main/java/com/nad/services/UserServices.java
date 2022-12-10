/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pojo.Member;
import com.nad.pojo.User;
import com.nad.utils.JdbcUtils;
import com.nad.utils.Utils;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import com.nad.utils.Utils;

/**
 *
 * @author thien thien
 */
public class UserServices {
    
    public static boolean checkUsernameExist(String username) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT `Username` FROM `users` WHERE `Username` = ?");
            
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            
            if (rs.isBeforeFirst()) {
                //Utils.getBox("Trùng tên tài khoản!", Alert.AlertType.INFORMATION).show();
                return true;
            }
            return false;
        }
    }
    
    public boolean dangKy(User user) throws SQLException {
        if(checkUsernameExist(user.getUserName()) == false){
            try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `users` "
                    + "(ID, Username, Password, FirstName,LastName, Gender,Address)"
                      + "VALUES(?, ?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, getMd5(user.getPassWord()));
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getAddress());
            
            preparedStatement.executeUpdate();
            //Utils.getBox("Đăng ký tài khoản thành công!", Alert.AlertType.INFORMATION).show();
            return true;
        }
        }else{
            //Utils.getBox("Đăng ký tài khoản thất bại!", Alert.AlertType.INFORMATION).show();
            return false;
        }
    }
    
    public static String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
  
            byte[] messageDigest = md.digest(input.getBytes());
 
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
   
    
    public User dangNhap(User user) throws SQLException, IOException{
        try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT * FROM `users`"
                        + " WHERE `Username` = ? and `Password` = ? ");

                stm.setString(1, user.getUserName());
                stm.setString(2, getMd5(user.getPassWord()));
                
                ResultSet rs = stm.executeQuery();

                User newUser = null;
                while (rs.next()) {
                    newUser = new User();
                     
//                    newUser.setId(rs.getInt("ID"));
                    newUser.setUserName(rs.getString("Username"));
                    newUser.setFirstName(rs.getString("FirstName"));
                    newUser.setLastName(rs.getString("LastName"));
                    newUser.setGender(rs.getString("Gender"));
                    newUser.setAddress(rs.getString("Address"));
                    
                    break;
                }
                
                return newUser;
        }
    }
    public static User getUserById(Integer maUser) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `users` WHERE `ID` = ?");
            stm.setInt(1, maUser);
            ResultSet rs = stm.executeQuery();
            
            User user = null;
            while (rs.next()) {
                user = new User();
                
                user.setId(rs.getInt("ID"));
                user.setUserName(rs.getString("Username"));
                user.setPassWord(rs.getString("Password"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                
               
                break;
            }
            
            return user;
        }
    }
    public static User getUserByUserName(String username) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `users` WHERE `Username` = ?");
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            
            User user = null;
            while (rs.next()) {
                user = new User();
                
                user.setId(rs.getInt("ID"));
                user.setUserName(rs.getString("Username"));
                user.setPassWord(rs.getString("Password"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                
               
                break;
            }
            
            return user;
        }
    }
    
    public static int MemberRegister(Member member) throws SQLException {
        
        if (Utils.isNullOrEmpty(member.getFirstName())){
            return -1;
        }
        
        if (Utils.isNullOrEmpty(member.getLastName())){
            return -2;
        }
        
        if (Utils.isNullOrEmpty(member.getPhone())){
            return -3;
        }
        
        if (!member.getPhone().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
            return -1;
        }
        
        if (Utils.isNullOrEmpty(member.getAddress())){
            return -1;
        }
        
        try(Connection connect = JdbcUtils.getConn()) {
            String sql = "INSERT INTO Members (FirstName, LastName, Phone, Address)"
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connect.prepareCall(sql);
            statement.setString(1, member.getFirstName());
            statement.setString(2, member.getLastName());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            return statement.executeUpdate();
        }
    }
    
    public static int CheckInValid(Member member) {
        
        if (Utils.isNullOrEmpty(member.getFirstName())){
            return 1;
        }
        
        if (Utils.isNullOrEmpty(member.getLastName())){
            return 2;
        }
        
        if (Utils.isNullOrEmpty(member.getPhone())){
            return 3;
        }
        
        if (!member.getPhone().matches("(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}")) {
            return 4;
        }
        
        if (Utils.isNullOrEmpty(member.getAddress())){
            return 5;
        }
        
        return 0;
    }
}
