package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeHoKhauCell;
import com.example.citizenmanagement.models.FeeHoKhauCellDot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

// Vẫn là FeeHoKhauCell nhưng bỏ checkbox
public class FeeHoKhauCell4Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text dia_chi;
    @FXML
    private HBox hbox;

    @FXML
    private Text ma_ho_khau;

    @FXML
    private Text so_tien_can_dong;

    @FXML
    private Text ten_chu_ho;

    private FeeHoKhauCellDot feeHoKhauCell;
    public FeeHoKhauCell4Controller(FeeHoKhauCellDot feeHoKhauCell) {
        this.feeHoKhauCell = feeHoKhauCell;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ma_ho_khau.setText(String.valueOf(feeHoKhauCell.getMaHoKhau()));
        ten_chu_ho.setText(feeHoKhauCell.getTenChuHo());
        dia_chi.setText(feeHoKhauCell.getDiaChi());
        so_tien_can_dong.setText(String.valueOf(feeHoKhauCell.getTongTien()));
    }
}
