/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.services;

import com.nad.pojo.Unit;
import com.nad.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thien thien
 */
public class UnitServices {
    public List<Unit> getListUnit() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM units");
            
            ResultSet rs = stm.executeQuery();
            List<Unit> listUnit = new ArrayList<>();
            
            while (rs.next()) {                
                int maUnit = rs.getInt("ID");
                String unitName = rs.getString("UnitName");
                
                listUnit.add(new Unit(maUnit, unitName));
            }
            
            return listUnit;
        }
    }
    public static Unit getUnitById(Integer maUnit) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `units` WHERE `ID` = ?");
            stm.setInt(1, maUnit);
            ResultSet rs = stm.executeQuery();
            
            Unit unit = null;
            while (rs.next()) {
                unit = new Unit();
                
                unit.setId(rs.getInt("ID"));
                unit.setUnitName(rs.getString("UnitName"));
                
                break;
            }
            
            return unit;
        }
    }
}
