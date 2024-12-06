package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.*;
import com.example.citizenmanagement.views.FeeHoKhauCell2Factory;
import com.example.citizenmanagement.views.FeeHoKhauCell4Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class FeeDSChuaHoanThanhPhiDot implements Initializable {

    @FXML
    private ListView<FeeHoKhauCellDot> listView;

    @FXML
    private Button quayLaiBtn;

    @FXML
    private TextField search_textfield;

    private Alert alert;
    private int soTienCanDong;
    @FXML
    void onQuayLaiBtn(ActionEvent event) {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.CHI_TIET_KHOAN_THU_DOT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDanhSach(); // khởi tạo Model.getInstance().getDanhSachChuaDongPhi()
        showDanhSach();
        onSearch();
        Model.getInstance().getFeeKhoanThuDotModel().maKhoanThuDotProperty().addListener((observable, oldValue, newValue) -> {
            initDanhSach();
            showDanhSach();
        });
        onItemClicked();
        listView.setCellFactory(param -> new FeeHoKhauCell4Factory());
    }

    private void onItemClicked() {
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // double-click
                FeeHoKhauCellDot selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {

                    if (Model.getInstance().getFeeKhoanThuModel().getBatBuoc().get() == 0) {
//
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText("Đóng phí");
                        alert1.setContentText("Xác nhận đóng: "+selectedItem.getTongTien()+" VNĐ");
                        Optional<ButtonType> result2 = alert1.showAndWait();
                        if(result2.isPresent() && result2.get() == ButtonType.OK ){
                            Model.getInstance().getDatabaseConnection().updateNopPhiTong(selectedItem.getMaDotThu(),selectedItem.getMaHoKhau(), selectedItem.getTongTien());
                            Model.getInstance().getDanhSachChuaDongPhiDot().remove(selectedItem);
                            showDanhSach();
                        }

                    }
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
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().danhSachChuaDongPhi_timKiemDot(newValue,
                        Model.getInstance().getFeeKhoanThuDotModel().getMaKhoanThuDot());
                listView.getItems().clear();
                    try {
                        if (resultSet.isBeforeFirst()){
                            while (resultSet.next()) {
                                int maHoKhau = resultSet.getInt(1);
                                String tenChuHo = resultSet.getNString(2);
                                String diaChi = resultSet.getNString(3);
                                soTienCanDong = resultSet.getInt(5);
//                                int maDotThu = resultSet.getInt(6);
                               listView.getItems().add(new FeeHoKhauCellDot(maHoKhau, tenChuHo, diaChi,  soTienCanDong));
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

            }
        });
    }

    private void initDanhSach() {
        Model.getInstance().getDanhSachChuaDongPhiDot().clear();
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachChuaDongPhiDot(Model.getInstance().getFeeKhoanThuDotModel().getMaKhoanThuDot());
        try {
            if(resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    int maHoKhau = resultSet.getInt(1);
                    String tenChuHo = resultSet.getNString(2);
                    String diaChi = resultSet.getNString(3);
                    soTienCanDong = resultSet.getInt(4);
                    int maDotThu = resultSet.getInt(5);
                    Model.getInstance().getDanhSachChuaDongPhiDot().add(new FeeHoKhauCellDot(maDotThu,maHoKhau, tenChuHo, diaChi, soTienCanDong));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showDanhSach() {
        listView.getItems().clear();
        listView.getItems().addAll(Model.getInstance().getDanhSachChuaDongPhiDot());
    }
}
