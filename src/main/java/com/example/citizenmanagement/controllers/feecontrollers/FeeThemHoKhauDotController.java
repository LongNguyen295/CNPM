package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.*;
import com.example.citizenmanagement.views.FeeHoKhauCellFactory;
import javafx.collections.ListChangeListener;
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
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FeeThemHoKhauDotController implements Initializable {

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

    private Alert alert;
    private Alert alert1;


    @FXML
    private void onQuayLaiBtn() {
        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.THEM_KHOAN_THU_DOT);
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
    private void onHoanThanhBtn() throws SQLException {
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
            alert.setContentText("Tạo đợt thu phí thành công!");
            alert.showAndWait();

            //add loại phí
            String tenDotThu = Model.getInstance().getFeeKhoanThuDotModel().tenKhoanThuDotProperty().getValue();
            int batBuoc = Model.getInstance().getFeeKhoanThuDotModel().batBuocProperty().getValue();

            LocalDate now = LocalDate.now();
            String moTa = Model.getInstance().getFeeKhoanThuDotModel().moTaProperty().getValue();

            int maDotThu = Model.getInstance().getFeeKhoanThuDotModel().maKhoanThuDotProperty().getValue();
            //########
            Model.getInstance().getDatabaseConnection().themKhoanThuPhiDot(maDotThu,tenDotThu, batBuoc, now, moTa);
            FeeKhoanThuDotCell feeKhoanThuDotCell1 = new FeeKhoanThuDotCell(maDotThu, tenDotThu, now.toString());
            Model.getInstance().getDanhSachKhoanThuDot().add(feeKhoanThuDotCell1);

            alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Information Message");
            alert1.setHeaderText(null);
            alert1.setContentText("Thêm Phí thu hộ: Điện - Nước - Internet");

            Optional<ButtonType> rs2 = alert1.showAndWait();
            if(rs2.isPresent() &&  rs2.get()==ButtonType.OK){
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
                File file = fileChooser.showOpenDialog(null);

                if (file != null) {
                    importDsDienNuoc(maDotThu, file);
                }
                if(file==null){
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Cần có phải thêm phí thu hộ\nTạo lại mã đợt thu!");

                    // Đợi người dùng xác nhận thông báo
                    alert.showAndWait().ifPresent(response -> {
                        // Sau khi người dùng đã đóng thông báo
                        Model.getInstance().getDatabaseConnection().deleteDotThuPhi(maDotThu);
                        Model.getInstance().getDanhSachKhoanThuDot().remove(feeKhoanThuDotCell1);
                        Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.THEM_KHOAN_THU_DOT);
                    });
                    return;
                }
            } else if (rs2.isPresent() && rs2.get() == ButtonType.CANCEL) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Cần có phải thêm phí thu hộ\nTạo lại mã đợt thu!");

                // Đợi người dùng xác nhận thông báo
                alert.showAndWait().ifPresent(response -> {
                    // Sau khi người dùng đã đóng thông báo
                    Model.getInstance().getDatabaseConnection().deleteDotThuPhi(maDotThu);
                    Model.getInstance().getDanhSachKhoanThuDot().remove(feeKhoanThuDotCell1);
                    Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.THEM_KHOAN_THU_DOT);
                });
                return;
            }

            // add danh sach thu phi vao database
            // add danh sách thu phí
            for (FeeHoKhauCell item : toanBoDanhSach) {
                if (item.getSelected()) {
                    try
                    {DanhSachThuPhiModel resultSetFeeCoDinh = Model.getInstance().getDatabaseConnection().getFeeCoDinh_ThuHo(item.getMaHoKhau(), maDotThu);
                        // Xử lý kết quả từ resultSetFeeCoDinh
                        if (resultSetFeeCoDinh != null) {
                            Model.getInstance().getDatabaseConnection().insertDANHSACHTHUPHI(
                                    maDotThu,item.getMaHoKhau(), resultSetFeeCoDinh.getChuHo(),
                                    resultSetFeeCoDinh.getTienNha(), resultSetFeeCoDinh.getTienDv(),
                                    resultSetFeeCoDinh.getTienXeMay(), resultSetFeeCoDinh.getTienOto(),
                                    resultSetFeeCoDinh.getTienDien(), resultSetFeeCoDinh.getSoDien(),
                                    resultSetFeeCoDinh.getTienNuoc(), resultSetFeeCoDinh.getSoNuoc(),
                                    resultSetFeeCoDinh.getTienInternet()
                            );
                        } else {
                            System.out.println("No data found for maHoKhau CoDinh: " + item.getMaHoKhau());
                        }
                    } catch (SQLException e) {
                        System.err.println("Error processing database operation: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }


            toanBoDanhSach.clear();
            initDanhSach();

            Model.getInstance().getFeeKhoanThuDotModel().setFeeKhoanThuDotModel(-5,"", 0, LocalDate.now().toString(), "");
            Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.DANH_SACH_KHOAN_THU_DOT);

        }
    }

    private boolean checkDanhSach() {
        for (FeeHoKhauCell item : toanBoDanhSach) {
            if (item.getSelected()) return true;
        }
        return false;
    }
    private void importDsDienNuoc(int s, File file) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement pre = null;
        String query = "INSERT INTO FEETHUHO(MADOTTHU, IDCANHO, TONGSODIEN, THANHTIENDIEN, TONGSONUOC, THANHTIENNUOC, THANHTIENINTERNET) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
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
                pre.setInt(2, (int) row.getCell(0).getNumericCellValue());
                pre.setInt(3, (int) row.getCell(1).getNumericCellValue());
                pre.setInt(4, (int) row.getCell(2).getNumericCellValue());
                pre.setInt(5, (int) row.getCell(3).getNumericCellValue());
                pre.setInt(6, (int) row.getCell(4).getNumericCellValue());
                pre.setInt(7, (int) row.getCell(5).getNumericCellValue());

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
    private void initDanhSach() {
       //  ######
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
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
            //    ######################
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
