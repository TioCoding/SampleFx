package pe.com.sample.view;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import pe.com.sample.bean.entity.Entidad;
import pe.com.sample.presenter.ComparatorPresenter;

import java.io.File;

public class ComparatorView {

    @FXML
    private StackPane root;

    @FXML
    private JFXTextField txtFileHost;

    @FXML
    private JFXButton btnFindHost;

    @FXML
    private JFXListView<Entidad> listViewHost;

    @FXML
    private Label lblCasoHost;

    @FXML
    private Label lblRepetidoHost;

    @FXML
    private Label lblTotalHost;

    @FXML
    private JFXTextField txtFilePeru;

    @FXML
    private JFXButton btnFindPeru;
    @FXML
    private JFXButton btnGenerar;

    @FXML
    private JFXListView<Entidad> listViewPeru;

    @FXML
    private Label lblCasoPeru;

    @FXML
    private Label lblRepetidoPeru;

    @FXML
    private Label lblTotalPeru;

    @FXML
    private JFXSpinner spinnerLoad;
    @FXML
    private JFXProgressBar progressBarComparator;

    @FXML
    private JFXDialog dialogLoadComparation;
    @FXML
    private Label lblIndexProgress;
    @FXML
    private Label lblTotalProgress;
    @FXML
    private JFXButton btnCancelarOperacion;

    private JFXSnackbar snackbarMessage;

    private Integer countProgressBar = 0;
    private ComparatorPresenter comparatorPresenter;

    @FXML
    public void initialize() {
        this.comparatorPresenter = new ComparatorPresenter(this);

        snackbarMessage = new JFXSnackbar(root);

        dialogLoadComparation.setDialogContainer(root);
        dialogLoadComparation.setOverlayClose(false);

        ObservableList<Entidad> wordsList = FXCollections.observableArrayList();
        wordsList.add(new Entidad("First Word", "Definition of First Word"));
        wordsList.add(new Entidad("Second Word", "Definition of Second Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));
        wordsList.add(new Entidad("Third Word", "Definition of Third Word"));

        listViewHost.setItems(wordsList);

        listViewHost.setCellFactory(param -> new JFXListCell<Entidad>() {
            @Override
            protected void updateItem(Entidad item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNroDocumento() + " " + item.getDesicion());
                }
            }
        });
    }

    public void disableButtonsAndInputs() {
        btnFindHost.setDisable(true);
        btnFindPeru.setDisable(true);
        txtFileHost.setEditable(false);
        txtFilePeru.setEditable(false);
        btnGenerar.setDisable(true);
    }

    public void enableButtonsAndInputs() {
        btnFindHost.setDisable(false);
        btnFindPeru.setDisable(false);
        txtFileHost.setEditable(true);
        txtFilePeru.setEditable(true);
        btnGenerar.setDisable(false);
    }

    public void plusProgressBar() {
        countProgressBar++;
        progressBarComparator.setProgress(((countProgressBar).doubleValue()) / 11);
        Platform.runLater(() -> lblIndexProgress.setText(String.valueOf(countProgressBar)));
    }

    public void setTotalProgressBar(Integer total) {
        lblTotalProgress.setText(total.toString());
    }

    public void resetProgressBar() {
        progressBarComparator.setProgress(0);
        countProgressBar = 0;
        Platform.runLater(() -> {
            lblIndexProgress.setText("0");
            lblTotalProgress.setText("0");
        });
    }

    public void showSpinnerLoad() {
        spinnerLoad.setVisible(true);
    }

    public void hideSpinnerLoad() {
        spinnerLoad.setVisible(false);
    }

    public void showDialogLoadOperation() {
        dialogLoadComparation.show();
    }

    public void closeDialogLoadOperation() {
        dialogLoadComparation.close();
    }

    public void showMessageSuccess(String message) {
//        snackbarMessage.getStyleClass().clear();
//        snackbarMessage.getStyleClass().add("success-toast");
//        snackbarMessage.close();
//        snackbarMessage.show(message, 3500);
        snackbarMessage.fireEvent(new JFXSnackbar.SnackbarEvent(message,"success"));
    }

    @FXML
    void onActionSearchFileHost() {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");

        chooser.getExtensionFilters().add(extFilter);

        File file = chooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            String fileAsString = file.toString();
            System.out.println(fileAsString);
        } else {
            System.out.println("No hay");
        }
    }

    @FXML
    void onActionSearchFilePeru() {
        FileChooser chooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");

        chooser.getExtensionFilters().add(extFilter);

        File file = chooser.showOpenDialog(root.getScene().getWindow());
        if (file != null) {
            String fileAsString = file.toString();
            System.out.println(fileAsString);
        } else {
            System.out.println("No hay");
        }
    }

    @FXML
    void onActionGenerateExcel() {
        comparatorPresenter.generateExcel();
    }

}
