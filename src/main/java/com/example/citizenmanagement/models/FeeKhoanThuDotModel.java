package com.example.citizenmanagement.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FeeKhoanThuDotModel {
   private IntegerProperty maKhoanThuDot;
   private StringProperty tenKhoanThuDot;
   private IntegerProperty batBuoc;
   private StringProperty ngayTao;
   private StringProperty moTa;

    public FeeKhoanThuDotModel() {
        maKhoanThuDot = new SimpleIntegerProperty(-1);
        tenKhoanThuDot = new SimpleStringProperty("");
        batBuoc = new SimpleIntegerProperty(0);
        ngayTao = new SimpleStringProperty(LocalDate.now().toString());
        moTa = new SimpleStringProperty("");
        maKhoanThuDot.addListener((observableValue, oldValue, newValue) -> {
            //System.out.println("maKhoanThuDot changed: " + oldValue + " -> " + newValue);
            changData(newValue.intValue());
        });

    }
    private void changData(int maHK) {
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getKhoanThuDot(maHK);


        try {
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                maKhoanThuDot.set(resultSet.getInt(1));
                tenKhoanThuDot.set(resultSet.getString(2));
                batBuoc.set(resultSet.getInt(3));
//                System.out.println("====FeeKhoanThuDotModel====");
//                System.out.println(resultSet.getInt(1));
//                System.out.println(resultSet.getString(2));
//                System.out.println(resultSet.getInt(3));
//                System.out.println("====FeeKhoanThuDotModel====");
                // Lấy giá trị DATE và chuyển đổi thành chuỗi
                java.sql.Date sqlDate = resultSet.getDate(4);
                if (sqlDate != null) {
                    ngayTao.set(sqlDate.toString()); // Hoặc sử dụng SimpleDateFormat để định dạng
                } else {
                    ngayTao.set(null);
                }

                moTa.set(resultSet.getNString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Da Thuc hien Xong FeeKhoanThuDoTModel");
    }
    public void setFeeKhoanThuDotModel(int maKhoanThuDot,String tenKhoanThuDot, int batBuoc, String ngayTao, String moTa){
        this.maKhoanThuDot.setValue(maKhoanThuDot);
        this.tenKhoanThuDot.setValue(tenKhoanThuDot);
        this.batBuoc.setValue(batBuoc);
        this.ngayTao.setValue(ngayTao);
        this.moTa.setValue(moTa);
    }



    public int getMaKhoanThuDot() {
        return maKhoanThuDot.get();
    }

    public IntegerProperty maKhoanThuDotProperty() {
        return maKhoanThuDot;
    }

    public void setMaKhoanThuDot(int maKhoanThuDot) {
        this.maKhoanThuDot.set(maKhoanThuDot);
    }

    public String getTenKhoanThuDot() {
        return tenKhoanThuDot.get();
    }

    public StringProperty tenKhoanThuDotProperty() {
        return tenKhoanThuDot;
    }

    public void setTenKhoanThuDot(String tenKhoanThuDot) {
        this.tenKhoanThuDot.set(tenKhoanThuDot);
    }

    public int getBatBuoc() {
        return batBuoc.get();
    }

    public IntegerProperty batBuocProperty() {
        return batBuoc;
    }

    public void setBatBuoc(int batBuoc) {
        this.batBuoc.set(batBuoc);
    }

    public String getNgayTao() {
        return ngayTao.get();
    }

    public StringProperty ngayTaoProperty() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao.set(ngayTao);
    }

    public String getMoTa() {
        return moTa.get();
    }

    public StringProperty moTaProperty() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa.set(moTa);
    }
}
