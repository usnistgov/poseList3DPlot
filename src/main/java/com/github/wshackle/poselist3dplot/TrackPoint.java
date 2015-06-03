/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wshackle.poselist3dplot;

import rcs.posemath.PmRpy;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class TrackPoint extends rcs.posemath.PmCartesian {
    
    public TrackPoint(double x, double y, double z, PmRpy rpy) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rpy = rpy;
    }
    
    public double distance(TrackPoint other) {
        return this.distFrom(other);
    }
    
    public TrackPoint() {
        this(0,0,0,null);
    }
    private PmRpy rpy = null;
    
    public void setRoll(double roll) {
        if(null == rpy) {
            rpy = new PmRpy();
        }
        rpy.r = roll;
    }
    
    public double getRoll() {
        return (rpy != null)?rpy.r:0.0;
    }
    
    public double getPitch() {
        return (rpy != null)?rpy.p:0.0;
    }
    
    public double getYaw() {
        return (rpy != null)?rpy.y:0.0;
    }
    
    public void setPitch(double pitch) {
        if(null == rpy) {
            rpy = new PmRpy();
        }
        rpy.p = pitch;
    }
    
    public void setYaw(double yaw) {
        if(null == rpy) {
            rpy = new PmRpy();
        }
        rpy.y = yaw;
    }

    public PmRpy getRpy() {
        return rpy;
    }

    public void setRpy(PmRpy rpy) {
        this.rpy = rpy;
    }

    @Override
    public String toString() {
        
        return (rpy != null) ?
                String.join(",", 
                java.lang.Double.toString(x),
                java.lang.Double.toString(y),
                java.lang.Double.toString(z),
                java.lang.Double.toString(rpy.r),
                java.lang.Double.toString(rpy.p),
                java.lang.Double.toString(rpy.y)
                ) 
                :
                String.join(",", 
                java.lang.Double.toString(x),
                java.lang.Double.toString(y),
                java.lang.Double.toString(z),
                "NA",
                "NA",
                "NA"
                );
    }
    
    public final static String HEADER = "x,y,z,roll,pitch,yaw";
    
}
