package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeHoaDon;
import com.example.citizenmanagement.models.FeeMenuOptions;
import com.example.citizenmanagement.models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.chart.BarChart;

public class ThongKeFeeTongHopController implements Initializable {
//    @FXML
//    public StackedBarChart<String, Number> thongKeeFeeAll;
    @FXML
    public BarChart<String, Number> thongKeeFeeAll;

    @FXML
    public Button QuayLai2;
    @FXML
    public Text tongsofeethu_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showBieuDoNhanKhau();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        QuayLai2.setOnAction(event -> onQuayLai1());
        tongsofeethu_text.setText(String.valueOf(Model.getInstance().getSumAllFee()));
    }
    void showBieuDoNhanKhau() throws SQLException {
        ArrayList<Integer> tongSoDotThu = Model.getInstance().getTongSoDotThu();
        // Series Ủng hộ
        final XYChart.Series<String, Number> phiDichVu = new XYChart.Series<>();
        phiDichVu.setName("Dịch vụ");
        final XYChart.Series<String, Number> phiQuanLy = new XYChart.Series<>();
        phiQuanLy.setName("Quản lý");
        final XYChart.Series<String, Number> phiXeMay = new XYChart.Series<>();
        phiXeMay.setName("Xe máy");
        final XYChart.Series<String, Number> phiOto = new XYChart.Series<>();
        phiOto.setName("Xe oto");
        final XYChart.Series<String, Number> phiDien = new XYChart.Series<>();
        phiDien.setName("Điện");
        final XYChart.Series<String, Number> phiNuoc = new XYChart.Series<>();
        phiNuoc.setName("Nước");
        final XYChart.Series<String, Number> phiInternet = new XYChart.Series<>();
        phiInternet.setName("Internet");

        for (Integer i : tongSoDotThu) {
            FeeHoaDon feeHoaDon = Model.getInstance().getDatabaseConnection().getFeeHoaDonSummaryByDotThu(i);
            String xValue = Model.getInstance().getFeeTenDotThu(i); // Trục X
            // Thêm dữ liệu vào từng series
            XYChart.Data<String, Number> data1 = new XYChart.Data<>(xValue, feeHoaDon.getTienNha());
            XYChart.Data<String, Number> data2 = new XYChart.Data<>(xValue, feeHoaDon.getTienDichVu());
            XYChart.Data<String, Number> data3 = new XYChart.Data<>(xValue, feeHoaDon.getTienXeMay());
            XYChart.Data<String, Number> data4 = new XYChart.Data<>(xValue, feeHoaDon.getTienOto());
            XYChart.Data<String, Number> data5 = new XYChart.Data<>(xValue, feeHoaDon.getTienDien());
            XYChart.Data<String, Number> data6 = new XYChart.Data<>(xValue, feeHoaDon.getTienNuoc());
            XYChart.Data<String, Number> data7 = new XYChart.Data<>(xValue, feeHoaDon.getTienInternet());

            phiDichVu.getData().add(data1);
            phiQuanLy.getData().add(data2);
            phiXeMay.getData().add(data3);
            phiOto.getData().add(data4);
            phiDien.getData().add(data5);
            phiNuoc.getData().add(data6);
            phiInternet.getData().add(data7);
        }

        // Thêm series vào biểu đồ
        thongKeeFeeAll.getData().addAll(phiDichVu,phiQuanLy,phiXeMay,phiOto,phiDien,phiNuoc,phiInternet);
    }

    public void onQuayLai1() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.TRANG_CHU);
    }
}
