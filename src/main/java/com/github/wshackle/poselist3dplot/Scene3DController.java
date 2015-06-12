/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wshackle.poselist3dplot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import rcs.posemath.PmRpy;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class Scene3DController {

    private static Optional<String> transformToOptString(Transform transform) {
        if (transform instanceof Translate) {
            Translate t = (Translate) transform;
            if (Math.max(Math.max(Math.abs(t.getX()), Math.abs(t.getY())), Math.abs(t.getZ())) < Double.MIN_NORMAL) {
                return Optional.empty();
            }
            return Optional.of(String.format("T(%+.3g,%+.3g,%+.3g)", t.getX(), t.getY(), t.getZ()));
        } else if (transform instanceof Rotate) {
            Rotate r = (Rotate) transform;
            if (Math.abs(r.getAngle()) < Double.MIN_NORMAL) {
                return Optional.empty();
            }
            if (r.getAxis().equals(Rotate.X_AXIS)) {
                return Optional.of(String.format("Rx(%+.3g)", r.getAngle()));
            } else if (r.getAxis().equals(Rotate.Y_AXIS)) {
                return Optional.of(String.format("Ry(%+.3g)", r.getAngle()));
            } else if (r.getAxis().equals(Rotate.Z_AXIS)) {
                return Optional.of(String.format("Rz(%+.3g)", r.getAngle()));
            }
        }
        return Optional.empty();
    }

    private Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    private Translate t = new Translate();
    private Scene scene3D = null;
    private Translate tmain = new Translate();
    private Rotate rxmain = new Rotate(0, Rotate.X_AXIS);
    private Rotate rymain = new Rotate(0, Rotate.Y_AXIS);
    private Translate ip = new Translate();
    private Node contentGroup = null;
    private Translate centerWinTranslate = new Translate();
    private Translate p = new Translate();
    private MouseEvent lastMouseEvent = null;
    private PerspectiveCamera camera = null;
    private Rotate rx = new Rotate(0, Rotate.X_AXIS);
    private Scale s = new Scale();
    private Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    private Rotate rzmain = new Rotate(0, Rotate.Z_AXIS);
    private HashMap<Track, Group> trackGroupMap = new HashMap<>();
    private HashMap<List<Track>, Group> trackListGroupMap = new HashMap<>();
    private Group TracksGroup = null;
    private HashMap<Track, Group> trackCurPosGroupMap = new HashMap<>();
    private View3DDragEnum dragEnum = View3DDragEnum.UNDEFINED;
    private double distScale = 100.0;
    private boolean showRotationFrames = false;

    private void updateTracksListsFX(final List<List<Track>> tracksList) {
        if (null == tracksList || tracksList.size() < 1) {
            clear();
            return;
        }
        Set<Group> matchedGroups = new HashSet<>();
        for (List<Track> lt : tracksList) {
            Group g = getTrackListGroupMap().get(lt);
            if (null == g) {
                g = new Group();
                TracksGroup.getChildren().add(g);
                getTrackListGroupMap().put(lt, g);
            }
            if (!TracksGroup.getChildren().contains(g)) {
                TracksGroup.getChildren().add(g);
            }
            this.updateTrackList(g, lt);
            matchedGroups.add(g);
        }
        for (Map.Entry<List<Track>, Group> entry : getTrackListGroupMap().entrySet()) {
            if (!matchedGroups.contains(entry.getValue())) {
                entry.getValue().getChildren().clear();
                getTrackListGroupMap().remove(entry.getKey());
            }
        }
    }

    public Scene create3DScene(int w, int h, View3DPlotJPanel view3DPlotJPanel) {
        Group root = new Group();
        root.setDepthTest(DepthTest.ENABLE);
        scene3D = new Scene(root, w, h, true);
        camera = new PerspectiveCamera();
        scene3D.setCamera(camera);
        getCenterWinTranslate().setX(w / 3);
        getCenterWinTranslate().setY((2 * h) / 3);
        root.getTransforms().addAll(getCenterWinTranslate(), new Rotate(180, Rotate.X_AXIS));
        setContentGroup(this.create3dContent());
        root.getChildren().addAll(getContentGroup());
        scene3D.setOnMouseDragged(this::handleFxRootMouseEvent);
        scene3D.setOnMouseReleased(this::handlePanelMouseReleasExitedEvent);
        scene3D.setOnMouseExited(this::handlePanelMouseReleasExitedEvent);
        scene3D.setOnKeyPressed(this::handleKeyEvent);
        scene3D.setOnKeyTyped(this::handleKeyEvent);
        return scene3D;
    }

    private void resetTransforms() {
        getContentGroup().getTransforms().clear();
        getContentGroup().getTransforms().addAll(getTmain(), getRxmain(), getRymain(), getRzmain());
        setupTransforms(getDragEnum());
    }

    /**
     *
     * @param redBox the value of redBox
     */
    private Group createAxis(double scale) {
        Box redBox = new Box(scale, scale / 10.0, scale / 10.0);
        redBox.setTranslateX(scale / 2.0);
        PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.RED);
        redMaterial.setDiffuseColor(Color.RED);
        redBox.setMaterial(redMaterial);
        Box blueBox = new Box(scale / 10.0, scale, scale / 10.0);
        blueBox.setTranslateY(scale / 2.0);
        PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setSpecularColor(Color.BLUE);
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueBox.setMaterial(blueMaterial);
        Box greenBox = new Box(scale / 10.0, scale / 10.0, scale);
        greenBox.setTranslateZ(scale / 2.0);
        PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setSpecularColor(Color.GREEN);
        greenMaterial.setDiffuseColor(Color.GREEN);
        greenBox.setMaterial(greenMaterial);
        Group axisGroup = new Group(redBox, blueBox, greenBox);
        return axisGroup;
    }

    private void updateTrack(Group trackGroup, Group curPosGroup, Track track) {
        if (null == track) {
            return;
        }
        int gsz = trackGroup.getChildren().size();
        final int cur_tg_index = track.cur_time_index * 2 - 1;
        if (cur_tg_index >= 0 && cur_tg_index < (gsz - 1)) {
            trackGroup.getChildren().remove(cur_tg_index, gsz - 1);
        }
        int start_index = trackGroup.getChildren().size() / 2;
        if (start_index < 1) {
            start_index = 1;
        }
        if (null == track.getData() || track.getData().size() < start_index + 1) {
            return;
        }
        Color pointColor = new Color(track.pointColor.getRed() / 255.0, track.pointColor.getGreen() / 255.0, track.pointColor.getBlue() / 255.0, 1);
        Color lineColor = new Color(track.lineColor.getRed() / 255.0, track.lineColor.getGreen() / 255.0, track.lineColor.getBlue() / 255.0, 1);
        PhongMaterial ptMaterial = new PhongMaterial();
        ptMaterial.setSpecularColor(pointColor);
        ptMaterial.setDiffuseColor(pointColor);
        PhongMaterial lineMaterial = new PhongMaterial();
        lineMaterial.setSpecularColor(lineColor);
        lineMaterial.setDiffuseColor(lineColor);
        if (null != track.currentPoint) {
            if (curPosGroup.getChildren().size() < 2) {
                Sphere sphere = new Sphere(12.0);
                sphere.setMaterial(ptMaterial);
                Text txt = new Text(track.name);
                txt.setTranslateX(20.0);
                txt.setTranslateY(20.0);
                txt.setTranslateZ(20.0);
                txt.setRotate(180.0);
                txt.setRotationAxis(Rotate.X_AXIS);
                curPosGroup.getChildren().addAll(sphere, txt);
            }
            curPosGroup.setTranslateX(track.currentPoint.x * getDistScale());
            curPosGroup.setTranslateY(track.currentPoint.y * getDistScale());
            curPosGroup.setTranslateZ(track.currentPoint.z * getDistScale());
        }
        TrackPoint last_tp = track.getData().get(start_index - 1);
        for (int i = start_index; i < track.getData().size() && i < track.cur_time_index; i++) {
            TrackPoint tp = track.getData().get(i);
            double dist = last_tp.distance(tp);
            if (dist < getDistScale() / 1000.0) {
                continue;
            }
            double distXY = Math.sqrt((tp.x - last_tp.x) * (tp.x - last_tp.x) + (tp.y - last_tp.y) * (tp.y - last_tp.y));
            Cylinder cyl = new Cylinder(1.0, dist * getDistScale());
            Rotation rot = new Rotation(Vector3D.PLUS_J, new Vector3D(tp.x - last_tp.x, tp.y - last_tp.y, tp.z - last_tp.z));
            cyl.getTransforms().addAll(new Translate(last_tp.x * getDistScale(), last_tp.y * getDistScale(), last_tp.z * getDistScale()), new Rotate(Math.toDegrees(rot.getAngle()), new Point3D(rot.getAxis().getX(), rot.getAxis().getY(), rot.getAxis().getZ())), new Translate(0, dist * getDistScale() / 2.0, 0));
            cyl.setMaterial(lineMaterial);
            Sphere sphere = new Sphere(5.0);
            sphere.setTranslateX(tp.x * getDistScale());
            sphere.setTranslateY(tp.y * getDistScale());
            sphere.setTranslateZ(tp.z * getDistScale());
            sphere.setMaterial(ptMaterial);
            trackGroup.getChildren().addAll(sphere, cyl);
            if (null != tp.getRpy() && isShowRotationFrames()) {
                Group axisGroup = createAxis(15.0);
                PmRpy rpy = tp.getRpy();
                axisGroup.getTransforms().addAll(new Translate(tp.x * getDistScale(), tp.y * getDistScale(), tp.z * getDistScale()), new Rotate(Math.toDegrees(rpy.y), Rotate.Z_AXIS), new Rotate(Math.toDegrees(rpy.p), Rotate.Y_AXIS), new Rotate(Math.toDegrees(rpy.r), Rotate.X_AXIS));
                trackGroup.getChildren().addAll(axisGroup);
            }
            last_tp = tp;
        }
    }

    public void updateTracksList(final List<List<Track>> tracksList) {
        Platform.runLater(() -> updateTracksListsFX(tracksList));
    }

    private void updateTrackList(Group g, List<Track> lt) {
        if (null == lt) {
            return;
        }
        Set<Group> matchedGroups = new HashSet<>();
        for (Track track : lt) {
            Group trackGroup = getTrackGroupMap().get(track);
            if (trackGroup == null) {
                trackGroup = new Group();
                g.getChildren().add(trackGroup);
                getTrackGroupMap().put(track, trackGroup);
            }
            if (!g.getChildren().contains(trackGroup)) {
                g.getChildren().add(trackGroup);
            }
            matchedGroups.add(trackGroup);
            Group curPosGroup = getTrackCurPosGroupMap().get(track);
            if (curPosGroup == null) {
                curPosGroup = new Group();
                g.getChildren().add(curPosGroup);
                getTrackCurPosGroupMap().put(track, curPosGroup);
            }
            if (!g.getChildren().contains(curPosGroup)) {
                g.getChildren().add(curPosGroup);
            }
            matchedGroups.add(curPosGroup);
            updateTrack(trackGroup, curPosGroup, track);
        }
        for (Map.Entry<Track, Group> entry : getTrackGroupMap().entrySet()) {
            if (!matchedGroups.contains(entry.getValue())) {
                entry.getValue().getChildren().clear();
                getTrackGroupMap().remove(entry.getKey());
            }
        }
        for (Map.Entry<Track, Group> entry : getTrackCurPosGroupMap().entrySet()) {
            if (!matchedGroups.contains(entry.getValue())) {
                entry.getValue().getChildren().clear();
                getTrackCurPosGroupMap().remove(entry.getKey());
            }
        }
    }

    /**
     *
     */
    private Node create3dContent() {
        TracksGroup = new Group();
        Group axisGroup = createAxis(100);
        Text xText = new Text("X");
        xText.setTranslateX(120.0);
        xText.getTransforms().add(new Rotate(180.0, Rotate.X_AXIS));
        Text yText = new Text("Y");
        yText.setTranslateY(120.0);
        yText.getTransforms().add(new Rotate(180.0, Rotate.X_AXIS));
        Text zText = new Text("Z");
        zText.setTranslateZ(120.0);
        zText.getTransforms().add(new Rotate(180.0, Rotate.X_AXIS));
        Group g = new Group(TracksGroup, axisGroup, xText, yText, zText);
        return g;
    }

    private void handlePanelMouseReleasExitedEvent(javafx.scene.input.MouseEvent event) {
        setLastMouseEvent(null);
    }

    private void handleFxRootMouseEvent(javafx.scene.input.MouseEvent me) {
        if (me.getEventType() == MouseEvent.MOUSE_RELEASED) {
            setLastMouseEvent(null);
            return;
        }
        if (me.getEventType() == MouseEvent.MOUSE_EXITED) {
            setLastMouseEvent(null);
            return;
        }
        if (null == getLastMouseEvent()) {
            setLastMouseEvent(me);
            return;
        }
        double mouseNewX = getLastMouseEvent().getX();
        double mouseNewY = getLastMouseEvent().getY();
        double mouseOldX = mouseNewX;
        double mouseOldY = mouseNewY;
        mouseNewX = me.getX();
        mouseNewY = me.getY();
        setLastMouseEvent(me);
        double mouseDeltaX = mouseNewX - mouseOldX;
        double mouseDeltaY = mouseNewY - mouseOldY;
        transformWMouseDelta(mouseDeltaX, mouseDeltaY, getDragEnum());
    }

    private void handleKeyEvent(javafx.scene.input.KeyEvent event) {
        if (event.isAltDown()) {
            double r = event.isShiftDown() ? 1 : -1;
            switch (event.getCode()) {
                case X:
                    rx.setAngle(rx.getAngle() + r);
                    break;
                case Y:
                    ry.setAngle(ry.getAngle() + r);
                    break;
                case Z:
                    rz.setAngle(rz.getAngle() + r);
                    break;
            }
        } else {
            double tinc = event.isShiftDown() ? 10 : -10;
            switch (event.getCode()) {
                case X:
                    t.setX(t.getX() + tinc);
                    break;
                case Y:
                    t.setY(t.getY() + tinc);
                    break;
                case Z:
                    t.setZ(t.getZ() + tinc);
                    break;
            }
        }
    }

    private boolean leftMultiplySelected = true;

    /**
     * Get the value of leftMultiplySelected
     *
     * @return the value of leftMultiplySelected
     */
    public boolean isLeftMultiplySelected() {
        return leftMultiplySelected;
    }

    /**
     * Set the value of leftMultiplySelected
     *
     * @param leftMultiplySelected new value of leftMultiplySelected
     */
    public void setLeftMultiplySelected(boolean leftMultiplySelected) {
        this.leftMultiplySelected = leftMultiplySelected;
    }

    public void setupTransforms(View3DDragEnum _dragEnum) {
        switch (_dragEnum) {
            case UNDEFINED:
                break;
            case ROT_XY:
                this.setRx(new Rotate(0, Rotate.X_AXIS));
                this.setRy(new Rotate(0, Rotate.Y_AXIS));
                if (leftMultiplySelected) {
                    this.getContentGroup().getTransforms().add(0, getRy());
                    this.getContentGroup().getTransforms().add(0, getRx());
                } else {
                    this.getContentGroup().getTransforms().add(getRy());
                    this.getContentGroup().getTransforms().add(getRx());
                }
                break;
            case ROT_Z:
                this.setRz(new Rotate(0, Rotate.Z_AXIS));
                if (leftMultiplySelected) {
                    this.getContentGroup().getTransforms().add(0, getRz());
                } else {
                    this.getContentGroup().getTransforms().add(getRz());
                }
                break;
            case TRAN_XY:
                this.setT(new Translate());
                if (leftMultiplySelected) {
                    this.getContentGroup().getTransforms().add(0, getT());
                } else {
                    this.getContentGroup().getTransforms().add(getT());
                }
                break;
            case TRAN_Z:
                this.setT(new Translate());
                if (leftMultiplySelected) {
                    this.getContentGroup().getTransforms().add(0, getT());
                } else {
                    this.getContentGroup().getTransforms().add(getT());
                }
                break;
            case SCALE:
                this.setS(new Scale());
                if (leftMultiplySelected) {
                    this.getContentGroup().getTransforms().add(0, getS());
                } else {
                    this.getContentGroup().getTransforms().add(getS());
                }
                break;
        }
        updateTransformsText();
    }

    /**
     *
     * @param mouseDeltaX the value of mouseDeltaX
     * @param mouseDeltaY the value of mouseDeltaY
     * @param dragEnum the value of dragEnum
     */
    private void transformWMouseDelta(double mouseDeltaX, double mouseDeltaY, View3DDragEnum dragEnum) {
        double mouseDelta = Math.sqrt(mouseDeltaX * mouseDeltaX + mouseDeltaY * mouseDeltaY);
        if (mouseDelta > 20.0) {
            return;
        }
        if (dragEnum == View3DDragEnum.SCALE) {
            double scale = s.getX();
            double newScale = scale + mouseDeltaX * 0.01;
            s.setX(newScale);
            s.setY(newScale);
            s.setZ(newScale);
        } else if (dragEnum == View3DDragEnum.ROT_Z) {
            rz.setAngle(rz.getAngle() + mouseDeltaY * 0.75);
        } else if (dragEnum == View3DDragEnum.TRAN_Z) {
            t.setZ(t.getZ() + mouseDeltaY);
        } else if (dragEnum == View3DDragEnum.TRAN_XY) {
            t.setX(t.getX() + mouseDeltaX);
            t.setY(t.getY() - mouseDeltaY);
        } else if (dragEnum == View3DDragEnum.ROT_XY) {
            ry.setAngle(ry.getAngle() + mouseDeltaX * 0.75);
            rx.setAngle(rx.getAngle() + mouseDeltaY * 0.75);
        }
        updateTransformsText();
    }

    private String transformText;

    public static final String PROP_TRANSFORMTEXT = "transformText";

    /**
     * Get the value of transformText
     *
     * @return the value of transformText
     */
    public String getTransformText() {
        return transformText;
    }

    /**
     * Set the value of transformText
     *
     * @param transformText new value of transformText
     */
    private void setTransformText(String transformText) {
        String oldTransformText = this.transformText;
        this.transformText = transformText;
        propertyChangeSupport.firePropertyChange(PROP_TRANSFORMTEXT, oldTransformText, transformText);
    }

    private transient final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private void updateTransformsText() {
        final List<Transform> transforms = this.getContentGroup().getTransforms();
        this.setTransformText(transformsToString(transforms));
    }

    private String transformsToString(List<? extends Transform> transforms) {
        return transforms
                .stream()
                .map(Scene3DController::transformToOptString)
                .flatMap((Optional<String> x) -> x.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.joining("*"));
    }

    void yPosView() {
        t.setX(0);
        t.setY(0);
        t.setZ(0);
        rx.setAngle(0);
        ry.setAngle(0);
        rz.setAngle(0);
        s.setX(1);
        s.setY(1);
        s.setZ(1);
        tmain.setX(0);
        tmain.setY(0);
        tmain.setZ(0);
        rxmain.setAngle(-90);
        rymain.setAngle(90);
        rzmain.setAngle(0);
        resetTransforms();
    }

    void xPosView() {
        t.setX(0);
        t.setY(0);
        t.setZ(0);
        rx.setAngle(0);
        ry.setAngle(0);
        rz.setAngle(0);
        s.setX(1);
        s.setY(1);
        s.setZ(1);
        tmain.setX(0);
        tmain.setY(0);
        tmain.setZ(0);
        rxmain.setAngle(-90);
        rymain.setAngle(0);
        rzmain.setAngle(90);
        resetTransforms();
    }

    void yNegView() {
        t.setX(0);
        t.setY(0);
        t.setZ(0);
        rx.setAngle(0);
        ry.setAngle(0);
        rz.setAngle(0);
        s.setX(1);
        s.setY(1);
        s.setZ(1);
        tmain.setX(0);
        tmain.setY(0);
        tmain.setZ(0);
        rxmain.setAngle(+90);
        rymain.setAngle(90);
        rzmain.setAngle(0);
        resetTransforms();
    }

    void xNegView() {
        t.setX(0);
        t.setY(0);
        t.setZ(0);
        rx.setAngle(0);
        ry.setAngle(0);
        rz.setAngle(0);
        s.setX(1);
        s.setY(1);
        s.setZ(1);
        tmain.setX(0);
        tmain.setY(0);
        tmain.setZ(0);
        rxmain.setAngle(-90);
        rymain.setAngle(0);
        rzmain.setAngle(-90);
        resetTransforms();
    }

    void zNegView() {
        t.setX(0);
        t.setY(0);
        t.setZ(0);
        rx.setAngle(0);
        ry.setAngle(0);
        rz.setAngle(0);
        s.setX(1);
        s.setY(1);
        s.setZ(1);
        tmain.setX(0);
        tmain.setY(0);
        tmain.setZ(0);
        rxmain.setAngle(0);
        rymain.setAngle(0);
        rzmain.setAngle(0);
        resetTransforms();
    }

    void zPosView() {
        t.setX(0);
        t.setY(0);
        t.setZ(0);
        rx.setAngle(0);
        ry.setAngle(0);
        rz.setAngle(0);
        s.setX(1);
        s.setY(1);
        s.setZ(1);
        tmain.setX(0);
        tmain.setY(0);
        tmain.setZ(0);
        rxmain.setAngle(0);
        rymain.setAngle(180);
        rzmain.setAngle(0);
    }

    public void updateSize(final int w, final int h) {
        Platform.runLater(() -> {
            getCenterWinTranslate().setX(w / 3);
            getCenterWinTranslate().setY((2 * h) / 3);
        });
    }

    public void clear() {
        for (Group g : getTrackListGroupMap().values()) {
            g.getChildren().clear();
        }
        getTrackListGroupMap().clear();
        for (Group g : getTrackGroupMap().values()) {
            g.getChildren().clear();
        }
        getTrackGroupMap().clear();
        for (Group g : getTrackCurPosGroupMap().values()) {
            g.getChildren().clear();
        }
        getTrackCurPosGroupMap().clear();
        xNegView();
    }

    /**
     *
     * @param tracksList the value of tracksList
     */
    public void refreshScene(List<List<Track>> tracksList) {
        Platform.runLater(this::clear);
        Platform.runLater(() -> {
            updateTracksList(tracksList);
        });
    }

    /**
     * @return the rz
     */
    public Rotate getRz() {
        return rz;
    }

    /**
     * @param rz the rz to set
     */
    public void setRz(Rotate rz) {
        this.rz = rz;
    }

    /**
     * @return the t
     */
    public Translate getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(Translate t) {
        this.t = t;
    }

    /**
     * @return the tmain
     */
    public Translate getTmain() {
        return tmain;
    }

    /**
     * @param tmain the tmain to set
     */
    public void setTmain(Translate tmain) {
        this.tmain = tmain;
    }

    /**
     * @return the rxmain
     */
    public Rotate getRxmain() {
        return rxmain;
    }

    /**
     * @param rxmain the rxmain to set
     */
    public void setRxmain(Rotate rxmain) {
        this.rxmain = rxmain;
    }

    /**
     * @return the rymain
     */
    public Rotate getRymain() {
        return rymain;
    }

    /**
     * @param rymain the rymain to set
     */
    public void setRymain(Rotate rymain) {
        this.rymain = rymain;
    }

    /**
     * @return the ip
     */
    public Translate getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(Translate ip) {
        this.ip = ip;
    }

    /**
     * @return the contentGroup
     */
    public Node getContentGroup() {
        return contentGroup;
    }

    /**
     * @param contentGroup the contentGroup to set
     */
    public void setContentGroup(Node contentGroup) {
        this.contentGroup = contentGroup;
    }

    /**
     * @return the centerWinTranslate
     */
    public Translate getCenterWinTranslate() {
        return centerWinTranslate;
    }

    /**
     * @param centerWinTranslate the centerWinTranslate to set
     */
    public void setCenterWinTranslate(Translate centerWinTranslate) {
        this.centerWinTranslate = centerWinTranslate;
    }

    /**
     * @return the p
     */
    public Translate getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(Translate p) {
        this.p = p;
    }

    /**
     * @return the lastMouseEvent
     */
    public MouseEvent getLastMouseEvent() {
        return lastMouseEvent;
    }

    /**
     * @param lastMouseEvent the lastMouseEvent to set
     */
    public void setLastMouseEvent(MouseEvent lastMouseEvent) {
        this.lastMouseEvent = lastMouseEvent;
    }

    /**
     * @return the rx
     */
    public Rotate getRx() {
        return rx;
    }

    /**
     * @param rx the rx to set
     */
    public void setRx(Rotate rx) {
        this.rx = rx;
    }

    /**
     * @return the s
     */
    public Scale getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(Scale s) {
        this.s = s;
    }

    /**
     * @return the ry
     */
    public Rotate getRy() {
        return ry;
    }

    /**
     * @param ry the ry to set
     */
    public void setRy(Rotate ry) {
        this.ry = ry;
    }

    /**
     * @return the rzmain
     */
    public Rotate getRzmain() {
        return rzmain;
    }

    /**
     * @param rzmain the rzmain to set
     */
    public void setRzmain(Rotate rzmain) {
        this.rzmain = rzmain;
    }

    /**
     * @return the trackGroupMap
     */
    public HashMap<Track, Group> getTrackGroupMap() {
        return trackGroupMap;
    }

    /**
     * @param trackGroupMap the trackGroupMap to set
     */
    public void setTrackGroupMap(HashMap<Track, Group> trackGroupMap) {
        this.trackGroupMap = trackGroupMap;
    }

    /**
     * @return the trackListGroupMap
     */
    public HashMap<List<Track>, Group> getTrackListGroupMap() {
        return trackListGroupMap;
    }

    /**
     * @param trackListGroupMap the trackListGroupMap to set
     */
    public void setTrackListGroupMap(HashMap<List<Track>, Group> trackListGroupMap) {
        this.trackListGroupMap = trackListGroupMap;
    }

    /**
     * @return the trackCurPosGroupMap
     */
    public HashMap<Track, Group> getTrackCurPosGroupMap() {
        return trackCurPosGroupMap;
    }

    /**
     * @param trackCurPosGroupMap the trackCurPosGroupMap to set
     */
    public void setTrackCurPosGroupMap(HashMap<Track, Group> trackCurPosGroupMap) {
        this.trackCurPosGroupMap = trackCurPosGroupMap;
    }

    /**
     * @return the dragEnum
     */
    public View3DDragEnum getDragEnum() {
        return dragEnum;
    }

    /**
     * @param dragEnum the dragEnum to set
     */
    public void setDragEnum(View3DDragEnum dragEnum) {
        this.dragEnum = dragEnum;
    }

    /**
     * @return the distScale
     */
    public double getDistScale() {
        return distScale;
    }

    /**
     * @param distScale the distScale to set
     */
    public void setDistScale(double distScale) {
        this.distScale = distScale;
    }

    /**
     * @return the showRotationFrames
     */
    public boolean isShowRotationFrames() {
        return showRotationFrames;
    }

    /**
     * @param showRotationFrames the showRotationFrames to set
     */
    public void setShowRotationFrames(boolean showRotationFrames) {
        this.showRotationFrames = showRotationFrames;
    }

}