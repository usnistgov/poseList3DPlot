/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wshackle.poselist3dplot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import rcs.posemath.PmException;
import rcs.posemath.PmPose;
import rcs.posemath.PmRpy;
import rcs.posemath.Posemath;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class TrackUtils {

    /**
     *
     * @param tracksList the value of tracksList
     * @return the double
     * @throws NumberFormatException
     */
    public static double getAutoScale(List<? extends List<? extends Track>> tracksList) throws NumberFormatException {
        double newDistScale = 1.0; // Double.valueOf(this.jTextFieldDistScale.getText());
        double minx = minStream(TrackUtils.allPoints(tracksList), (TrackPoint x) -> x.x);
        double maxx = maxStream(TrackUtils.allPoints(tracksList), (TrackPoint x) -> x.x);
        double miny = minStream(TrackUtils.allPoints(tracksList), (TrackPoint x) -> x.y);
        double maxy = maxStream(TrackUtils.allPoints(tracksList), (TrackPoint x) -> x.y);
        double minz = minStream(TrackUtils.allPoints(tracksList), (TrackPoint x) -> x.z);
        double maxz = maxStream(TrackUtils.allPoints(tracksList), (TrackPoint x) -> x.z);
        double maxdiff = Arrays.stream(new double[]{maxx - minx, maxy - miny, maxz - minz}).max().orElse(0);
        if (maxdiff > Double.MIN_NORMAL) {
            int log = (int) Math.log10(maxdiff);
            newDistScale = Math.pow(10.0, 2 - log);
        }
        return newDistScale;
    }

    public static Track getTest1Track() {
        //        this.view3DPlotJPanel1.clear();
        Track t = new Track();
        double x = 0;
        double y = 0;
        double z = 0;
        PmRpy rpy = new PmRpy();
        t.setData(new ArrayList<>());
        for (; x < 2.0; x += 0.25) {
            t.getData().add(new TrackPoint(x, y, z, rpy));
        }
        for (; y < 2.0; y += 0.25) {
            t.getData().add(new TrackPoint(x, y, z, rpy));
        }
        for (; z < 2.0; z += 0.25) {
            t.getData().add(new TrackPoint(x, y, z, rpy));
        }
        for (; y > 0.0; y -= 0.25) {
            t.getData().add(new TrackPoint(x, y, z, rpy));
        }
        for (; z < 6.0; z += 0.25) {
            rpy.y = 2.0 * Math.PI * (z - 2.0) / 3.0;
            x = 2.0 * Math.cos(rpy.y);
            y = 2.0 * Math.sin(rpy.y);
            t.getData().add(new TrackPoint(x, y, z, rpy));
        }
        t.cur_time_index = t.getData().size();
        return t;
    }

    private TrackUtils() {
        // Don't call me
    }

    public static Track toTrack(List<? extends PmPose> l) {
        if (null == l) {
            return null;
        }
        Track track = new Track();
        track.setData(new ArrayList<TrackPoint>());
        List<TrackPoint> data = track.getData();
        boolean pmErrorOccurred = false;
        for (PmPose pose : l) {
            TrackPoint tp = new TrackPoint();
            tp.x = pose.tran.x;
            tp.y = pose.tran.y;
            tp.z = pose.tran.z;
            if (!pmErrorOccurred) {
                try {
                    tp.setRpy(Posemath.toRpy(pose.rot));
                } catch (PmException ex) {
                    pmErrorOccurred = true;
                    Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            data.add(tp);
        }
        return track;
    }

    /**
     *
     * @param tracksList the value of tracksList
     */
    public static Stream<TrackPoint> allPoints(List<? extends List<? extends Track>> tracksList) {
        return tracksList
                .stream()
                .flatMap((List<? extends Track> x) -> x.stream())
                .map((Track x) -> x.getData())
                .flatMap((List<TrackPoint> x) -> x.stream());
    }

    /**
     *
     * @param stream the value of stream
     * @param mapper the value of mapper
     * @return the double
     */
    public static <T> double maxStream(Stream<T> stream, ToDoubleFunction<T> mapper) {
        return stream.mapToDouble(mapper).max().orElse(Double.NEGATIVE_INFINITY);
    }

    /**
     *
     * @param stream the value of stream
     * @param mapper the value of mapper
     * @return the double
     */
    public static <T> double minStream(Stream<T> stream, ToDoubleFunction<T> mapper) {
        return stream.mapToDouble(mapper).min().orElse(Double.POSITIVE_INFINITY);
    }

    static public Track readTrack(CsvParseOptions options, File f) {
        Track track = new Track();
        track.setData(new ArrayList<TrackPoint>());
        try (final BufferedReader br = new BufferedReader(new FileReader(f))) {
            // ignore header
            br.readLine();
            String line = null;
            while (null != (line = br.readLine())) {
                String[] tok = line.split(options.delim);
                TrackPoint tp = new TrackPoint();
                if (options.X_INDEX >= 0 && options.X_INDEX < tok.length) {
                    tp.x = options.DISTANCE_SCALE * Double.valueOf(tok[options.X_INDEX]);
                }
                if (options.Y_INDEX >= 0 && options.Y_INDEX < tok.length) {
                    tp.y = options.DISTANCE_SCALE * Double.valueOf(tok[options.Y_INDEX]);
                }
                if (options.Z_INDEX >= 0 && options.Z_INDEX < tok.length) {
                    tp.z = options.DISTANCE_SCALE * Double.valueOf(tok[options.Z_INDEX]);
                }
                if (options.ROLL_INDEX >= 0 && options.ROLL_INDEX < tok.length) {
                    tp.setRoll(Math.toRadians(Double.valueOf(tok[options.ROLL_INDEX])));
                }
                if (options.PITCH_INDEX >= 0 && options.PITCH_INDEX < tok.length) {
                    tp.setPitch(Math.toRadians(Double.valueOf(tok[options.PITCH_INDEX])));
                }
                if (options.YAW_INDEX >= 0 && options.YAW_INDEX < tok.length) {
                    tp.setYaw(Math.toRadians(Double.valueOf(tok[options.YAW_INDEX])));
                }
                track.getData().add(tp);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return track;
    }

}
