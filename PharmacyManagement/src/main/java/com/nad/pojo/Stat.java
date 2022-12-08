/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pojo;

/**
 *
 * @author thien thien
 */
public class Stat {
    private Integer nam;
    private Integer tongSoLuong;

    public Stat() {
    }

    public Stat(Integer nam, Integer tongSoLuong) {
        this.nam = nam;
        this.tongSoLuong = tongSoLuong;
    }

    /**
     * @return the nam
     */
    public Integer getNam() {
        return nam;
    }

    /**
     * @param nam the nam to set
     */
    public void setNam(Integer nam) {
        this.nam = nam;
    }

    /**
     * @return the tongSoLuong
     */
    public Integer getTongSoLuong() {
        return tongSoLuong;
    }

    /**
     * @param tongSoLuong the tongSoLuong to set
     */
    public void setTongSoLuong(Integer tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }
    
    
}
