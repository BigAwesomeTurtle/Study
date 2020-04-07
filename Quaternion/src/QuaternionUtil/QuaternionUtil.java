package QuaternionUtil;

import Quaternion.Quaternion;
import Vector.Vector;

public final class QuaternionUtil {
    public static Quaternion getQuaternion(double w, Vector v) {
        return getQuaternion(w, v.getX(), v.getY(), v.getZ());
    }

    public static Quaternion getQuaternion(double w, double x, double y, double z) {
        return new Quaternion(Math.cos(w / 2), Math.sin(w / 2) * x, Math.sin(w / 2) * y, Math.sin(w / 2) * z);
    }

    public static Quaternion getQuaternion() {
        return new Quaternion();
    }

    public static Vector getAxis(Quaternion a) {
        return a.getVectorPart();
    }

    public static double getAngle(Quaternion a) {
        return a.getScalarPart();
    }
}
