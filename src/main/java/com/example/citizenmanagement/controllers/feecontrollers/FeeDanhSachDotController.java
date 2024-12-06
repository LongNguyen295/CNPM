package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeKhoanThuCell;
import com.example.citizenmanagement.models.FeeKhoanThuDotCell;
import com.example.citizenmanagement.models.FeeMenuOptions;
import com.example.citizenmanagement.models.Model;
import com.example.citizenmanagement.views.FeeKhoanThuCellFactory;
import com.example.citizenmanagement.views.FeeKhoanThuDotCellFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FeeDanhSachDotController implements Initializable {

    @FXML
    private ListView<FeeKhoanThuDotCell> listView;

    @FXML
    private TextField search_textfield;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        initDanhSach();
        onSearch();
        Model.getInstance().getDanhSachKhoanThuDot().addListener((ListChangeListener.Change<? extends FeeKhoanThuDotCell> change) -> {
            while (change.next()){
                initDanhSach();
            }
        });
        listView.setCellFactory(param -> new FeeKhoanThuDotCellFactory());

        onClick();
    }

    private void onClick() {
        listView.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { // double-click
                FeeKhoanThuDotCell selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    //Model.getInstance().getFeeKhoanThuDotModel().setMaKhoanThuDot(-1);
                    Model.getInstance().getFeeKhoanThuDotModel().setMaKhoanThuDot(selectedItem.getMaDotThu());
                    Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.CHI_TIET_KHOAN_THU_DOT);
                }
            }

        });
    }

    private void onSearch() {
        search_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                initDanhSach();
            }
            else {
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().danhSachKhoanThu_timKiemDot(newValue);
                listView.getItems().clear();
                try {
                    if (resultSet.isBeforeFirst()) {
                        while (resultSet.next()) {
                            int maKhoanThu = resultSet.getInt(1);
                            String tenKhoanThu = resultSet.getNString(2);
//                            int batBuoc = resultSet.getInt(3);
//                            long soTienCanDong = resultSet.getLong(4);
                            String ngayTao = resultSet.getString(4);

                            listView.getItems().add(new FeeKhoanThuDotCell(maKhoanThu, tenKhoanThu, ngayTao));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    private void initDanhSach() {
        listView.getItems().clear();
        listView.getItems().addAll(Model.getInstance().getDanhSachKhoanThuDot());

    }
}
