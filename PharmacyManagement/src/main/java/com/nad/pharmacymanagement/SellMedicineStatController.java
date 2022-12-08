/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nad.pharmacymanagement;

import com.nad.pojo.Stat;
import com.nad.services.SellMedicineServices;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author thien thien
 */
public class SellMedicineStatController implements Initializable{
    @FXML
    private BorderPane bpThongKeTheoNam;
    @FXML
    private ComboBox<Integer> cbNam;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Integer currYear = LocalDate.now().getYear();
            for(int i = currYear; i >= currYear-10; i--) {
                cbNam.getItems().add(i);
            }
            cbNam.setValue(currYear);
            
            this.thongKeTheoNam(cbNam.getValue());
         
            
            cbNam.valueProperty().addListener(evt -> {
                try {
                    this.thongKeTheoNam(cbNam.getValue());
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private void thongKeTheoNam(Integer nam) throws SQLException {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Nam");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("So luong ban");
        
        BarChart barChart = new BarChart(xAxis, yAxis);
        
        SellMedicineServices s = new SellMedicineServices();
        Stat stat = s.sumSellByYear(nam);
        
        XYChart.Series data = new XYChart.Series();
        data.setName("Thong ke theo nam");
        
        data.getData().add(new XYChart.Data(stat.getNam().toString(), stat.getTongSoLuong()));
        
        barChart.getData().add(data);
        barChart.setLegendVisible(false);
        
        bpThongKeTheoNam.setCenter(barChart);
    }
    
}
