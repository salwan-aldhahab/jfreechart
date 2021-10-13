/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * -------------------------------
 * VectorSeriesCollectionTest.java
 * -------------------------------
 * (C) Copyright 2007-2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link VectorSeriesCollection} class.
 */
public class VectorSeriesCollectionTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        VectorSeries<String> s1 = new VectorSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection<String> c1 = new VectorSeriesCollection<>();
        c1.addSeries(s1);
        VectorSeries<String> s2 = new VectorSeries<>("Series");
        s2.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection<String> c2 = new VectorSeriesCollection<>();
        c2.addSeries(s2);
        assertTrue(c1.equals(c2));
        assertTrue(c2.equals(c1));

        c1.addSeries(new VectorSeries<String>("Empty Series"));
        assertFalse(c1.equals(c2));

        c2.addSeries(new VectorSeries<String>("Empty Series"));
        assertTrue(c1.equals(c2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        VectorSeries<String> s1 = new VectorSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection<String> c1 = new VectorSeriesCollection<>();
        c1.addSeries(s1);
        VectorSeriesCollection<String> c2 = CloneUtils.clone(c1);
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check independence
        s1.setDescription("XYZ");
        assertFalse(c1.equals(c2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        VectorSeriesCollection<String> d1 = new VectorSeriesCollection<>();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        VectorSeries<String> s1 = new VectorSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection<String> c1 = new VectorSeriesCollection<>();
        c1.addSeries(s1);
        VectorSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * Some checks for the removeSeries() method.
     */
    @Test
    public void testRemoveSeries() {
        VectorSeries<String> s1 = new VectorSeries<>("S1");
        VectorSeries<String> s2 = new VectorSeries<>("S2");
        VectorSeriesCollection<String> vsc = new VectorSeriesCollection<>();
        vsc.addSeries(s1);
        vsc.addSeries(s2);
        assertEquals(2, vsc.getSeriesCount());
        boolean b = vsc.removeSeries(s1);
        assertTrue(b);
        assertEquals(1, vsc.getSeriesCount());
        assertEquals("S2", vsc.getSeriesKey(0));
        b = vsc.removeSeries(new VectorSeries<String>("NotInDataset"));
        assertFalse(b);
        assertEquals(1, vsc.getSeriesCount());
        b = vsc.removeSeries(s2);
        assertTrue(b);
        assertEquals(0, vsc.getSeriesCount());
    }

}
