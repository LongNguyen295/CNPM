package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeHoaDon;
import com.example.citizenmanagement.models.FeeMenuOptions;
import com.example.citizenmanagement.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ThongKeFeeTongHopUngHoController implements Initializable {
//    @FXML
//    public StackedBarChart<String, Number> thongKeeFeeAll;
    @FXML
    public BarChart<String, Number> soluongdonggop;
    @FXML
    public BarChart<String, Number> tiendonggop;
    @FXML
    public Button QuayLai2;
    @FXML
    public Text tongtienungho;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showBieuDoSoLuongLoaiDongGop();
            showBieuDoTienDongGop();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        QuayLai2.setOnAction(event -> onQuayLai1());
        tongtienungho.setText(String.valueOf(Model.getInstance().getTongTienDongGop())); // Tổng tiền ủng hộ
    }
    void showBieuDoSoLuongLoaiDongGop() throws SQLException {
        ArrayList<Integer> tongSoDotThu = Model.getInstance().getTongSoDotThu();
        // Series Ủng hộ
        final XYChart.Series<String, Number> soluong = new XYChart.Series<>();

        for (Integer i : tongSoDotThu) {
            Number count = Model.getInstance().getSoLuongLoaiPhi(i);
            String xValue = Model.getInstance().getFeeTenDotThu(i); // Trục X
            // Thêm dữ liệu vào từng series
            XYChart.Data<String, Number> data1 = new XYChart.Data<>(xValue, count);
            soluong.getData().add(data1);
        }
        // Thêm series vào biểu đồ
        soluongdonggop.getData().addAll(soluong);
    }
    void showBieuDoTienDongGop() throws SQLException {
        ArrayList<Integer> tongSoDotThu = Model.getInstance().getTongSoDotThu();
        // Series Ủng hộ
        final XYChart.Series<String, Number> soluong = new XYChart.Series<>();

        for (Integer i : tongSoDotThu) {
            Number count = Model.getInstance().getTongTienDongGop(i);
            String xValue = Model.getInstance().getFeeTenDotThu(i); // Trục X
            // Thêm dữ liệu vào từng series
            XYChart.Data<String, Number> data1 = new XYChart.Data<>(xValue, count);
            soluong.getData().add(data1);
        }
        // Thêm series vào biểu đồ
        tiendonggop.getData().addAll(soluong);
    }

    public void onQuayLai1() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.TRANG_CHU);
    }
}
