/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pojo;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author thien thien
 */
public class SellMedicine {
    private User userID;
    private Medicine medicineID;
    private Timestamp date;
    private Float unitPrice;
    private Integer quantity;

    public SellMedicine() {
    }

    public SellMedicine(User userID, Medicine medicineID, Timestamp date, Float unitPrice, Integer quantity) {
        this.userID = userID;
        this.medicineID = medicineID;
        this.date = date;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public SellMedicine(Medicine medicine, User user, Integer quantity, Timestamp date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public SellMedicine(Medicine medicine, User user, Timestamp date, Integer unitPrice, Integer quantity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * @return the userID
     */
    public User getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(User userID) {
        this.userID = userID;
    }

    /**
     * @return the medicineID
     */
    public Medicine getMedicineID() {
        return medicineID;
    }

    /**
     * @param medicineID the medicineID to set
     */
    public void setMedicineID(Medicine medicineID) {
        this.medicineID = medicineID;
    }

    /**
     * @return the date
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Timestamp date) {
        this.date = date;
    }

    /**
     * @return the unitPrice
     */
    public Float getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
     
}
