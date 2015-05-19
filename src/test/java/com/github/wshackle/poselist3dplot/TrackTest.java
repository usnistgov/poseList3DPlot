/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.wshackle.poselist3dplot;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov>}
 */
public class TrackTest {
    
    public TrackTest() {
    }

    /**
     * Test of getData method, of class Track.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Track instance = new Track();
        List<TrackPoint> expResult = null;
        List<TrackPoint> result = instance.getData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setData method, of class Track.
     */
    @Test
    public void testSetData() {
        System.out.println("setData");
        List<TrackPoint> data = null;
        Track instance = new Track();
        instance.setData(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
