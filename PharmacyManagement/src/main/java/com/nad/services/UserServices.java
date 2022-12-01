/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pojo.User;
import com.nad.utils.JdbcUtils;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author thien thien
 */
public class UserServices {
    public boolean dangKy(User user) throws SQLException {
        if(user.getId()!= null){
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
            
            return true;
        }
        } return false;
        
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
                stm.setString(2, user.getPassWord());
                
                ResultSet rs = stm.executeQuery();

                User newUser = null;
                while (rs.next()) {
                    newUser = new User();
                     
                    newUser.setId(rs.getInt("ID"));
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
}
