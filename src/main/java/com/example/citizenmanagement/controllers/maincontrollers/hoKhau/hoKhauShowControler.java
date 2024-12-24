package com.example.citizenmanagement.controllers.maincontrollers.hoKhau;

import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;
import com.example.citizenmanagement.models.MainHoKhauCell;
import com.example.citizenmanagement.views.MainHoKhauCellFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class hoKhauShowControler implements Initializable {
    @FXML
    private TextField search_textfield;
    @FXML
    private ListView<MainHoKhauCell> listView;
    @FXML
    private Button them_but;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cập nhật danh sách hộ khẩu ban đầu
        capnhat();

        // Lắng nghe sự thay đổi trong ô tìm kiếm
        search_textfield.textProperty().addListener((observable, oldvalue, newvalue) -> {
            if (newvalue.isEmpty()) {
                // Nếu ô tìm kiếm trống, hiển thị danh sách đầy đủ
                capnhat();
            } else {
                // Lọc danh sách dựa trên từ khóa
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().timKiemHoKhauCoTrangThai1(newvalue);
                listView.getItems().clear();
                try {
                    if (resultSet.isBeforeFirst()) {
                        while (resultSet.next()) {
                            String id = resultSet.getString(1);
                            String owner = resultSet.getString(6);
                            String address = resultSet.getString(3);
                            String dateCreated = resultSet.getString(4);
                            String note = resultSet.getString(5);
                            String motorbike = resultSet.getString(7);
                            String car = resultSet.getString(8);

                            // Thêm hộ khẩu vào danh sách
                            listView.getItems().add(new MainHoKhauCell(id, owner, address, dateCreated, note, motorbike, car));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Đặt nhà máy cell để hiển thị từng mục trong danh sách
        listView.setCellFactory(param -> new MainHoKhauCellFactory());

        // Xử lý sự kiện khi nhấn đúp vào một hộ khẩu
        listView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                MainHoKhauCell selectedHoKhau = listView.getSelectionModel().getSelectedItem();
                if (selectedHoKhau != null) {
                    Model.setHoKhauDuocChon(selectedHoKhau);
                    Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.XEM_CHI_TIET_HO_KHAU);
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút "Thêm Mới"
        them_but.setOnAction(event -> {
            Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.THEM_CHU_HO_KHAU);
        });
    }

    // Cập nhật danh sách hộ khẩu
    private void capnhat() {
        // Truy vấn danh sách hộ khẩu có TRANGTHAI = 1
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachHoKhauCoTrangThai1();
        listView.getItems().clear();
        try {
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String id = resultSet.getString(1);
                    ResultSet resultSet1 = Model.getInstance().getDatabaseConnection().lay_nhan_khau(resultSet.getString(2));
                    String owner = null;
                    try {
                        if (resultSet1.isBeforeFirst()) {
                            resultSet1.next();
                            owner = resultSet1.getString(2);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String address = resultSet.getString(3);
                    String dateCreated = resultSet.getString(4);
                    String note = resultSet.getString(5);
                    String motorbike = resultSet.getString(7);
                    String car = resultSet.getString(8);

                    // Thêm hộ khẩu vào danh sách
                    listView.getItems().add(new MainHoKhauCell(id, owner, address, dateCreated, note, motorbike, car));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
