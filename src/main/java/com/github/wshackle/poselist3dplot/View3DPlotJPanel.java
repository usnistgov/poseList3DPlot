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
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class View3DPlotJPanel extends javax.swing.JPanel {

    private JFXPanel fxpanel = null;
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
            java.awt.EventQueue.invokeLater(() -> {
                Platform.runLater(() -> {
                    initFxScene();
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            setDragEnum(View3DDragEnum.ROT_XY);
                            scene3DController.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                                SwingUtilities.invokeLater(() -> {
                                    jTextFieldTransforms.setText(evt.getNewValue().toString());
                                });
                            });
                        }
                    });
                });
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

    private Scene3DController scene3DController = new Scene3DController();
    
    private void initFxScene() {
        fxpanel.setScene(
                scene3DController.create3DScene(
                        Math.max(this.jPanel1.getPreferredSize().width, this.jPanel1.getSize().width),
                        Math.max(this.jPanel1.getPreferredSize().height, this.jPanel1.getSize().height)
                , this)
        );
    }


    private List<List<Track>> tracksList;

    /**
     * Get the value of tracksList
     *
     * @return the value of tracksList
     */
    public List<List<Track>> getTracksList() {
        return tracksList;
    }

    /**
     * Set the value of tracksList
     *
     * @param TracksList new value of tracksList
     */
    public void setTracksList(List<List<Track>> TracksList) {
        this.tracksList = TracksList;
        scene3DController.updateTracksList(this.tracksList);
    }

    private final Runnable runUpdateTracsList = new Runnable() {
        @Override
        public void run() {
            scene3DController.updateTracksList(tracksList);
        }
    };





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
        jPanelDragChoices = new javax.swing.JPanel();
        jRadioButtonTranXY = new javax.swing.JRadioButton();
        jRadioButtonRotXY = new javax.swing.JRadioButton();
        jRadioButtonTranZ = new javax.swing.JRadioButton();
        jRadioButtonRotZ = new javax.swing.JRadioButton();
        jPanelSelectView = new javax.swing.JPanel();
        jButtonZNegView = new javax.swing.JButton();
        jButtonYNegView = new javax.swing.JButton();
        jButtonXPosView = new javax.swing.JButton();
        jButtonYPosView = new javax.swing.JButton();
        jButtonXNegView = new javax.swing.JButton();
        jButtonZPosView = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelMisc = new javax.swing.JPanel();
        jCheckBoxPre = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jCheckBoxShowRotationFrames = new javax.swing.JCheckBox();
        jTextFieldDistScale = new javax.swing.JTextField();
        jButtonSetScale = new javax.swing.JButton();
        jPanelTransformText = new javax.swing.JPanel();
        jTextFieldTransforms = new javax.swing.JTextField();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(34, 228, 32));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 400));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jPanelDragChoices.setBorder(javax.swing.BorderFactory.createTitledBorder("Drag mouse to ..."));

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

        javax.swing.GroupLayout jPanelDragChoicesLayout = new javax.swing.GroupLayout(jPanelDragChoices);
        jPanelDragChoices.setLayout(jPanelDragChoicesLayout);
        jPanelDragChoicesLayout.setHorizontalGroup(
            jPanelDragChoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDragChoicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonRotXY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonRotZ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonTranXY)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonTranZ)
                .addContainerGap())
        );
        jPanelDragChoicesLayout.setVerticalGroup(
            jPanelDragChoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDragChoicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDragChoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonRotXY)
                    .addComponent(jRadioButtonRotZ)
                    .addComponent(jRadioButtonTranXY)
                    .addComponent(jRadioButtonTranZ))
                .addContainerGap())
        );

        jPanelSelectView.setBorder(javax.swing.BorderFactory.createTitledBorder("Select View"));

        jButtonZNegView.setText("Z-");
        jButtonZNegView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZNegViewActionPerformed(evt);
            }
        });

        jButtonYNegView.setText("Y-");
        jButtonYNegView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonYNegViewActionPerformed(evt);
            }
        });

        jButtonXPosView.setText("X+");
        jButtonXPosView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXPosViewActionPerformed(evt);
            }
        });

        jButtonYPosView.setText("Y+");
        jButtonYPosView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonYPosViewActionPerformed(evt);
            }
        });

        jButtonXNegView.setText("X-");
        jButtonXNegView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXNegViewActionPerformed(evt);
            }
        });

        jButtonZPosView.setText("Z+");
        jButtonZPosView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZPosViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSelectViewLayout = new javax.swing.GroupLayout(jPanelSelectView);
        jPanelSelectView.setLayout(jPanelSelectViewLayout);
        jPanelSelectViewLayout.setHorizontalGroup(
            jPanelSelectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectViewLayout.createSequentialGroup()
                .addContainerGap()
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
                .addContainerGap())
        );
        jPanelSelectViewLayout.setVerticalGroup(
            jPanelSelectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSelectViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSelectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonXPosView)
                    .addGroup(jPanelSelectViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonXNegView)
                        .addComponent(jButtonYPosView)
                        .addComponent(jButtonYNegView)
                        .addComponent(jButtonZPosView)
                        .addComponent(jButtonZNegView)))
                .addContainerGap())
        );

        jPanelMisc.setBorder(javax.swing.BorderFactory.createTitledBorder("Misc"));

        jCheckBoxPre.setSelected(true);
        jCheckBoxPre.setText("Left Multiply New Transforms");
        jCheckBoxPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPreActionPerformed(evt);
            }
        });

        jLabel2.setText("Distance Scale:");

        jCheckBoxShowRotationFrames.setText("Show Rotation Frames");
        jCheckBoxShowRotationFrames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowRotationFramesActionPerformed(evt);
            }
        });

        jTextFieldDistScale.setText("1.0");
        jTextFieldDistScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDistScaleActionPerformed(evt);
            }
        });

        jButtonSetScale.setText("Auto Set Scale");
        jButtonSetScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetScaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMiscLayout = new javax.swing.GroupLayout(jPanelMisc);
        jPanelMisc.setLayout(jPanelMiscLayout);
        jPanelMiscLayout.setHorizontalGroup(
            jPanelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMiscLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMiscLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldDistScale, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSetScale))
                    .addGroup(jPanelMiscLayout.createSequentialGroup()
                        .addComponent(jCheckBoxShowRotationFrames)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxPre)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMiscLayout.setVerticalGroup(
            jPanelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMiscLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxShowRotationFrames)
                    .addComponent(jCheckBoxPre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelMiscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldDistScale)
                    .addComponent(jButtonSetScale))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Options", jPanelMisc);

        jPanelTransformText.setBorder(javax.swing.BorderFactory.createTitledBorder("Transform Text"));

        javax.swing.GroupLayout jPanelTransformTextLayout = new javax.swing.GroupLayout(jPanelTransformText);
        jPanelTransformText.setLayout(jPanelTransformTextLayout);
        jPanelTransformTextLayout.setHorizontalGroup(
            jPanelTransformTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTransformTextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldTransforms, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelTransformTextLayout.setVerticalGroup(
            jPanelTransformTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTransformTextLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldTransforms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Transform Text", jPanelTransformText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanelDragChoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanelSelectView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelSelectView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelDragChoices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanelDragChoices, jPanelSelectView});

    }// </editor-fold>//GEN-END:initComponents

    /**
     * Get the value of dragEnum
     *
     * @return the value of dragEnum
     */
    public View3DDragEnum getDragEnum() {
        return scene3DController.getDragEnum();
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
        if (scene3DController.getDragEnum() != _dragEnum) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    scene3DController.setupTransforms(_dragEnum);
                }
            });
        }
        scene3DController.setDragEnum(_dragEnum);
    }

    private void updateDragEnum() {
        if (false) {// this.jRadioButtonDragAny.isSelected()) {
//            if (dragEnum != View3DDragEnum.UNDEFINED) {
//                this.setDragEnum(View3DDragEnum.UNDEFINED);
//            }
        } else if (this.jRadioButtonRotXY.isSelected()) {
            if (scene3DController.getDragEnum() != View3DDragEnum.ROT_XY) {
                this.setDragEnum(View3DDragEnum.ROT_XY);
            }
        } else if (this.jRadioButtonRotZ.isSelected()) {
            if (scene3DController.getDragEnum() != View3DDragEnum.ROT_Z) {
                this.setDragEnum(View3DDragEnum.ROT_Z);
            }
        } else if (this.jRadioButtonTranXY.isSelected()) {
            if (scene3DController.getDragEnum() != View3DDragEnum.TRAN_XY) {
                this.setDragEnum(View3DDragEnum.TRAN_XY);
            }
        } else if (this.jRadioButtonTranZ.isSelected()) {
            if (scene3DController.getDragEnum() != View3DDragEnum.TRAN_Z) {
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


    private void jButtonZPosViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZPosViewActionPerformed
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                scene3DController.zPosView();
            }
        });
    }//GEN-LAST:event_jButtonZPosViewActionPerformed

    private void jButtonXPosViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXPosViewActionPerformed
        Platform.runLater(scene3DController::xPosView);
    }//GEN-LAST:event_jButtonXPosViewActionPerformed

    private void jButtonXNegViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXNegViewActionPerformed
        Platform.runLater(scene3DController::xNegView);
    }//GEN-LAST:event_jButtonXNegViewActionPerformed

    private void jButtonYPosViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonYPosViewActionPerformed
        Platform.runLater(scene3DController::yPosView);
    }//GEN-LAST:event_jButtonYPosViewActionPerformed

    private void jButtonYNegViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonYNegViewActionPerformed
        Platform.runLater(scene3DController::yNegView);
    }//GEN-LAST:event_jButtonYNegViewActionPerformed

    private void jButtonZNegViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZNegViewActionPerformed
        Platform.runLater(scene3DController::zNegView);
    }//GEN-LAST:event_jButtonZNegViewActionPerformed

    private void jCheckBoxPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPreActionPerformed
        
        final boolean preMult = jCheckBoxPre.isSelected();
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                scene3DController.setLeftMultiplySelected(preMult);
                scene3DController.setupTransforms(scene3DController.getDragEnum());
            }
        });
    }//GEN-LAST:event_jCheckBoxPreActionPerformed


    public double getDistScale() {
        return scene3DController.getDistScale();
    }

    public void setDistScale(double distScale) {
        scene3DController.setDistScale(distScale);
        scene3DController.refreshScene(tracksList);
    }

    private void jTextFieldDistScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDistScaleActionPerformed
        double newDistScale = Double.valueOf(this.jTextFieldDistScale.getText());
        this.setDistScale(newDistScale / 100.0);
    }//GEN-LAST:event_jTextFieldDistScaleActionPerformed

    private <T> double minStream(Stream<T> stream, ToDoubleFunction<T> mapper) {
        return stream
                .mapToDouble(mapper)
                .min()
                .orElse(Double.POSITIVE_INFINITY);
    }

    private <T> double maxStream(Stream<T> stream, ToDoubleFunction<T> mapper) {
        return stream
                .mapToDouble(mapper)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);
    }

    private void jButtonSetScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetScaleActionPerformed
        autoSetScale();
    }//GEN-LAST:event_jButtonSetScaleActionPerformed

    public void autoSetScale() throws NumberFormatException {
        double newDistScale = Double.valueOf(this.jTextFieldDistScale.getText());
        double minx = minStream(allPoints(), (TrackPoint x) -> x.x);
        double maxx = maxStream(allPoints(), (TrackPoint x) -> x.x);
        double miny = minStream(allPoints(), (TrackPoint x) -> x.y);
        double maxy = maxStream(allPoints(), (TrackPoint x) -> x.y);
        double minz = minStream(allPoints(), (TrackPoint x) -> x.z);
        double maxz = maxStream(allPoints(), (TrackPoint x) -> x.z);
        double maxdiff
                = Arrays.stream(new double[]{
                    (maxx - minx),
                    (maxy - miny),
                    (maxz - minz)
                })
                .max()
                .orElse(0);
        if (maxdiff > Double.MIN_NORMAL) {
            int log = (int) Math.log10(maxdiff);
            newDistScale = Math.pow(10.0, 2 - log);
            this.jTextFieldDistScale.setText(String.format("%.1g", newDistScale * 100.0));
        }
        this.setDistScale(newDistScale);
    }

    private Stream<TrackPoint> allPoints() {
        return this.tracksList
                .stream()
                .flatMap((List<Track> x) -> x.stream())
                .map((Track x) -> x.getData())
                .flatMap((List<TrackPoint> x) -> x.stream());
    }


    public boolean isShowRotationFrames() {
        return scene3DController.isShowRotationFrames();
    }

    public void setShowRotationFrames(boolean showRotationFrames) {
        scene3DController.setShowRotationFrames(showRotationFrames);
        scene3DController.refreshScene(tracksList);
    }



    private void jCheckBoxShowRotationFramesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowRotationFramesActionPerformed
        this.setShowRotationFrames(this.jCheckBoxShowRotationFrames.isSelected());
    }//GEN-LAST:event_jCheckBoxShowRotationFramesActionPerformed

    private void jPanel1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentResized
        updateSizes();
    }//GEN-LAST:event_jPanel1ComponentResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
       updateSizes();
    }//GEN-LAST:event_formComponentResized

    private void updateSizes() {
        final int w = Math.max(this.jPanel1.getPreferredSize().width,
                Math.min(this.getSize().width,this.jPanel1.getSize().width));
        final int h = Math.max(this.jPanel1.getPreferredSize().height,
                Math.min(this.getSize().height,this.jPanel1.getSize().height));
        scene3DController.updateSize(w, h);
    }


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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDragChoices;
    private javax.swing.JPanel jPanelMisc;
    private javax.swing.JPanel jPanelSelectView;
    private javax.swing.JPanel jPanelTransformText;
    private javax.swing.JRadioButton jRadioButtonRotXY;
    private javax.swing.JRadioButton jRadioButtonRotZ;
    private javax.swing.JRadioButton jRadioButtonTranXY;
    private javax.swing.JRadioButton jRadioButtonTranZ;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldDistScale;
    private javax.swing.JTextField jTextFieldTransforms;
    // End of variables declaration//GEN-END:variables
}
