package com.example.citizenmanagement.controllers.maincontrollers.NhankhauController;

import com.example.citizenmanagement.models.List_nhan_khau;
import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class KhaiTuController implements Initializable {
    public Button thoat_khaitu_btn;
    public TextField ma_nguoi_khai;
    public TextField ma_nguoi_mat;
    public TextArea li_do_mat;
    public DatePicker ngay_mat;
    public Button xac_nhan_but;
    private List_nhan_khau tam;

    private void onthoatKhaitubtn() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.NHAN_KHAU);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Xử lý nút thoát
        thoat_khaitu_btn.setOnAction(actionEvent -> onthoatKhaitubtn());

        // Lấy thông tin người đã chọn
        tam = Model.getNhanKhauDuocChon();
        if (tam != null) {
            ma_nguoi_mat.setText(tam.getSo_nhan_khau());
            ma_nguoi_mat.setDisable(true); // Không cho phép sửa mã người mất
        }

        // Xử lý nút xác nhận khai tử
        xac_nhan_but.setOnAction(actionEvent -> {
            if (ngay_mat.getValue() == null || li_do_mat.getText().isEmpty() || ma_nguoi_khai.getText().isEmpty()) {
                // Kiểm tra nếu người dùng bỏ trống thông tin
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setContentText("Vui lòng điền đầy đủ thông tin trước khi xác nhận!");
                alert.showAndWait();
                return;
            }

            // Thêm khai tử vào cơ sở dữ liệu
            int thanhcong = Model.getInstance().getDatabaseConnection().addKhaitu(
                    ma_nguoi_khai.getText(),
                    ma_nguoi_mat.getText(),
                    Date.valueOf(ngay_mat.getValue()),
                    li_do_mat.getText()
            );

            if (thanhcong == 1) {
                // Xóa nhân khẩu khỏi cơ sở dữ liệu nếu khai tử thành công
                int xoaThanhCong = Model.getInstance().getDatabaseConnection().xoaNhanKhau(ma_nguoi_mat.getText());

                if (xoaThanhCong == 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setContentText("Đã khai tử và xóa người này khỏi danh sách nhân khẩu!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Cảnh báo");
                    alert.setContentText("Đã khai tử nhưng không thể xóa người này khỏi danh sách nhân khẩu!");
                    alert.showAndWait();
                }

                // Trở về giao diện quản lý nhân khẩu
                Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.NHAN_KHAU);
            } else {
                // Thông báo lỗi nếu khai tử thất bại
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setContentText("Đã xảy ra lỗi trong quá trình khai tử. Vui lòng kiểm tra lại!");
                alert.showAndWait();
            }
        });
    }
}
