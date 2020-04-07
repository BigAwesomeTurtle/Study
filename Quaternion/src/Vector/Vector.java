package Vector;

import java.util.Objects;

public class Vector {
    private final double x;
    private final double y;
    private final double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj != null && obj instanceof Vector) {
            Vector other = (Vector) obj;
            return x == other.x && y == other.y && z == other.z;
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "" + x + "i" +
                ((y >= 0) ? "+" : "") + y + "j" + ((z >= 0) ? "+" : "") + z + "k";
    }
}
