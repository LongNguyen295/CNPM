package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeKhoanThuCell;
import com.example.citizenmanagement.models.FeeKhoanThuDotCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FeeKhoanThuDotCellController implements Initializable {
    
    @FXML
    private Text ma_dot_thu;

    @FXML
    private Text ngay_tao;

    @FXML
    private Text ten_khoan_thu;

    private FeeKhoanThuDotCell feeKhoanThuCell;

    public FeeKhoanThuDotCellController(FeeKhoanThuDotCell feeKhoanThuCell) {this.feeKhoanThuCell = feeKhoanThuCell;}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ma_dot_thu.setText(String.valueOf(feeKhoanThuCell.getMaDotThu()));
        ten_khoan_thu.setText(feeKhoanThuCell.getTenKhoanThu());
        ngay_tao.setText(feeKhoanThuCell.getNgayTao());

    }
}
