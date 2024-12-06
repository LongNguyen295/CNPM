package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.*;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.apache.poi.ss.formula.functions.T;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class FeeHoaDonController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private Label ten_chu_ho;
    @FXML
    private Label id_can_ho;
    @FXML
    private Text ma_dot_thu;
    @FXML
    private Text dot_thu;
    @FXML
    private Text tien_nha;
    @FXML
    private Text tien_dich_vu;
    @FXML
    private Text tien_dien;
    @FXML
    private Text tien_nuoc;
    @FXML
    private Text tien_internet;
    @FXML
    private Text phi_gui_xe_may;
    @FXML
    private Text phi_gui_oto;
    @FXML
    private Text dien_tich_chung_cu;
    @FXML
    private Text so_xe_may;
    @FXML
    private Text so_oto;
    @FXML
    private Text so_dien;
    @FXML
    private Text so_nuoc;
    @FXML
    private Label ngay_dong;
    @FXML
    private Label tong_so_tien;
    @FXML
    private Text tien_ung_ho;

    @FXML
    private void onDaHoanThanhBtn(MouseEvent event) {
        // Xử lý sự kiện
    }

    @FXML
    private void onBackBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.HOAN_THANH_PHI_DOT);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int maDotThu = Model.getInstance().getHoaDonModel().getMaDotThu();
        int maHoKhau = Model.getInstance().getHoaDonModel().getMaHoKhau();
        try {
            FeeHoaDon feeHoaDon = Model.getInstance().getDatabaseConnection().getFeeHoaDon(maDotThu,maHoKhau);
            ten_chu_ho.setText(feeHoaDon.getChuHo());
            id_can_ho.setText(feeHoaDon.getCanHo());
            ma_dot_thu.setText(String.valueOf(maDotThu));
            dot_thu.setText(feeHoaDon.getDotThu());
            tien_nha.setText(String.valueOf(feeHoaDon.getTienNha()));
            tien_dich_vu.setText(String.valueOf(feeHoaDon.getTienDichVu()));
            tien_dien.setText(String.valueOf(feeHoaDon.getTienDien()));
            tien_nuoc.setText(String.valueOf(feeHoaDon.getTienNuoc()));
            tien_internet.setText(String.valueOf(feeHoaDon.getTienInternet()));
            dien_tich_chung_cu.setText(String.valueOf(feeHoaDon.getDienTichChungCu()));
            so_xe_may.setText(String.valueOf(feeHoaDon.getSoLuongXeMay()));
            so_oto.setText(String.valueOf(feeHoaDon.getSoLuongOto()));
            so_dien.setText(String.valueOf(feeHoaDon.getTongSoDien()));
            so_nuoc.setText(String.valueOf(feeHoaDon.getTongSoNuoc()));
            ngay_dong.setText(String.valueOf(feeHoaDon.getNgayDong()));
            phi_gui_oto.setText(String.valueOf(feeHoaDon.getTienOto()));
            phi_gui_xe_may.setText(String.valueOf(feeHoaDon.getTienXeMay()));
            tong_so_tien.setText(String.valueOf(feeHoaDon.getTongSoTien()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
