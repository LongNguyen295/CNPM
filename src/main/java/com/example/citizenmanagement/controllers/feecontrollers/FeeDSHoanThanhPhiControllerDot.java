package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.*;
import com.example.citizenmanagement.views.FeeHoKhauCell2Factory;
import com.example.citizenmanagement.views.FeeHoKhauCell4Factory;
import com.example.citizenmanagement.views.FeeHoKhauCell5Factory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FeeDSHoanThanhPhiControllerDot implements Initializable {

    @FXML
    private ListView<FeeHoKhauCellDot> listView;

    @FXML
    private Button quayLaiBtn;

    @FXML
    private TextField search_textfield;

    private Alert alert;
    @FXML
    void onQuayLaiBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.CHI_TIET_KHOAN_THU_DOT);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDanhSach(); // khởi tạo Model.getInstance().getDanhSachDaDongPhi()
        showDanhSach();
        onSearch();
        Model.getInstance().getFeeKhoanThuDotModel().maKhoanThuDotProperty().addListener((observable, oldValue, newValue) -> {
            initDanhSach();
            showDanhSach();

        });
        Model.getInstance().getDanhSachChuaDongPhiDot().addListener((ListChangeListener.Change<? extends FeeHoKhauCellDot> change) -> {
            while(change.next()) {
                if (change.wasRemoved()) {
                    initDanhSach();
                    showDanhSach();

                }
            }
        });
        onItemClicked();
        listView.setCellFactory(param -> new FeeHoKhauCell5Factory());
    }

    private void onItemClicked() {
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // double-click
                System.out.println("DONE -1");
                FeeHoKhauCellDot selectedItem = listView.getSelectionModel().getSelectedItem();
                System.out.println("DONE 0");
                if (selectedItem != null) {
                    System.out.println("DONE 1");
                    Model.getInstance().getHoaDonModel().setMaDotThu(selectedItem.getMaDotThu());
                    System.out.println("DONE 2");
                    Model.getInstance().getHoaDonModel().setMaHoKhau(selectedItem.getMaHoKhau());
                    System.out.println("DONE 3");
                    Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.THONG_TIN_HOA_DON);
                    System.out.println("DONE 4");
                }
            }

        });
    }

    private void onSearch() {
        search_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                showDanhSach();
            }
            else {
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().danhSachDaDongPhi_timKiemDot(newValue,
                        Model.getInstance().getFeeKhoanThuDotModel().maKhoanThuDotProperty().get());
                listView.getItems().clear();
                try {
                    if (resultSet.isBeforeFirst()){
                        while (resultSet.next()) {
                            int maHoKhau = resultSet.getInt(1);
                            String tenChuHo = resultSet.getNString(2);
                            String diaChi = resultSet.getNString(3);
                            int soTienDaDong = resultSet.getInt(4);
                            String ngayDong = resultSet.getString(5);

                            listView.getItems().add(new FeeHoKhauCellDot(maHoKhau, tenChuHo, diaChi, soTienDaDong,ngayDong));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private void initDanhSach() {
        Model.getInstance().getDanhSachDaDongPhiDot().clear();
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachDaDongPhiDot(Model.getInstance().getFeeKhoanThuDotModel().maKhoanThuDotProperty().get());
        try {
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    int maDotThu = resultSet.getInt(1);
                    int maHoKhau = resultSet.getInt(2);
                    String tenChuHo = resultSet.getNString(3);
                    String diaChi = resultSet.getNString(4);
                    int soTienDaDong = resultSet.getInt(5);
                    String ngayDong = resultSet.getString(6);
                    Model.getInstance().getDanhSachDaDongPhiDot().add(new FeeHoKhauCellDot(maDotThu,maHoKhau, tenChuHo, diaChi, soTienDaDong,ngayDong));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showDanhSach() {
        listView.getItems().clear();
        listView.getItems().addAll(Model.getInstance().getDanhSachDaDongPhiDot());
    }
}
