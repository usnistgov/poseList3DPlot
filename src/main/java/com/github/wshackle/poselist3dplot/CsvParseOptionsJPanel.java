package com.github.wshackle.poselist3dplot;

import java.awt.Dialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shackle
 */
public class CsvParseOptionsJPanel extends javax.swing.JPanel {

    /**
     * Creates new form CsvParseOptionsJPanel
     */
    public CsvParseOptionsJPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelSettings = new javax.swing.JPanel();
        jComboBoxZIndex = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxRollIndex = new javax.swing.JComboBox();
        jCheckBoxTabDelim = new javax.swing.JCheckBox();
        jComboBoxYawIndex = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxXIndex = new javax.swing.JComboBox();
        jTextFieldFileDelimiter = new javax.swing.JTextField();
        jComboBoxYIndex = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxPitchIndex = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBoxZIndex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        jLabel9.setText("Yaw Index:");

        jComboBoxRollIndex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        jCheckBoxTabDelim.setText("Tab");
        jCheckBoxTabDelim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTabDelimActionPerformed(evt);
            }
        });

        jComboBoxYawIndex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        jLabel8.setText("Z Index:");

        jLabel5.setText("Y Index:");

        jLabel14.setText("File Delimiter: ");

        jComboBoxXIndex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        jTextFieldFileDelimiter.setText(",");
        jTextFieldFileDelimiter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFileDelimiterActionPerformed(evt);
            }
        });

        jComboBoxYIndex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        jLabel7.setText("Roll Index:");

        jComboBoxPitchIndex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));

        jLabel10.setText("Pitch Index:");

        jLabel4.setText("X Index:");

        javax.swing.GroupLayout jPanelSettingsLayout = new javax.swing.GroupLayout(jPanelSettings);
        jPanelSettings.setLayout(jPanelSettingsLayout);
        jPanelSettingsLayout.setHorizontalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSettingsLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldFileDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxTabDelim)
                        .addContainerGap(193, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSettingsLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxZIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxYawIndex, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelSettingsLayout.createSequentialGroup()
                        .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxYIndex, 0, 215, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxXIndex, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxPitchIndex, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxRollIndex, 0, 227, Short.MAX_VALUE)))))
        );

        jPanelSettingsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBoxXIndex, jComboBoxYIndex, jComboBoxZIndex});

        jPanelSettingsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel7, jLabel9});

        jPanelSettingsLayout.setVerticalGroup(
            jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldFileDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxTabDelim))
                .addGap(0, 0, 0)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxXIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxRollIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxYIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(jComboBoxPitchIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jComboBoxZIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxYawIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancel))
                    .addComponent(jPanelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOk)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public boolean cancelled = false;

    public int min_fields = -1;

    public void loadFileToTable(File f) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            List<String> lines = new LinkedList<>();
            String line = null;
            while (null != (line = br.readLine())) {
                lines.add(line);
                if (lines.size() > 1000) {
                    break;
                }
            }
            br.close();
            br = null;
            if(lines.size() < 1) {
                return;
            }
            String delim = this.jTextFieldFileDelimiter.getText();
            String first_line_fa[] = lines.get(0).split(delim);
            DefaultTableModel tm = new DefaultTableModel(first_line_fa, lines.size() - 1);
//            {
//
//                @Override
//                public Class<?> getColumnClass(int columnIndex) {
//                    if(columnIndex > 5) {
//                        return Double.class;
//                    }
//                    return super.getColumnClass(columnIndex); 
//                }
//                
//            };
            min_fields = first_line_fa.length;
            for (int i = 1; i < lines.size(); i++) {
                line = lines.get(i);
                String fa[] = line.split(delim);
                if (fa.length < min_fields) {
                    min_fields = fa.length;
                }
                for (int j = 0; j < fa.length; j++) {
                    tm.setValueAt(fa[j], i - 1, j);
                }
                if (i >= 999) {
                    for (int j = 0; j < fa.length; j++) {
                        tm.setValueAt("... (lines > 1000 not loaded)", 999, j);
                    }
                    break;
                }
            }
            this.jTable1.setModel(tm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (Exception e) {
                };
                br = null;
            }
        }
    }

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        try {
            options.X_INDEX = this.jComboBoxXIndex.getSelectedIndex()-1;
            options.Y_INDEX = this.jComboBoxYIndex.getSelectedIndex()-1;
            options.Z_INDEX = this.jComboBoxZIndex.getSelectedIndex()-1;
            options.ROLL_INDEX = this.jComboBoxRollIndex.getSelectedIndex()-1;
            options.PITCH_INDEX = this.jComboBoxPitchIndex.getSelectedIndex()-1;
            options.YAW_INDEX = this.jComboBoxYawIndex.getSelectedIndex()-1;

            this.setVisible(false);
            if (null != this.dialog) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }//GEN-LAST:event_jButtonOkActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        try {
            cancelled = true;
            options = null;
            this.setVisible(false);
            if (null != this.dialog) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, exception.getMessage());
        }
    }//GEN-LAST:event_jButtonCancelActionPerformed


    private void jTextFieldFileDelimiterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFileDelimiterActionPerformed
        this.setDelimiter(this.jTextFieldFileDelimiter.getText());
    }//GEN-LAST:event_jTextFieldFileDelimiterActionPerformed

    private void jCheckBoxTabDelimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTabDelimActionPerformed
        if (this.jCheckBoxTabDelim.isSelected()) {
            this.jTextFieldFileDelimiter.setText("\t");
            this.jTextFieldFileDelimiter.setEnabled(false);
            this.jTextFieldFileDelimiter.setEditable(false);
            this.setDelimiter("\t");
        } else {
            this.jTextFieldFileDelimiter.setEnabled(true);
            this.jTextFieldFileDelimiter.setEditable(true);
        }
    }//GEN-LAST:event_jCheckBoxTabDelimActionPerformed
    public JDialog dialog = null;
    public CsvParseOptions options = new CsvParseOptions();
    public Frame frame = null;

    public CsvParseOptions getParseOptions() {
        return this.options;
    }

    public void setParseOptions(CsvParseOptions o) {        
        guardedSetComboBoxIndex(o.X_INDEX, this.jComboBoxXIndex);
        guardedSetComboBoxIndex(o.Y_INDEX, this.jComboBoxYIndex);
        guardedSetComboBoxIndex(o.Z_INDEX, this.jComboBoxZIndex);
        guardedSetComboBoxIndex(o.ROLL_INDEX, this.jComboBoxRollIndex);
        guardedSetComboBoxIndex(o.PITCH_INDEX, this.jComboBoxPitchIndex);
        guardedSetComboBoxIndex(o.YAW_INDEX, this.jComboBoxYawIndex);
        this.options = o;
    }

    private void guardedSetComboBoxIndex(int index, JComboBox comboBox) {
        if (index >= 0 && index < comboBox.getItemCount()-1) {
            comboBox.setSelectedIndex(index+1);
        } else {
            comboBox.setSelectedIndex(0);
        }
    }

    static public void LoadHeadingsToComboBoxModel(DefaultComboBoxModel cbm, String headings[]) {
        cbm.removeAllElements();
        String column_prefix = "";
        cbm.addElement("Ignore = -1");
        for (int i = 0; i < headings.length; i++) {
            if (i > 25) {
                column_prefix = "" + ((char) ('A' + ((i / 26) - 1)));
            }
            cbm.addElement(headings[i] + " ( Column " + column_prefix + ((char) ('A' + (i % 26))) + " ) = " + Integer.toString(i));
        }
    }

    /**
     * Get the value of delimiter
     *
     * @return the value of delimiter
     */
    public String getDelimiter() {
        return options.delim;
    }

    /**
     * Set the value of delimiter
     *
     * @param _delimiter
     */
    public void setDelimiter(String _delimiter) {
        options.delim = _delimiter;
        this.loadFileToTable(file);
        this.parseHeadings(file, false, min_fields);
    }

    private File file;

    /**
     * Get the value of file
     *
     * @return the value of file
     */
    public File getFile() {
        return file;
    }

    /**
     * Set the value of file
     *
     * @param file new value of file
     */
    public void setFile(File file) {
        this.file = file;
    }

    static public String[] headingsFromFile(File f, String delim) {
        String headings[] = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String first_line = br.readLine();
            if (null == first_line) {
                System.err.println("Can not read from " + f);
                return null;
            }
            //System.out.println("delim = " + delim);
            headings = first_line.split(delim);
            String line = null;
            int linecount = 0;
            int min_fields = headings.length;
            while (null != (line = br.readLine()) && linecount < 1000) {
                if (line.trim().length() < 1) {
                    continue;
                }
                String fa[] = line.split(delim);
                if (min_fields > fa.length) {
                    min_fields = fa.length;
                }
            }
            if (headings.length > min_fields) {
                headings = Arrays.copyOfRange(headings, 0, min_fields);
            }
            br.close();
            br = null;
        } catch (Exception e) {
            System.err.println("Error reading : " + f);
            e.printStackTrace();
        }
        return headings;
    }

    static public CsvParseOptions optionsFromFileHeadings(CsvParseOptions o, File f, String delim) {
        String headings[] = CsvParseOptionsJPanel.headingsFromFile(f, delim);
        return optionsFromHeadings(o, headings);
    }

    static public CsvParseOptions optionsFromHeadings(CsvParseOptions o, String headings[]) {
        try {
            o.TIME_INDEX = CsvParseOptions.DEFAULT.TIME_INDEX;
            o.NAME_INDEX = CsvParseOptions.DEFAULT.NAME_INDEX;
            o.X_INDEX = CsvParseOptions.DEFAULT.X_INDEX;
            o.Y_INDEX = CsvParseOptions.DEFAULT.Y_INDEX;
            o.Z_INDEX = CsvParseOptions.DEFAULT.Z_INDEX;
            o.VX_INDEX = CsvParseOptions.DEFAULT.VX_INDEX;
            o.VY_INDEX = CsvParseOptions.DEFAULT.VY_INDEX;
            o.VZ_INDEX = CsvParseOptions.DEFAULT.VZ_INDEX;
            o.RADIUS_INDEX = CsvParseOptions.DEFAULT.RADIUS_INDEX;
            o.ROI_HEIGHT_INDEX = CsvParseOptions.DEFAULT.ROI_HEIGHT_INDEX;
            o.ROI_WIDTH_INDEX = CsvParseOptions.DEFAULT.ROI_WIDTH_INDEX;
            o.CONFIDENCE_INDEX = CsvParseOptions.DEFAULT.CONFIDENCE_INDEX;
            if (null == headings || headings.length < 2) {
                return o;
            }
            for (int i = 0; i < headings.length; i++) {
                if (headings[i].startsWith("\"")) {
                    headings[i] = headings[i].substring(1);
                }
                if (headings[i].endsWith("\"")) {
                    headings[i] = headings[i].substring(0, headings[i].length() - 1);
                }
            }
            if ("Time".compareTo(headings[0]) == 0
                    && "GpsTime".compareTo(headings[1]) == 0) {
                o.DISTANCE_SCALE = 1e-3f;
                o.TIME_SCALE = 1e-6f;
                o.TIME_INDEX = 0;
                o.NAME_INDEX = 2;
                o.CONFIDENCE_INDEX = -1;
                o.RADIUS_INDEX = -1;
                o.ROI_HEIGHT_INDEX = -1;
                o.ROI_WIDTH_INDEX = -1;
                o.VX_INDEX = -1;
                o.VY_INDEX = -1;
                o.VZ_INDEX = -1;
            }
            if (headings.length < 5) {
                o.CONFIDENCE_INDEX = -1;
                o.RADIUS_INDEX = -1;
                o.ROI_HEIGHT_INDEX = -1;
                o.ROI_WIDTH_INDEX = -1;
                o.VX_INDEX = -1;
                o.VY_INDEX = -1;
                o.VZ_INDEX = -1;
            }
            for (int i = 0; i < headings.length; i++) {
                String h = headings[i].trim();
                try {
                    if (h.length() >= 4
                            && ("time".equalsIgnoreCase(h.substring(0, 4))
                            || "# time".equalsIgnoreCase(h.substring(0, 6)))
                            && o.TIME_INDEX == CsvParseOptions.DEFAULT.TIME_INDEX) {
                        o.TIME_INDEX = i;
                        if (h.indexOf("(ns)") > 0) {
                            o.TIME_SCALE = 1e-9;
                        }
                        if (h.indexOf("(ms)") > 0) {
                            o.TIME_SCALE = 1e-3;
                        }
                    }
                } catch (Exception e) {
                }
                if ("framename".equalsIgnoreCase(h)
                        && o.NAME_INDEX == CsvParseOptions.DEFAULT.NAME_INDEX) {
                    o.NAME_INDEX = i;
                }
                if ("personid".equalsIgnoreCase(h)
                        && o.NAME_INDEX == CsvParseOptions.DEFAULT.NAME_INDEX) {
                    o.NAME_INDEX = i;
                }
                if ("tiplocationx".equalsIgnoreCase(h)
                        && o.X_INDEX == CsvParseOptions.DEFAULT.X_INDEX) {
                    o.X_INDEX = i;
                }
                if ("tiplocationy".equalsIgnoreCase(h)
                        && o.Y_INDEX == CsvParseOptions.DEFAULT.Y_INDEX) {
                    o.Y_INDEX = i;
                }
                if ("tiplocationz".equalsIgnoreCase(h)
                        && o.Z_INDEX == CsvParseOptions.DEFAULT.Z_INDEX) {
                    o.Z_INDEX = i;
                }
                if ("x".equalsIgnoreCase(h)
                        && o.X_INDEX == CsvParseOptions.DEFAULT.X_INDEX) {
                    o.X_INDEX = i;
                }
                if ("y".equalsIgnoreCase(h)
                        && o.Y_INDEX == CsvParseOptions.DEFAULT.Y_INDEX) {
                    o.Y_INDEX = i;
                }
                if ("z".equalsIgnoreCase(h)
                        && o.Z_INDEX == CsvParseOptions.DEFAULT.Z_INDEX) {
                    o.Z_INDEX = i;
                }
                if ("position.x".equalsIgnoreCase(h)
                        && o.X_INDEX == CsvParseOptions.DEFAULT.X_INDEX) {
                    o.X_INDEX = i;
                }
                if ("position.y".equalsIgnoreCase(h)
                        && o.Y_INDEX == CsvParseOptions.DEFAULT.Y_INDEX) {
                    o.Y_INDEX = i;
                }
                if ("position.z".equalsIgnoreCase(h)
                        && o.Z_INDEX == CsvParseOptions.DEFAULT.Z_INDEX) {
                    o.Z_INDEX = i;
                }
                if ("headcentriodx".equalsIgnoreCase(h)
                        && o.X_INDEX == CsvParseOptions.DEFAULT.X_INDEX) {
                    o.X_INDEX = i;
                }
                if ("headcentriody".equalsIgnoreCase(h)
                        && o.Y_INDEX == CsvParseOptions.DEFAULT.Y_INDEX) {
                    o.Y_INDEX = i;
                }
                if ("headcentriodz".equalsIgnoreCase(h)
                        && o.Z_INDEX == CsvParseOptions.DEFAULT.Z_INDEX) {
                    o.Z_INDEX = i;
                }
                if ("personcentriodx".equalsIgnoreCase(h)
                        && o.X_INDEX == CsvParseOptions.DEFAULT.X_INDEX) {
                    o.X_INDEX = i;
                }
                if ("personcentriody".equalsIgnoreCase(h)
                        && o.Y_INDEX == CsvParseOptions.DEFAULT.Y_INDEX) {
                    o.Y_INDEX = i;
                }
                if ("personcentriodz".equalsIgnoreCase(h)
                        && o.Z_INDEX == CsvParseOptions.DEFAULT.Z_INDEX) {
                    o.Z_INDEX = i;
                }
                if ("xvelocity".equalsIgnoreCase(h)
                        && o.VX_INDEX == CsvParseOptions.DEFAULT.VX_INDEX) {
                    o.VX_INDEX = i;
                }
                if ("yvelocity".equalsIgnoreCase(h)
                        && o.VY_INDEX == CsvParseOptions.DEFAULT.VY_INDEX) {
                    o.VY_INDEX = i;
                }
                if ("zvelocity".equalsIgnoreCase(h)
                        && o.VZ_INDEX == CsvParseOptions.DEFAULT.VZ_INDEX) {
                    o.VZ_INDEX = i;
                }
                if ("confidence".equalsIgnoreCase(h)
                        && o.CONFIDENCE_INDEX == CsvParseOptions.DEFAULT.CONFIDENCE_INDEX) {
                    o.CONFIDENCE_INDEX = i;
                }
                if ("radius".equalsIgnoreCase(h)
                        && o.RADIUS_INDEX == CsvParseOptions.DEFAULT.RADIUS_INDEX) {
                    o.RADIUS_INDEX = i;
                }
                if ("roll".equalsIgnoreCase(h)
                        && o.ROLL_INDEX == CsvParseOptions.DEFAULT.ROLL_INDEX) {
                    o.ROLL_INDEX = i;
                }
                if ("pitch".equalsIgnoreCase(h)
                        && o.PITCH_INDEX == CsvParseOptions.DEFAULT.PITCH_INDEX) {
                    o.PITCH_INDEX = i;
                }
                if ("yaw".equalsIgnoreCase(h)
                        && o.YAW_INDEX == CsvParseOptions.DEFAULT.YAW_INDEX) {
                    o.YAW_INDEX = i;
                }
            }
            if (swap_yz) {
                int tmp = o.Y_INDEX;
                o.Y_INDEX = o.Z_INDEX;
                o.Z_INDEX = tmp;
                tmp = o.VY_INDEX;
                o.VY_INDEX = o.VZ_INDEX;
                o.VZ_INDEX = tmp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    static public boolean swap_yz = false;

    public void parseHeadings(File f, boolean first, int min_fields) {
        try {
            if (f != null) {
                this.setFile(f);
            } else {
                f = this.getFile();
            }
            String delim = this.getDelimiter();
            if (delim.length() < 1) {
                JOptionPane.showMessageDialog(this, "Empty delimiter");
                return;
            }
            String headings[] = CsvParseOptionsJPanel.headingsFromFile(f, delim);
            if (headings.length < 2 && first) {
                String try_headings_tab[] = CsvParseOptionsJPanel.headingsFromFile(f, "[ \t]+");
                String try_headings_semicolon[] = CsvParseOptionsJPanel.headingsFromFile(f, ";");
                if (try_headings_tab.length > 2 && try_headings_semicolon.length < 2) {
                    delim = "[ \t]+";
                    this.jTextFieldFileDelimiter.setText(delim);
                    this.setDelimiter(delim);
                    return;
                } else if (try_headings_tab.length < 2 && try_headings_semicolon.length > 2) {
                    delim = ";";
                    this.jTextFieldFileDelimiter.setText(delim);
                    this.setDelimiter(";");
                    return;
                }
            }
            if (null == this.options) {
                this.options = new CsvParseOptions();
            }
            this.options = CsvParseOptionsJPanel.optionsFromHeadings(options, headings);
            CsvParseOptions o = this.options;
            DefaultComboBoxModel cbm = null;
//            cbm = (DefaultComboBoxModel) this.jComboBoxTimeIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxTimeIndex.setModel(cbm);
//            cbm = (DefaultComboBoxModel) this.jComboBoxNameIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxNameIndex.setModel(cbm);
            SetComboBoxModelFromHeadings(this.jComboBoxXIndex, headings);
            SetComboBoxModelFromHeadings(this.jComboBoxYIndex,headings);
            SetComboBoxModelFromHeadings(this.jComboBoxZIndex,headings);
            SetComboBoxModelFromHeadings(this.jComboBoxRollIndex,headings);
            SetComboBoxModelFromHeadings(this.jComboBoxPitchIndex,headings);
            SetComboBoxModelFromHeadings(this.jComboBoxYawIndex,headings);
//            cbm = (DefaultComboBoxModel) this.jComboBoxVXIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxVXIndex.setModel(cbm);
//            cbm = (DefaultComboBoxModel) this.jComboBoxVYIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxVYIndex.setModel(cbm);
//            cbm = (DefaultComboBoxModel) this.jComboBoxVZIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxVZIndex.setModel(cbm);
//            cbm = (DefaultComboBoxModel) this.jComboBoxConfidenceIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxConfidenceIndex.setModel(cbm);
//            cbm = (DefaultComboBoxModel) this.jComboBoxRadiusIndex.getModel();
//            LoadHeadingsToComboBoxModel(cbm, headings);
//            this.jComboBoxRadiusIndex.setModel(cbm);
            this.setParseOptions(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SetComboBoxModelFromHeadings(JComboBox comboBox, String[] headings) {
        DefaultComboBoxModel cbm;
        cbm = (DefaultComboBoxModel) comboBox.getModel();
        LoadHeadingsToComboBoxModel(cbm, headings);
        comboBox.setModel(cbm);
    }

    static public CsvParseOptions showDialog(Frame parent, File f) {
        try {

            CsvParseOptionsJPanel opanel = new CsvParseOptionsJPanel();
            opanel.loadFileToTable(f);
            opanel.parseHeadings(f, true, opanel.min_fields);
            opanel.frame = parent;

            JDialog dialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL);
            opanel.setVisible(true);
            opanel.dialog = dialog;
            dialog.add(opanel);
            dialog.pack();
            dialog.setVisible(true);
            opanel.frame = null;
            if (opanel.cancelled) {
                return null;
            }
            return opanel.options;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public CsvParseOptions showDialog(Frame parent, CsvParseOptions o) {
        CsvParseOptionsJPanel opanel = new CsvParseOptionsJPanel();
        JDialog dialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL);
        opanel.setVisible(true);
        opanel.dialog = dialog;
        opanel.frame = parent;
        opanel.setParseOptions(o);
        dialog.add(opanel);
        dialog.pack();
        //dialog.setModal(Dialog.ModalityType.APPLICATION_MODAL);
        //OR, you can do the following...
        //JDialog dialog = new JDialog();
        //dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //dialog.setBounds(transform_frame.getBounds());
        dialog.setVisible(true);
        opanel.frame = null;
        if (opanel.cancelled) {
            return null;
        }
        return opanel.options;
    }

    static public CsvParseOptions showDialog(Frame parent) {
        CsvParseOptionsJPanel opanel = new CsvParseOptionsJPanel();
        JDialog dialog = new JDialog(parent, Dialog.ModalityType.APPLICATION_MODAL);
        opanel.setVisible(true);
        opanel.dialog = dialog;
        dialog.add(opanel);
        dialog.pack();
        //dialog.setModal(Dialog.ModalityType.APPLICATION_MODAL);
        //OR, you can do the following...
        //JDialog dialog = new JDialog();
        //dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        //dialog.setBounds(transform_frame.getBounds());
        dialog.setVisible(true);
        return opanel.options;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JCheckBox jCheckBoxTabDelim;
    private javax.swing.JComboBox jComboBoxPitchIndex;
    private javax.swing.JComboBox jComboBoxRollIndex;
    private javax.swing.JComboBox jComboBoxXIndex;
    private javax.swing.JComboBox jComboBoxYIndex;
    private javax.swing.JComboBox jComboBoxYawIndex;
    private javax.swing.JComboBox jComboBoxZIndex;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelSettings;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldFileDelimiter;
    // End of variables declaration//GEN-END:variables
}
