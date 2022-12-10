/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pojo;

/**
 *
 * @author thien thien
 */
public class Medicine {
    private Integer id;
    private String brandName;
    private String chemicalName;
    private Integer unitId;
    private Integer unitInStock;
    private Float unitPrice;
    private Integer allowedUnitInStock;
    private String producingCountry;

    public Medicine() {
    }

    public Medicine(Integer id, String brandName, String chemicalName, Integer unitId, Integer unitInStock, Float unitPrice, Integer allowedUnitInStock, String producingCountry) {
        this.id = id;
        this.brandName = brandName;
        this.chemicalName = chemicalName;
        this.unitId = unitId;
        this.unitInStock = unitInStock;
        this.unitPrice = unitPrice;
        this.allowedUnitInStock = allowedUnitInStock;
        this.producingCountry = producingCountry;
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
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return the chemicalName
     */
    public String getChemicalName() {
        return chemicalName;
    }

    /**
     * @param chemicalName the chemicalName to set
     */
    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    /**
     * @return the unitId
     */
    public Integer getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    /**
     * @return the unitInStock
     */
    public Integer getUnitInStock() {
        return unitInStock;
    }

    /**
     * @param unitInStock the unitInStock to set
     */
    public void setUnitInStock(Integer unitInStock) {
        this.unitInStock = unitInStock;
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
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the allowedUnitInStock
     */
    public Integer getAllowedUnitInStock() {
        return allowedUnitInStock;
    }

    /**
     * @param allowedUnitInStock the allowedUnitInStock to set
     */
    public void setAllowedUnitInStock(Integer allowedUnitInStock) {
        this.allowedUnitInStock = allowedUnitInStock;
    }

    /**
     * @return the producingCountry
     */
    public String getProducingCountry() {
        return producingCountry;
    }

    /**
     * @param producingCountry the producingCountry to set
     */
    public void setProducingCountry(String producingCountry) {
        this.producingCountry = producingCountry;
    }
    @Override
    public String toString() {
        return this.brandName;
    }

//    public void setUnitPrice(double d) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
    
}
