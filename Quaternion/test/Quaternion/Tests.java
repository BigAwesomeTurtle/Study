package Quaternion;

import Vector.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {
    @Test
    public void multByNumber() {
        assertEquals(new Quaternion(4, 4, 4, 4), new Quaternion(1, 1, 1, 1).multByNumber(4));
        assertEquals(new Quaternion(12.5, 20.5, 10, 6.5), new Quaternion(2.5, 4.1, 2, 1.3).multByNumber(5));
        assertEquals(new Quaternion(14, -36.75, 5.25, 0), new Quaternion(-4, 10.5, -1.5, 0).multByNumber(-3.5));
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(100, -23.14, -0.09009, 111).multByNumber(0));
        assertEquals(new Quaternion(0, 0, 32.5, -6.5), new Quaternion(0, 0, 10, -2).multByNumber(3.25));
    }

    @Test
    public void conjugation() {
        assertEquals(new Quaternion(1, -1, -1, -1), new Quaternion(1, 1, 1, 1).conjugation());
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(0, 0, 0, 0).conjugation());
        assertEquals(new Quaternion(10, 3, 11.0, -0.11), new Quaternion(10, -3, -11.0, 0.11).conjugation());
        assertEquals(new Quaternion(0, -10, 0, 12.1), new Quaternion(0, 10, 0, -12.1).conjugation());
        assertEquals(new Quaternion(-12.1, -2, -3.01, 12), new Quaternion(-12.1, 2, 3.01, -12).conjugation());
    }

    @Test
    public void plus() {
        assertEquals(new Quaternion(2, 2, 2, 2), new Quaternion(1, 1, 1, 1).plus(new Quaternion(1, 1, 1, 1)));
        assertEquals(new Quaternion(10, 5.5, -10, 2.21), new Quaternion(10, 0, -10, 0).plus(new Quaternion(0, 5.5, 0, 2.21)));
        final Quaternion resQ = new Quaternion(5.2, -6, 2, 12.3).plus(new Quaternion(-5.5, 21, -2.3, -1));
        final Quaternion expQ = new Quaternion(-0.3, 15, -0.3, 11.3);
        assertEquals(expQ.getScalarPart(), resQ.getScalarPart(), 1e-10);
        assertEquals(expQ.getVectorPart().getX(), resQ.getVectorPart().getX(), 1e-10);
        assertEquals(expQ.getVectorPart().getY(), resQ.getVectorPart().getY(), 1e-10);
        assertEquals(expQ.getVectorPart().getZ(), resQ.getVectorPart().getZ(), 1e-10);
        assertEquals(new Quaternion(23.0, -0.0021, 1234, 1), new Quaternion(0, 0, 0, 0).plus(new Quaternion(23.0, -0.0021, 1234, 1)));
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(2, 0.1, -1, 1).plus(new Quaternion(-2, -0.1, 1, -1)));
    }

    @Test
    public void minus() {
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(1, 1, 1, 1).minus(new Quaternion(1, 1, 1, 1)));
        assertEquals(new Quaternion(10, -5.5, -10, -2.21), new Quaternion(10, 0, -10, 0).minus(new Quaternion(0, 5.5, 0, 2.21)));
        final Quaternion resQ = new Quaternion(5.2, -6, 2, 12.3).minus(new Quaternion(-5.5, 21, -2.3, -1));
        final Quaternion expQ = new Quaternion(10.7, -27, 4.3, 13.3);
        assertEquals(expQ.getScalarPart(), resQ.getScalarPart(), 1e-10);
        assertEquals(expQ.getVectorPart().getX(), resQ.getVectorPart().getX(), 1e-10);
        assertEquals(expQ.getVectorPart().getY(), resQ.getVectorPart().getY(), 1e-10);
        assertEquals(expQ.getVectorPart().getZ(), resQ.getVectorPart().getZ(), 1e-10);
        assertEquals(new Quaternion(-23.0, 0.0021, -1234, -1), new Quaternion(0, 0, 0, 0).minus(new Quaternion(23.0, -0.0021, 1234, 1)));
        assertEquals(new Quaternion(4, 0.2, -2, 2), new Quaternion(2, 0.1, -1, 1).minus(new Quaternion(-2, -0.1, 1, -1)));
    }

    @Test
    public void times() {
        assertEquals(new Quaternion(-2, 2, 2, 2), new Quaternion(1, 1, 1, 1).times(new Quaternion(1, 1, 1, 1)));
        assertEquals(new Quaternion(0, 32.9, 0, 77.1), new Quaternion(10, 0, -10, 0).times(new Quaternion(0, 5.5, 0, 2.21)));
        final Quaternion resQ = new Quaternion(5.2, -6, 2, 12.3).times(new Quaternion(-5.5, 21, -2.3, -1));
        final Quaternion expQ = new Quaternion(114.3, 168.49, 229.34, -101.05);
        assertEquals(expQ.getScalarPart(), resQ.getScalarPart(), 1e-10);
        assertEquals(expQ.getVectorPart().getX(), resQ.getVectorPart().getX(), 1e-10);
        assertEquals(expQ.getVectorPart().getY(), resQ.getVectorPart().getY(), 1e-10);
        assertEquals(expQ.getVectorPart().getZ(), resQ.getVectorPart().getZ(), 1e-10);
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(0, 0, 0, 0).times(new Quaternion(23.0, -0.0021, 1234, 1)));
    }

    @Test
    public void abs() {
        assertEquals(2, new Quaternion(1, 1, 1, 1).abs(), 1e-10);
        assertEquals(0, new Quaternion(0, 0, 0, 0).abs(), 1e-10);
        assertEquals(15.166149808, new Quaternion(10, -3, -11.0, 0.11).abs(), 1e-10);
        assertEquals(15.6974520225, new Quaternion(0, 10, 0, -12.1).abs(), 1e-10);
        assertEquals(17.4203932217, new Quaternion(-12.1, 2, 3.01, -12).abs(), 1e-10);
    }

    @Test
    public void divideOnNumber() {
        assertEquals(new Quaternion(0.25, 0.25, 0.25, 0.25), new Quaternion(1, 1, 1, 1).divideOnNumber(4));
        assertEquals(new Quaternion(2, 1, 4, 1.3), new Quaternion(10, 5, 20, 6.5).divideOnNumber(5));
        assertEquals(new Quaternion(1, -2.625, 0.375, 0), new Quaternion(-4, 10.5, -1.5, 0).divideOnNumber(-4));
        assertEquals(new Quaternion(0, 0, 1, -0.2), new Quaternion(0, 0, 10, -2).divideOnNumber(10));
        try {
            new Quaternion(100, -23.14, -0.09009, 111).divideOnNumber(0);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

    }

    @Test
    public void inverse() {
        assertEquals(new Quaternion(0.25, -0.25, -0.25, -0.25), new Quaternion(1, 1, 1, 1).inverse());
        final Quaternion resQ = new Quaternion(10, -3, -11.0, 0.11).inverse();
        final Quaternion expQ = new Quaternion(0.043476, 0.0130428, 0.0478236, -0.000478236);
        assertEquals(expQ.getScalarPart(), resQ.getScalarPart(), 1e-7);
        assertEquals(expQ.getVectorPart().getX(), resQ.getVectorPart().getX(), 1e-7);
        assertEquals(expQ.getVectorPart().getY(), resQ.getVectorPart().getY(), 1e-7);
        assertEquals(expQ.getVectorPart().getZ(), resQ.getVectorPart().getZ(), 1e-7);
        assertEquals(new Quaternion(0, -0.1, 0, 0), new Quaternion(0, 10, 0, 0).inverse());
        try {
            new Quaternion(0, 0, 0, 0).inverse();
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    @Test
    public void divide() {
        assertEquals(new Quaternion(1, 0, 0, 0), new Quaternion(1, 1, 1, 1).divide(new Quaternion(1, 1, 1, 1)));
        assertEquals(new Quaternion(0, 5, 0, -5), new Quaternion(10, 0, -10, 0).divide(new Quaternion(0, 0, 0, 2)));
        final Quaternion resQ = new Quaternion(5.2, -6, 2, 12.3).divide(new Quaternion(-5.5, 21, -2.3, -1));
        final Quaternion expQ = new Quaternion(-0.359133, -0.214621, -0.526323, -0.0717222);
        assertEquals(expQ.getScalarPart(), resQ.getScalarPart(), 1e-6);
        assertEquals(expQ.getVectorPart().getX(), resQ.getVectorPart().getX(), 1e-6);
        assertEquals(expQ.getVectorPart().getY(), resQ.getVectorPart().getY(), 1e-6);
        assertEquals(expQ.getVectorPart().getZ(), resQ.getVectorPart().getZ(), 1e-6);
        assertEquals(new Quaternion(0, 0, 0, 0), new Quaternion(0, 0, 0, 0).divide(new Quaternion(23.0, -0.0021, 1234, 1)));
    }

    @Test
    public void rational() {
        assertEquals(new Quaternion(0.5, 0.5, 0.5, 0.5), new Quaternion(1, 1, 1, 1).rational());
        try {
            new Quaternion(0, 0, 0, 0).rational();
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
        final Quaternion exp = new Quaternion(0.65795169496, -0.19738550848, -0.72374687176, 0.06579517016);
        final Quaternion res = new Quaternion(10, -3, -11.0, 1).rational();
        assertEquals(exp.getScalarPart(), res.getScalarPart(), 1e-7);
        assertEquals(exp.getVectorPart().getX(), res.getVectorPart().getX(), 1e-7);
        assertEquals(exp.getVectorPart().getY(), res.getVectorPart().getY(), 1e-7);
        assertEquals(exp.getVectorPart().getZ(), res.getVectorPart().getZ(), 1e-7);
        assertEquals(new Quaternion(0, 0.6, 0, 0.8), new Quaternion(0, 6, 0, 8).rational());
    }

    @Test
    public void equals() {
        assertTrue(new Quaternion(1, 1, 1, 1).equals(new Quaternion(1, new Vector(1, 1, 1))));
        assertTrue(new Quaternion(0, 0, 0, 0).equals(new Quaternion()));
        assertFalse(new Quaternion(1, 2, 3, 4).equals(new Quaternion(1, 2, 4, 4)));
    }

    @Test
    public void toStringTest() {
        assertEquals("1.0+1.0i+1.0j+1.0k", new Quaternion(1, 1, 1, 1).toString());
        assertEquals("-2.0-1.1i-1000.0j+12.0k", new Quaternion(-2, -1.1, -1000, 12).toString());
        assertEquals("0.0+0.0i+0.0j+0.0k", new Quaternion(0, 0, 0, 0).toString());
        assertEquals("0.0+0.0i+0.0j+0.0k", new Quaternion().toString());
    }
}

