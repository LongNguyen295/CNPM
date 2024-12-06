package com.example.citizenmanagement.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class HoaDonModel {
    private IntegerProperty maDotThu = new SimpleIntegerProperty();
    private IntegerProperty maHoKhau = new SimpleIntegerProperty();

    public HoaDonModel() {
    }

    public HoaDonModel(int maDotThu, int maHoKhau) {
        this.maDotThu.setValue(maDotThu);
        this.maHoKhau.setValue(maHoKhau);

    }

    public int getMaDotThu() {
        return maDotThu.get();
    }

    public IntegerProperty maDotThuProperty() {
        return maDotThu;
    }

    public void setMaDotThu(int maDotThu) {
        this.maDotThu.set(maDotThu);
    }

    public int getMaHoKhau() {
        return maHoKhau.get();
    }

    public IntegerProperty maHoKhauProperty() {
        return maHoKhau;
    }

    public void setMaHoKhau(int maHoKhau) {
        this.maHoKhau.set(maHoKhau);
    }
}
