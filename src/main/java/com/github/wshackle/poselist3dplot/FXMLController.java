package com.github.wshackle.poselist3dplot;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    private RadioButton radioRotateZ;
    @FXML
    private RadioButton radioTranslateXY;
    @FXML
    private RadioButton radioTranslateZ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        scene3DController = new Scene3DController();
        scene3DController.setupSubscene(subScene3D, subScene3D.getWidth(), subScene3D.getHeight());
//        subScene3D.setManaged(false);
        subScene3D.heightProperty().bind(SCENE_PARENT.heightProperty());
        subScene3D.widthProperty().bind(SCENE_PARENT.widthProperty());
        System.out.println("SCENE_PARENT = " + SCENE_PARENT);
//        SCENE_PARENT.setPrefSize(200, 200);
//        SCENE_PARENT.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        subScene3D.setHeight(200);
//        subScene3D.setWidth(200);
        scene3DController.getCenterWinTranslate().xProperty().bind(SCENE_PARENT.widthProperty().divide(3));
        scene3DController.getCenterWinTranslate().yProperty().bind(SCENE_PARENT.heightProperty().multiply(2.0 / 3.0));
        SCENE_PARENT.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("width newValue = " + newValue);
        });
        
        SCENE_PARENT.heightProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("height newValue = " + newValue);
        });
        
        scene3DController.getCenterWinTranslate().yProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("y newValue = " + newValue);
        });
        scene3DController.getCenterWinTranslate().xProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            System.out.println("x newValue = " + newValue);
        });
        xNegButton.setOnAction(e -> scene3DController.xNegView());
        xPlusButton.setOnAction(e -> scene3DController.xPosView());
        yNegButton.setOnAction(e -> scene3DController.yNegView());
        yPlusButton.setOnAction(e -> scene3DController.yPosView());
        zNegButton.setOnAction(e -> scene3DController.zNegView());
        zPlusButton.setOnAction(e -> scene3DController.zPosView());
        final ToggleGroup dragModeToggleGroup = new ToggleGroup();
        radioRotateXY.setToggleGroup(dragModeToggleGroup);
        radioRotateZ.setToggleGroup(dragModeToggleGroup);
        radioTranslateXY.setToggleGroup(dragModeToggleGroup);
        radioTranslateZ.setToggleGroup(dragModeToggleGroup);
        dragModeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                View3DDragEnum de = scene3DController.getDragEnum();
                View3DDragEnum orig_de = de;
                if (newValue == radioRotateXY) {
                    de = View3DDragEnum.ROT_XY;
                } else if (newValue == radioRotateZ) {
                    de = View3DDragEnum.ROT_Z;
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
    }

}
