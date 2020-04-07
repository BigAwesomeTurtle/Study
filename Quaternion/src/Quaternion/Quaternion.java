package Quaternion;

import Vector.Vector;

import java.util.Objects;

public class Quaternion {
    private final double w;
    private final double x;
    private final double y;
    private final double z;

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternion(double w, Vector v) {
        this.w = w;
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    public Quaternion() {
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Quaternion multByNumber(double n) {
        return new Quaternion(w * n, x * n, y * n, z * n);
    }

    public Quaternion conjugation() {
        return new Quaternion(w, -x, -y, -z);
    }

    public Quaternion plus(Quaternion other) {
        return new Quaternion(w + other.w, x + other.x, y + other.y, z + other.z);
    }

    public Quaternion minus(Quaternion other) {
        return new Quaternion(w - other.w, x - other.x, y - other.y, z - other.z);
    }

    public Quaternion times(Quaternion other) {
        return new Quaternion(w * other.w - x * other.x - y * other.y - z * other.z,
                w * other.x + x * other.w + y * other.z - z * other.y,
                w * other.y - x * other.z + y * other.w + z * other.x,
                w * other.z + x * other.y - y * other.x + z * other.w);
    }

    public double abs() {
        return Math.sqrt(w * w + x * x + y * y + z * z);
    }

    public Quaternion divideOnNumber(double n) {
        if (n == 0) throw new ArithmeticException("Division by zero");
        return new Quaternion(w / n, x / n, y / n, z / n);
    }

    public Quaternion inverse() {
        return this.conjugation().divideOnNumber(this.abs() * this.abs());
    }

    public Quaternion divide(Quaternion other) {
        return this.times(other.inverse());
    }

    public Quaternion rational() {
        return this.divideOnNumber(this.abs());
    }

    public double getScalarPart() {
        return w;
    }

    public Vector getVectorPart() {
        return new Vector(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj != null && obj instanceof Quaternion) {
            Quaternion other = (Quaternion) obj;
            return w == other.w && x == other.x && y == other.y && z == other.z;
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(w, x, y, z);
    }

    @Override
    public String toString() {
        return "" + w + ((x >= 0) ? "+" : "") + x + "i" +
                ((y >= 0) ? "+" : "") + y + "j" + ((z >= 0) ? "+" : "") + z + "k";
    }
}