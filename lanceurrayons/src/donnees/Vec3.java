package donnees;

public class Vec3 extends Triplet {

    public Vec3(double x, double y, double z) {
        super(x, y, z);
    }

    public Vec3 add(Vec3 v2) {
        return new Vec3(this.getX() + v2.getX(), this.getY() + v2.getY(), this.getZ() + v2.getZ());
    }

    public Point add(Point p2) {
        return new Point(this.getX() + p2.getX(), this.getY() + p2.getY(), this.getZ() + p2.getZ());
    }

    public Vec3 sub(Vec3 v2) {
        return new Vec3(this.getX() - v2.getX(), this.getY() - v2.getY(), this.getZ() - v2.getZ());
    }

    public Vec3 mul(double d) {
        return new Vec3(this.getX() * d, this.getY() * d, this.getZ() * d);
    }

    public double dot(Vec3 v2) {
        return this.getX() * v2.getX() + this.getY() * v2.getY() + this.getZ() * v2.getZ();
    }

    public Vec3 cross(Vec3 v2) {
        return new Vec3(getY() * v2.getZ() - getZ() * v2.getY(), getZ() * v2.getX() - getX() * v2.getZ(),
                getX() * v2.getY() - getY() * v2.getX());
    }

    public double length() {
        return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
    }

    public Vec3 opposite() {
        return this.mul(-1);
    }

    public Vec3 norm() {
        double fac = 1 / this.length();
        return new Vec3(getX() * fac, getY() * fac, getZ() * fac);
    }

    public String toString() {
        return "[" + getX() + " " + getY() + " " + getZ() + "]";
    }
}
