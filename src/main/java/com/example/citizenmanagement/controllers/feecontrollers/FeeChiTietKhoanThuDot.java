package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.*;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeeChiTietKhoanThuDot implements Initializable {

    @FXML
    private Label chua_hoan_thanh_lbl;

    @FXML
    private Label da_hoan_thanh_lbl;

    @FXML
    private Text ma_khoan_thu; // Mã đợt thu

    @FXML
    private Text ngay_tao;


    @FXML
    private Text ten_khoan_thu;

    @FXML
    private TextArea mo_ta;
    @FXML
    private Label tong_tien_da_thu;
    private int daDong;
    private int chuaDong;
    private int tongSo;

    private Alert alert;
    @FXML
    private void onBackBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.DANH_SACH_KHOAN_THU_DOT);
    }
    @FXML
    private void onDeleteBtn() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation dialog");
        alert.setHeaderText(null);
        alert.setContentText("Bạn chắc chắn không?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            int maKhoanThuDot = Model.getInstance().getFeeKhoanThuDotModel().getMaKhoanThuDot();
            // System.out.println("Ma Khoan thu: " +Model.getInstance().getFeeKhoanThuDotModel().getMaKhoanThuDot());
            Model.getInstance().getDatabaseConnection().deleteDotThuPhi(
                    Model.getInstance().getFeeKhoanThuDotModel().getMaKhoanThuDot());

            List<FeeKhoanThuCell> khoanThuToRemove = new ArrayList<>();
            List<FeeKhoanThuDotCell> khoanThuDotToRemove = new ArrayList<>();

            // Tìm các item cần xóa
            for (FeeKhoanThuDotCell item : Model.getInstance().getDanhSachKhoanThuDot()) {
                if (item.getMaDotThu() == maKhoanThuDot) {
                    khoanThuDotToRemove.add(item);

                    // Tìm các khoản thu liên quan
                    for (FeeKhoanThuCell item1 : Model.getInstance().getDanhSachKhoanThu()) {
                        if (item1.getMaDotThu() == maKhoanThuDot) {
                            khoanThuToRemove.add(item1);
                        }
                    }
                    break;
                }
            }

            // Thực hiện xóa từ danh sách tạm
            Model.getInstance().getDanhSachKhoanThu().removeAll(khoanThuToRemove);
            Model.getInstance().getDanhSachKhoanThuDot().removeAll(khoanThuDotToRemove);

            Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.DANH_SACH_KHOAN_THU_DOT);

        }

    }
    @FXML
    private void onDaHoanThanhBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.HOAN_THANH_PHI_DOT);
    }
    @FXML
    private void onChuaHoanThanhBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.CHUA_HOAN_THANH_PHI_DOT);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mo_ta.setWrapText(true);

        chiTiet1();


        Model.getInstance().getFeeKhoanThuDotModel().maKhoanThuDotProperty().addListener((observable, oldValue, newValue) -> {
            chiTiet1();
        });

        Model.getInstance().getDanhSachChuaDongPhiDot().addListener((ListChangeListener.Change<? extends FeeHoKhauCellDot> change) -> {
            while(change.next()) {
                if (change.wasRemoved()) {
//                    System.out.println("nop phi thanh cong");
                    chiTiet1();
                }
            }
        });
    }

    private void chiTiet1() {

        FeeKhoanThuDotModel selectedItem = Model.getInstance().getFeeKhoanThuDotModel();
        try{
            tong_tien_da_thu.setText(String.valueOf(Model.getInstance().getDatabaseConnection().getTongThuPhiByDot(selectedItem.maKhoanThuDotProperty().get())));
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        ma_khoan_thu.setText(String.valueOf(selectedItem.maKhoanThuDotProperty().get())); //maDotThu
        ten_khoan_thu.setText(selectedItem.tenKhoanThuDotProperty().get());
        if(selectedItem.getNgayTao() != null)
            ngay_tao.setText(selectedItem.getNgayTao());
        else
            ngay_tao.setText("");
        if (selectedItem.getMoTa()!= null)
            mo_ta.setText(selectedItem.getMoTa());
        else
            mo_ta.setText("");

        daDong = Model.getInstance().getDatabaseConnection().getSoLuongHoDongPhiDot(selectedItem.getMaKhoanThuDot());
        chuaDong = Model.getInstance().getDatabaseConnection().getSoLuongHoChuaDongPhiDot(selectedItem.getMaKhoanThuDot());
        tongSo = Model.getInstance().getDatabaseConnection().getTongSoLuongHoDongPhiDot(selectedItem.getMaKhoanThuDot());

        da_hoan_thanh_lbl.setText("" + daDong + "/" + tongSo);
        chua_hoan_thanh_lbl.setText("" + chuaDong + "/" + tongSo);
    }
}
