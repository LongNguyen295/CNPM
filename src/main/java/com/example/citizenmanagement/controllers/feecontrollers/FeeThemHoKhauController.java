package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeHoKhauCell;
import com.example.citizenmanagement.models.FeeKhoanThuCell;
import com.example.citizenmanagement.models.FeeMenuOptions;
import com.example.citizenmanagement.models.Model;
import com.example.citizenmanagement.views.FeeHoKhauCellFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FeeThemHoKhauController implements Initializable {

    @FXML
    private Button hoanThanhBtn;

    @FXML
    private Button quayLaiBtn;

    @FXML
    private ListView listView;

    @FXML
    private TextField search_textfield;

    private List<FeeHoKhauCell> toanBoDanhSach = new ArrayList<>();

    private boolean reloadListView = false;
    @FXML
    private Button select_all;


    private Alert alert;

    @FXML
    private void onQuayLaiBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.THEM_KHOAN_THU_PHI);
    }
    @FXML
    private void unCheckAll(){
        for (FeeHoKhauCell item : toanBoDanhSach) {
            item.setSelected(false);
        }

        // Cập nhật giao diện của ListView để phản ánh thay đổi
        listView.getItems().clear();
        listView.getItems().addAll(toanBoDanhSach);
        listView.setCellFactory(param -> new FeeHoKhauCellFactory());
    }
    @FXML
    private void onSelectAll(){
        for (FeeHoKhauCell item : toanBoDanhSach) {
            item.setSelected(true);
        }

        // Cập nhật giao diện của ListView để phản ánh thay đổi
        listView.getItems().clear();
        listView.getItems().addAll(toanBoDanhSach);
        listView.setCellFactory(param -> new FeeHoKhauCellFactory());
    }
    @FXML
    private void onHoanThanhBtn(){
        if (!checkDanhSach()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Message");
            alert.setHeaderText(null);
            alert.setContentText("Chưa có thông tin danh sách hộ khẩu cần đóng phí!");
            alert.showAndWait();
        }
        else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Tạo khoản phí thành công!");
            alert.showAndWait();

            //add loại phí
            String tenKhoanThu = Model.getInstance().getFeeKhoanThuModel().getTenKhoanThu().getValue();
            int batBuoc = Model.getInstance().getFeeKhoanThuModel().getBatBuoc().getValue();

            LocalDate now = LocalDate.now();
            String moTa = Model.getInstance().getFeeKhoanThuModel().getMoTa().getValue();
            int maKhoanThu = Model.getInstance().getFeeKhoanThuModel().getMaKhoanThu().getValue();
            Model.getInstance().getDatabaseConnection().themKhoanThuPhi(maKhoanThu,tenKhoanThu, batBuoc, 0, now, moTa);
            int id = Model.getInstance().getDatabaseConnection().getIdKhoanThu(maKhoanThu,tenKhoanThu,batBuoc,0,now,moTa);
            Model.getInstance().getDanhSachKhoanThu().add(new FeeKhoanThuCell(id, maKhoanThu, tenKhoanThu, now.toString()));


            // add danh sách thu phí
            for (FeeHoKhauCell item : toanBoDanhSach) {
                if (item.getSelected())
                    Model.getInstance().getDatabaseConnection().themDanhSachThuPhi(
                            item.getMaHoKhau(), maKhoanThu, 0,id);
            }

            toanBoDanhSach.clear();
            initDanhSach();

            Model.getInstance().getFeeKhoanThuModel().setFeeKhoanThuModel(-1,"", 0, LocalDate.now().toString(), "");
            Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.DANH_SACH_KHOAN_THU);
        }
    }

    private boolean checkDanhSach() {
        for (FeeHoKhauCell item : toanBoDanhSach) {
            if (item.getSelected()) return true;
        }
        return false;
    }

    private void initDanhSach() {
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().getDanhSachDongPhi();
        toanBoDanhSach.clear();
        try {
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    int maHoKhau = resultSet.getInt(1);
                    String tenChuHo = resultSet.getNString(2);
                    String diaChi = resultSet.getNString(3);
                    int soThanhVien = resultSet.getInt(4);
                    toanBoDanhSach.add(new FeeHoKhauCell(false, maHoKhau, tenChuHo, diaChi, soThanhVien,0));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showDanhSach() {
        listView.getItems().clear();
        listView.getItems().addAll(toanBoDanhSach);
        listView.setCellFactory(param -> new FeeHoKhauCellFactory());
    }
    private void selectItem() {
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super FeeHoKhauCell>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (FeeHoKhauCell item : c.getAddedSubList()) {
                        toanBoDanhSach.get(toanBoDanhSach.indexOf(item)).setSelected(true);
                        item.setSelected(true);
                    }
                }

                if (c.wasRemoved() && !reloadListView) {
                    for (FeeHoKhauCell item : c.getRemoved()) {
                        if (!toanBoDanhSach.isEmpty()) {
                            toanBoDanhSach.get(toanBoDanhSach.indexOf(item)).setSelected(false);
                            item.setSelected(false);
                        }
                    }
                }

            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        initDanhSach();
        showDanhSach();
        search_textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            reloadListView = true;
            if (newValue.isEmpty()) {
                showDanhSach();
                reloadListView = false;
            }
            else {
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().danhsachdongphi_timKiem(newValue);
                listView.getItems().clear();
                try {
                    if(resultSet.isBeforeFirst()){
                        while (resultSet.next()){
                            int maHoKhau = resultSet.getInt(1);

                            for (FeeHoKhauCell item : toanBoDanhSach) {
                                if (maHoKhau == item.getMaHoKhau()) {
                                    listView.getItems().add(item);
                                    break;
                                }
                            }
                        }
                        listView.setCellFactory(param ->
                            new FeeHoKhauCellFactory()
                        );
                        reloadListView = false;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        selectItem();

    }
}
