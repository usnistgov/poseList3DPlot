/*
 * This is public domain software, however it is preferred
 * that the following disclaimers be attached.
 * 
 * Software Copywrite/Warranty Disclaimer
 * 
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of their
 * official duties. Pursuant to title 17 Section 105 of the United States
 * Code this software is not subject to copyright protection and is in the
 * public domain. NIST Real-Time Control System software is an experimental
 * system. NIST assumes no responsibility whatsoever for its use by other
 * parties, and makes no guarantees, expressed or implied, about its
 * quality, reliability, or any other characteristic. We would appreciate
 * acknowledgement if the software is used. This software can be
 * redistributed and/or modified freely provided that any derivative works
 * bear some notice that they are derived from it, and any modified
 * versions bear some notice that they have been modified.
 * 
 */
package com.github.wshackle.poselist3dplot;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.X;
import static javafx.scene.input.KeyCode.Y;
import static javafx.scene.input.KeyCode.Z;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javax.swing.SwingUtilities;
import rcs.posemath.PmRpy;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class View3DPlotJPanel extends javax.swing.JPanel {

    private JFXPanel fxpanel = null;
    private PerspectiveCamera camera = null;
    private Scene scene3D = null;
    javax.swing.Timer pollButtonTimer = null;

    /**
     * Creates new form View3DPlotJPanel
     */
    public View3DPlotJPanel() {
        initComponents();
        try {
            fxpanel = new JFXPanel();
            //fxpanel.setPreferredSize(this.jPanel1.getPreferredSize());
            fxpanel.setBackground(java.awt.Color.red);
            fxpanel.setVisible(true);

//            fxpanel.setScene(this.create3DScene(this.jPanel1.getPreferredSize().width,
//                    this.jPanel1.getPreferredSize().height));
            //this.jPanel1.add(fxpanel);
            this.jPanel1.setLayout(new BorderLayout());
            this.jPanel1.add(fxpanel, BorderLayout.CENTER);
//            this.jPanel1.addMouseMotionListener(new MouseMotionListener() {
//
//                @Override
//                public void mouseDragged(java.awt.event.MouseEvent e) {
//                    mouseDraggedInSwingJPanel(e);
//                }
//
//                @Override
//                public void mouseMoved(java.awt.event.MouseEvent e) {
////                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
//            });
            this.jPanel1.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    lastSwingJpanelMouseEvent = null;
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    lastSwingJpanelMouseEvent = null;
                }
            });
            // create JavaFX scene
            Platform.runLater(new Runnable() {
                public void run() {
                    initFxScene();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            setDragEnum(View3DDragEnum.ROT_XY);
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void mouseDraggedInSwingJPanel(java.awt.event.MouseEvent e) {
//        if (null == lastSwingJpanelMouseEvent) {
//            lastSwingJpanelMouseEvent = e;
//            return;
//        }
//        double maxSize = Math.max(Math.max(this.jPanel1.getSize().height, this.jPanel1.getSize().width),
//                Math.max(this.jPanel1.getPreferredSize().height, this.jPanel1.getPreferredSize().width));
//        final double mouseDeltaX = ((double) e.getX() - lastSwingJpanelMouseEvent.getX()) / maxSize;
//        final double mouseDeltaY = ((double) e.getY() - lastSwingJpanelMouseEvent.getY()) / maxSize;
//        double halfX = this.jPanel1.getSize().width / 2.0;
//        double halfY = this.jPanel1.getSize().height / 2.0;
//        final double mouseDeltaTheta
//                = Math.toDegrees(
//                        Math.atan2(e.getY() - halfY, e.getX() - halfX)
//                        - Math.atan2(lastSwingJpanelMouseEvent.getY() - halfY, lastSwingJpanelMouseEvent.getX() - halfX)
//                );
//        Platform.runLater(new Runnable() {
//
//            @Override
//            public void run() {
//                transformWMouseDelta(mouseDeltaX, mouseDeltaY, mouseDeltaTheta,
//                        dragEnum);
//            }
//        });
//    }
    private java.awt.event.MouseEvent lastSwingJpanelMouseEvent = null;

    private void initFxScene() {
        fxpanel.setScene(this.create3DScene(this.jPanel1.getPreferredSize().width,
                this.jPanel1.getPreferredSize().height));
    }

    Translate t = new Translate();
    Translate p = new Translate();
    Translate ip = new Translate();
    Rotate rx = new Rotate(0, Rotate.X_AXIS);
    Rotate ry = new Rotate(0, Rotate.Y_AXIS);
    Rotate rz = new Rotate(0, Rotate.Z_AXIS);
    Scale s = new Scale();
    Translate tmain = new Translate();
    Rotate rxmain = new Rotate(0, Rotate.X_AXIS);
    Rotate rymain = new Rotate(0, Rotate.Y_AXIS);
    Rotate rzmain = new Rotate(0, Rotate.Z_AXIS);

    Node contentGroup = null;

    private Scene create3DScene(int w, int h) {
        Group root = new Group();
        root.setDepthTest(DepthTest.ENABLE);
//        primaryStage.setResizable(false);
        scene3D = new Scene(root, w, h, true);
        camera = new PerspectiveCamera();
        scene3D.setCamera(camera);
        root.getTransforms().addAll(
                new Translate(400 / 2, 400 / 2),
                new Rotate(180, Rotate.X_AXIS)
        );
        contentGroup = this.create3dContent();
        //contentGroup.getTransforms().addAll( t, rx, ry, rz, s, tmain, rxmain, rymain, rzmain);
        root.getChildren().addAll(contentGroup);

        scene3D.setOnMouseDragged(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handleFxRootMouseEvent(event);
            }
        });
        scene3D.setOnMouseReleased(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handlePanelMouseReleasExitedEvent(event);
            }
        });
        scene3D.setOnMouseExited(new javafx.event.EventHandler<javafx.scene.input.MouseEvent>() {

            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                handlePanelMouseReleasExitedEvent(event);
            }
        });
        scene3D.setOnKeyPressed(new javafx.event.EventHandler<javafx.scene.input.KeyEvent>() {

            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                handleKeyEvent(event);
            }
        });
        scene3D.setOnKeyTyped(new javafx.event.EventHandler<javafx.scene.input.KeyEvent>() {

            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                handleKeyEvent(event);
            }
        });

        return scene3D;
    }

    public void handleKeyEvent(javafx.scene.input.KeyEvent event) {
        //System.out.println("event = " + event);
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
//            System.out.println("tinc = " + tinc);
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

    javafx.scene.input.MouseEvent lastMouseEvent = null;

    private void handlePanelMouseReleasExitedEvent(javafx.scene.input.MouseEvent event) {
        this.lastMouseEvent = null;
    }

    private void handleFxRootMouseEvent(javafx.scene.input.MouseEvent me) {
//        System.out.println("me = " + me);
        if (me.getEventType() == javafx.scene.input.MouseEvent.MOUSE_RELEASED) {
            lastMouseEvent = null;
            return;
        }
        if (me.getEventType() == javafx.scene.input.MouseEvent.MOUSE_EXITED) {
            lastMouseEvent = null;
            return;
        }
        if (null == lastMouseEvent) {
            lastMouseEvent = me;
            return;
        }
        //System.out.println("me = " + me);
        double mouseNewX = lastMouseEvent.getX();
        double mouseNewY = lastMouseEvent.getY();
        double mouseOldX = mouseNewX;
        double mouseOldY = mouseNewY;
        mouseNewX = me.getX();
        mouseNewY = me.getY();
        lastMouseEvent = me;
        double mouseDeltaX = mouseNewX - mouseOldX;
        //System.out.println("mouseDeltaX = " + mouseDeltaX);
        double mouseDeltaY = mouseNewY - mouseOldY;
//        double halfX = scene3D.getWidth() / 2.0;
//        double halfY = scene3D.getHeight() / 2.0;
//        double oldAngle = Math.atan2(mouseOldY - halfY, mouseOldX - halfX);
//        double newAngle = Math.atan2(mouseNewY - halfY, mouseNewX - halfX);
//        double angleDiff = newAngle -oldAngle;
//        if(angleDiff > Math.PI) {
//            angleDiff -= 2*Math.PI;
//        }
//        if(angleDiff < -Math.PI) {
//            angleDiff += 2*Math.PI;
//        }
//        double mouseDeltaTheta
//                = Math.signum(newAngle-oldAngle) * Math.sqrt(mouseDeltaX * mouseDeltaX + mouseDeltaY * mouseDeltaY / 2.0);
        transformWMouseDelta(mouseDeltaX, mouseDeltaY, dragEnum);
    }

    /**
     *
     * @param mouseDeltaX the value of mouseDeltaX
     * @param mouseDeltaY the value of mouseDeltaY
     * @param dragEnum the value of dragEnum
     */
    public void transformWMouseDelta(double mouseDeltaX, double mouseDeltaY, View3DDragEnum dragEnum) {
        //System.out.println("mouseDeltaY = " + mouseDeltaY);
        double mouseDelta = Math.sqrt(mouseDeltaX * mouseDeltaX + mouseDeltaY * mouseDeltaY);
        //System.out.println("mouseDelta = " + mouseDelta);
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
//        System.out.println("contentGroup.getTransforms() = " + contentGroup.getTransforms());
    }

    private List<List<Track>> TracksList;

    /**
     * Get the value of TracksList
     *
     * @return the value of TracksList
     */
    public List<List<Track>> getTracksList() {
        return TracksList;
    }

    /**
     * Set the value of TracksList
     *
     * @param TracksList new value of TracksList
     */
    public void setTracksList(List<List<Track>> TracksList) {
        this.TracksList = TracksList;
        updateTracksList();
    }

    private final Runnable runUpdateTracsList = new Runnable() {

        @Override
        public void run() {
            updateTracksListsFX();
        }
    };

    public void updateTracksList() {
        Platform.runLater(runUpdateTracsList);
    }

    public void updateTrack(Group trackGroup, Group curPosGroup, Track track) {
        if (null == track) {
            return;
        }
        int gsz = trackGroup.getChildren().size();
        final int cur_tg_index = (track.cur_time_index * 2 - 1);
        if (cur_tg_index >= 0
                && cur_tg_index < (gsz - 1)) {
            trackGroup.getChildren().remove(cur_tg_index, gsz - 1);
        }
        int start_index = trackGroup.getChildren().size() / 2;
        if (start_index < 1) {
            start_index = 1;
        }
        if (null == track.getData() || track.getData().size() < start_index + 1) {
            return;
        }
        javafx.scene.paint.Color pointColor
                = new javafx.scene.paint.Color(
                        track.pointColor.getRed() / 255.0,
                        track.pointColor.getGreen() / 255.0,
                        track.pointColor.getBlue() / 255.0,
                        1);
        javafx.scene.paint.Color lineColor
                = new javafx.scene.paint.Color(
                        track.lineColor.getRed() / 255.0,
                        track.lineColor.getGreen() / 255.0,
                        track.lineColor.getBlue() / 255.0,
                        1);
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
            curPosGroup.setTranslateX(track.currentPoint.x * distScale);
            curPosGroup.setTranslateY(track.currentPoint.y * distScale);
            curPosGroup.setTranslateZ(track.currentPoint.z * distScale);
        }
        TrackPoint last_tp = track.getData().get(start_index - 1);
        for (int i = start_index; i < track.getData().size() && i < track.cur_time_index; i++) {
            TrackPoint tp = track.getData().get(i);
            double dist = last_tp.distance(tp);
            if (dist < distScale/1000.0) {
                continue;
            }
//            double radius = Math.min(dist * distScale / 2.0, 1.0);
            Cylinder cyl = new Cylinder(1.0, dist * distScale);
//            cyl.setTranslateX(last_tp.x*100.0);
//            cyl.setTranslateY(last_tp.y*100.0);
//            cyl.setTranslateZ(last_tp.z*100.0);
            cyl.getTransforms().addAll(
                    new Translate(last_tp.x * distScale, last_tp.y * distScale, last_tp.z * distScale),
                    new Rotate(Math.toDegrees(Math.atan2(tp.x - last_tp.x, tp.z - last_tp.z)), Rotate.Y_AXIS),
                    new Rotate(Math.toDegrees(Math.acos((tp.y - last_tp.y) / dist)), Rotate.X_AXIS),
                    new Translate(0, dist * distScale/2.0, 0)
            );
            cyl.setMaterial(lineMaterial);
            Sphere sphere = new Sphere(5.0);
            sphere.setTranslateX(tp.x * distScale);
            sphere.setTranslateY(tp.y * distScale);
            sphere.setTranslateZ(tp.z * distScale);
            sphere.setMaterial(ptMaterial);
            trackGroup.getChildren().addAll(sphere, cyl);
            if (null != tp.getRpy() && this.showRotationFrames) {
                Group axisGroup = createAxis(15.0);
//                axisGroup.setTranslateX(tp.x * 100.0);
//                axisGroup.setTranslateY(tp.y * 100.0);
//                axisGroup.setTranslateZ(tp.z * 100.0);
                PmRpy rpy = tp.getRpy();
                axisGroup.getTransforms().addAll(
                        new Translate(tp.x * distScale, tp.y * distScale, tp.z *distScale),
                        new Rotate(Math.toDegrees(rpy.y), Rotate.Z_AXIS),
                        new Rotate(Math.toDegrees(rpy.p), Rotate.Y_AXIS),
                        new Rotate(Math.toDegrees(rpy.r), Rotate.X_AXIS)
                );
                trackGroup.getChildren().addAll(axisGroup);
            }
            last_tp = tp;
        }
    }

    public void updateTrackList(Group g, List<Track> lt) {
        if (null == lt) {
            return;
        }
        Set<Group> matchedGroups  = new HashSet<>();
        for (Track track : lt) {
            Group trackGroup = this.trackGroupMap.get(track);
            if (trackGroup == null) {
                trackGroup = new Group();
                g.getChildren().add(trackGroup);
                this.trackGroupMap.put(track, trackGroup);
            }
            if (!g.getChildren().contains(trackGroup)) {
                g.getChildren().add(trackGroup);
            }
            matchedGroups.add(trackGroup);
            Group curPosGroup = this.trackCurPosGroupMap.get(track);
            if (curPosGroup == null) {
                curPosGroup = new Group();
                g.getChildren().add(curPosGroup);
                this.trackCurPosGroupMap.put(track, curPosGroup);
            }
            if (!g.getChildren().contains(curPosGroup)) {
                g.getChildren().add(curPosGroup);
            }
            matchedGroups.add(curPosGroup);
            updateTrack(trackGroup, curPosGroup, track);
        }
        for(Entry<Track,Group> entry : this.trackGroupMap.entrySet()) {
            if(!matchedGroups.contains(entry.getValue())) {
                entry.getValue().getChildren().clear();
                this.trackGroupMap.remove(entry.getKey());
            }
        }
        for(Entry<Track,Group> entry : this.trackCurPosGroupMap.entrySet()) {
            if(!matchedGroups.contains(entry.getValue())) {
                entry.getValue().getChildren().clear();
                this.trackCurPosGroupMap.remove(entry.getKey());
            }
        }
    }

    public void updateTracksListsFX() {
        if (null == this.TracksList || this.TracksList.size() < 1) {
            clear();
            return;
        }
        Set<Group> matchedGroups  = new HashSet<>();
        for (List<Track> lt : this.TracksList) {
            Group g = trackListGroupMap.get(lt);
            if (null == g) {
                g = new Group();
                TracksGroup.getChildren().add(g);
                trackListGroupMap.put(lt, g);
            }
            if (!TracksGroup.getChildren().contains(g)) {
                TracksGroup.getChildren().add(g);
            }
            this.updateTrackList(g, lt);
            matchedGroups.add(g);
        }
        for(Entry<List<Track>,Group> entry : this.trackListGroupMap.entrySet()) {
            if(!matchedGroups.contains(entry.getValue())) {
                entry.getValue().getChildren().clear();
                this.trackListGroupMap.remove(entry.getKey());
            }
        }
    }

    public void clear() {
        for (Group g : trackListGroupMap.values()) {
            g.getChildren().clear();
        }
        trackListGroupMap.clear();
        for (Group g : trackGroupMap.values()) {
            g.getChildren().clear();
        }
        trackGroupMap.clear();
        for (Group g : trackCurPosGroupMap.values()) {
            g.getChildren().clear();
        }
        trackCurPosGroupMap.clear();
    }

    HashMap<Track, Group> trackGroupMap = new HashMap<>();
    HashMap<Track, Group> trackCurPosGroupMap = new HashMap<>();
    HashMap<List<Track>, Group> trackListGroupMap = new HashMap<>();

    private Group TracksGroup = null;

    public Node create3dContent() {
        TracksGroup = new Group();
        Group axisGroup = createAxis(100);

//        animation = new Timeline();
//        animation.getKeyFrames().addAll(
//                new KeyFrame(Duration.ZERO,
//                        new KeyValue(c.ry.angleProperty(), 0d),
//                        new KeyValue(c2.rx.angleProperty(), 0d),
//                        new KeyValue(c3.rz.angleProperty(), 0d)
//                ),
//                new KeyFrame(Duration.seconds(1),
//                        new KeyValue(c.ry.angleProperty(), 360d),
//                        new KeyValue(c2.rx.angleProperty(), 360d),
//                        new KeyValue(c3.rz.angleProperty(), 360d)
//                ));
//        animation.setCycleCount(Animation.INDEFINITE);
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

    /**
     *
     * @param redBox the value of redBox
     */
    private Group createAxis(double scale) {
        Box redBox = new Box(scale, scale / 10.0, scale / 10.0);
        redBox.setTranslateX(scale / 2.0);
        PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(javafx.scene.paint.Color.RED);
        redMaterial.setDiffuseColor(javafx.scene.paint.Color.RED);
        redBox.setMaterial(redMaterial);
        Box blueBox = new Box(scale / 10.0, scale, scale / 10.0);
        blueBox.setTranslateY(scale / 2.0);
        PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setSpecularColor(javafx.scene.paint.Color.BLUE);
        blueMaterial.setDiffuseColor(javafx.scene.paint.Color.BLUE);
        blueBox.setMaterial(blueMaterial);
        Box greenBox = new Box(scale / 10.0, scale / 10.0, scale);
        greenBox.setTranslateZ(scale / 2.0);
        PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setSpecularColor(javafx.scene.paint.Color.GREEN);
        greenMaterial.setDiffuseColor(javafx.scene.paint.Color.GREEN);
        greenBox.setMaterial(greenMaterial);
        Group axisGroup = new Group(redBox, blueBox, greenBox);
        //redBox.getTransforms().add(new Translate(100,100,100));
//        Cube3D.Cube c = new Cube3D.Cube(50, Color.RED, 1);
//        c.rx.setAngle(45);
//        c.ry.setAngle(45);
//        Cube3D.Cube c2 = new Cube3D.Cube(50, Color.GREEN, 1);
//        c2.setTranslateX(100);
//        c2.rx.setAngle(45);
//        c2.ry.setAngle(45);
//        Cube3D.Cube c3 = new Cube3D.Cube(50, Color.ORANGE, 1);
//        c3.setTranslateX(-100);
//        c3.rx.setAngle(45);
//        c3.ry.setAngle(45);
        return axisGroup;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupDragEnum = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButtonZPosView = new javax.swing.JButton();
        jRadioButtonRotXY = new javax.swing.JRadioButton();
        jRadioButtonRotZ = new javax.swing.JRadioButton();
        jRadioButtonTranXY = new javax.swing.JRadioButton();
        jRadioButtonTranZ = new javax.swing.JRadioButton();
        jButtonXPosView = new javax.swing.JButton();
        jButtonXNegView = new javax.swing.JButton();
        jButtonYPosView = new javax.swing.JButton();
        jButtonZNegView = new javax.swing.JButton();
        jButtonYNegView = new javax.swing.JButton();
        jCheckBoxPre = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDistScale = new javax.swing.JTextField();
        jButtonSetScale = new javax.swing.JButton();
        jCheckBoxShowRotationFrames = new javax.swing.JCheckBox();

        jPanel1.setBackground(new java.awt.Color(34, 228, 32));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 400));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        jButtonZPosView.setText("Z+ View");
        jButtonZPosView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZPosViewActionPerformed(evt);
            }
        });

        buttonGroupDragEnum.add(jRadioButtonRotXY);
        jRadioButtonRotXY.setText("Rot(XY)");
        jRadioButtonRotXY.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonRotXYItemStateChanged(evt);
            }
        });
        jRadioButtonRotXY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonRotXYActionPerformed(evt);
            }
        });

        buttonGroupDragEnum.add(jRadioButtonRotZ);
        jRadioButtonRotZ.setText("Rot(Z)");
        jRadioButtonRotZ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonRotZItemStateChanged(evt);
            }
        });
        jRadioButtonRotZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonRotZActionPerformed(evt);
            }
        });

        buttonGroupDragEnum.add(jRadioButtonTranXY);
        jRadioButtonTranXY.setText("Tran(XY)");
        jRadioButtonTranXY.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonTranXYItemStateChanged(evt);
            }
        });
        jRadioButtonTranXY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonTranXYActionPerformed(evt);
            }
        });

        buttonGroupDragEnum.add(jRadioButtonTranZ);
        jRadioButtonTranZ.setText("Tran(Z)");
        jRadioButtonTranZ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jRadioButtonTranZItemStateChanged(evt);
            }
        });
        jRadioButtonTranZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonTranZActionPerformed(evt);
            }
        });

        jButtonXPosView.setText("X+ View");
        jButtonXPosView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXPosViewActionPerformed(evt);
            }
        });

        jButtonXNegView.setText("X- View");
        jButtonXNegView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXNegViewActionPerformed(evt);
            }
        });

        jButtonYPosView.setText("Y+ View");
        jButtonYPosView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonYPosViewActionPerformed(evt);
            }
        });

        jButtonZNegView.setText("Z- View");
        jButtonZNegView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZNegViewActionPerformed(evt);
            }
        });

        jButtonYNegView.setText("Y- View");
        jButtonYNegView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonYNegViewActionPerformed(evt);
            }
        });

        jCheckBoxPre.setSelected(true);
        jCheckBoxPre.setText("Left Multiply New Transforms");
        jCheckBoxPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPreActionPerformed(evt);
            }
        });

        jLabel1.setText("Drag mouse in display to ...: ");

        jLabel2.setText("Distance Scale:");

        jTextFieldDistScale.setText("100.0    ");
        jTextFieldDistScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDistScaleActionPerformed(evt);
            }
        });

        jButtonSetScale.setText("Set Scale");
        jButtonSetScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetScaleActionPerformed(evt);
            }
        });

        jCheckBoxShowRotationFrames.setText("Show Rotation Frames");
        jCheckBoxShowRotationFrames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowRotationFramesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonRotXY)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonRotZ)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonTranXY)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonTranZ))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonXPosView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonXNegView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonYPosView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonYNegView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonZPosView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonZNegView)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBoxShowRotationFrames)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDistScale, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSetScale)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBoxPre))))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonRotXY)
                    .addComponent(jRadioButtonRotZ)
                    .addComponent(jRadioButtonTranXY)
                    .addComponent(jRadioButtonTranZ)
                    .addComponent(jCheckBoxPre)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonXPosView)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonXNegView)
                        .addComponent(jButtonYPosView)
                        .addComponent(jButtonYNegView)
                        .addComponent(jButtonZPosView)
                        .addComponent(jButtonZNegView)
                        .addComponent(jCheckBoxShowRotationFrames))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldDistScale)
                            .addComponent(jLabel2)
                            .addComponent(jButtonSetScale))
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private View3DDragEnum dragEnum = View3DDragEnum.UNDEFINED;

    /**
     * Get the value of dragEnum
     *
     * @return the value of dragEnum
     */
    public View3DDragEnum getDragEnum() {
        return dragEnum;
    }

    /**
     * Set the value of dragEnum
     *
     * @param _dragEnum which coordinate(s) should be moved when a mouse is
     * dragged
     */
    public void setDragEnum(final View3DDragEnum _dragEnum) {
        switch (_dragEnum) {
            case UNDEFINED:
//                if (null != this.jRadioButtonDragAny && !this.jRadioButtonDragAny.isSelected()) {
//                    this.jRadioButtonDragAny.setSelected(true);
//                }
                break;

            case ROT_XY:
                if (null != this.jRadioButtonRotXY && !this.jRadioButtonRotXY.isSelected()) {
                    this.jRadioButtonRotXY.setSelected(true);
                }
                break;

            case ROT_Z:
                if (null != this.jRadioButtonRotZ && !this.jRadioButtonRotZ.isSelected()) {
                    this.jRadioButtonRotZ.setSelected(true);
                }
                break;

            case TRAN_XY:
                if (null != this.jRadioButtonTranXY && !this.jRadioButtonTranXY.isSelected()) {
                    this.jRadioButtonTranXY.setSelected(true);
                }
                break;

            case TRAN_Z:
                if (null != this.jRadioButtonTranZ && !this.jRadioButtonTranZ.isSelected()) {
                    this.jRadioButtonTranZ.setSelected(true);
                }
                break;

//            case SCALE:
//                if (null != this.jRadioButtonScale && !this.jRadioButtonScale.isSelected()) {
//                    this.jRadioButtonScale.setSelected(true);
//                }
//                break;
        }
        if (this.dragEnum != _dragEnum) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    setupTransforms(_dragEnum);
                }
            });
        }
        this.dragEnum = _dragEnum;
    }

    private void resetTransforms() {
        int sz = contentGroup.getTransforms().size();
        contentGroup.getTransforms().remove(0, sz - 1);
        contentGroup.getTransforms().addAll(tmain, rxmain, rymain, rzmain);
        setupTransforms(this.dragEnum);
    }

    private void setupTransforms(View3DDragEnum _dragEnum) {
        switch (_dragEnum) {
            case UNDEFINED:
                break;

            case ROT_XY:
                this.rx = new Rotate(0, Rotate.X_AXIS);
                this.ry = new Rotate(0, Rotate.Y_AXIS);
                if (this.jCheckBoxPre.isSelected()) {
                    this.contentGroup.getTransforms().add(0, ry);
                    this.contentGroup.getTransforms().add(0, rx);
                } else {
                    this.contentGroup.getTransforms().add(ry);
                    this.contentGroup.getTransforms().add(rx);
                }
                break;

            case ROT_Z:
                this.rz = new Rotate(0, Rotate.Z_AXIS);
                if (this.jCheckBoxPre.isSelected()) {
                    this.contentGroup.getTransforms().add(0, rz);
                } else {
                    this.contentGroup.getTransforms().add(rz);
                }
                break;

            case TRAN_XY:
                this.t = new Translate();
                if (this.jCheckBoxPre.isSelected()) {
                    this.contentGroup.getTransforms().add(0, t);
                } else {
                    this.contentGroup.getTransforms().add(t);
                }
                break;

            case TRAN_Z:
                this.t = new Translate();
                if (this.jCheckBoxPre.isSelected()) {
                    this.contentGroup.getTransforms().add(0, t);
                } else {
                    this.contentGroup.getTransforms().add(t);
                }
                break;

            case SCALE:
                this.s = new Scale();
                if (this.jCheckBoxPre.isSelected()) {
                    this.contentGroup.getTransforms().add(0, s);
                } else {
                    this.contentGroup.getTransforms().add(s);
                }
                break;
        }
//        System.out.println("this.contentGroup.getTransforms() = " + this.contentGroup.getTransforms());
    }

    private void updateDragEnum() {
        if (false) {// this.jRadioButtonDragAny.isSelected()) {
//            if (dragEnum != View3DDragEnum.UNDEFINED) {
//                this.setDragEnum(View3DDragEnum.UNDEFINED);
//            }
        } else if (this.jRadioButtonRotXY.isSelected()) {
            if (dragEnum != View3DDragEnum.ROT_XY) {
                this.setDragEnum(View3DDragEnum.ROT_XY);
            }
        } else if (this.jRadioButtonRotZ.isSelected()) {
            if (dragEnum != View3DDragEnum.ROT_Z) {
                this.setDragEnum(View3DDragEnum.ROT_Z);
            }
        } else if (this.jRadioButtonTranXY.isSelected()) {
            if (dragEnum != View3DDragEnum.TRAN_XY) {
                this.setDragEnum(View3DDragEnum.TRAN_XY);
            }
        } else if (this.jRadioButtonTranZ.isSelected()) {
            if (dragEnum != View3DDragEnum.TRAN_Z) {
                this.setDragEnum(View3DDragEnum.TRAN_Z);
            }
        }
//        else if (this.jRadioButtonScale.isSelected()) {
//            if (dragEnum != View3DDragEnum.SCALE) {
//                this.setDragEnum(View3DDragEnum.SCALE);
//            }
//        }
    }

    private void jRadioButtonRotXYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonRotXYActionPerformed
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonRotXYActionPerformed

    private void jRadioButtonRotZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonRotZActionPerformed
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonRotZActionPerformed

    private void jRadioButtonTranXYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonTranXYActionPerformed
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonTranXYActionPerformed

    private void jRadioButtonTranZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonTranZActionPerformed
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonTranZActionPerformed

    private void jRadioButtonRotXYItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonRotXYItemStateChanged
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonRotXYItemStateChanged

    private void jRadioButtonRotZItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonRotZItemStateChanged
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonRotZItemStateChanged

    private void jRadioButtonTranXYItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonTranXYItemStateChanged
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonTranXYItemStateChanged

    private void jRadioButtonTranZItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jRadioButtonTranZItemStateChanged
        updateDragEnum();
    }//GEN-LAST:event_jRadioButtonTranZItemStateChanged

    private void zPosView() {
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
        resetTransforms();

    }

    private void zNegView() {
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

    private void xPosView() {
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
        rymain.setAngle(90);
        rzmain.setAngle(0);
        resetTransforms();
    }

    private void xNegView() {
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
        rymain.setAngle(-90);
        rzmain.setAngle(0);
        resetTransforms();
    }

    private void yPosView() {
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

    private void yNegView() {
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


    private void jButtonZPosViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZPosViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                zPosView();
            }
        });
    }//GEN-LAST:event_jButtonZPosViewActionPerformed

    private void jButtonXPosViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXPosViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                xPosView();
            }
        });
    }//GEN-LAST:event_jButtonXPosViewActionPerformed

    private void jButtonXNegViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXNegViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                xNegView();
            }
        });
    }//GEN-LAST:event_jButtonXNegViewActionPerformed

    private void jButtonYPosViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonYPosViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                yPosView();
            }
        });
    }//GEN-LAST:event_jButtonYPosViewActionPerformed

    private void jButtonYNegViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonYNegViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                yNegView();
            }
        });
    }//GEN-LAST:event_jButtonYNegViewActionPerformed

    private void jButtonZNegViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZNegViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                zNegView();
            }
        });
    }//GEN-LAST:event_jButtonZNegViewActionPerformed

    private void jCheckBoxPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPreActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                setupTransforms(dragEnum);
            }
        });
    }//GEN-LAST:event_jCheckBoxPreActionPerformed

    private double distScale = 100.0;

    public double getDistScale() {
        return distScale;
    }

    public void setDistScale(double distScale) {
        this.distScale = distScale;
        Platform.runLater(this::clear);
        Platform.runLater(runUpdateTracsList);
    }
    
    private void jTextFieldDistScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistScaleActionPerformed
        double newDistScale = Double.valueOf(this.jTextFieldDistScale.getText());
        this.setDistScale(newDistScale);
    }//GEN-LAST:event_jTextFieldDistScaleActionPerformed

    private void jButtonSetScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetScaleActionPerformed
        double newDistScale = Double.valueOf(this.jTextFieldDistScale.getText());
        this.setDistScale(newDistScale);
    }//GEN-LAST:event_jButtonSetScaleActionPerformed

    
    private boolean showRotationFrames = false;

    public boolean isShowRotationFrames() {
        return showRotationFrames;
    }

    public void setShowRotationFrames(boolean showRotationFrames) {
        this.showRotationFrames = showRotationFrames;
        Platform.runLater(this::clear);
        Platform.runLater(runUpdateTracsList);
    }
    
    
    private void jCheckBoxShowRotationFramesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowRotationFramesActionPerformed
        this.setShowRotationFrames(this.jCheckBoxShowRotationFrames.isSelected());
    }//GEN-LAST:event_jCheckBoxShowRotationFramesActionPerformed

//    private void updateFxPanelSize(Dimension prefSize, Dimension sz) {
//        fxpanel.setPreferredSize(this.jPanel1.getPreferredSize());
//        fxpanel.setSize(this.jPanel1.getSize());
//    }

//    private void updateSizes() {
////        if(this.jPanel1.getSize().width > this.jPanel1.getPreferredSize().width
////                && this.jPanel1.getSize().height > this.jPanel1.getPreferredSize().height) {
////            this.jPanel1.setPreferredSize(this.jPanel1.getSize());
////        }
////        this.jPanel1.remove(this.fxpanel);
////        final Dimension prefSize = this.jPanel1.getPreferredSize();
////        System.out.println("prefSize = " + prefSize);
////        final Dimension sz = this.jPanel1.getSize();
////        System.out.println("sz = " + sz);
////        //this.jPanel1.add(this.fxpanel);
////        Platform.runLater(new Runnable() {
////
////            @Override
////            public void run() {
////                updateFxPanelSize(prefSize,sz);
////            }
////        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupDragEnum;
    private javax.swing.JButton jButtonSetScale;
    private javax.swing.JButton jButtonXNegView;
    private javax.swing.JButton jButtonXPosView;
    private javax.swing.JButton jButtonYNegView;
    private javax.swing.JButton jButtonYPosView;
    private javax.swing.JButton jButtonZNegView;
    private javax.swing.JButton jButtonZPosView;
    private javax.swing.JCheckBox jCheckBoxPre;
    private javax.swing.JCheckBox jCheckBoxShowRotationFrames;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonRotXY;
    private javax.swing.JRadioButton jRadioButtonRotZ;
    private javax.swing.JRadioButton jRadioButtonTranXY;
    private javax.swing.JRadioButton jRadioButtonTranZ;
    private javax.swing.JTextField jTextFieldDistScale;
    // End of variables declaration//GEN-END:variables
}
