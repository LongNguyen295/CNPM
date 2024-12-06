package com.example.citizenmanagement.views;

import com.example.citizenmanagement.controllers.feecontrollers.FeeKhoanThuCellController;
import com.example.citizenmanagement.controllers.feecontrollers.FeeKhoanThuDotCellController;
import com.example.citizenmanagement.models.FeeKhoanThuCell;
import com.example.citizenmanagement.models.FeeKhoanThuDotCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class FeeKhoanThuDotCellFactory extends ListCell<FeeKhoanThuDotCell> {
    @Override
    public void updateItem(FeeKhoanThuDotCell feeKhoanThuCell, boolean empty) {
        super.updateItem(feeKhoanThuCell, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fee/FeeKhoanThuDotCell.fxml"));
            loader.setController(new FeeKhoanThuDotCellController(feeKhoanThuCell));

            setText(null);
            try {
                setGraphic(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
