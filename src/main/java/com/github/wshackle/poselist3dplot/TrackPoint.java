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
 * public domain. This software is experimental.
 * NIST assumes no responsibility whatsoever for its use by other
 * parties, and makes no guarantees, expressed or implied, about its
 * quality, reliability, or any other characteristic. We would appreciate
 * acknowledgment if the software is used. This software can be
 * redistributed and/or modified freely provided that any derivative works
 * bear some notice that they are derived from it, and any modified
 * versions bear some notice that they have been modified.
 * 
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
