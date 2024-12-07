package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FeeThemKhoanThuDotController implements Initializable {

    @FXML
    private ChoiceBox<String> bat_buoc;

    @FXML
    private TextArea mo_ta;
    @FXML
    private TextField ma_dot_thu;
    @FXML
    private Button next_page_btn;

    @FXML
    private Button tienDienNuocInternet;
    @FXML
    private TextField ten_khoan_thu;
    private Alert alert;
    private boolean checkImport;
    private ObservableList<String> choices = FXCollections.observableArrayList(
            "Bắt buộc"
    );

    private int luaChon;
    @FXML
    private void onFeeCreateTiepBtn() {
        if(checkThongTin()) {
            Model.getInstance().getFeeKhoanThuDotModel().setFeeKhoanThuDotModel(
                    Integer.parseInt(ma_dot_thu.getText()),
                    ten_khoan_thu.getText(),
                    luaChon,
                    LocalDate.now().toString(),
                    mo_ta.getText());
            Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.THEM_HO_KHAU_DOT);

        }
    }

    public void onResetBtn() {
        ma_dot_thu.setText("");
        ten_khoan_thu.setText("");
        bat_buoc.setValue(choices.get(0));
        mo_ta.setText("");
    }

    private boolean checkThongTin() {
        if (ten_khoan_thu.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đủ thông tin TÊN KHOẢN THU !");
            alert.showAndWait();
            return false;
        }
        else if(!isValidInteger(ma_dot_thu.getText())){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền MÃ ĐỢT THU là một số !");
            alert.showAndWait();
            return false;
        }
        else {
            int s = Integer.parseInt(ma_dot_thu.getText());
            if(Model.getInstance().getDatabaseConnection().checkMaKhoanThu(s)){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("MÃ ĐỢT THU này đã tồn tại !");
                alert.showAndWait();
                return false;
            }
        }
        if(!checkImport){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa thêm File Thanh Toán Điện Nước Internet");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true; // Nếu chuyển đổi thành công, trả về true
        } catch (NumberFormatException e) {
            return false; // Nếu có lỗi, trả về false
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkImport = false;

        luaChon = 0;
        bat_buoc.setItems(choices);
        bat_buoc.setValue(choices.get(0));

        FeeKhoanThuDotModel fee = Model.getInstance().getFeeKhoanThuDotModel();
        fee.tenKhoanThuDotProperty().addListener((observable, oldValue, newValue) -> {
            ten_khoan_thu.setText(newValue.toString());
        });
        fee.moTaProperty().addListener((observable, oldValue, newValue) -> {
            mo_ta.setText(newValue.toString());
        });
    }
    @FXML
    public void ImportExcel(){
        if (!isValidInteger(ma_dot_thu.getText())) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền mã đợt thu !");
            alert.showAndWait();
        }
        else {
               int s = Integer.parseInt(ma_dot_thu.getText());
            if (Model.getInstance().getDatabaseConnection().checkMaKhoanThu(s)) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Mã đợt thu đã tồn taị !");
                alert.showAndWait();
            } else {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Connection connection = null;
                PreparedStatement pre = null;
                String query = "INSERT INTO FEETHUHO(MADOTTHU, IDCANHO, TONGSODIEN, THANHTIENDIEN, TONGSONUOC, THANHTIENNUOC, THANHTIENINTERNET) VALUES(?, ?, ?, ?, ?, ?, ?)";
                try {
                    // Mở FileChooser để chọn file Excel
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
                    File file = fileChooser.showOpenDialog(tienDienNuocInternet.getScene().getWindow());

                    if (file == null) {
                        showAlert(Alert.AlertType.WARNING, "Chưa chọn file", "Vui lòng chọn file Excel để tiếp tục.");
                        return;
                    }

                    connection = databaseConnection.getConnection();

                    pre = connection.prepareStatement(query);

                    FileInputStream fileInputStream = new FileInputStream(file);
                    XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
                    XSSFSheet sheet = wb.getSheetAt(0);
                    Row row;
                    int result = 0;

                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        row = sheet.getRow(i);
                        if (row == null) continue;

                        pre.setInt(1, s);
                        pre.setInt(2, (int) row.getCell(0).getNumericCellValue()); // ID Căn hộ
                        pre.setInt(3, (int) row.getCell(1).getNumericCellValue()); // Tổng số điện
                        pre.setInt(4, (int) row.getCell(2).getNumericCellValue()); // Thành tiền điện
                        pre.setInt(5, (int) row.getCell(3).getNumericCellValue()); // Tổng số nước
                        pre.setInt(6, (int) row.getCell(4).getNumericCellValue()); // Thành tiền nước
                        pre.setInt(7, (int) row.getCell(5).getNumericCellValue()); // Tiền mạng

                        result += pre.executeUpdate();
                    }

                    wb.close();
                    fileInputStream.close();

                    if (result > 0) {
                        checkImport = true;
                        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thêm dữ liệu thành công!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Thất bại", "Không có dữ liệu nào được thêm.");
                    }
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi đọc file", "Không thể đọc file Excel: " + e.getMessage());
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi SQL", "Lỗi thực thi câu lệnh SQL: " + e.getMessage());
                } finally {
                    try {
                        if (pre != null) pre.close();
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
