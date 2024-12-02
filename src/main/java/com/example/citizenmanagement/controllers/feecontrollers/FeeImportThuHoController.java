package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeeImportThuHoController {
    private Button importbtn;
    public void ImportExcel(ActionEvent event){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement pre = null;
        try {
            // Mở FileChooser để chọn file Excel
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showOpenDialog(importbtn.getScene().getWindow());

            if (file == null) {
                showAlert(Alert.AlertType.WARNING, "Chưa chọn file", "Vui lòng chọn file Excel để tiếp tục.");
                return;
            }

            connection = databaseConnection.getConnection();
            String query = "INSERT INTO QLDIENNUOC(IDCANHO, TONGSODIEN, THANHTIENDIEN, TONGSONUOC, THANHTIENNUOC) VALUES(?, ?, ?, ?, ?)";
            pre = connection.prepareStatement(query);

            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            int result = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                pre.setInt(1, (int) row.getCell(0).getNumericCellValue()); // ID Căn hộ -> IDCANHO
                pre.setInt(2, (int) row.getCell(1).getNumericCellValue()); // Tổng số điện -> TONGSODIEN
                pre.setInt(3, (int) row.getCell(2).getNumericCellValue()); // Thành tiền -> THANHTIENDIEN
                pre.setInt(4, (int) row.getCell(3).getNumericCellValue()); // Tổng số nước -> TONGSONUOC
                pre.setInt(5, (int) row.getCell(4).getNumericCellValue()); // Thành tiền -> THANHTIENNUOC
                result += pre.executeUpdate();
            }

            wb.close();
            fileInputStream.close();

            if (result > 0) {
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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

