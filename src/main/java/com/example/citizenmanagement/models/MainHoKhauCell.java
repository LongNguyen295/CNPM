package com.example.citizenmanagement.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainHoKhauCell {
private final StringProperty id;
private final StringProperty owner;
private final StringProperty address;
private final StringProperty date_tao;
private final StringProperty ghi_chu;
private final StringProperty xe_may;
private final StringProperty o_to;

public MainHoKhauCell(String id, String owner, String address, String ngaytao, String ghichu, String xemay, String oto){
    this.id = new SimpleStringProperty(this,"id",id);
    this.owner = new SimpleStringProperty(this,"owner",owner);
    this.address = new SimpleStringProperty(this,"address",address);
    this.date_tao = new SimpleStringProperty(this,"date_tao",ngaytao);
    this.ghi_chu = new SimpleStringProperty(this,"ghi_chu",ghichu);
    this.xe_may = new SimpleStringProperty(this,"xe_may",xemay);
    this.o_to = new SimpleStringProperty(this,"o_to",oto);
}
public StringProperty getId(){
    return this.id;
}
public StringProperty getOwner(){
    return this.owner;
}
public StringProperty getAddress(){
    return this.address;
}
public StringProperty getDate_tao(){
    return this.date_tao;
}
    public StringProperty getGhi_chu(){
    return ghi_chu;
    }
    public StringProperty getXe_may() {
        return xe_may;
    }
    public StringProperty getO_to() {
        return o_to;
    }
    public void setId(String id) {
        this.id.set(id);
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setDate_tao(String date_tao) {
        this.date_tao.set(date_tao);
    }
    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu.set(ghi_chu);
    }
    public void setXe_may(String xe_may) {
        this.xe_may.set(xe_may);
    }

    // Setter cho o_to
    public void setO_to(String o_to) {
        this.o_to.set(o_to);
    }
}
