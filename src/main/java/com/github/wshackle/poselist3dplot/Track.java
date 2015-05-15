/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wshackle.poselist3dplot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class Track {
    
    public int cur_time_index;
    public String name;
    public TrackPoint currentPoint;
    public java.awt.Color pointColor = java.awt.Color.BLACK;
    public java.awt.Color lineColor = java.awt.Color.BLACK;
    
    private List<TrackPoint> data = new ArrayList<>();
    
    public List<TrackPoint> getData() {
        return data;
    }

    public void setData(List<TrackPoint> data) {
        this.data = data;
    }
    
}
