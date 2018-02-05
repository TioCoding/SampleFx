package pe.com.sample.view;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SideMenuView {

    @FXML
    private JFXListView<Label> sideList;

    @FXML
    public void initialize() {
        sideList.getSelectionModel().select(0);
    }

}
