package com.example.citizenmanagement.controllers.maincontrollers.hoKhau;

import com.example.citizenmanagement.models.List_nhan_khau;
import com.example.citizenmanagement.models.MainMenuOptions;
import com.example.citizenmanagement.models.Model;
import com.example.citizenmanagement.models.thanh_vien_cua_ho_cell;
import com.example.citizenmanagement.views.List_nhan_khau_factory;
import com.example.citizenmanagement.views.thanh_vien_cua_ho_cell_factory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ThemThanhVienHoKhauController implements Initializable {

    public Button cancel_but;
    public TextField id_chu_ho_text;
    public TextField add_text;
    public TextField add_maphong;
    public TextField ghi_chu_text;
    public ListView<List_nhan_khau> listView_to_chon;
    public ListView<thanh_vien_cua_ho_cell> listView_to_them;
    public Button chuyen_lai;
    public Button chuyen_sang;
    public TextField search_text;
    public Button xac_nhan_but;
    public TextField quan_he_textField;

    private List_nhan_khau nhan_khau_duoc_chon;
    private ObservableList<List_nhan_khau> forSelect = FXCollections.observableArrayList();

    private thanh_vien_cua_ho_cell thanh_vien_duoc_chon;

    private List_nhan_khau chuHo = Model.getNhanKhauDuocChon();

    public boolean checkValid(String input) {
        try {
            // Kiểm tra độ dài của chuỗi đầu vào
            if (input.length() != 4) {
                return false; // Mã phòng phải có 4 ký tự
            }

            // Tách các ký tự
            char x = input.charAt(0);
            char y = input.charAt(1);
            char z = input.charAt(2);
            char t = input.charAt(3);

            // Kiểm tra điều kiện cho từng ký tự
            if (x >= '0' && x <= '3' && // Ký tự đầu tiên là số từ 0 đến 3
                    y >= '0' && y <= '9' && // Ký tự thứ hai là số từ 0 đến 9
                    z == '0' &&             // Ký tự thứ ba phải là '0'
                    t >= '1' && t <= '5') { // Ký tự thứ tư là số từ 1 đến 5
                return true; // Nếu tất cả điều kiện đều thỏa mãn
            }
        } catch (Exception e) {
            // Nếu có lỗi, trả về false
            return false;
        }

        // Trả về false nếu bất kỳ điều kiện nào không thỏa mãn
        return false;
    }


    public boolean checkMaP(String input){
        try {
            int x = Integer.parseInt(input);

        } catch (NumberFormatException e) {
            return false; // Nếu có lỗi, trả về false
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_chu_ho_text.setDisable(true);
        id_chu_ho_text.setText(Model.getNhanKhauDuocChon().getSo_nhan_khau());

        init_forSelect();

        listView_to_chon.setOnMouseClicked(event -> {
            nhan_khau_duoc_chon = listView_to_chon.getSelectionModel().getSelectedItem();
        });
        listView_to_them.setOnMouseClicked(event -> {
            thanh_vien_duoc_chon = listView_to_them.getSelectionModel().getSelectedItem();
        });


        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            listView_to_chon.getItems().clear();
            if(newValue.isEmpty()) {
                for(List_nhan_khau item : forSelect) {
                    listView_to_chon.getItems().add(item);
                }
            }
            else {
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().nhanKhau_timkiem_chua_co_nha(search_text.getText());
                try {
                    if (resultSet.isBeforeFirst()){
                        while (resultSet.next()) {
//                            boolean chuaChet = Model.getInstance().getDatabaseConnection().checkKhaiTu(resultSet.getString(1));
                            if(!resultSet.getString(1).equals(chuHo.getSo_nhan_khau())) {
                                String ma_nhan_khau = resultSet.getString(1);
                                String cccd = resultSet.getString(2);
                                String hoten = resultSet.getNString(3);
                                String gioitinh = resultSet.getString(4);
                                String namsinh = resultSet.getString(5);
                                String thuongtru = resultSet.getNString(6);

                                List_nhan_khau item = new List_nhan_khau(ma_nhan_khau, cccd, hoten, gioitinh, namsinh, thuongtru);
                                if(forSelect.contains(item)) listView_to_chon.getItems().add(item);
                            }
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        chuyen_sang.setOnAction(event -> {
            if(!quan_he_textField.getText().isEmpty()){
                if(nhan_khau_duoc_chon != null) {
                    thanh_vien_cua_ho_cell item = new thanh_vien_cua_ho_cell(
                            nhan_khau_duoc_chon.getSo_nhan_khau(),
                            nhan_khau_duoc_chon.getCccd(),
                            nhan_khau_duoc_chon.getHoten(),
                            quan_he_textField.getText(),
                            nhan_khau_duoc_chon.getNgay_sinh(),
                            nhan_khau_duoc_chon.getGioi_tinh()
                    );
                    forSelect.remove(nhan_khau_duoc_chon);
                    listView_to_chon.getItems().remove(nhan_khau_duoc_chon);
                    listView_to_them.getItems().add(item);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông báo lỗi");
                    alert.setContentText("Bạn chưa chọn người được thêm");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Bạn chưa điền quan hệ với chủ hộ");
                alert.setTitle("Thông báo lỗi");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            nhan_khau_duoc_chon = null; quan_he_textField.setText("");
        });

        chuyen_lai.setOnAction(event -> {
            if(thanh_vien_duoc_chon != null) {
                listView_to_them.getItems().remove(thanh_vien_duoc_chon);
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().lay_nhan_khau(thanh_vien_duoc_chon.getmaNhanKhau());
                try {
                    resultSet.next();
                    String maNhanKhau = thanh_vien_duoc_chon.getmaNhanKhau();
                    String cccd = resultSet.getString(1);
                    String hoTen = resultSet.getNString(2);
                    String gioiTinh;
                    if(resultSet.getInt(3) == 1) {
                        gioiTinh = "Nam";
                    }
                    else gioiTinh = "Nữ";
                    String ngaySinh = resultSet.getString(4);
                    String noiThuongTru = resultSet.getNString(5);
                    List_nhan_khau item = new List_nhan_khau(
                            maNhanKhau, cccd, hoTen, gioiTinh, ngaySinh, noiThuongTru
                    );

                    listView_to_chon.getItems().add(item);
                    forSelect.add(item);



                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        cancel_but.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Bạn chắc chắn muốn hủy?");
            alert.setHeaderText(null);
            alert.setTitle("Confirm Alert");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.HO_KHAU);
            }
        });

        xac_nhan_but.setOnAction(event -> {
            if (add_text.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Thông báo lỗi");
                alert.setContentText("Bạn chưa nhập địa chỉ");
                alert.showAndWait();
            } else if (add_maphong.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Thông báo lỗi");
                alert.setContentText("Bạn chưa nhập mã phòng");
                alert.showAndWait();
            } else if (!checkValid(add_maphong.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Thông báo lỗi");
                alert.setContentText("Mã phòng không tồn tại \n Nhập mã phòng 0XXX, 1XXX, 2XXX, 3001");
                alert.showAndWait();
            } else if (Model.getInstance().getDatabaseConnection().checkMaPhong(Integer.parseInt(add_maphong.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Mã phòng đã có chủ hộ ! \n Vui lòng kiểm tra lại");
                alert.showAndWait();
            } else if (ghi_chu_text.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Thông báo lỗi");
                alert.setContentText("Bạn chưa số thành viên hiện tại");
                alert.showAndWait();

            } else {
                String diaChi = add_maphong.getText()+" "+add_text.getText();
                Model.getInstance().getDatabaseConnection().addHoKhauV1(chuHo.getSo_nhan_khau(), diaChi, ghi_chu_text.getText(),Integer.parseInt(add_maphong.getText()));
                String maHoKhau;
                ResultSet resultSet = Model.getInstance().getDatabaseConnection().getMaHoKhau(chuHo.getSo_nhan_khau());
                try {
                    resultSet.next();
                    maHoKhau = resultSet.getString(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                for(thanh_vien_cua_ho_cell item : listView_to_them.getItems()) {
                    Model.getInstance().getDatabaseConnection().add_thanh_vien_cua_ho(item.getmaNhanKhau(), maHoKhau, item.getQuan_he());
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Thông báo");
                alert.setContentText("Đã thêm thành công");
                alert.showAndWait();
                Model.getInstance().getViewFactory().getSelectedMenuItem().set(MainMenuOptions.HO_KHAU);
            }
        });
        listView_to_chon.setCellFactory(param -> new List_nhan_khau_factory());
        listView_to_them.setCellFactory(param -> new thanh_vien_cua_ho_cell_factory());
    }

    private void init_forSelect() {
        ResultSet resultSet = Model.getInstance().getDatabaseConnection().truyvan_chua_co_nha();
        listView_to_chon.getItems().clear();
        try {
            if(resultSet.isBeforeFirst()){
                while (resultSet.next()) {
//                    boolean chuaChet = Model.getInstance().getDatabaseConnection().checkKhaiTu(resultSet.getString(1));
                    if(!chuHo.getSo_nhan_khau().equals(resultSet.getString(1)) ) {
                        String ma_nhan_khau = resultSet.getString(1);
                        String cccd = resultSet.getString(2);
                        String hoten = resultSet.getNString(3);
                        String gioitinh = resultSet.getString(4);
                        String namsinh = resultSet.getString(5);
                        String thuongtru = resultSet.getNString(6);

                        List_nhan_khau item = new List_nhan_khau(ma_nhan_khau, cccd, hoten, gioitinh, namsinh, thuongtru);
                        listView_to_chon.getItems().add(item);
                        forSelect.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
