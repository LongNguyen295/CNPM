package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeKhoanThuCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FeeKhoanThuCellController implements Initializable {

    @FXML
    private Text id;

    @FXML
    private Text ma_khoan_thu;

    @FXML
    private Text ngay_tao;

    @FXML
    private Text ten_khoan_thu;

    private FeeKhoanThuCell feeKhoanThuCell;

    public FeeKhoanThuCellController(FeeKhoanThuCell feeKhoanThuCell) {this.feeKhoanThuCell = feeKhoanThuCell;}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setText(String.valueOf(feeKhoanThuCell.getId()));
        ma_khoan_thu.setText(String.valueOf(feeKhoanThuCell.getMaDotThu()));
        ten_khoan_thu.setText(feeKhoanThuCell.getTenKhoanThu());
        ngay_tao.setText(feeKhoanThuCell.getNgayTao());
    }
}
