package QuaternionUtil;

import org.junit.Test;
import Vector.Vector;
import Quaternion.Quaternion;

import static org.junit.Assert.assertEquals;

public class Tests {
    @Test
    public void getQuaternion() {
        assertEquals(new Quaternion(1, 0, 0, 0), QuaternionUtil.getQuaternion(0, 2, 3, 4));
        final Quaternion exp = new Quaternion(0, 2.5, -53, 14);
        final Quaternion res = QuaternionUtil.getQuaternion(Math.PI, new Vector(2.5, -53, 14));
        assertEquals(exp.getScalarPart(), res.getScalarPart(), 1e-6);
        assertEquals(exp.getVectorPart().getX(), res.getVectorPart().getX(), 1e-6);
        assertEquals(exp.getVectorPart().getY(), res.getVectorPart().getY(), 1e-6);
        assertEquals(exp.getVectorPart().getZ(), res.getVectorPart().getZ(), 1e-6);
        assertEquals(new Quaternion(), QuaternionUtil.getQuaternion());
    }

    @Test
    public void getAxis() {
        assertEquals(new Quaternion(1, 2, 3, 4).getVectorPart(), QuaternionUtil.getAxis(new Quaternion(1, 2, 3, 4)));
    }

    @Test
    public void getAngle() {
        assertEquals(new Quaternion(1, 2, 3, 4).getScalarPart(), QuaternionUtil.getAngle(new Quaternion(1, 2, 3, 4)), 1e-10);
    }
}
