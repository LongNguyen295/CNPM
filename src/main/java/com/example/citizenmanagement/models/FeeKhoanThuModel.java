package com.example.citizenmanagement.models;

import javafx.beans.property.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FeeKhoanThuModel{
    // lưu tạm thời khoản thu phí
    private IntegerProperty id; // Mã khoản thu
    private IntegerProperty maKhoanThu; // Đợt thu
    private StringProperty tenKhoanThu;
    private IntegerProperty batBuoc;

    private StringProperty ngayTao;
    private StringProperty moTa;

    public FeeKhoanThuModel() {
        id = new SimpleIntegerProperty(-1);
        maKhoanThu = new SimpleIntegerProperty(-1);
        tenKhoanThu = new SimpleStringProperty("");
        batBuoc = new SimpleIntegerProperty(0);
        ngayTao = new SimpleStringProperty(LocalDate.now().toString());
        moTa = new SimpleStringProperty("");
        id.addListener((observable, oldValue, newValue) -> {
            changeData(newValue.intValue());
        });
    }

    private void changeData(int maHK) {
        try {
            ResultSet resultSet = Model.getInstance().getDatabaseConnection().getKhoanThuPhi(maHK);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                maKhoanThu.set(resultSet.getInt(1));
                tenKhoanThu.set(resultSet.getNString(2));
                batBuoc.set(resultSet.getInt(3));
                ngayTao.set(resultSet.getString(5));
                moTa.set(resultSet.getNString(6));
                System.out.println("Data updated: " + maHK);
            } else {
                System.out.println("No data found for maHK: " + maHK);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setFeeKhoanThuModel(int maKhoanThu,String tenKhoanThu, int batBuoc, String ngayTao, String moTa) {
        this.maKhoanThu.setValue(maKhoanThu);
        this.tenKhoanThu.setValue(tenKhoanThu);
        this.batBuoc.setValue(batBuoc);
        this. ngayTao.setValue(ngayTao);
        this.moTa.setValue(moTa);

    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty getTenKhoanThu() {
        return tenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu.setValue(tenKhoanThu);
    }

    public IntegerProperty getBatBuoc() {
        return batBuoc;
    }

    public void setBatBuoc(int batBuoc) {
        this.batBuoc.setValue(batBuoc);
    }


    public StringProperty getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao.setValue(ngayTao);
    }

    public StringProperty getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa.setValue(moTa);
    }

    public IntegerProperty getMaKhoanThu() {return maKhoanThu;}
    public void setMaKhoanThu(int maKhoanThu) {this.maKhoanThu.set(maKhoanThu);}
}
