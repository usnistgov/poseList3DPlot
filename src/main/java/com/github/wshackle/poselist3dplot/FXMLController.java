package com.github.wshackle.poselist3dplot;

import static com.github.wshackle.poselist3dplot.View3DPlotJPanel.getAutoScale;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLController implements Initializable {

    private Label label;
    @FXML
    private SubScene subScene3D;
    @FXML
    private AnchorPane SCENE_PARENT;
    @FXML
    private Button xPlusButton;
    @FXML
    private Button xNegButton;
    @FXML
    private Button yPlusButton;
    @FXML
    private Button yNegButton;
    @FXML
    private Button zPlusButton;
    @FXML
    private Button zNegButton;

    private Scene3DController scene3DController = null;
    @FXML
    private RadioButton radioRotateXY;
    private RadioButton radioRotateZ;
    @FXML
    private RadioButton radioTranslateXY;
    @FXML
    private RadioButton radioTranslateZ;
    @FXML
    private MenuItem menuFileClose;
    @FXML
    private MenuItem menuItemTest1;
    @FXML
    private MenuItem menuItemOpenCSVFile;
    @FXML
    private MenuItem setDistanceScale;
    @FXML
    private CheckMenuItem showRotationFrames;
    @FXML
    private MenuItem autoSetDistanceScale;

    private void closeWindow() {
        System.err.println("trying to close window");
        Optional.ofNullable(stage)
                .map(x -> x.getScene())
                .map(x -> x.getWindow())
                .map(x -> x.getOnCloseRequest())
                .map(x -> {
                    x.handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
                    return x;
                })
                .orElseGet(() -> {
                    Platform.exit();
                    return null;
                });
//        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private Stage stage;

    public void setStage(Stage _stage) {
        this.stage = _stage;
    }

    public void autoSetScale() throws NumberFormatException {
        double newDistScale = getAutoScale(scene3DController.tracksList);
//        this.jTextFieldDistScale.setText(String.format("%.1g", newDistScale * 100.0));
        scene3DController.setDistScale(newDistScale);
    }

    public void chooseAndOpenCsvFile() {
        FileChooser chooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter(
                "Comma-Separated-Values", "csv");
        chooser.setSelectedExtensionFilter(filter);
        File f = chooser.showOpenDialog(stage);
        if (null != f) {
            try {
                openCsvFile(f);
            } catch (Exception ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openCsvFile(final File f) {
        java.awt.EventQueue.invokeLater(() -> {
            CsvParseOptions csvParseOptions = CsvParseOptionsJPanel.showDialog(null, f);
//                List<PM_POSE> l = Posemath.csvToPoseListF(chooser.getSelectedFile(),
//                        csvParseOptions.X_INDEX,
//                        csvParseOptions.Y_INDEX,
//                        csvParseOptions.Z_INDEX,
//                        -1, -1, -1);
            final Track track = TrackUtils.readTrack(csvParseOptions, f);
            track.cur_time_index = track.getData().size();
            Platform.runLater(() -> {
//        this.view3DPlotJPanel1.setSingleTrack(track);
                this.scene3DController.addTrack(track);
            });
        });

    }

    private void queryDistanceScale() {
        TextInputDialog dialog = new TextInputDialog("" + scene3DController.getDistScale());
        dialog.setTitle("Distance Scale");
        dialog.setHeaderText("Set Distance Scale (pixels/m)");
//        dialog.setContentText("Distance Scale");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent((String val) ->  {
            double newScale = Double.valueOf(val);
            scene3DController.setDistScale(newScale);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        scene3DController = new Scene3DController();
        scene3DController.setupSubscene(subScene3D, subScene3D.getWidth(), subScene3D.getHeight());
//        subScene3D.setManaged(false);
        subScene3D.heightProperty().bind(SCENE_PARENT.heightProperty());
        subScene3D.widthProperty().bind(SCENE_PARENT.widthProperty());
//        System.out.println("SCENE_PARENT = " + SCENE_PARENT);
        scene3DController.getCenterWinTranslate().xProperty().bind(SCENE_PARENT.widthProperty().divide(3));
        scene3DController.getCenterWinTranslate().yProperty().bind(SCENE_PARENT.heightProperty().multiply(2.0 / 3.0));
//        SCENE_PARENT.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            System.out.println("width newValue = " + newValue);
//        });
//
//        SCENE_PARENT.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            System.out.println("height newValue = " + newValue);
//        });
//
//        scene3DController.getCenterWinTranslate().yProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            System.out.println("y newValue = " + newValue);
//        });
//        scene3DController.getCenterWinTranslate().xProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//            System.out.println("x newValue = " + newValue);
//        });
        xNegButton.setOnAction(e -> scene3DController.xNegView());
        xPlusButton.setOnAction(e -> scene3DController.xPosView());
        yNegButton.setOnAction(e -> scene3DController.yNegView());
        yPlusButton.setOnAction(e -> scene3DController.yPosView());
        zNegButton.setOnAction(e -> scene3DController.zNegView());
        zPlusButton.setOnAction(e -> scene3DController.zPosView());
        final ToggleGroup dragModeToggleGroup = new ToggleGroup();
        radioRotateXY.setToggleGroup(dragModeToggleGroup);
//        radioRotateZ.setToggleGroup(dragModeToggleGroup);
        radioTranslateXY.setToggleGroup(dragModeToggleGroup);
        radioTranslateZ.setToggleGroup(dragModeToggleGroup);
        menuFileClose.setOnAction(e -> closeWindow());
        menuItemTest1.setOnAction(e -> {
            scene3DController.setSingleTrack(TrackUtils.getTest1Track());
            autoSetScale();
        });
        showRotationFrames.setOnAction(e -> {
            scene3DController.setShowRotationFrames(showRotationFrames.isSelected());
            scene3DController.refreshScene(scene3DController.tracksList);
        });
        setDistanceScale.setOnAction(e -> queryDistanceScale());
        dragModeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                View3DDragEnum de = scene3DController.getDragEnum();
                View3DDragEnum orig_de = de;
                if (newValue == radioRotateXY) {
                    de = View3DDragEnum.ROT_XY;
//                } else if (newValue == radioRotateZ) {
//                    de = View3DDragEnum.ROT_Z;
                } else if (newValue == radioTranslateXY) {
                    de = View3DDragEnum.TRAN_XY;
                } else if (newValue == radioTranslateZ) {
                    de = View3DDragEnum.TRAN_Z;
                }
                if (de != orig_de) {
                    scene3DController.setDragEnum(de);
                    scene3DController.setupTransforms();
                }
            }
        });
        menuItemOpenCSVFile.setOnAction(e -> chooseAndOpenCsvFile());
        autoSetDistanceScale.setOnAction(e -> scene3DController.autoSetScale());
    }

}
