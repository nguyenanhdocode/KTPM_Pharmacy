/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pojo;

/**
 *
 * @author thien thien
 */
public class Unit {
    private Integer id;
    private String unitName;

    public Unit() {
    }

    public Unit(Integer id, String unitName) {
        this.id = id;
        this.unitName = unitName;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    @Override
    public String toString() {
        return this.unitName;
    }
}
