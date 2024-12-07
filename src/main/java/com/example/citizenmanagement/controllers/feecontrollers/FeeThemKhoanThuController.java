package com.example.citizenmanagement.controllers.feecontrollers;

import com.example.citizenmanagement.models.FeeKhoanThuModel;
import com.example.citizenmanagement.models.FeeMenuOptions;
import com.example.citizenmanagement.models.Model;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FeeThemKhoanThuController implements Initializable {

    @FXML
    private ChoiceBox<String> bat_buoc;

    @FXML
    private TextArea mo_ta;

    @FXML
    private Button next_page_btn;

    @FXML
    private TextField ma_khoan_thu;
    @FXML
    private TextField ten_khoan_thu;
    private Alert alert;
    private boolean check = false;
    private ObservableList<String> choices = FXCollections.observableArrayList(
            "Không Bắt Buộc"
    );

    private int luaChon;
    @FXML
    private void onFeeCreateTiepBtn() {
        if(checkThongTin()) {
            Model.getInstance().getFeeKhoanThuModel().setFeeKhoanThuModel(
                    Integer.parseInt(ma_khoan_thu.getText()),
                    ten_khoan_thu.getText(),
                    luaChon,
                    LocalDate.now().toString(),
                    mo_ta.getText());
            Model.getInstance().getViewFactory().getFeeSelectedMenuItem().set(FeeMenuOptions.DANH_SACH_HO_KHAU_CAN_THU_PHI);

        }
    }

    public void onResetBtn() {
        ma_khoan_thu.setText("");
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
        else if(!isValidInteger(ma_khoan_thu.getText())){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền MÃ ĐỢT THU là một số !");
            alert.showAndWait();
            return false;
        }
        else {
            int s = Integer.parseInt(ma_khoan_thu.getText());
            System.out.println(s);
            if(!Model.getInstance().getDatabaseConnection().checkMaKhoanThu(s)){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Kiểm tra lại mã đợt thu !");
                alert.showAndWait();
                return false;
            }
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

        luaChon = 0;
        bat_buoc.setItems(choices);
        bat_buoc.setValue(choices.get(0));

        FeeKhoanThuModel fee = Model.getInstance().getFeeKhoanThuModel();
        fee.getTenKhoanThu().addListener((observable, oldValue, newValue) -> {
            ten_khoan_thu.setText(newValue.toString());
        });
        fee.getMoTa().addListener((observable, oldValue, newValue) -> {
            mo_ta.setText(newValue.toString());
        });
    }


}
