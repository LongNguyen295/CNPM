package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeHoaDon;
import com.example.citizenmanagement.models.FeeMenuOptions;
import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FeeTrangChuController implements Initializable {
    @FXML
    public Label name_thuphi;
    @FXML
    public Circle profile_thuphi;


    @FXML
    public Label tongSoHoKhau;
    @FXML
    public Label tongSoNhanKhau;
    @FXML
    public Label tien_nha;
    @FXML
    public Label tien_quan_ly;
    @FXML
    public Label xe_may;
    @FXML
    public Label tong_loai_phi;
    @FXML
    public Label tong_tien_ung_ho;
    @FXML
    public Label xe_oto;
    @FXML
    public Label tien_dien;
    @FXML
    public Label tien_nuoc;
    @FXML
    public Label tien_internet;
    @FXML
    public Label tong_tien_da_dong;
    @FXML
    public AnchorPane thongkedichvu;
    @FXML
    public AnchorPane thongkequanly;
    @FXML
    public AnchorPane thongkexemay;
    @FXML
    public AnchorPane thongkedonggop;
    @FXML
    public AnchorPane thongketongtiendonggop;
    @FXML
    public AnchorPane thongkeoto;
    @FXML
    public AnchorPane thongketiendien;
    @FXML
    public AnchorPane thognketiennuoc;
    @FXML
    public AnchorPane thongkeinternet;
    @FXML
    public AnchorPane thongketongtien;


    @FXML
    void clickdichvu(MouseEvent e){
        System.out.println("Done DichVu");
    }
    @FXML
    void clickquanly(MouseEvent e){

    }
    @FXML
    void clickxemay(MouseEvent e){

    }
    @FXML
    void clickoto(MouseEvent e){

    }
    @FXML
    void clickdonggop(MouseEvent e){

    }
    @FXML
    void clicktongtiendonggop(MouseEvent e){

    }
    @FXML
    void clicktiendien(MouseEvent e){

    }
    @FXML
    void clicktiennuoc(MouseEvent e){

    }
    @FXML
    void clicktieninternet(MouseEvent e){

    }
    @FXML
    void clicktongtien(MouseEvent e){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        displayName();

        Model.getInstance().getImageObjectProperty().addListener((observable, oldValue, newValue) -> {
            profile_thuphi.setFill(new ImagePattern(newValue));
            System.out.println("change");
        });

        try {
            profile_thuphi.setFill(new ImagePattern(new Image(getClass().getResource("/images/login_form/profile.png").toURI().toString(), 60, 60, false, true)));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        //----------------------------------------------------//
        // hien thi cac thong so tren bang tom tat
        tongSoNhanKhau.setText(Integer.toString(Model.getInstance().getNumberOfNhanKhau()));
        tongSoHoKhau.setText(Integer.toString(Model.getInstance().getNumberOfHoKhau()));
        try {
            FeeHoaDon feeCDTH  = Model.getInstance().getDatabaseConnection().getFeeHoaDonSummary();
            tien_nha.setText(String.valueOf(feeCDTH.getTienNha()));
            tien_quan_ly.setText(String.valueOf(feeCDTH.getTienDichVu()));
            xe_may.setText(String.valueOf(feeCDTH.getTienXeMay()));
            tien_dien.setText(String.valueOf(feeCDTH.getTienDien()));
            xe_oto.setText(String.valueOf(feeCDTH.getTienOto()));
            tien_nuoc.setText(String.valueOf(feeCDTH.getTienNuoc()));
            tien_internet.setText(String.valueOf(feeCDTH.getTienInternet()));
            tong_tien_da_dong.setText(feeCDTH.getTongSoTien());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            FeeHoaDon feeDG = Model.getInstance().getDatabaseConnection().getFeeHoaDonDongGopSummary();
            tong_loai_phi.setText(String.valueOf(feeDG.getTongLoaiPhi()));
            tong_tien_ung_ho.setText(String.valueOf(feeDG.getTienUngHo()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void displayName() {
        String[] parts = Model.getInstance().getCitizenManager().getHoTen().trim().split(" ");

        if (parts.length > 0) {
            name_thuphi.setText(parts[parts.length - 1]);
        }
    }
    public void onProFile_Fee(MouseEvent mouseEvent) {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.FEE_PROFILE);
    }




}
